package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import SQLService.ConnectionSQL;

public class Sach {
	private String maSach;
	private String tenSach;
	private String tacGia;
	private String theLoai;
	private String NXB;
	private int giaBia;
	private String tinhTrangMuon;
	private int soLuong;
	private int isDeleted;
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getMaSach() {
		return maSach;
	}
	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public String getTacGia() {
		return tacGia;
	}
	public void setTacGia(String tacGia) {
		this.tacGia = tacGia;
	}
	public String getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}
	public String getNXB() {
		return NXB;
	}
	public void setNXB(String nXB) {
		NXB = nXB;
	}
	public int getGiaBia() {
		return giaBia;
	}
	public void setGiaBia(int giaBia) {
		this.giaBia = giaBia;
	}
	public String getTinhTrangMuon() {
		return tinhTrangMuon;
	}
	public void setTinhTrangMuon(String tinhTrangMuon) {
		this.tinhTrangMuon = tinhTrangMuon;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public ArrayList<Sach> dulieusach() throws SQLException{
		ArrayList<Sach> sach = new ArrayList<Sach>();
		ConnectionSQL obj = new ConnectionSQL();
		Connection con = obj.connect();
		String sql = "SELECT* FROM Sach";
		PreparedStatement prestate = con.prepareStatement(sql);
		ResultSet result = prestate.executeQuery();
		while(result.next()){
			Sach s = new Sach();
			s.setMaSach(result.getString("MaSach"));
			s.setTenSach(result.getString("TenSach"));
			s.setTacGia(result.getString("TacGia"));
			s.setTheLoai(result.getString("TheLoai"));
		    s.setNXB(result.getString("NXB"));
			s.setGiaBia(result.getInt("Gia"));
			s.setTinhTrangMuon(result.getString("Gia"));
			s.setSoLuong(result.getInt("SoLuong"));
			s.setIsDeleted(0);
			sach.add(s);
		}
		con.close();
		return sach;
	}

}
