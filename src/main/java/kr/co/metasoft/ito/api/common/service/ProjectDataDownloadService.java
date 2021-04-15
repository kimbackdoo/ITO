package kr.co.metasoft.ito.api.common.service;

import kr.co.metasoft.ito.api.common.dto.CodeParamDto;
import kr.co.metasoft.ito.api.common.dto.ProjectParamDto;
import kr.co.metasoft.ito.api.common.entity.CodeEntity;
import kr.co.metasoft.ito.api.common.entity.ProjectEntity;
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
import java.util.HashMap;
import java.util.List;

@Valid
@Service
public class ProjectDataDownloadService {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private CodeService codeService;

    @Transactional(readOnly = true)
    public byte[] getProjectListXlsx(ProjectParamDto projectParamDto, PageRequest pageRequest) {
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
            sheet = workbook.createSheet("프로젝트 목록");
            row = sheet.createRow(0);
            cell = row.createCell(0);cell.setCellStyle(headerCellStyle);cell.setCellValue("프로젝트명");
            cell = row.createCell(1);cell.setCellStyle(headerCellStyle);cell.setCellValue("직종");
            cell = row.createCell(2);cell.setCellStyle(headerCellStyle);cell.setCellValue("기술");
            cell = row.createCell(3);cell.setCellStyle(headerCellStyle);cell.setCellValue("학위요건");
            cell = row.createCell(4);cell.setCellStyle(headerCellStyle);cell.setCellValue("경력요건");
            cell = row.createCell(5);cell.setCellStyle(headerCellStyle);cell.setCellValue("프로젝트 시작");
            cell = row.createCell(6);cell.setCellStyle(headerCellStyle);cell.setCellValue("프로젝트 끝");
            cell = row.createCell(7);cell.setCellStyle(headerCellStyle);cell.setCellValue("장소(시)");
            cell = row.createCell(8);cell.setCellStyle(headerCellStyle);cell.setCellValue("장소(구)");
            cell = row.createCell(9);cell.setCellStyle(headerCellStyle);cell.setCellValue("필요인원");
            cell = row.createCell(10);cell.setCellStyle(headerCellStyle);cell.setCellValue("현황");
            cell = row.createCell(11);cell.setCellStyle(headerCellStyle);cell.setCellValue("희망급여");
            cell = row.createCell(12);cell.setCellStyle(headerCellStyle);cell.setCellValue("모집마감일");

            PageRequest codePageRequest = new PageRequest();
            codePageRequest.setRowSize(100000000);

            CodeParamDto jobCode = CodeParamDto.builder().parentId("001").build();
            List<CodeEntity> jobCodeList = codeService.getCodeList(jobCode, codePageRequest).getItems();
            HashMap<String, String> jobCodeMap = new HashMap<>();
            for(CodeEntity e : jobCodeList) {
                jobCodeMap.put(e.getId(), e.getName());
            }

            CodeParamDto educationCode = CodeParamDto.builder().parentId("007").build();
            List<CodeEntity> educationCodeList = codeService.getCodeList(educationCode, codePageRequest).getItems();
            HashMap<String, String> educationMap  = new HashMap<>();
            for(CodeEntity e : educationCodeList) {
                educationMap.put(e.getId(), e.getName());
            }

            CodeParamDto localCode = CodeParamDto.builder().parentId("006").build();
            List<CodeEntity> localCodeList = codeService.getCodeList(localCode, pageRequest).getItems();
            HashMap<String, String> localMap = new HashMap<>();
            for(CodeEntity e : localCodeList) {
                localMap.put(e.getId(), e.getName());
            }

            CodeParamDto detailLocalCode = CodeParamDto.builder().idStartLike("006_____").build();
            List<CodeEntity> detailLocalCodeList = codeService.getCodeList(detailLocalCode, pageRequest).getItems();
            HashMap<String, String> detailLocalMap = new HashMap<>();
            for(CodeEntity e : detailLocalCodeList) {
                detailLocalMap.put(e.getId(), e.getName());
            }

            List<ProjectEntity> projectList = projectService.getProjectList(projectParamDto, pageRequest).getItems();
            for (int i = 0; i < projectList.size(); i++) {
                ProjectEntity entity = projectList.get(i);
                String jobCodeName = jobCodeMap.get(entity.getJob());
                String educationName = educationMap.get(entity.getDegree());
                String localName = localMap.get(entity.getLocal());
                String detailLocalName = detailLocalMap.get(entity.getDetailLocal());

                String name = entity.getName();
                String job = jobCodeName;
                String skill = entity.getSkill();
                String education = educationName;
                String career = entity.getCareer() + "년";
                String sterm = String.valueOf(entity.getSterm());
                String eterm = String.valueOf(entity.getEterm());
                String local = localName;
                String detailLocal = detailLocalName;
                Integer prsnl = entity.getPrsnl();

                String inputStatus = "";
                switch (entity.getStatus()) {
                    case "P": inputStatus = "투입중"; break;
                    case "I": inputStatus = "인터뷰"; break;
                    case "C": inputStatus = "섭외 완료"; break;
                    case "A": inputStatus = "섭외중"; break;
                    default: inputStatus = "이외";
                }

                Long salary = entity.getSalary();
                String limitDate = String.valueOf(entity.getLimitDate());

                row = sheet.createRow(i + 1);
                cell = row.createCell(0); cell.setCellStyle(bodyCellStyle); cell.setCellValue(name);
                cell = row.createCell(1); cell.setCellStyle(bodyCellStyle); cell.setCellValue(job);
                cell = row.createCell(2); cell.setCellStyle(bodyCellStyle); cell.setCellValue(skill);
                cell = row.createCell(3); cell.setCellStyle(bodyCellStyle); cell.setCellValue(education);
                cell = row.createCell(4); cell.setCellStyle(bodyCellStyle); cell.setCellValue(career);
                cell = row.createCell(5); cell.setCellStyle(bodyCellStyle); cell.setCellValue(sterm);
                cell = row.createCell(6); cell.setCellStyle(bodyCellStyle); cell.setCellValue(eterm);
                cell = row.createCell(7); cell.setCellStyle(bodyCellStyle); cell.setCellValue(local);
                cell = row.createCell(8); cell.setCellStyle(bodyCellStyle); cell.setCellValue(detailLocal);
                cell = row.createCell(9); cell.setCellStyle(bodyCellStyle); cell.setCellValue(prsnl);
                cell = row.createCell(10); cell.setCellStyle(bodyCellStyle); cell.setCellValue(inputStatus);
                cell = row.createCell(11); cell.setCellStyle(bodyCellStyle); cell.setCellValue(salary);
                cell = row.createCell(12); cell.setCellStyle(bodyCellStyle); cell.setCellValue(limitDate);
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
