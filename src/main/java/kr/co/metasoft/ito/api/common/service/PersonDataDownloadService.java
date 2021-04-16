package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.dto.PersonParamDto;
import kr.co.metasoft.ito.api.common.dto.ProjectPersonParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.entity.PersonEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectPersonEntity;
import kr.co.metasoft.ito.api.common.mapper.PersonProjectGroupMapper;
import kr.co.metasoft.ito.api.common.mapper.ProjectPersonMapper;
import kr.co.metasoft.ito.common.util.PageRequest;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Valid
@Service
public class PersonDataDownloadService {
    @Autowired
    private PersonProjectGroupMapper personProjectGroupMapper;

    @Autowired
    private CodeService codeService;


    @Transactional(readOnly = true)
    public byte[] getPersonListXlsx(PersonParamDto personParamDto, PageRequest pageRequest) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;
            XSSFCellStyle headerCellStyle = null;
            XSSFCellStyle bodyCellStyle = null;
            XSSFFont headerFont = null;
            XSSFFont bodyFont = null;
            headerFont = workbook.createFont();
            headerFont.setFontName("맑은 고딕");
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBold(true);
            bodyFont = workbook.createFont();
            bodyFont.setFontName("맑은 고딕");
            bodyFont.setFontHeightInPoints((short) 11);
            headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setFont(headerFont);
            bodyCellStyle = workbook.createCellStyle();
            bodyCellStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyCellStyle.setBorderTop(BorderStyle.THIN);
            bodyCellStyle.setBorderRight(BorderStyle.THIN);
            bodyCellStyle.setBorderBottom(BorderStyle.THIN);
            bodyCellStyle.setBorderLeft(BorderStyle.THIN);
            bodyCellStyle.setFont(bodyFont);
            sheet = workbook.createSheet("사용자 현황");
            row = sheet.createRow(0);
            cell = row.createCell(0);cell.setCellStyle(headerCellStyle);cell.setCellValue("이름");
            cell = row.createCell(1);cell.setCellStyle(headerCellStyle);cell.setCellValue("성별");
            cell = row.createCell(2);cell.setCellStyle(headerCellStyle);cell.setCellValue("평가점수");
            cell = row.createCell(3);cell.setCellStyle(headerCellStyle);cell.setCellValue("평가메모");
            cell = row.createCell(4);cell.setCellStyle(headerCellStyle);cell.setCellValue("전화번호");
            cell = row.createCell(5);cell.setCellStyle(headerCellStyle);cell.setCellValue("직종");
            cell = row.createCell(6);cell.setCellStyle(headerCellStyle);cell.setCellValue("기술");
            cell = row.createCell(7);cell.setCellStyle(headerCellStyle);cell.setCellValue("학력");
            cell = row.createCell(8);cell.setCellStyle(headerCellStyle);cell.setCellValue("경력");
            cell = row.createCell(9);cell.setCellStyle(headerCellStyle);cell.setCellValue("자격증유무");
            cell = row.createCell(10);cell.setCellStyle(headerCellStyle);cell.setCellValue("생년월일(나이)");
            cell = row.createCell(11);cell.setCellStyle(headerCellStyle);cell.setCellValue("희망월급여");
            cell = row.createCell(12);cell.setCellStyle(headerCellStyle);cell.setCellValue("지역");
            cell = row.createCell(13);cell.setCellStyle(headerCellStyle);cell.setCellValue("현재 지원한 프로젝트");
            cell = row.createCell(14);cell.setCellStyle(headerCellStyle);cell.setCellValue("투입여부");
            cell = row.createCell(15);cell.setCellStyle(headerCellStyle);cell.setCellValue("업무가능일");

            CodeParamDto job = CodeParamDto.builder().parentId("001").build();
            PageRequest codePageRequest = new PageRequest();
            codePageRequest.setRowSize(100000000);

