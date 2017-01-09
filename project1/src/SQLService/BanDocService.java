package SQLService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.BanDoc;

public class BanDocService extends SQLServerService{
	
	public int xoaBandoc(BanDoc bd){
		int ret=JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa "+bd.getMaBanDoc()+" này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ret==JOptionPane.YES_OPTION){
			try{
				String sql="delete from DocGia where MaDocGia=?";
				PreparedStatement prestatement=conn.prepareStatement(sql);
				prestatement.setString(1, bd.getMaBanDoc());
				return prestatement.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return -1;
		
	}
	
	public int luuBanDoc(BanDoc bd){
		try{
			java.sql.Date ngay_sinh=new java.sql.Date(bd.getNgaySinh().getTime());
			String sql="insert into DocGia values(?,?,?,?,?,?,?)";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, bd.getMaBanDoc());
			prestatement.setString(2, bd.getHoTen());
			prestatement.setString(3, bd.getSoDienThoai());
			prestatement.setString(4, bd.getEmail());
			prestatement.setDate(5, ngay_sinh);
			prestatement.setString(6, bd.getQueQuan());
			prestatement.setString(7,bd.getGioiTinh());
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	
	public int suaBanDoc(BanDoc bd){
		try{
			java.sql.Date ngay_sinh=new java.sql.Date(bd.getNgaySinh().getTime());
			String sql="update DocGia set HoTen=?,SoDT=?,Email=?,NgaySinh=?,QueQuan=?,GioiTinh=? where MaDocGia=?";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, bd.getHoTen());
			prestatement.setString(2, bd.getSoDienThoai());
			prestatement.setString(3, bd.getEmail());
			prestatement.setDate(4,ngay_sinh);
			prestatement.setString(5, bd.getQueQuan());
			prestatement.setString(6,bd.getGioiTinh());
			prestatement.setString(7, bd.getMaBanDoc());
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<ArrayList<String>> readExcel(String s) throws IOException {
		ArrayList<ArrayList<String>> list = new ArrayList<>();

		FileInputStream file = new FileInputStream(new File(s));

		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		// Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			ArrayList<String> data = new ArrayList<>();

			for (int cn = 0; cn < row.getLastCellNum(); cn++) {

				Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

				data.add(cell.toString());
			}
			list.add(data);
		}
		file.close();
		FileOutputStream out = new FileOutputStream(new File(s));
		workbook.write(out);
		out.close();
		return list;

	}



}
