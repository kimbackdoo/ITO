package kr.co.metasoft.ito.api.common.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.dto.DataUploadParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.PersonSkillEntity;
import kr.co.metasoft.ito.api.common.mapper.CodeMapper;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import kr.co.metasoft.ito.api.common.repository.PersonSkillRepository;
import kr.co.metasoft.ito.common.util.PageRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataUploadService {

    @Autowired
    private PersonSkillRepository personSkillRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CodeMapper codeMapper;

    //엑셀 항목 값
    String[] personColumnKeyArray = {"이름","휴대폰 번호","직업종류","투입여부","필수 자격증 여부","보유 스킬","경력","급여","투입 가능 시작일"
            ,"우편번호","주소","상세주소","이메일","웹사이트","최종학력","생일","생성일","수정일"};


    @Transactional
    public Map<String, Object> uploadCMDBExcelFile(DataUploadParamDto dataUploadParamDto) {

        String returnVal = "FAIL";
        String returnMsg = "";
        Map<String, Object> returnMap = new HashMap<>();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRowSize(10000000);
        CodeParamDto codeParamDto = new CodeParamDto();

        codeParamDto.setIdStartLike("001");
        List<String> sort = new ArrayList<>();
        sort.add("id,asc");
        pageRequest.setSort(sort);
        HashMap<String, String> jobMap = new HashMap<>();

        for(CodeEntity job : codeMapper.selectCodeList(codeParamDto, pageRequest)) {
            System.out.println("Key: " + job.getName() + ", Value: " + job.getValue());
            jobMap.put(job.getName(), job.getId());
        }

        codeParamDto.setIdStartLike("004");
        HashMap<String, String> skillMap = new HashMap<>();

        String jobName = "";
        for(CodeEntity skill : codeMapper.selectCodeList(codeParamDto, pageRequest)) {
            if(jobMap.containsKey(skill.getName())) {
                jobName = skill.getName();
            }else {
                System.out.println("Key: " + jobName+skill.getName() + ", Value: " + skill.getValue());
                skillMap.put(jobName+skill.getName(), skill.getId());
            }
        }
        //위에 cmdKey값들을 columnkeylist로 넣는다.
        List<String> columnKeyList = new ArrayList<>();
        Collections.addAll(columnKeyList, personColumnKeyArray);

        //excelFile 변수 값으로 가져온다.
        MultipartFile excelFile = dataUploadParamDto.getFile();

        // excelFile 값 확인
        if(excelFile != null && excelFile.getSize() > 0 && excelFile.getOriginalFilename() != null) {
            if(excelFile.getOriginalFilename().endsWith(".xlsx") || excelFile.getOriginalFilename().endsWith(".XLSX")) {

                //여기서 파일을 제대로 못 붙러오는듯한데....
                try (
                    OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
                    XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
                    ) {

                    // 첫번째 시트 불러오기
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    //한개의 시트에 몇개의 로우가 있는지 체크
                    XSSFRow row = sheet.getRow(0);
                    HashMap<String,Integer> columnKeyNumberList = new HashMap<>();

                    System.out.println("row ");
                    System.out.println(" row.getPysicalNumberCells");
                    System.out.println(" row 값 " + row);
                    System.out.println(" row.getPysicalNumberCells 값  "+ row.getPhysicalNumberOfCells());

                    //row.getPysicalNumberCells() 한개의 로우마다 몇개의 cell 수
                    int cols= row.getPhysicalNumberOfCells();
                    for(int i = 0; i < cols; i++) {
                        XSSFCell cell = row.getCell(i);
                        cell.setCellType(Cell.CELL_TYPE_STRING);

                        if(columnKeyList.contains(cell.getStringCellValue())) {
                            columnKeyNumberList.put(cell.getStringCellValue(), i);
                        }
                    }


                    System.out.println("엑셀 양식 확인 검사");
                    // 엑셀 양식 확인
                    if(columnKeyNumberList.size() == columnKeyList.size()) {


                        System.out.println("엑셀 양식 확인 통과");

                        PersonEntity dataUploadEntity = null;
                        List<PersonSkillEntity> personSkillEntityList = null;

                        for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
                            dataUploadEntity = new PersonEntity();
                            personSkillEntityList = new ArrayList<>();
                            row = sheet.getRow(i);

                            System.out.println(" sheet.getLastRowNum 에서 for문 검사중 문제  : " + i);

                            // 행이 존재하기 않으면 패스
                            if(null == row) {
                                continue;
                            }

                            // (이름)
                            XSSFCell cell = row.getCell(columnKeyNumberList.get("이름"));

                            if(null != cell) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                dataUploadEntity.setName(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "이름");

                            // (휴대폰 번호)
                            cell = row.getCell(columnKeyNumberList.get("휴대폰 번호"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setPhoneNumber(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "휴대폰 번호");

                            // (직업 종류)
                            cell = row.getCell(columnKeyNumberList.get("직업종류"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                if(jobMap.containsKey(cell.getStringCellValue())) {
                                    System.out.println(jobMap.get(cell.getStringCellValue()));
                                    dataUploadEntity.setJobType(jobMap.get(cell.getStringCellValue()));
                                }else {
                                    System.out.println("없다.");
                                    dataUploadEntity.setJobType(null);
                                }
                            }
                            System.out.println("breakPoint :  " + "직업종류");

                            // (투입여부)
                            cell = row.getCell(columnKeyNumberList.get("투입여부"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setInputStatus(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "투입여부");


                            // (필수 자격증 여부)
                            cell = row.getCell(columnKeyNumberList.get("필수 자격증 여부"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setCertificateStatus(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "필수 자격증 여부");

                            // (경력)
                            cell = row.getCell(columnKeyNumberList.get("경력"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setCareer(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "경력");


                            // (급여)
                            cell = row.getCell(columnKeyNumberList.get("급여"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                Long pay = Long.parseLong(cell.getStringCellValue());
                                dataUploadEntity.setPay(pay);
                            }
                            System.out.println("breakPoint :  " + "급여");


                            // (투입가능 시작 가능일) - localDate 형 변환
                            cell = row.getCell(columnKeyNumberList.get("투입 가능 시작일"));
//                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {

                                String s = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                                System.out.println(s);
                                LocalDate workDay = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
                                dataUploadEntity.setWorkableDay(workDay);
                            }
                            System.out.println("breakPoint :  " + "투입 가능 시작일");


                            // (우편번호)
                            cell = row.getCell(columnKeyNumberList.get("우편번호"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
//                              Long pc = cell.getStringCellValue().isEmpty() || cell.getStringCellValue() == null ? null : Long.parseLong(cell.getStringCellValue());
                                dataUploadEntity.setPostcode(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "우편번호");


                            // (주소)
                            cell = row.getCell(columnKeyNumberList.get("주소"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setAddress(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "주소");


                            // (상세주소)
                            cell = row.getCell(columnKeyNumberList.get("상세주소"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setDetailAddress(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "상세주소");


                            // (이메일)
                            cell = row.getCell(columnKeyNumberList.get("이메일"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEmail(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "이메일");


                            // (웹사이트)
                            cell = row.getCell(columnKeyNumberList.get("웹사이트"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEmail(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "웹사이트");


                            // (최종학력)
                            cell = row.getCell(columnKeyNumberList.get("최종학력"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEducation(cell.getStringCellValue());
                            }
                            System.out.println("breakPoint :  " + "최종학력");


                            // (생일)
                            cell = row.getCell(columnKeyNumberList.get("생일"));
//                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                String s = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                                System.out.println(s);
                                LocalDate birth = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
                                dataUploadEntity.setBirthDate(birth);

                            }
                            System.out.println("breakPoint :  " + "생일");

                            PersonEntity entity = personRepository.save(dataUploadEntity);

                            // (보유 스킬)
                            cell = row.getCell(columnKeyNumberList.get("보유 스킬"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                for(String str : cell.getStringCellValue().split(",")) {
                                    if(skillMap.containsKey(row.getCell(columnKeyNumberList.get("직업종류")) + str.trim())) {
                                        System.out.println(skillMap.get(row.getCell(columnKeyNumberList.get("직업종류")) + str.trim()));
                                        personSkillEntityList.add(PersonSkillEntity.builder()
                                            .skill(skillMap.get(row.getCell(columnKeyNumberList.get("직업종류")) + str.trim()))
                                            .personId(entity.getId())
                                            .build()
                                        );
                                    }
                                }
                            }
                            System.out.println("breakPoint :  " + "보유 스킬");

                            personSkillRepository.saveAll(personSkillEntityList);

                        }


                        returnVal = "SUCCESS";
                        returnMsg = "업로드에 성공했습니다.";
                    } else {
                        returnVal = "FAIL";
                        returnMsg = "잘못된 엑셀 양식입니다.";
                    }
                } catch (Exception e) {
                    returnVal = "FAIL";
                    returnMsg = "업로드 중에 오류가 발생했습니다.";
                }
            } else {
                returnVal = "FAIL";
                returnMsg = "잘못된 파일 형식입니다.";
            }
        } else {
            returnVal = "FAIL";
            returnMsg = "파일의 데이터가 존재하지 않습니다.";
        }

        returnMap.put("returnVal", returnVal);
        returnMap.put("returnMsg", returnMsg);
        return returnMap;
    }

    @Transactional
    public Map<String, Object> uploadITOExcelFile(DataUploadParamDto dataUploadParamDto) {

        String returnVal = "FAIL";
        String returnMsg = "";
        Map<String, Object> returnMap = new HashMap<>();

        //excelFile 변수 값으로 가져온다.
        MultipartFile excelFile = dataUploadParamDto.getFile();

        CodeParamDto codeParamDto = new CodeParamDto();
        codeParamDto.setIdStartLike("007");
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRowSize(10000000);
        List<String> sort = new ArrayList<>();
        sort.add("id,asc");

        List<CodeEntity> addressList = codeMapper.selectCodeList(codeParamDto, pageRequest);
        HashMap<String, String> addressMap = new HashMap<>();
        for(int i = 0; i < addressList.size(); i++) {
            CodeEntity entity = addressList.get(i);
            if(entity.getId().equals("007")) {
                continue;
            }
            addressMap.put(entity.getName().substring(0,2), entity.getId());
        }

        // excelFile 값 확인
        if(excelFile != null && excelFile.getSize() > 0 && excelFile.getOriginalFilename() != null) {
            if(excelFile.getOriginalFilename().endsWith(".txt") || excelFile.getOriginalFilename().endsWith(".TXT")) {
                try (
                        Reader filereader = new InputStreamReader(excelFile.getInputStream());
                        BufferedReader bufReader = new BufferedReader(filereader);
                    ) {
                    String line = "";
                    PersonEntity person = new PersonEntity();
                    List<PersonEntity> personList = new ArrayList<>();
                    String [] lineArray = null;
                    List<String> jobTypeList = new ArrayList<>();

                    while((line = bufReader.readLine()) != null){
                        if(line.trim().isEmpty()) {
                            continue;
                        }
                        if(line.trim().indexOf("*") == 0) {
                            jobTypeList.add(line.replaceAll("[*]", "").trim());
                        }else {
                            if(line.indexOf(":") == -1) {
                                continue;
                            }
                            lineArray = line.split(",");
                            person = new PersonEntity();
                            person.setName(lineArray[0].split(":")[0].trim());
                            line = line.replaceAll("[\\w|가-힣| ]*:", "").trim();
                            String regex = "[0-9]{2}세\\(([0-9]{2})년\\)";
                            Pattern pat = Pattern.compile(regex);
                            Matcher match = pat.matcher(line);
                            if(match.find()) {
                                int birth = Integer.parseInt(match.group(1));
                                if(birth >= 0 && birth <= (LocalDate.now().getYear() % 100)) {
                                    person.setBirthDate(LocalDate.parse("20"+birth+"-01-01"));
                                }else {
                                    person.setBirthDate(LocalDate.parse("19"+birth+"-01-01"));
                                }
                                line = line.replace(match.group(), "");
                            }
                            regex = "([0-9]{3})(-){0,1}([0-9]{3,4})(-){0,1}([0-9]{4})";
                            pat = Pattern.compile(regex);
                            match = pat.matcher(line);
                            if (match.find()) {
                                 person.setPhoneNumber(match.group());
                                 line = line.replace(match.group(), "");
                            }
                            regex = "(\\w*{1,50})@(\\w*{1,50})\\.([\\w|.]*{1,50})";
                            pat = Pattern.compile(regex);
                            match = pat.matcher(line);
                            if (match.find()) {
                                person.setEmail(match.group());
                                line = line.replace(match.group(), "");
                            }
                            regex = "([가-힣]{2,10}[시|도]{0,1})( {0,2})([가-힣]*[시|구])( {0,2})([가-힣]*[0-9]*[동|구] {0,2}[가-힣|\\w]*){0,1}";
                            pat = Pattern.compile(regex);
                            match = pat.matcher(line);
                            if (match.find()) {
                                person.setAddress(match.group());
                                line = line.replace(match.group(), "");
                            }
                            regex = "([0-9]{1,4})~{0,1}([0-9]{0,4})만원";
                            pat = Pattern.compile(regex);
                            match = pat.matcher(line);
                            if (match.find()) {
                                String str = match.group(1);
                                person.setPay(Long.parseLong(str));
                                line = line.replace(match.group(), "");
                            }
                            regex = ", {0,10}([1-9]{0,1}[0-9]{1}\\.{0,1}[0-9]{0,2})년{0,1}[^-]";
                            pat = Pattern.compile(regex);
                            match = pat.matcher(line);
                            if (match.find()) {
                                String str = match.group(1);
                                if(!str.startsWith("0")) {
                                    person.setCareer(str);
                                    line = line.replace(match.group(), "");
                                }
                            }
                            line = line.replaceAll("[,|거주함\\.|거주함,]", "").trim();
                            log.info(line);
                            personList.add(person);
                        }
                    }
                    for(int i = 0; i < jobTypeList.size(); i++) {
                        log.info(jobTypeList.get(i));
                    }
                    for(int i = 0; i < personList.size(); i++) {
                        log.info(personList.get(i).toString());
                    }
                    returnVal = "SUCCESS";
                    returnMsg = "업로드에 성공했습니다.";
                } catch (Exception e) {
                    e.printStackTrace();
                    returnVal = "FAIL";
                    returnMsg = "업로드 중에 오류가 발생했습니다.";
                }
            } else {
                returnVal = "FAIL";
                returnMsg = "잘못된 파일 형식입니다.";
            }
        } else {
            returnVal = "FAIL";
            returnMsg = "파일의 데이터가 존재하지 않습니다.";
        }
        returnMap.put("returnVal", returnVal);
        returnMap.put("returnMsg", returnMsg);
        return returnMap;
    }


}
