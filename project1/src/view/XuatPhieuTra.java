package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.ChiTietMuonTra;
import IO.WritePhieuMuon;
import IO.WritePhieuTra;

public class XuatPhieuTra extends JDialog{
	JComboBox cbPM;
	DefaultTableModel dtm;
	JTable tb;
	JTextField txtMaBanDoc,txtTenBanDoc,txtTongTienPhat;
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	JButton btnIn,btnThongKe,btnTien;
	public XuatPhieuTra(String Title){
		this.setTitle(Title);
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
	
	public void MaPhieu(JComboBox cmbMaPhieu){
		try {
			Connection con=null;
			Statement stm=null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=DriverManager.getConnection("jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;");
			stm=con.createStatement();
			ResultSet rss = stm.executeQuery("SELECT DISTINCT MaPhieu from ChiTietMuonTra");
			cmbMaPhieu.addItem("-Select-");
			while (rss.next()) {
				String k = rss.getString(1); 
				cmbMaPhieu.addItem(k);
			}

		} catch (Exception ez) {
			ez.printStackTrace();
		}
	}
	
//	public void TongTienPhat(JTextField txtTienPhat){
//		try {
//			Connection con=null;
//			Statement stm=null;
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			con=DriverManager.getConnection("jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;");
//			stm=con.createStatement();
//			ResultSet rss = stm.executeQuery("SELECT sum(PhatQuaHan) from ChiTietMuonTra where MaPhieu="+cbPM.getSelectedItem()+" group by MaPhieu");
//			
//			while (rss.next()) {
//				String k=Integer.toString(result.getInt("PhatQuaHan"));
//				txtTienPhat.setText(k);
//			}
//
//		} catch (Exception ez) {
//			ez.printStackTrace();
//		}
//	}
	
	public int TongTienPhat(){
		int s=0;
		for(int i = 0; i < tb.getRowCount(); i++){
			
			String MaPhieu=tb.getValueAt(i, 0).toString();
     		if(cbPM.getSelectedItem().toString().compareTo(MaPhieu)==0){
				int tien=Integer.parseInt(tb.getValueAt(i, 11).toString());
				s+=tien;
			}
		}
		return s;
	}
	
	private void HienDuLieuDocGiaTheoMaPhieuMuon() throws SQLException{
		ChiTietMuonTra ctmt = new ChiTietMuonTra();
		ArrayList<ChiTietMuonTra> tl = ctmt.dulieumuontra();
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaPhieu().equals(cbPM.getSelectedItem())){
				txtTenBanDoc.setText(tl.get(i).getTenDG());
				txtMaBanDoc.setText(tl.get(i).getMaDG());
			}

		}		
	}
	

	private void addControls() {
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BorderLayout());
		con.add(pnMain);
		
		JPanel pnHeader=new JPanel();
		pnHeader.setLayout(new BoxLayout(pnHeader, BoxLayout.Y_AXIS));
		pnMain.add(pnHeader,BorderLayout.NORTH);
		dtm=new DefaultTableModel();
		dtm.addColumn("Mã phiếu");
		dtm.addColumn("Mã bạn đọc");
		dtm.addColumn("Tên bạn đọc");
		dtm.addColumn("Mã sách");
		dtm.addColumn("Tên sách");
		dtm.addColumn("Giá");
		dtm.addColumn("Ngày mượn");
		dtm.addColumn("Số lượng mượn");
		dtm.addColumn("Hạn trả");
		dtm.addColumn("Ngày trả");
		dtm.addColumn("Số lượng trả");
		dtm.addColumn("Tiền phạt");
		tb=new JTable(dtm);
		JScrollPane sc=new JScrollPane(tb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnMain.add(sc,BorderLayout.CENTER);
		
		JPanel pnTim=new JPanel();
		pnTim.setLayout(new FlowLayout());
		pnHeader.add(pnTim);
		JLabel lblPM=new JLabel("Chọn mã phiếu mượn:");
	    cbPM=new JComboBox<>();
	    cbPM.setPreferredSize(new Dimension(220, 20));
	    MaPhieu(cbPM);
	    pnTim.add(lblPM);
		pnTim.add(cbPM);
		
		JPanel pnMBD=new JPanel();
		pnHeader.add(pnMBD);
		JLabel lblMBD=new JLabel("Mã bạn đọc:");
		txtMaBanDoc=new JTextField(20);
		pnMBD.add(lblMBD);
		pnMBD.add(txtMaBanDoc);
		
		
		JPanel pnTenBD=new JPanel();
		pnHeader.add(pnTenBD);
		JLabel lblTenBD=new JLabel("Tên bạn đọc:");
		txtTenBanDoc=new JTextField(20);
		pnTenBD.add(lblTenBD);
		pnTenBD.add(txtTenBanDoc);
		
		JPanel pnTienPhat=new JPanel();
		pnHeader.add(pnTienPhat);
		JLabel lblTienPhat=new JLabel("Tổng phạt:");
		txtTongTienPhat=new JTextField(20);
//		txtTongTienPhat.setText(TongTienPhat()+"");
		pnTienPhat.add(lblTienPhat);
		pnTienPhat.add(txtTongTienPhat);
		
		lblMBD.setPreferredSize(new Dimension(lblPM.getPreferredSize()));
		lblTenBD.setPreferredSize(new Dimension(lblPM.getPreferredSize()));
		lblTienPhat.setPreferredSize(new Dimension(lblPM.getPreferredSize()));
		JPanel pnButton=new JPanel();
		pnMain.add(pnButton,BorderLayout.SOUTH);
		btnIn=new JButton("In phiếu");
		btnTien=new JButton("Tổng tiền");
		pnButton.add(btnIn);
		pnButton.add(btnTien);
		
	   
	}
	
	private void addEvents() {
		
		btnTien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				txtTongTienPhat.setText(Integer.toString(TongTienPhat()));
			}
		});
		cbPM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					HienDuLieuDocGiaTheoMaPhieuMuon();
					try{
						String sqlMaPhieu="select *from ChiTietMuonTra where MaPhieu=?";
						preStatement=conn.prepareStatement(sqlMaPhieu);
						preStatement.setString(1, cbPM.getSelectedItem().toString());
						result=preStatement.executeQuery();
						dtm.setRowCount(0);
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
							vec.add(result.getDate("NgayTra"));
							vec.add(result.getInt("SoLuongTra"));
							vec.add(result.getInt("PhatQuaHan"));
							dtm.addRow(vec);
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WritePhieuTra wp=new WritePhieuTra();
				wp.WriteWord(tb, cbPM, txtMaBanDoc, txtTenBanDoc,txtTongTienPhat, "Phiếu trả", "PHIẾU TRẢ SÁCH");
			}
		});
		
	}
	
	public void showWindow(){
		this.setSize(700,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}


}
