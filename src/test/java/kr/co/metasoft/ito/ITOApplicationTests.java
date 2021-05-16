package kr.co.metasoft.ito;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.record.CommonObjectDataSubRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SubRecord;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.metasoft.ito.api.app.dto.MailDto;

@SpringBootTest
class ITOApplicationTests {


    @Test
    public void testMail() {

        try {

            String text = "<a href=http://localhost:81/groupware/approval" +
                    "?vacationId=12&role=ROLE_TEAMLEADER" + ">" +
                    "http://localhost:81/groupware/approval</a>";
            String subject = "제목";

            Properties properties = new Properties();

            //SMTP 서버 정보 설정
              properties.setProperty("mail.smtp.host", "smtp.cafe24.com");
              properties.setProperty("mail.smtp.port", "587");
              properties.setProperty("mail.smtp.auth", "true");
              properties.setProperty("mail.smtp.timeout", "5000");
              properties.setProperty("mail.smtp.starttls.enable", "true");


            //session 생성
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("smpark@meta-soft.co.kr", "metasoft1@");
                }
            });

            DataHandler dataHandler = null;

            //MimeMessage 생성
            MimeMessage mimeMessage = new MimeMessage(session);

            /*
             * File logoEmailFile = new ClassPathResource(
             * "static/resources/lib/hkpif-crm/0.0.1/images/logo-email.png").getFile();
             * DataSource imageFile = new FileDataSource(logoEmailFile);
             */

            //발신자 셋팅,
            mimeMessage.setFrom(new InternetAddress(""));

            //수신자 셋팅
            mimeMessage.addRecipients(RecipientType.TO, "");



            //제목 셋팅
            mimeMessage.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart mimeBodyPart = new MimeBodyPart();



            //내용 셋팅
            mimeBodyPart.setContent(text, "text/html;charset=UTF-8");
            multipart.addBodyPart(mimeBodyPart);

            //이미지 추가
            /*
             * mimeBodyPart = new MimeBodyPart(); mimeBodyPart.setDataHandler(new
             * DataHandler(imageFile)); mimeBodyPart.setHeader("Content-ID", "<image>");
             * multipart.addBodyPart(mimeBodyPart);
             */

            //파일 있을 시
//	        if(mailDto.getFileData() != null && mailDto.getFileName() != null) {
//	            dataHandler = new DataHandler(mailDto.getFileData());
//	            mimeBodyPart = new MimeBodyPart();
//	            mimeBodyPart.setDataHandler(dataHandler);
//	            mimeBodyPart.setDisposition(Part.ATTACHMENT);
//	            mimeBodyPart.setFileName(mailDto.getFileName());
//	            multipart.addBodyPart(mimeBodyPart);
//	        }


            mimeMessage.setContent(multipart);

            //보내기
            Transport.send(mimeMessage);

            System.out.println("보내기 성공");

        } catch (Exception e) {

            System.out.println("실패");
            e.printStackTrace();
            // TODO: handle exception
        }



    }








    public void contextLoads() {

        try {
            FileInputStream file = new FileInputStream("C:\\\\originExcel\\\\휴가신청서.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            //신청자 싸인
            addExcelImage(workbook, sheet, "C:\\testSign.png", 27, 6, 0.3);

            //결제 도장
            addExcelImage(workbook, sheet, "C:\\seal.png", 4, 7, 0.15);
            addExcelImage(workbook, sheet, "C:\\seal.png", 4, 8, 0.15);
            addExcelImage(workbook, sheet, "C:\\seal.png", 4, 9, 0.15);


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


            //부서
            row = sheet.getRow(8); cell = row.getCell(1); cell.setCellValue("부우서");

            //직급
            row = sheet.getRow(8); cell = row.getCell(6); cell.setCellValue("지익급");

            //성 명
            row = sheet.getRow(9); cell = row.getCell(1); cell.setCellValue("유지훈");

            //비상연락망
            row = sheet.getRow(9); cell = row.getCell(6); cell.setCellValue("010-1234-5678");


            //구분 (값에 따라 CASE 구분하기 )
            row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("▣ 월 차           □ 연 차           □ 반 차           □ 병 가           □ 기 타");

            //휴가 기간
            String sterm = "2021-05-12";
            String sYear = sterm.substring(0, 4);
            String sMonth = sterm.substring(5, 7);
            String sDay = sterm.substring(8, 10);
            String eterm = "2021-05-13";
            String eYear = sterm.substring(0, 4);
            String eMonth = sterm.substring(5, 7);
            String eDay = sterm.substring(8, 10);
            row = sheet.getRow(11); cell = row.getCell(1); cell.setCellValue(sYear+"년    "+sMonth+"월    "+sDay+"일    ~    "+eYear+"년    "+eMonth+"월    "+eDay+"일");


            //세부 사항
            row = sheet.getRow(12); cell = row.getCell(1); cell.setCellValue(
                    " 세부사항 ");
            //인수 인계자
            String name = "유지훈";
            row = sheet.getRow(18); cell = row.getCell(1); cell.setCellValue(
                    "                             인수인계자 :    " + name);

            //휴가 신청 날짜 입력 + 신청자 인
            Calendar cal = Calendar.getInstance();
            String year = String.valueOf(cal.get(Calendar.YEAR));
            String month = String.valueOf(cal.get(Calendar.MONTH)+1);
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            row = sheet.getRow(19); cell = row.getCell(0); cell.setCellValue(
                    " 위와 같이 휴가를 신청합니다.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    year+
                    "년             "+
                    month+
                    "월              "+
                    day+
                    "일\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "신청자 :     "+
                    name+
                    "      (인)");


            String path = "C:\\excelTest\\";
            String fileName = "휴가신청서_"+name+".xlsx";
            File xlsxFile = new File(path + fileName);
            FileOutputStream fileOut = new FileOutputStream(xlsxFile);
            workbook.write(fileOut);






/*            int rowindex = 0;
            int columnindex = 0;
            // 행의 수
            int rows = sheet.getPhysicalNumberOfRows();
            for (rowindex = 0; rowindex < rows; rowindex++) {
                // 행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    // 셀의 수
                    int cells = row.getPhysicalNumberOfCells();
                    for (columnindex = 0; columnindex <= cells; columnindex++) {
                        // 셀값을 읽는다
                        XSSFCell cell = row.getCell(columnindex);
                        String value = "";
                        // 셀이 빈값일경우를 위한 널체크
                        if (cell == null) {
                            continue;
                        } else {
                            // 타입별로 내용 읽기
                            switch (cell.getCellType()) {
                            case XSSFCell.CELL_TYPE_FORMULA:
                                value = cell.getCellFormula();
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:
                                value = cell.getNumericCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:
                                value = cell.getBooleanCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                value = cell.getErrorCellValue() + "";
                                break;
                            }


                        }
                       System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
                    }

                }
            }
*/


        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void addExcelImage(XSSFWorkbook workbook, XSSFSheet sheet,String fileUrl, int row, int col,double size) throws IOException {
        String filePath = fileUrl;

        InputStream is = new FileInputStream(filePath);
        byte[] bytes = IOUtils.toByteArray(is);
        int picIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);
        is.close();

        XSSFCreationHelper helper = workbook.getCreationHelper();
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = helper.createClientAnchor();

        //이미지 cell 위치
        anchor.setRow1(row);
        anchor.setDx1(XSSFShape.EMU_PER_PIXEL*6);
        anchor.setCol1(col);
        anchor.setDy1(XSSFShape.EMU_PER_PIXEL*4);

        XSSFPicture pict = drawing.createPicture(anchor, picIdx);
        pict.resize();
        pict.resize(size, size);
    }




}
