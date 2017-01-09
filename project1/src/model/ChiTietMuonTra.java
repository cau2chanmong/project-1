package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import SQLService.ConnectionSQL;
import SQLService.SQLServerService;

public class ChiTietMuonTra{
	String MaPhieu, MaDG,TenDG, MaTL,TenTL, Gia, PhatHongMat;
	int SLtra,PhatQuaHan;
	int SLmuon;
	Date NgayMuon, HanTra, NgayTra;
	public String getMaPhieu() {
		return MaPhieu;
	}
	public void setMaPhieu(String MaPhieu) {
		this.MaPhieu = MaPhieu;
	}
	public String getMaTL() {
		return MaTL;
	}
	public void setMaTL(String MaTL) {
		this.MaTL = MaTL;
	}
        public String getTenTL() {
		return TenTL;
	}
	public void setTenTL(String TenTL) {
		this.TenTL = TenTL;
	}
        public String getMaDG() {
		return MaDG;
	}
	public void setMaDG(String MaDG) {
		this.MaDG = MaDG;
	}
        public String getTenDG() {
		return TenDG;
	}
	public void setTenDG(String TenDG) {
		this.TenDG = TenDG;
	}
        public String getGia() {
		return Gia;
	}
	public void setGia(String Gia) {
		this.Gia = Gia;
	}
	public int getSLmuon() {
		return SLmuon;
	}
	public void setSLmuon(int SLmuon) {
		this.SLmuon = SLmuon;
	}
        public int getSLtra() {
		return SLtra;
	}
	public void setSLtra(int SLtra) {
		this.SLtra = SLtra;
        }
        public int getPhatQuaHan() {
		return PhatQuaHan;
	}
	public void setPhatQuaHan(int PhatQuaHan) {
		this.PhatQuaHan = PhatQuaHan;
        }
        public String getPhatHongMat() {
		return PhatHongMat;
	}
	public void setPhatHongMat(String PhatHongMat) {
		this.PhatHongMat = PhatHongMat;
        }
	public Date getNgayMuon() {
		return NgayMuon;
	}
	public void setNgayMuon(Date NgayMuon) {
		this.NgayMuon = NgayMuon;
	}
	public Date getHanTra() {
		return HanTra;
	}
	public void setHanTra(Date HanTra) {
		this.HanTra = HanTra;
	}
	public Date getNgayTra() {
		return NgayTra;
	}
	public void setNgayTra(Date NgayTra) {
		this.NgayTra = NgayTra;
	}
	
	public ArrayList<ChiTietMuonTra> dulieumuontra() throws SQLException{
		ArrayList<ChiTietMuonTra> chitietmuontra = new ArrayList<ChiTietMuonTra>();
		ConnectionSQL obj=new ConnectionSQL();
		Connection con = obj.connect();
		String sql = "SELECT* FROM ChiTietMuonTra";
		PreparedStatement prestate = con.prepareStatement(sql);
		ResultSet result = prestate.executeQuery();
		while(result.next()){
			ChiTietMuonTra ctmt = new ChiTietMuonTra();
			ctmt.setMaPhieu(result.getString("MaPhieu"));
			ctmt.setMaDG(result.getString("MaDocGia"));
			ctmt.setTenDG(result.getString("TenDocGia"));
			ctmt.setMaTL(result.getString("MaSach"));
			ctmt.setTenTL(result.getString("TenSach"));
			ctmt.setGia(result.getString("GiaBia"));
			ctmt.setNgayMuon(result.getDate("NgayMuon"));
			ctmt.setSLmuon(result.getInt("SoLuongMuon"));
			ctmt.setHanTra(result.getDate("NgayHetHan"));
			ctmt.setNgayTra(result.getDate("NgayTra"));
			ctmt.setSLtra(result.getInt("SoLuongTra"));
			ctmt.setPhatQuaHan(result.getInt("PhatQuaHan"));
			ctmt.setPhatHongMat(result.getString("PhatMat"));
			chitietmuontra.add(ctmt);
		}
		con.close();
		return chitietmuontra;
	}

}
