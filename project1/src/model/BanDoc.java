package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import SQLService.ConnectionSQL;
import SQLService.SQLServerService;



public class BanDoc extends SQLServerService{
	private String maBanDoc;
	private String hoTen;
	private String soDienThoai;
	private String email;
	private Date ngaySinh;
	private String queQuan;
	private String gioiTinh;
	public String getMaBanDoc() {
		return maBanDoc;
	}
	public void setMaBanDoc(String maBanDoc) {
		this.maBanDoc = maBanDoc;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getQueQuan() {
		return queQuan;
	}
	public void setQueQuan(String queQuan) {
		this.queQuan = queQuan;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
//	public Vector<BanDoc> DuLieuBanDoc() throws SQLException{
//		Vector<BanDoc> bandoc = new Vector<BanDoc>();
//		SQLServerService obj = new SQLServerService();
//		Connection conn=obj.connectSQLServerService();
//		String sql = "select *from DocGia";
//		PreparedStatement prestate = conn.prepareStatement(sql);
//		ResultSet result = prestate.executeQuery();
//		while(result.next()){
//			BanDoc bd = new BanDoc();
//			bd.setMaBanDoc(result.getString(1));
//			bd.setHoTen(result.getString(2));
//            bd.setSoDienThoai(result.getString(3));
//			bd.setEmail(result.getString(4));
//			bd.setNgaySinh(result.getDate(5));
//            bd.setQueQuan(result.getString(6));
//			bd.setGioiTinh(result.getString(7));
//			bandoc.add(bd);
//		}
//		conn.close();
//		return bandoc;	
	public ArrayList<BanDoc> dulieudocgia() throws SQLException{
		ArrayList<BanDoc> docgia = new ArrayList<BanDoc>();
		ConnectionSQL obj = new ConnectionSQL();
		Connection con = obj.connect();
		String sql = "SELECT* FROM DocGia";
		PreparedStatement prestate = con.prepareStatement(sql);
		ResultSet result = prestate.executeQuery();
		while(result.next()){
			BanDoc bd = new BanDoc();
			bd.setMaBanDoc(result.getString("MaDocGia"));
			bd.setHoTen(result.getString("HoTen"));
			bd.setSoDienThoai(result.getString("SoDT"));
			bd.setEmail(result.getString("Email"));
			bd.setNgaySinh(result.getDate("NgaySinh"));
			bd.setQueQuan(result.getString("QueQuan"));
			bd.setGioiTinh(result.getString("GioiTinh"));
			docgia.add(bd);
		}
		con.close();
		return docgia;
	}
	 	

}
