package SQLService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;




import javax.swing.JOptionPane;
import model.Sach;

public class SachService extends SQLServerService{
	
	public int xoaSach(Sach sach){
		int ret=JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa "+sach.getMaSach()+" này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ret==JOptionPane.YES_OPTION){
			try{
				String sql="delete from Sach where MaSach=?";
				PreparedStatement prestatement=conn.prepareStatement(sql);
				prestatement.setString(1, sach.getMaSach());
				return prestatement.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return -1;
		
	}
	
	public int suaSach(Sach sach){
		try{
			String sql="update Sach set TenSach=?,TacGia=?,TheLoai=?,NXB=?,Gia=?,TinhTrangMuon=?,SoLuong=?,isDeleted=? where MaSach=?";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, sach.getTenSach());
			prestatement.setString(2, sach.getTacGia());
			prestatement.setString(3, sach.getTheLoai());
			prestatement.setString(4, sach.getNXB());
			prestatement.setInt(5, sach.getGiaBia());
			prestatement.setString(6, sach.getTinhTrangMuon());
			prestatement.setInt(7, sach.getSoLuong());
			prestatement.setInt(8, 0);
			prestatement.setString(9, sach.getMaSach());
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	public int luuSach(Sach sach){
		try{
			String sql="insert into Sach values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, sach.getMaSach());
			prestatement.setString(2, sach.getTenSach());
			prestatement.setString(3, sach.getTacGia());
			prestatement.setString(4, sach.getTheLoai());
			prestatement.setString(5, sach.getNXB());
			prestatement.setInt(6, sach.getGiaBia());
			prestatement.setString(7, sach.getTinhTrangMuon());
			prestatement.setInt(8, sach.getSoLuong());
			prestatement.setInt(9, 0);
			return prestatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	public Vector<Sach> docToanBoSach(){
		Vector<Sach> vec=new Vector<Sach>();
		try{
			String sql="select *from Sach where isDelete=0";
			Statement statement=conn.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()){
				Sach sach=new Sach();
				sach.setMaSach(result.getString(1));
				sach.setTenSach(result.getString(2));
				sach.setTacGia(result.getString(3));
				sach.setTheLoai(result.getString(4));
				sach.setNXB(result.getString(5));
				sach.setGiaBia(result.getInt(6));
				sach.setTinhTrangMuon(result.getString(7));
				sach.setSoLuong(result.getInt(8));
				sach.setIsDeleted(0);
				vec.add(sach);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return vec;
	}
	

}
