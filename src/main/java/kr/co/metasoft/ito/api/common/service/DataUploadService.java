package kr.co.metasoft.ito.api.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import kr.co.metasoft.ito.api.common.dto.DataUploadParamDto;
import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.mapper.DataUploadMapper;
import kr.co.metasoft.ito.api.common.mapper.PersonMapper;
import kr.co.metasoft.ito.api.common.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataUploadService {

    @Autowired
    private PersonRepository dataUploadCMDBRepository;


    @Autowired
    private DataUploadMapper dataUploadMapper;


    //엑셀 항목 값
    String[] personColumnKeyArray = {"이름","휴대폰 번호","직업종류","투입여부","필수 자격증 여부","보유 스킬","경력","급여","투입 가능 시작일"
            ,"우편번호","주소","상세주소","이메일","웹사이트","최종학력","생일","생성일","수정일"};


    @Transactional
    public Map<String, Object> uploadCMDBExcelFile(DataUploadParamDto dataUploadParamDto) {

        String returnVal = "FAIL";
        String returnMsg = "";
        Map<String, Object> returnMap = new HashMap<>();

        //위에 cmdKey값들을 columnkeylist로 넣는다.
        List<String> columnKeyList = new ArrayList<>();
        Collections.addAll(columnKeyList, personColumnKeyArray);

        //excelFile 변수 값으로 가져온다.
        MultipartFile excelFile = dataUploadParamDto.getFile();

        //execel 파일 이름 가져오기
        String excelOriginFileName = excelFile.getOriginalFilename();

        // order 순서인가.... 모르겠다...
        Long uploadFileOrder = Long.valueOf(0);

        // 기존 테이블에 있는 다음 행의 index( = id값)을 더해준다
//        uploadFileOrder += dataUploadMapper.selectUploadFileOrder();

        LocalDate uploadDat = null;

        SimpleDateFormat dateFormat= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        DateTimeFormatter dateFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Date day1 = null;
        Date day2 = new Date();
        try {
            //param에서 넘겨온 Month 값
            String yearMonth = dataUploadParamDto.getUploadReferenceMonth();

            Calendar cal = Calendar.getInstance();

            cal.set(Integer.valueOf(yearMonth.substring(0, 4)), Integer.valueOf(yearMonth.substring(5, 7)) - 1, 1); //월은 -1해줘야 해당월로 인식

            day1 = dateFormat.parse(dataUploadParamDto.getUploadReferenceMonth() + "-" + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compare = day1.compareTo( day2 );

        try {

            if ( compare > 0 )
            {
                uploadDat = day2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            else if ( compare < 0 )
            {
                uploadDat = day1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            else
            {
                uploadDat = day1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            }

        } catch(Exception ex) {
            log.error(ex.getMessage());
        }


        // excelFile 값 확인
        if(excelFile != null && excelFile.getSize() > 0 && excelFile.getOriginalFilename() != null) {
            if(excelFile.getOriginalFilename().endsWith(".xlsx") || excelFile.getOriginalFilename().endsWith(".XLSX")) {
                List<PersonEntity> list = new ArrayList<>();

                try (
                    OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
                    XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
                ) {
                    // 첫번째 시트 불러오기
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    XSSFRow row = sheet.getRow(0);
                    HashMap<String,Integer> columnKeyNumberList = new HashMap<>();

                    for(int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                        XSSFCell cell = row.getCell(i);
                        cell.setCellType(Cell.CELL_TYPE_STRING);

                        if(columnKeyList.contains(cell.getStringCellValue())) {
                            columnKeyNumberList.put(cell.getStringCellValue(), i);
                        }
                    }

                    // 엑셀 양식 확인
                    if(columnKeyNumberList.size() == columnKeyList.size()) {


                        for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
                            PersonEntity dataUploadEntity = new PersonEntity();
                            dataUploadEntity.setId(uploadFileOrder);   //id값만 있으면 된다 더 필요하다 싶으면 추가
//                          dataUploadEntity.setOrginFileNm(excelOriginFileName);
//                          dataUploadEntity.setFileRowNum(Long.valueOf(i));
                            row = sheet.getRow(i);

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

                            // (휴대폰 번호)
                            cell = row.getCell(columnKeyNumberList.get("휴대폰 번호"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setPhoneNumber(cell.getStringCellValue());
                            }

                            // (직업 종류)
                            cell = row.getCell(columnKeyNumberList.get("직업종류"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setJobType(cell.getStringCellValue());
                            }

                            // (투입여부)
                            cell = row.getCell(columnKeyNumberList.get("투입여부"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setInputStatus(cell.getStringCellValue());
                            }

                            // (필수 자격증 여부)
                            cell = row.getCell(columnKeyNumberList.get("필수 자격증 여부"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setCertificateStatus(cell.getStringCellValue());
                            }

                            // (보유 스킬)
                            cell = row.getCell(columnKeyNumberList.get("보유 스킬"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setSkill(cell.getStringCellValue());
                            }

                            // (경력)
                            cell = row.getCell(columnKeyNumberList.get("경력"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setCareer(cell.getStringCellValue());
                            }

                            // (급여)
                            cell = row.getCell(columnKeyNumberList.get("급여"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                Long pay = Long.parseLong(cell.getStringCellValue());
                                dataUploadEntity.setPay(pay);
                            }

                            // (투입가능 시작 가능일) - localDate 형 변환
                            cell = row.getCell(columnKeyNumberList.get("투입 가능 시작일"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {

                                LocalDate workDay = LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ISO_DATE);
                                dataUploadEntity.setWorkableDay(workDay);
                            }

                            // (우편번호)
                            cell = row.getCell(columnKeyNumberList.get("우편번호"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                Long pc = Long.parseLong(cell.getStringCellValue());
                                dataUploadEntity.setPostcode(pc);
                            }

                            // (주소)
                            cell = row.getCell(columnKeyNumberList.get("주소"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setAddress(cell.getStringCellValue());
                            }

                            // (상세주소)
                            cell = row.getCell(columnKeyNumberList.get("상세주소"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setDetailAddress(cell.getStringCellValue());
                            }

                            // (이메일)
                            cell = row.getCell(columnKeyNumberList.get("이메일"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEmail(cell.getStringCellValue());
                            }

                            // (웹사이트)
                            cell = row.getCell(columnKeyNumberList.get("웹사이트"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEmail(cell.getStringCellValue());
                            }

                            // (최종학력)
                            cell = row.getCell(columnKeyNumberList.get("최종학력"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                dataUploadEntity.setEducation(cell.getStringCellValue());
                            }

                            // (생일)
                            cell = row.getCell(columnKeyNumberList.get("생일"));
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(null != cell) {
                                LocalDate birth = LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ISO_DATE);
                                dataUploadEntity.setBirthDate(birth);
                            }

                            //만든 personEntity를 list에 저장한다.
                            list.add(dataUploadEntity);
                        }

                        for(Integer i = 0; i< list.size();i = i + 100)
                        {
                            dataUploadMapper.insertUploadFileOrder(new ArrayList<>( list.subList(i, Math.min((i + 100) , list.size()))));
                        }
//                        dataUploadMapper.insertUploadFileOrderByCmdb(uploadFileOrder);

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

    /*
     * @Validated (value = {ReadValidationGroup.class}) public
     * PageResponse<UploadFileLogDto> selectCMDBUploadFileLog( PageRequest
     * pageRequest) { Integer sosanBackupResultListCount =
     * dataUploadCMDBMapper.selectCmdbLogListCount(); List<UploadFileLogDto>
     * sosanBackupResultList = dataUploadCMDBMapper.selectCmdbLogList(pageRequest);
     * PageResponse<UploadFileLogDto> pageResponse = new PageResponse<>(pageRequest,
     * sosanBackupResultListCount); pageResponse.setItems(sosanBackupResultList);
     * return pageResponse; }
     */

}
