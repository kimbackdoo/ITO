package kr.co.metasoft.ito.api.common.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.api.common.dto.RoleUserDto;
import kr.co.metasoft.ito.api.common.dto.RoleUserParamDto;
import kr.co.metasoft.ito.api.common.dto.VacationParamDto;
import kr.co.metasoft.ito.api.common.entity.ApprovalEntity;
import kr.co.metasoft.ito.api.common.entity.RoleUserEntity;
import kr.co.metasoft.ito.api.common.entity.UserEntity;
import kr.co.metasoft.ito.api.common.entity.VacationEntity;
import kr.co.metasoft.ito.api.common.mapper.RoleUserMapper;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@Valid
@Service
public class VacationDataDownloadService {

    @Autowired
    private VacationService vacationService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSealService userSealService;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Value (value = "${ito.file.load.url.vacation.form}")
    private String filePath;


    @Transactional(readOnly = true)
    public byte[] getVacationXlsx(VacationParamDto vacationParamDto) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        VacationEntity vacationEntity = vacationService.getVacation(vacationParamDto.getId());
        ApprovalEntity approvalEntity = approvalService.getApprovalEntity(vacationEntity.getId());
        UserEntity userEntity = userService.getUser(vacationEntity.getUserId());


        //????????? ?????? ?????? ????????????
        FileInputStream file = new FileInputStream(filePath);

