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

public class TimKiemBandoc extends JDialog{
	JTextField txtTimBanDoc;
	JButton btnTimBanDoc,btnIn;
	DefaultTableModel dtmBandoc;
	JTable tbBanDoc;
	JTabbedPane tabTimKiemBanDoc;
	JComboBox cmbTimKiemBanDoc;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public TimKiemBandoc(String title){
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
		
		JPanel pnTimKiemBanDoc=new JPanel();
		pnTimKiemBanDoc.setLayout(new BorderLayout());
		pnMain.add(pnTimKiemBanDoc);
		dtmBandoc=new DefaultTableModel();
		dtmBandoc.addColumn("Mã bạn đọc");
		dtmBandoc.addColumn("Họ tên");
		dtmBandoc.addColumn("Số điện thoại");
		dtmBandoc.addColumn("Email");
		dtmBandoc.addColumn("Ngày sinh");
		dtmBandoc.addColumn("Quê quán");
		dtmBandoc.addColumn("Giới tính");
		
		tbBanDoc=new JTable(dtmBandoc);
		JScrollPane sc=new JScrollPane(tbBanDoc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTimKiemBanDoc.add(sc,BorderLayout.CENTER);
		
		JPanel pnTim=new JPanel();
		pnTim.setLayout(new FlowLayout());
		con.add(pnTim,BorderLayout.NORTH);
	    cmbTimKiemBanDoc=new JComboBox<>();
	    cmbTimKiemBanDoc.addItem("Tất cả");
	    cmbTimKiemBanDoc.addItem("Mã bạn đọc");
	    cmbTimKiemBanDoc.addItem("Họ tên");
	    cmbTimKiemBanDoc.addItem("SĐT");
	    cmbTimKiemBanDoc.addItem("Email");
	    cmbTimKiemBanDoc.addItem("Ngày sinh");
	    cmbTimKiemBanDoc.addItem("Quê quán");
		txtTimBanDoc=new JTextField(15);
		btnTimBanDoc=new JButton("Tìm kiếm bạn đọc");
		pnTim.add(cmbTimKiemBanDoc);
		pnTim.add(txtTimBanDoc);
		pnTim.add(btnTimBanDoc);
		pnTimKiemBanDoc.add(pnTim,BorderLayout.NORTH);
		
	    JPanel pnIn=new JPanel();
	    btnIn=new JButton("Xuất file");
	    pnIn.add(btnIn);
	    pnTimKiemBanDoc.add(pnIn,BorderLayout.SOUTH);
	}
	
	public void addEvents(){
		btnTimBanDoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiTimKiemBanDoc();
				
			}
		});
		
		btnIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WriteFileWord wf=new WriteFileWord();
				wf.WriteWord(tbBanDoc, "Tìm kiếm thông tin sách","Tìm kiếm thông tin sách");
			}
		});
	}
	
	protected void xuLiTimKiemBanDoc() {
		String option=cmbTimKiemBanDoc.getSelectedItem().toString();
		String txt_search=txtTimBanDoc.getText().trim();
		switch(option){
		case "Tất cả":
		{
			try{
				String sql="select *from DocGia";
				preStatement=conn.prepareStatement(sql);
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					if(vec.toString().contains(txt_search)){
						dtmBandoc.addRow(vec);
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Mã bạn đọc":
		{
			try{
				String sqlMaBanDoc="select *from DocGia where MaDocGia like ?";
				preStatement=conn.prepareStatement(sqlMaBanDoc);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Họ tên":
		{
			try{
				String sqlTenBanDoc="select *from DocGia where HoTen like ?";
				preStatement=conn.prepareStatement(sqlTenBanDoc);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "SĐT":
		{
			try{
				String sqlSDT="select *from DocGia where SoDT like ?";
				preStatement=conn.prepareStatement(sqlSDT);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Email":
		{
			try{
				String sqlEmail="select *from DocGia where Email like ?";
				preStatement=conn.prepareStatement(sqlEmail);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Ngày sinh":
		{
			try{
				String sqlNgaySinh="select *from DocGia where NgaySinh like ?";
				preStatement=conn.prepareStatement(sqlNgaySinh);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Quê quán":
		{
			try{
				String sqlQueQuan="select *from DocGia where QueQuan like ?";
				preStatement=conn.prepareStatement(sqlQueQuan);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			break;
		}
		case "Giới tính":
		{
			try{
				String sqlGioiTinh="select *from DocGia where GioiTinh like ?";
				preStatement=conn.prepareStatement(sqlGioiTinh);
				preStatement.setString(1, "%"+txt_search+"%");
				result=preStatement.executeQuery();
				dtmBandoc.setRowCount(0);
				while(result.next()){
					Vector<Object> vec=new Vector<Object>();
					vec.add(result.getString("MaDocGia"));
					vec.add(result.getString("HoTen"));
					vec.add(result.getString("SoDT"));
					vec.add(result.getString("Email"));
					vec.add(result.getDate("NgaySinh"));
					vec.add(result.getString("QueQuan"));
					vec.add(result.getString("GioiTinh"));
					dtmBandoc.addRow(vec);
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
