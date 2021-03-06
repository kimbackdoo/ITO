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
            String subject = "??????";

            Properties properties = new Properties();

            //SMTP ?????? ?????? ??????
              properties.setProperty("mail.smtp.host", "smtp.cafe24.com");
              properties.setProperty("mail.smtp.port", "587");
              properties.setProperty("mail.smtp.auth", "true");
              properties.setProperty("mail.smtp.timeout", "5000");
              properties.setProperty("mail.smtp.starttls.enable", "true");


            //session ??????
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("smpark@meta-soft.co.kr", "metasoft1@");
                }
            });

            DataHandler dataHandler = null;

            //MimeMessage ??????
            MimeMessage mimeMessage = new MimeMessage(session);

            /*
             * File logoEmailFile = new ClassPathResource(
             * "static/resources/lib/hkpif-crm/0.0.1/images/logo-email.png").getFile();
             * DataSource imageFile = new FileDataSource(logoEmailFile);
             */

            //????????? ??????,
            mimeMessage.setFrom(new InternetAddress(""));

            //????????? ??????
            mimeMessage.addRecipients(RecipientType.TO, "");



            //?????? ??????
            mimeMessage.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart mimeBodyPart = new MimeBodyPart();



            //?????? ??????
            mimeBodyPart.setContent(text, "text/html;charset=UTF-8");
            multipart.addBodyPart(mimeBodyPart);

            //????????? ??????
            /*
             * mimeBodyPart = new MimeBodyPart(); mimeBodyPart.setDataHandler(new
             * DataHandler(imageFile)); mimeBodyPart.setHeader("Content-ID", "<image>");
             * multipart.addBodyPart(mimeBodyPart);
             */

            //?????? ?????? ???
//	        if(mailDto.getFileData() != null && mailDto.getFileName() != null) {
//	            dataHandler = new DataHandler(mailDto.getFileData());
//	            mimeBodyPart = new MimeBodyPart();
//	            mimeBodyPart.setDataHandler(dataHandler);
//	            mimeBodyPart.setDisposition(Part.ATTACHMENT);
//	            mimeBodyPart.setFileName(mailDto.getFileName());
//	            multipart.addBodyPart(mimeBodyPart);
//	        }


            mimeMessage.setContent(multipart);

            //?????????
            Transport.send(mimeMessage);

            System.out.println("????????? ??????");

        } catch (Exception e) {

            System.out.println("??????");
            e.printStackTrace();
            // TODO: handle exception
        }



    }








    public void contextLoads() {

        try {
            FileInputStream file = new FileInputStream("C:\\\\originExcel\\\\???????????????.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            //????????? ??????
            addExcelImage(workbook, sheet, "C:\\testSign.png", 27, 6, 0.3);

            //?????? ??????
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
            row = sheet.getRow(8); cell = row.getCell(1); cell.setCellValue("?????????");

            //??????
            row = sheet.getRow(8); cell = row.getCell(6); cell.setCellValue("?????????");

            //??? ???
            row = sheet.getRow(9); cell = row.getCell(1); cell.setCellValue("?????????");

            //???????????????
            row = sheet.getRow(9); cell = row.getCell(6); cell.setCellValue("010-1234-5678");


            //?????? (?????? ?????? CASE ???????????? )
            row = sheet.getRow(10); cell = row.getCell(1); cell.setCellValue("??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???           ??? ??? ???");

            //?????? ??????
            String sterm = "2021-05-12";
            String sYear = sterm.substring(0, 4);
            String sMonth = sterm.substring(5, 7);
            String sDay = sterm.substring(8, 10);
            String eterm = "2021-05-13";
            String eYear = sterm.substring(0, 4);
            String eMonth = sterm.substring(5, 7);
            String eDay = sterm.substring(8, 10);
            row = sheet.getRow(11); cell = row.getCell(1); cell.setCellValue(sYear+"???    "+sMonth+"???    "+sDay+"???    ~    "+eYear+"???    "+eMonth+"???    "+eDay+"???");


            //?????? ??????
            row = sheet.getRow(12); cell = row.getCell(1); cell.setCellValue(
                    " ???????????? ");
            //?????? ?????????
            String name = "?????????";
            row = sheet.getRow(18); cell = row.getCell(1); cell.setCellValue(
                    "                             ??????????????? :    " + name);

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
                    name+
                    "      (???)");


            String path = "C:\\excelTest\\";
            String fileName = "???????????????_"+name+".xlsx";
            File xlsxFile = new File(path + fileName);
            FileOutputStream fileOut = new FileOutputStream(xlsxFile);
            workbook.write(fileOut);






/*            int rowindex = 0;
            int columnindex = 0;
            // ?????? ???
            int rows = sheet.getPhysicalNumberOfRows();
            for (rowindex = 0; rowindex < rows; rowindex++) {
                // ???????????????
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    // ?????? ???
                    int cells = row.getPhysicalNumberOfCells();
                    for (columnindex = 0; columnindex <= cells; columnindex++) {
                        // ????????? ?????????
                        XSSFCell cell = row.getCell(columnindex);
                        String value = "";
                        // ?????? ?????????????????? ?????? ?????????
                        if (cell == null) {
                            continue;
                        } else {
                            // ???????????? ?????? ??????
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
                       System.out.println(rowindex+"??? ??? : "+columnindex+"??? ??? ??????: "+value);
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