        try(XSSFWorkbook workbook = new XSSFWorkbook(file)){

            XSSFSheet sheet = workbook.getSheetAt(0);


            //?????? ??????
            //??????(userId = 16), ??????(userId = 17), ????????????(userId = 1) => ?????? ?????? url ????????????
            String teamLeaderSeal = userSealService.selectUserseal(16L).getImageUrl();
            String directorSeal = userSealService.selectUserseal(17L).getImageUrl();
            String presidentSeal = userSealService.selectUserseal(1L).getImageUrl();

            //?????? role ?????? ??????, ??????, ??????, ???????????? ?????? ??????
            RoleUserParamDto r = RoleUserParamDto.builder().userId(userEntity.getId()).build();
            PageRequest p = new PageRequest();
            List<RoleUserEntity> list = roleUserMapper.selectRoleUserList(r,p);

            //??????????????? ??????
            String signPath = userSealService.selectUserseal(userEntity.getId()).getSignUrl();
            addExcelImage(workbook, sheet, signPath, 27, 6, 0.3);

            //??????????????? ??????
            String roleValue = list.get(0).getRole().getValue();

            //?????? approval??? ??????
            switch (roleValue) {
            case "ROLE_EMPLOYEE":
                roleValue = "??????";
                addExcelImage(workbook, sheet, teamLeaderSeal, 4, 7, 0.15);
                addExcelImage(workbook, sheet, directorSeal, 4, 8, 0.15);
                addExcelImage(workbook, sheet, presidentSeal, 4, 9, 0.15);
                break;
            case "ROLE_TEAMLEADER":
                roleValue = "??????";
                addExcelImage(workbook, sheet, directorSeal, 4, 8, 0.15);
                addExcelImage(workbook, sheet, presidentSeal, 4, 9, 0.15);
                break;
            case "ROLE_DIRECTOR":
                roleValue = "??????";
                addExcelImage(workbook, sheet, presidentSeal, 4, 9, 0.15);
                break;
            case "ROLE_ADMIN":
                roleValue = "?????? ??????";
                addExcelImage(workbook, sheet, presidentSeal, 4, 9, 0.15);
                break;
            default:
                break;
            }

            XSSFRow row = null;
            XSSFCell cell = null;
            XSSFCellStyle headerCellStyle = null;
            XSSFCellStyle bodyCellStyle = null;
            XSSFFont headerFont = null;
            XSSFFont bodyFont = null;
            headerFont = workbook.createFont();
            headerFont.setFontName("?????? ??????");
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBold(true);
            bodyFont = workbook.createFont();
            bodyFont.setFontName("?????? ??????");
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


            //??????
            row = sheet.getRow(8); cell = row.getCell(1); cell.setCellValue(vacationEntity.getDepartment());

            //??????
            row = sheet.getRow(8); cell = row.getCell(6); cell.setCellValue(roleValue);

            //??? ???
            row = sheet.getRow(9); cell = row.getCell(1); cell.setCellValue(userEntity.getUsername());

            //???????????????
            row = sheet.getRow(9); cell = row.getCell(6); cell.setCellValue(vacationEntity.getEmergencyNum());


            //?????? (?????? ?????? CASE ???????????? )
            switch (vacationEntity.getType()) {
            case "M":
                row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");
                break;
            case "N":
                row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");
                break;
            case "O":
                row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");
                break;
            case "S":
                row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");
                break;
            default:
                row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");
                break;
            }


            //?????? ??????
            String sterm = String.valueOf(vacationEntity.getSterm());
            String sYear = sterm.substring(0, 4);
            String sMonth = sterm.substring(5, 7);
            String sDay = sterm.substring(8, 10);
            String eterm = String.valueOf(vacationEntity.getEterm());
            String eYear = eterm.substring(0, 4);
            String eMonth = eterm.substring(5, 7);
            String eDay = eterm.substring(8, 10);
            row = sheet.getRow(11); cell = row.getCell(1); cell.setCellValue(sYear+"???    "+sMonth+"???    "+sDay+"???    ~    "+eYear+"???    "+eMonth+"???    "+eDay+"???");


            //?????? ??????
            row = sheet.getRow(12); cell = row.getCell(1); cell.setCellValue(vacationEntity.getDetail());

            //?????? ?????????
            String takingUser = vacationEntity.getTakingUser();
            if(takingUser == null) takingUser = "";
            row = sheet.getRow(18); cell = row.getCell(1); cell.setCellValue(
                    "                             ??????????????? :    " + takingUser);

            //?????? ?????? ?????? ?????? + ????????? ???
            Calendar cal = Calendar.getInstance();
            String year = String.valueOf(cal.get(Calendar.YEAR));
            String month = String.valueOf(cal.get(Calendar.MONTH)+1);
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            row = sheet.getRow(19); cell = row.getCell(0); cell.setCellValue(
                    " ?????? ?????? ????????? ???????????????.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    year+
                    "???             "+
                    month+
                    "???              "+
                    day+
                    "???\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "????????? :     "+
                    userEntity.getUsername()+
                    "      (???)");


//            String path = "C:\\";
//            String fileName = "???????????????_"+userEntity.getUsername()+"("+vacationEntity.getId()+")"+".xlsx";
//            File xlsxFile = new File(path + fileName);
//            FileOutputStream fileOut = new FileOutputStream(xlsxFile);
//            workbook.write(fileOut);

            workbook.write(baos);

        }catch (Exception e) {

            System.out.println("=========================================");
            System.out.println(e);
            System.out.println("=========================================");
        }

        //baos ??????
        return baos.toByteArray();
    }

    public void addExcelImage(XSSFWorkbook workbook, XSSFSheet sheet,String fileUrl, int row, int col,double size) throws IOException {
        String picturePath = fileUrl;

        InputStream is = new FileInputStream(picturePath);
        byte[] bytes = IOUtils.toByteArray(is);
        int picIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);
        is.close();

        XSSFCreationHelper helper = workbook.getCreationHelper();
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = helper.createClientAnchor();

        //????????? cell ??????
        anchor.setRow1(row);
        anchor.setDx1(XSSFShape.EMU_PER_PIXEL*6);
        anchor.setCol1(col);
        anchor.setDy1(XSSFShape.EMU_PER_PIXEL*4);

        XSSFPicture pict = drawing.createPicture(anchor, picIdx);
        pict.resize();
        pict.resize(size, size);
    }



}