            List<CodeEntity> jobCodeList = codeService.getCodeList(job, codePageRequest).getItems();
            HashMap<String, String> jobCodeMap = new HashMap<>();
            for(CodeEntity e : jobCodeList) {
                jobCodeMap.put(e.getId(), e.getName());
            }
            List<PersonEntity> personList = personProjectGroupMapper.selectPersonProjectList(personParamDto, pageRequest);
            for (int i = 0; i < personList.size(); i++) {
                PersonEntity entity = personList.get(i);
                String jobCodeName = jobCodeMap.get(entity.getJobType());

                String name = entity.getName();

                String gender = "";
                if(entity.getGender() == null) gender = "비공개";
                else if(entity.getGender().equals("M")) gender = "남자";
                else if(entity.getGender().equals("F")) gender = "여자";

                Long ratingScore = entity.getRatingScore();
                String memo = entity.getMemo();
                String phoneNumber = entity.getPhoneNumber();
                String jobType = jobCodeName;
                String skill = entity.getSkill();
                String education = entity.getEducation();
                String career = entity.getCareer();

                String certificateStatus = "";
                if(entity.getCertificateStatus().equals("T")) certificateStatus = "있음";
                else if(entity.getCertificateStatus().equals("F")) certificateStatus = "없음";

                String birthDate = "";
                if(entity.getBirthDate() != null) {
                    birthDate = String.valueOf(entity.getBirthDate());
                }

                String pay = "";
                if(entity.getMinPay() != null) { // 최소희망월급여가 null이 아닐경우
                    pay = entity.getMinPay() + ""; // 최소희망월급여 출력
                    if(entity.getMaxPay() != null) { // 최대희망월급여가 null이 아닐경우
                        pay += " ~ " + entity.getMaxPay(); // 최소희망월급여 ~ 최대희망월급여 출력
                    }
                }

                String address = entity.getAddress();

                String applyProject = "";
                if(entity.getProjectNameList() != null) applyProject = entity.getProjectNameList();

                String inputStatus = "";
                switch (entity.getInputStatus()) {
                    case "P": inputStatus = "투입중"; break;
                    case "I": inputStatus = "인터뷰"; break;
                    case "C": inputStatus = "섭외 완료"; break;
                    case "A": inputStatus = "섭외중"; break;
                    default: inputStatus = "이외";
                }

                String workableDay = String.valueOf(entity.getWorkableDay());

                row = sheet.createRow(i + 1);
                cell = row.createCell(0); cell.setCellStyle(bodyCellStyle); cell.setCellValue(name);
                cell = row.createCell(1); cell.setCellStyle(bodyCellStyle); cell.setCellValue(gender);
                cell = row.createCell(2); cell.setCellStyle(bodyCellStyle); cell.setCellValue(ratingScore);
                cell = row.createCell(3); cell.setCellStyle(bodyCellStyle); cell.setCellValue(memo);
                cell = row.createCell(4); cell.setCellStyle(bodyCellStyle); cell.setCellValue(phoneNumber);
                cell = row.createCell(5); cell.setCellStyle(bodyCellStyle); cell.setCellValue(jobType);
                cell = row.createCell(6); cell.setCellStyle(bodyCellStyle); cell.setCellValue(skill);
                cell = row.createCell(7); cell.setCellStyle(bodyCellStyle); cell.setCellValue(education);
                cell = row.createCell(8); cell.setCellStyle(bodyCellStyle); cell.setCellValue(career);
                cell = row.createCell(9); cell.setCellStyle(bodyCellStyle); cell.setCellValue(certificateStatus);
                cell = row.createCell(10); cell.setCellStyle(bodyCellStyle); cell.setCellValue(birthDate);
                cell = row.createCell(11); cell.setCellStyle(bodyCellStyle); cell.setCellValue(pay);
                cell = row.createCell(12); cell.setCellStyle(bodyCellStyle); cell.setCellValue(address);
                cell = row.createCell(13); cell.setCellStyle(bodyCellStyle); cell.setCellValue(applyProject);
                cell = row.createCell(14); cell.setCellStyle(bodyCellStyle); cell.setCellValue(inputStatus);
                cell = row.createCell(15); cell.setCellStyle(bodyCellStyle); cell.setCellValue(workableDay);
            }

            row = workbook.getSheetAt(0).getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, Math.min(124 * 125, sheet.getColumnWidth(i) + (256 * 5)));
            }
            workbook.write(baos);
        } catch (IOException e) {
        }

        return baos.toByteArray();
    }
}
