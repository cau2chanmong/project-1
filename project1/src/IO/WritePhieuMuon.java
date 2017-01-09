package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class WritePhieuMuon {
	public void WriteWord(JTable tb,JComboBox cbPM,JTextField txtMBD,JTextField txtTenBD,String TenFile,String TenTieuDe){
		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out=null;
		try {
			out = new FileOutputStream(new File(
					"C:\\java\\project1\\"+TenFile+".docx"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String str1=cbPM.getSelectedItem().toString();
		String str2=txtMBD.getText();
		String str3=txtTenBD.getText();

		// create paragraph
		XWPFParagraph paragraph = document.createParagraph();

		// Set Bold an Italic
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setText("TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI");
		paragraphOneRunOne.addBreak();
		paragraphOneRunOne.setText("THƯ VIỆN TẠ QUANG BỬU");
		paragraphOneRunOne.addBreak();
		paragraphOneRunOne.addBreak();
		paragraphOneRunOne.setText(TenTieuDe);

		paragraphOneRunOne.setFontSize(18);
		paragraphOneRunOne.setFontFamily("Times New Roman");
		paragraphOneRunOne.addBreak();
		
		paragraph = document.createParagraph();
		XWPFRun paragraphOneRunThree = paragraph.createRun();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		paragraphOneRunThree.setFontFamily("Times New Roman");
		paragraphOneRunThree.setText("Mã số phiếu:"+str1);
		paragraphOneRunThree.addBreak();
		paragraphOneRunThree.setText("Mã bạn đọc:"+str2);
		paragraphOneRunThree.addBreak();
		paragraphOneRunThree.setText("Tên bạn đọc:"+str3);
		paragraphOneRunThree.addBreak();




		int r=tb.getRowCount();
		int c=tb.getColumnCount();
		// create second row
		XWPFTable table = document.createTable(r+1,c);
		XWPFTableRow tableRow=table.getRow(0);
		for (int j = 0; j < c; j++) {
			tableRow.getCell(j).setText(tb.getModel().getColumnName(j));
		}

		for (int i = 1; i < r+1; i++) {
			tableRow = table.getRow(i);
			for (int j = 0; j < c; j++) {
				tableRow.getCell(j).setText(tb.getValueAt(i-1, j) + "");
			}
		}
		table.setWidth(1000);
		CTJc jc = table.getCTTbl().getTblPr().addNewJc();
		jc.setVal(STJc.CENTER);
		table.getCTTbl().getTblPr().setJc(jc);

		table.getCTTbl().getTblPr().getTblW().setW(BigInteger.valueOf(5000));
		table.getCTTbl().getTblPr().getTblW().setType(STTblWidth.PCT);
		// Set text Position
		paragraph = document.createParagraph();
		XWPFRun paragraphp = paragraph.createRun();
		paragraphp.addBreak();

		paragraph = document.createParagraph();
		XWPFRun paragraphOneRunTwo = paragraph.createRun();
		paragraph.setAlignment(ParagraphAlignment.RIGHT);
		paragraphOneRunTwo.setFontFamily("Times New Roman");
		paragraphOneRunTwo.setFontSize(15);
		paragraphOneRunTwo.addBreak();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunTwo.setText("Hà nội,ngày    tháng   năm");
		paragraphOneRunTwo.addBreak();
		paragraphOneRunTwo.setText("Người lập phiếu");
		paragraphOneRunTwo.addBreak();
		paragraphOneRunTwo.addBreak();
		paragraphOneRunTwo.setText("Nguyễn Trung Nam");



		try {
			document.write(out);
			JOptionPane.showMessageDialog(null, "Xuất file thành công");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Xuất file thất bại");
		}
	}

}
