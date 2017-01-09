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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import IO.WriteFileWord;

public class TimKiemSach extends JDialog{
	JTextField txtTim;
	JButton btnTim,btnIn;
	DefaultTableModel dtmSach;
	JTable tbSach;
	JTabbedPane tabTimKiem;
	JComboBox cmbTimKiemSach;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public TimKiemSach(String title){
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
		
		JPanel pnTimKiemSach=new JPanel();
		pnTimKiemSach.setLayout(new BorderLayout());
		pnMain.add(pnTimKiemSach);
		dtmSach=new DefaultTableModel();
		dtmSach.addColumn("Mã sách");
		dtmSach.addColumn("Tên sách");
		dtmSach.addColumn("Tác giả");
		dtmSach.addColumn("Thể loại");
		dtmSach.addColumn("NXB");
		dtmSach.addColumn("Giá bìa");
		dtmSach.addColumn("Tình trạng mượn");
		dtmSach.addColumn("Số lượng");
		
		tbSach=new JTable(dtmSach);
		JScrollPane sc=new JScrollPane(tbSach, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTimKiemSach.add(sc,BorderLayout.CENTER);
		
		JPanel pnTim=new JPanel();
		pnTim.setLayout(new FlowLayout());
		con.add(pnTim,BorderLayout.NORTH);
	    cmbTimKiemSach=new JComboBox<>();
	    cmbTimKiemSach.addItem("Tất cả");
	    cmbTimKiemSach.addItem("Mã sách");
	    cmbTimKiemSach.addItem("Tên sách");
	    cmbTimKiemSach.addItem("Tác giả");
	    cmbTimKiemSach.addItem("Thể loại");
	    cmbTimKiemSach.addItem("NXB");
	    
	    cmbTimKiemSach.addItem("Tình trạng");
	    cmbTimKiemSach.addItem("Số lượng");
		txtTim=new JTextField(15);
		btnTim=new JButton("Tìm kiếm sách");
		pnTim.add(cmbTimKiemSach);
		pnTim.add(txtTim);
		pnTim.add(btnTim);
		pnTimKiemSach.add(pnTim,BorderLayout.NORTH);
		JPanel pnTimKiemBanDoc=new JPanel();
		JPanel pnTimKiemMuonTra=new JPanel();
		
		JPanel pnIn=new JPanel();
	    btnIn=new JButton("Xuất file");
	    pnIn.add(btnIn);
	    pnTimKiemSach.add(pnIn,BorderLayout.SOUTH);
	}
	
	public void addEvents(){
		btnTim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiTimKiemSach();
				
			}
		});
		
		btnIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WriteFileWord wf=new WriteFileWord();
				wf.WriteWord(tbSach, "Tìm kiếm thông tin sách","Tìm kiếm thông tin sách");
			}
		});
	}
	
	protected void xuLiTimKiemSach() {
		String option=cmbTimKiemSach.getSelectedItem().toString();
		String txt_search=txtTim.getText().trim();
		switch(option){
		case "Tất cả":
		{
			try{
				String sql="select *from Sach";
				preStatement=conn.prepareStatement(sql);
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					if(vec.toString().contains(txt_search)){
						dtmSach.addRow(vec);
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Mã sách":
		{
			try{
				String sqlMaSach="select *from Sach where MaSach like ?";
				preStatement=conn.prepareStatement(sqlMaSach);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Tên sách":
		{
			try{
				String sqlTenSach="select *from Sach where TenSach like ?";
				preStatement=conn.prepareStatement(sqlTenSach);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Tác giả":
		{
			try{
				String sqlTacGia="select *from Sach where TacGia like ?";
				preStatement=conn.prepareStatement(sqlTacGia);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Thể loại":
		{
			try{
				String sqlTheLoai="select *from Sach where TheLoai like ?";
				preStatement=conn.prepareStatement(sqlTheLoai);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "NXB":
		{
			try{
				String sqlNXB="select *from Sach where NXB like ?";
				preStatement=conn.prepareStatement(sqlNXB);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Tình trạng":
		{
			try{
				String sqlTinhTrang="select *from Sach where TinhTrangMuon like ?";
				preStatement=conn.prepareStatement(sqlTinhTrang);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Số lượng":
		{
			try{
				String sqlSoLuong="select *from Sach where SoLuong like ?";
				preStatement=conn.prepareStatement(sqlSoLuong);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmSach.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaSach"));
					vec.add(result.getString("TenSach"));
					vec.add(result.getString("TacGia"));
					vec.add(result.getString("TheLoai"));
					vec.add(result.getString("NXB"));
					vec.add(result.getInt("Gia"));
					vec.add(result.getString("TinhTrangMuon"));
					vec.add(result.getInt("SoLuong"));
					dtmSach.addRow(vec);
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
