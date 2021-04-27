package kr.co.metasoft.ito.common.util;

import java.io.FileInputStream;
import java.io.*;
import org.apache.poi.openxml4j.opc.*;
//import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.xmlbeans.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTShape;
import org.apache.poi.xssf.usermodel.*;

/*import com.microsoft.schemas.vml.*;
import com.microsoft.schemas.office.excel.CTClientData;
*/
import java.lang.reflect.Field;
import javax.xml.namespace.QName;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFactory;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFVMLDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {



    /*
     private static XSSFVMLDrawing getVMLDrawing(XSSFSheet sheet) throws Exception {
          XSSFVMLDrawing drawing = null;
          if (sheet.getCTWorksheet().getLegacyDrawing() != null) {
           String legacyDrawingId = sheet.getCTWorksheet().getLegacyDrawing().getId();
           drawing = (XSSFVMLDrawing)sheet.getRelationById(legacyDrawingId);
          } else {
           int drawingNumber = sheet.getPackagePart().getPackage()
            .getPartsByContentType(XSSFRelation.VML_DRAWINGS.getContentType()).size() + 1;
           POIXMLDocumentPart.RelationPart rp =
            sheet.createRelationship(XSSFRelation.VML_DRAWINGS, XSSFFactory.getInstance(), drawingNumber, false);
           drawing = rp.getDocumentPart();
           String rId = rp.getRelationship().getId();
           sheet.getCTWorksheet().addNewLegacyDrawing().setId(rId);
          }
          return drawing;
         }






     private static void addCheckbox(XSSFVMLDrawing drawing,
              int col1, int dx1, int row1, int dy1, int col2, int dx2, int row2, int dy2,
              String label, boolean checked) throws Exception {

              String shapeTypeId = "_x0000_t201";

              Field _shapeId = XSSFVMLDrawing.class.getDeclaredField("_shapeId");
              _shapeId.setAccessible(true);
              int shapeId = (int)_shapeId.get(drawing);
              _shapeId.set(drawing, shapeId + 1);

              CTShape shape = CTShape.Factory.newInstance();
              shape.setId("_x0000_s" + shapeId);
              shape.setType("#" + shapeTypeId);
              shape.setFilled(com.microsoft.schemas.vml.STTrueFalse.F);
              shape.setStroked(com.microsoft.schemas.vml.STTrueFalse.F);
              String textboxHTML =
               "<div style='text-align:left'>"
              +"<font face=\"Tahoma\" size=\"160\" color=\"auto\">" + label + "</font>"
              +"</div>";
              CTTextbox[] textboxArray = new CTTextbox[1];
              textboxArray[0] = CTTextbox.Factory.parse(textboxHTML);
              textboxArray[0].setStyle("mso-direction-alt:auto");
              textboxArray[0].setSingleclick(com.microsoft.schemas.office.office.STTrueFalse.F);
              shape.setTextboxArray(textboxArray);
              CTClientData cldata = shape.addNewClientData();
              cldata.setObjectType(com.microsoft.schemas.office.excel.STObjectType.CHECKBOX);
              cldata.addNewMoveWithCells();
              cldata.addNewSizeWithCells();
              cldata.addNewAnchor().setStringValue(
               "" + col1 + ", " + dx1 + ", " + row1 + ", " +dy1 + ", " + col2 + ", " + dx2 + ", " + row2 + ", " + dy2
              );
              cldata.addAutoFill(com.microsoft.schemas.office.excel.STTrueFalseBlank.FALSE);
              cldata.addAutoLine(com.microsoft.schemas.office.excel.STTrueFalseBlank.FALSE);
              cldata.addTextVAlign("Center");
              cldata.addNoThreeD(com.microsoft.schemas.office.excel.STTrueFalseBlank.TRUE);

              cldata.addChecked((checked)?java.math.BigInteger.valueOf(1):java.math.BigInteger.valueOf(0));

              Field _items = XSSFVMLDrawing.class.getDeclaredField("_items");
              _items.setAccessible(true);
              @SuppressWarnings("unchecked") //we know the problem and expect runtime error if it possibly occurs
              List<XmlObject> items = (List<XmlObject>)_items.get(drawing);

              Field _qnames = XSSFVMLDrawing.class.getDeclaredField("_qnames");
              _qnames.setAccessible(true);
              @SuppressWarnings("unchecked") //we know the problem and expect runtime error if it possibly occurs
              List<QName> qnames = (List<QName>)_qnames.get(drawing);

              items.add(shape);
              qnames.add(new QName("urn:schemas-microsoft-com:vml", "shape"));

     }

*/


    public static void main(String[] args) {

        try {
            FileInputStream file = new FileInputStream("\\\\192.168.0.200\\Share\\04_공통\\양식\\★휴가신청서.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet=workbook.getSheetAt(0);

            int rowindex=0;
            int columnindex=0;
            //행의 수
            int rows=sheet.getPhysicalNumberOfRows();
            for(rowindex=0;rowindex<rows;rowindex++){
                //행을읽는다
                XSSFRow row=sheet.getRow(rowindex);
                if(row !=null){
                    //셀의 수
                    int cells=row.getPhysicalNumberOfCells();
                    for(columnindex=0; columnindex<=cells; columnindex++){
                        //셀값을 읽는다
                        XSSFCell cell=row.getCell(columnindex);
                        String value="";
                        //셀이 빈값일경우를 위한 널체크
                        if(cell==null){
                            continue;
                        }else{
                            //타입별로 내용 읽기
                            switch (cell.getCellType()){
                            case XSSFCell.CELL_TYPE_FORMULA:
                                value=cell.getCellFormula();
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:
                                value=cell.getNumericCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_STRING:
                                value=cell.getStringCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:
                                value=cell.getBooleanCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                value=cell.getErrorCellValue()+"";
                                break;
                            }
                        }
                        System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
                    }

                }
            }

        }catch (Exception e) {
            // TODO: handle exception
        }


    }

}
