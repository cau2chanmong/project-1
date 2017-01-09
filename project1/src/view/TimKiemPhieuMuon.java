package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import IO.WriteFileWord;

public class TimKiemPhieuMuon extends JDialog{
	JTextField txtTimPhieuMuon;
	JButton btnTimPhieuMuon,btnIn;
	DefaultTableModel dtmPhieuMuon;
	JTable tbPhieuMuon;
	JComboBox cmbTimKiemPhieuMuon;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public TimKiemPhieuMuon(String title) {
		this.setTitle(title);
		addControls();
		addEvents();
		ketNoiCoSoDuLieu();
	}
	
	private void ketNoiCoSoDuLieu() {
		try{
 			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl=
					"jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;";
			conn= DriverManager.getConnection(connectionUrl);
			
 		}catch(Exception ex){
 			ex.printStackTrace();
 		}
	}
	
	
	public void addControls(){
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		con.add(pnMain);
		
		JPanel pnTimKiemPhieuMuon=new JPanel();
		pnTimKiemPhieuMuon.setLayout(new BorderLayout());
		pnMain.add(pnTimKiemPhieuMuon);
		dtmPhieuMuon=new DefaultTableModel();
		dtmPhieuMuon.addColumn("Mã phiếu");
		dtmPhieuMuon.addColumn("Mã bạn đọc");
		dtmPhieuMuon.addColumn("Tên bạn đọc");
		dtmPhieuMuon.addColumn("Mã sách");
		dtmPhieuMuon.addColumn("Tên sách");
		dtmPhieuMuon.addColumn("Giá bìa");
		dtmPhieuMuon.addColumn("Ngày mượn");
		dtmPhieuMuon.addColumn("Số lượng mượn");
		dtmPhieuMuon.addColumn("Hạn trả");
		
		tbPhieuMuon=new JTable(dtmPhieuMuon);
		JScrollPane sc=new JScrollPane(tbPhieuMuon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTimKiemPhieuMuon.add(sc,BorderLayout.CENTER);
		
		JPanel pnTim=new JPanel();
		pnTim.setLayout(new FlowLayout());
		con.add(pnTim,BorderLayout.NORTH);
	    cmbTimKiemPhieuMuon=new JComboBox<>();
	    cmbTimKiemPhieuMuon.addItem("Tất cả");
	    cmbTimKiemPhieuMuon.addItem("Mã phiếu");
	    cmbTimKiemPhieuMuon.addItem("Mã bạn đọc");
	    cmbTimKiemPhieuMuon.addItem("Tên bạn đọc");
	    cmbTimKiemPhieuMuon.addItem("Mã sách");
	    cmbTimKiemPhieuMuon.addItem("Tên sách");
	    cmbTimKiemPhieuMuon.addItem("Giá bìa");
	    cmbTimKiemPhieuMuon.addItem("Ngày mượn");
	    cmbTimKiemPhieuMuon.addItem("Số lượng mượn");
	    cmbTimKiemPhieuMuon.addItem("Hạn trả");
		txtTimPhieuMuon=new JTextField(15);
		btnTimPhieuMuon=new JButton("Tìm kiếm phiếu mượn");
		pnTim.add(cmbTimKiemPhieuMuon);
		pnTim.add(txtTimPhieuMuon);
		pnTim.add(btnTimPhieuMuon);
		pnTimKiemPhieuMuon.add(pnTim,BorderLayout.NORTH);
		
		JPanel pnIn=new JPanel();
	    btnIn=new JButton("Xuất file");
	    pnIn.add(btnIn);
	    pnTimKiemPhieuMuon.add(pnIn,BorderLayout.SOUTH);
	}
	
	public void addEvents(){
		btnTimPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiTimKiemPhieuMuon();
				
			}
		});
		
		btnIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WriteFileWord wf=new WriteFileWord();
				wf.WriteWord(tbPhieuMuon, "Tìm kiếm phiếu mượn","Tìm kiếm phiếu mượn");
			}
		});
	}
	
	protected void xuLiTimKiemPhieuMuon() {
		String option=cmbTimKiemPhieuMuon.getSelectedItem().toString();
		String txt_search=txtTimPhieuMuon.getText().trim();
		switch(option){
		case "Tất cả":
		{
			try{
				String sql="select *from ChiTietMuonTra";
				preStatement=conn.prepareStatement(sql);
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					if(vec.toString().contains(txt_search)){
						dtmPhieuMuon.addRow(vec);
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Mã phiếu":
		{
			try{
				String sqlMaPhieu="select *from ChiTietMuonTra where MaPhieu like ?";
				preStatement=conn.prepareStatement(sqlMaPhieu);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Mã bạn đọc":
		{
			try{
				String sqlMaBanDoc="select *from ChiTietMuonTra where MaDocGia like ?";
				preStatement=conn.prepareStatement(sqlMaBanDoc);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Tên bạn đọc":
		{
			try{
				String sqlTenBanDoc="select *from ChiTietMuonTra where TenDocGia like ?";
				preStatement=conn.prepareStatement(sqlTenBanDoc);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Mã sách":
		{
			try{
				String sqlMaSach="select *from ChiTietMuonTra where MaSach like ?";
				preStatement=conn.prepareStatement(sqlMaSach);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Tên sách":
		{
			try{
				String sqlTenSach="select *from ChiTietMuonTra where TenSach like ?";
				preStatement=conn.prepareStatement(sqlTenSach);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Giá bìa":
		{
			try{
				String sqlGiaBia="select *from ChiTietMuonTra where GiaBia like ?";
				preStatement=conn.prepareStatement(sqlGiaBia);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Ngày mượn":
		{
			try{
				String sqlNgayMuon="select *from ChiTietMuonTra where NgayMuon like ?";
				preStatement=conn.prepareStatement(sqlNgayMuon);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Số lượng mượn":
		{
			try{
				String sqlSLMuon="select *from ChiTietMuonTra where SoLuongMuon like ?";
				preStatement=conn.prepareStatement(sqlSLMuon);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Hạn trả":
		{
			try{
				String sqlHanTra="select *from ChiTietMuonTra where NgayHetHan like ?";
				preStatement=conn.prepareStatement(sqlHanTra);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmPhieuMuon.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaPhieu"));
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("TenDocGia"));
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getInt("GiaBia"));
					vec.add(result.getDate("NgayMuon"));
					vec.add(result.getInt("SoLuongMuon"));
					vec.add(result.getDate("NgayHetHan"));
					dtmPhieuMuon.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		}
	}

	public void showWindow(){
		this.setSize(700,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}


}
