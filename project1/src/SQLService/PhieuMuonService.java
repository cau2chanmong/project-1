package SQLService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import model.BanDoc;
import model.ChiTietMuonTra;
import model.PhieuMuon;



public class PhieuMuonService extends SQLServerService{
	public void ComboMaDocGia(JComboBox comboBanDoc){
		try{
			String sql="select MaDocGia from DocGia";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			ResultSet result=prestatement.executeQuery();
			comboBanDoc.addItem("Chọn mã độc giả");
			while(result.next()){
				String k=result.getString(1);
				comboBanDoc.addItem(k);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	

	public int xoaPhieuMuon(ChiTietMuonTra pm){
		int ret=JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa "+pm.getMaPhieu()+" này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ret==JOptionPane.YES_OPTION){
			try{
				String sql="delete from ChiTietMuonTra where MaPhieu=? ";
				PreparedStatement prestatement=conn.prepareStatement(sql);
				prestatement.setString(1, pm.getMaPhieu());
				return prestatement.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return -1;
		
	}
	
	public int suaPhieuMuon(PhieuMuon pm){
		try{
			java.sql.Date ngay_muon=new java.sql.Date(pm.getNgayMuon().getTime());
			String sql="update PhieuMuon set MaSach=?,MaDocGia=?,NgayMuon=?,SoLuong=?,TinhTrang=? where MaPhieu=?";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, pm.getMaSach());
			prestatement.setString(2, pm.getMaBanDoc());
			prestatement.setDate(3,ngay_muon);
			prestatement.setInt(4, pm.getSoLuong());
			prestatement.setString(5, pm.getTinhTrang());
			prestatement.setString(6, pm.getMaPhieu());
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	public int luuPhieuMuon(PhieuMuon pm){
		try{
			java.sql.Date ngay_muon=new java.sql.Date(pm.getNgayMuon().getTime());
			String sql="insert into PhieuMuon values(?,?,?,?,?,?)";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, pm.getMaPhieu());
			prestatement.setString(2, pm.getMaSach());
			prestatement.setString(3, pm.getMaBanDoc());
			prestatement.setDate(4, ngay_muon);
			prestatement.setInt(5, pm.getSoLuong());
			prestatement.setString(6, pm.getTinhTrang());
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	public Vector<PhieuMuon> docDuLieuPhieuMuon(){
		Vector<PhieuMuon> vec=new Vector<PhieuMuon>();
		try{
			String sql="select *from PhieuMuon";
			Statement statement=conn.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()){
				PhieuMuon pm=new PhieuMuon();
				pm.setMaPhieu(result.getString(1));
				pm.setMaSach(result.getString(2));
				pm.setMaBanDoc(result.getString(3));
			    pm.setNgayMuon(result.getDate(4));
				pm.setSoLuong(result.getInt(5));
				pm.setTinhTrang(result.getString(6));
				vec.add(pm);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return vec;
	}
	
	public void InPhieuMuon() throws IOException{
		XWPFDocument document=new XWPFDocument();
		FileOutputStream out=new FileOutputStream(new File("nam.docx"));
		XWPFParagraph paragraph=document.createParagraph();
		XWPFRun run=paragraph.createRun();
		run.setFontSize(20);
		run.setBold(true);
		run.setText("Phiếu mượn");
		document.write(out);
		out.close();
		
	}

}



