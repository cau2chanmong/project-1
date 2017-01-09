package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.jws.Oneway;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;

import IO.WritePhieuMuon;
import SQLService.BanDocService;
import SQLService.ConnectionSQL;
import SQLService.PhieuMuonService;
import SQLService.SachService;
import model.BanDoc;
import model.ChiTietMuonTra;
import model.PhieuMuon;
import model.Sach;

public class FormQuanLiThuVien extends JFrame{
	private final static String FILE = "C:\\Users\\NGHIA\\Desktop\\hinhanhjava\\data\\";
	public static File fontFile = new File("C:\\Users\\Nam\\Downloads\\Compressed\\VietFontsWeb1_ttf\\vuArial.ttf");
	
	XSSFWorkbook wb;

	JTabbedPane tab;
	JTextField txtMaSach,txtTenSach,txtTheLoai,txtDonGia,txtSoLuong,txtTacGia,txtNXB,txtTinhTrang;
	JTextField txtMaBanDoc,txtHoTen,txtTuoi,txtSoDienThoai,txtNgaySinh,txtQueQuan,txtGioiTinh,txtEmail;
	JTextField txtMaPhieuMuon,txtMaSachCanMuon,txtTenSachMuon,txtTenBanDocMuon,txtMaBanDocMuon,txtNgayMuon,txtSoLuongMuon,txtTinhTrangSach,txtGiaBiaSachMuon,txtNgayHetHan;
	JTextField txtMaSachTra,txtMaBanDocTra,txtTenSachTra,txtNgayMuonSach,txtNgayTra,txtSoLuongTra,txtTinhTrangSachTra,txtNgayHetHanTra,txtSoLuongMuonSach,txtPhatQuaHan,txtPhatMat,txtGiaBiaSachTra,txtTenBanDocTra;
	JTextField txtTimKiemSach,txtTimKiemBanDoc,txtTimKiemPhieuMuon;
	JRadioButton rCon,rHet,rNam,rNu;
	ButtonGroup groupTinhTrang,groupGioiTinh;
	JButton btnLuuSach,btnXoaSach,btnTaoMoiSach,btnXuatFileSach;
	DefaultTableModel dtmSach,dtmBanDoc,dtmPhieuMuon,dtmTra;
	JTable tbSach,tbBanDoc,tbPhieuMuon,tbTra;
	JButton btnTimKiemSach,btnTimKiemBanDoc,btnTimKiemPhieuMuon;
	JButton btnTaoMoiBanDoc,btnLuuBanDoc,btnXoaBanDoc;
	JButton btnTaoMoiPhieuMuon,btnLuuPhieuMuon,btnXoaPhieuMuon,btnSuaPhieuMuon,btnXemPhieuMuon;
	JButton btnTaoMoiTraSach,btnLuuTraSach,btnXoaTraSach,btnPhatMuonQuaHan;
	JButton btnThemFileBanDoc,btnThemFilePhieuMuon,btnInPhieuMuon;
	JButton btnChonThongKe,btnChonBieuMau,btnTinhTienPhat,btnChonTimKiem;
	JButton btnCapNhatPhieuMuon,btnCapNhatTra,btnInPhieuTra;
	JComboBox comboBanDoc;
	JComboBox comboSach;
	JComboBox comboMaSachTra,comboMaPhieuMuon;
	JComboBox comboThongKeTheoSach;
	JComboBox comboThongKeTheoPhieuMuon;
	JComboBox comboTimKiem;
	Connection conn=null;
	PreparedStatement preStatement=null;
	Statement st=null;
	ResultSet result;
	

	public FormQuanLiThuVien(String title){
		super(title);
		addControls();
		addEvents();
		ketNoiCoSoDuLieu();
		hienThiToanBoThongTinSach();
		hienThiToanBoThongTinBanDoc();
		hienThiToanBoThongTinPhieuMuon();
		HienThiToanBoThongTinTraSach();
		
		
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
			ResultSet rss = stm.executeQuery("SELECT DISTINCT MaPhieu from ChiTietMuonTra WHERE NgayTra is NULL");
			cmbMaPhieu.addItem("-Select-");
			while (rss.next()) {
				String k = rss.getString(1); 
				cmbMaPhieu.addItem(k);
			}

		} catch (Exception ez) {
			ez.printStackTrace();
		}

	}
	
	private void HienDuLieuTheoMaSachTra() throws SQLException{
		ChiTietMuonTra ctmt = new ChiTietMuonTra();
		ArrayList<ChiTietMuonTra> tl = ctmt.dulieumuontra();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaTL().equals(comboMaSachTra.getSelectedItem())){
				txtTenSachTra.setText(tl.get(i).getTenTL());
				txtGiaBiaSachTra.setText(tl.get(i).getGia());
				txtSoLuongMuonSach.setText(tl.get(i).getSLmuon()+"");
				txtNgayMuonSach.setText(sdf.format(tl.get(i).getNgayMuon()));
				txtNgayHetHanTra.setText(sdf.format(tl.get(i).getHanTra()));
				txtSoLuongTra.setText(tl.get(i).getSLtra()+"");
//				txtPhatQuaHan.setText(tl.get(i).getPhatQuaHan()+"");
//				txtPhatMat.setText(tl.get(i).getPhatHongMat());
			}

		}
	}
	
	private void HienDuLieuDocGiaTheoMaPhieuMuon() throws SQLException{
		ChiTietMuonTra ctmt = new ChiTietMuonTra();
		ArrayList<ChiTietMuonTra> tl = ctmt.dulieumuontra();
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaPhieu().equals(comboMaPhieuMuon.getSelectedItem())){
				txtTenBanDocTra.setText(tl.get(i).getTenDG());
				txtMaBanDocTra.setText(tl.get(i).getMaDG());
			}

		}		
	}
	
	


	public void hienThiChiTiet(String ma){
		try{
			String sql="select *from Sach where MaSach=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, ma);
			ResultSet rs=preStatement.executeQuery();
			if(rs.next()){
				txtMaSach.setText(rs.getString(1));
				txtTenSach.setText(rs.getString(2));
				txtTacGia.setText(rs.getString(3));
				txtTheLoai.setText(rs.getString(4));
				txtNXB.setText(rs.getString(5));
				txtDonGia.setText(rs.getInt(6)+"");
				txtTinhTrang.setText(rs.getString(7));
				txtSoLuong.setText(rs.getInt(8)+"");
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void hienThiChiTietTraSach(String MaPhieu,String MaSach){
		try{
			String sql="select *from ChiTietMuonTra where (MaPhieu=? AND MaSach=?)";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, MaPhieu);
			preStatement.setString(2, MaSach);
			ResultSet rs=preStatement.executeQuery();
			if(rs.next()){
				comboMaPhieuMuon.setSelectedItem(rs.getString(1));
				txtMaBanDocTra.setText(rs.getString(2));
				txtTenBanDocTra.setText(rs.getString(3));
				comboMaSachTra.setSelectedItem(rs.getString(4));
				txtTenSachTra.setText(rs.getString(5));
				txtGiaBiaSachTra.setText(rs.getString(6));
				txtNgayMuonSach.setText(rs.getString(7));
				txtSoLuongTra.setText(rs.getString(8));
				txtNgayHetHanTra.setText(rs.getString(9));
				
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void hienThiChiTietBanDoc(String maBanDoc){
		try{
			String sql="select *from DocGia where MaDocGia=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, maBanDoc);
			ResultSet rs=preStatement.executeQuery();
			if(rs.next()){
				txtMaBanDoc.setText(rs.getString(1));
				txtHoTen.setText(rs.getString(2));
				txtSoDienThoai.setText(rs.getString(3));
				txtEmail.setText(rs.getString(4));
				txtNgaySinh.setText(rs.getDate(5).toString());
				txtQueQuan.setText(rs.getString(6));
				txtGioiTinh.setText(rs.getString(7));
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void hienThiToanBoThongTinSach() {

		try{
			String sql="select *from Sach";
			preStatement=conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			dtmSach.setRowCount(0);
			while(result.next()){
				Vector<Object> vec=new Vector<Object>();
				vec.add(result.getString(1));
				vec.add(result.getString(2));
				vec.add(result.getString(3));
				vec.add(result.getString(4));
				vec.add(result.getString(5));
				vec.add(result.getInt(6));
				vec.add(result.getString(7));
				vec.add(result.getInt(8));
				dtmSach.addRow(vec);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void hienThiToanBoThongTinBanDoc(){
		try{
			String sql="select *from DocGia";
			preStatement=conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			dtmBanDoc.setRowCount(0);
			while(result.next()){
				Vector<Object> vec=new Vector<Object>();
				vec.add(result.getString(1));
				vec.add(result.getString(2));
				vec.add(result.getString(3));
				vec.add(result.getString(4));
				vec.add(result.getDate(5));
				vec.add(result.getString(6));
				vec.add(result.getString(7));
				dtmBanDoc.addRow(vec);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//		dtmBanDoc.setRowCount(0);
		//		try{
		//			BanDoc bd=new BanDoc();
		//			dtmBanDoc.addRow(bd.DuLieuBanDoc());
		//		}catch(Exception ex){
		//			ex.printStackTrace();
		//		}
	}
	
	public void HienThiToanBoThongTinTraSach(){
		try{
			String sql="select *from ChiTietMuonTra";
			preStatement=conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			dtmTra.setRowCount(0);
			while(result.next()){
				Vector<Object> vec=new Vector<Object>();
				vec.add(result.getString(1));
				vec.add(result.getString(2));
				vec.add(result.getString(3));
				vec.add(result.getString(4));
				vec.add(result.getString(5));
				vec.add(result.getInt(6));
				vec.add(result.getDate(7));
				vec.add(result.getInt(8));
				vec.add(result.getDate(9));
				vec.add(result.getDate(10));
				vec.add(result.getString(11));
				vec.add(result.getString(12));
				vec.add(result.getString(13));
				dtmTra.addRow(vec);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void hienThiChiTietPhieuMuon(String maPhieuMuon,String maSachMuon){
		try{
			String sql="select *from ChiTietMuonTra where (MaPhieu=? AND MaSach=?)";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, maPhieuMuon);
			preStatement.setString(2, maSachMuon);
			ResultSet rs=preStatement.executeQuery();
			if(rs.next()){
				txtMaPhieuMuon.setText(rs.getString(1));
				comboBanDoc.setSelectedItem(rs.getString(2));
				txtTenBanDocMuon.setText(rs.getString(3));
				comboSach.setSelectedItem(rs.getString(4));
				txtTenSachMuon.setText(rs.getString(5));
				txtGiaBiaSachMuon.setText(rs.getString(6));
				txtNgayMuon.setText(rs.getString(7));
				txtSoLuongMuon.setText(rs.getString(8));
				txtNgayHetHan.setText(rs.getString(9));
				
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void hienThiToanBoThongTinPhieuMuon(){
		try{
			String sql="select *from ChiTietMuonTra";
			preStatement=conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			dtmPhieuMuon.setRowCount(0);
			while(result.next()){
				Vector<Object> vec=new Vector<Object>();
				vec.add(result.getString(1));
				vec.add(result.getString(2));
				vec.add(result.getString(3));
				vec.add(result.getString(4));
				vec.add(result.getString(5));
				vec.add(result.getInt(6));
				vec.add(result.getDate(7));
				vec.add(result.getInt(8));
				vec.add(result.getDate(9));
				dtmPhieuMuon.addRow(vec);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void MaBandoc(JComboBox cmbBanDoc){
		try {

			Connection con=null;
			Statement stm=null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=DriverManager.getConnection("jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;");
			stm=con.createStatement();
			ResultSet rss = stm.executeQuery("SELECT DISTINCT MaDocGia from DocGia");
			cmbBanDoc.addItem("-Select-");
			while (rss.next()) {
				String k = rss.getString(1); 
				cmbBanDoc.addItem(k);
			}

		} catch (Exception ez) {
			ez.printStackTrace();
		}

	}
	
	public void MaTaiLieuMuon(JComboBox cmbMaTaiLieuMuon){
		try {

			Connection con=null;
			Statement stm=null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=DriverManager.getConnection("jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;");
			stm=con.createStatement();
			ResultSet rss = stm.executeQuery("SELECT DISTINCT MaSach from Sach");
			cmbMaTaiLieuMuon.addItem("-Select-");
			while (rss.next()) {
				String k = rss.getString(1); 
				cmbMaTaiLieuMuon.addItem(k);
			}

		} catch (Exception ez) {
			ez.printStackTrace();
		}

	}
	
	public void MaSach(JComboBox cmbMaSach){
		try {

			Connection con=null;
			Statement stm=null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=DriverManager.getConnection("jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;");
			stm=con.createStatement();
			ResultSet rss = stm.executeQuery("SELECT DISTINCT MaSach from Sach");
			cmbMaSach.addItem("-Select-");
			while (rss.next()) {
				String k = rss.getString(1); 
				cmbMaSach.addItem(k);
			}

		} catch (Exception ez) {
			ez.printStackTrace();
		}

	}
	
	public void hienThiDuLieuChiTietMuonTra() throws SQLException{
		ChiTietMuonTra ctmt = new ChiTietMuonTra();
		ArrayList<ChiTietMuonTra> tl = ctmt.dulieumuontra();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaTL().equals(comboMaSachTra.getSelectedItem())){
				txtTenSachTra.setText(tl.get(i).getTenTL());
				txtGiaBiaSachTra.setText(tl.get(i).getGia());
				txtSoLuongMuonSach.setText(tl.get(i).getSLmuon()+"");
				txtNgayMuonSach.setText(sdf.format(tl.get(i).getNgayMuon()));
				txtNgayHetHan.setText(sdf.format(tl.get(i).getHanTra()));
				txtSoLuongTra.setText(tl.get(i).getSLtra()+"");
//				txtPhatQuaHan.setText(tl.get(i).getPhatQuaHan()+"");
//				txtPhatMat.setText(tl.get(i).getPhatHongMat());
			}

		}

	}
	
	public void HienThiThongTinDocGia() throws SQLException{
		ChiTietMuonTra ctmt = new ChiTietMuonTra();
		ArrayList<ChiTietMuonTra> tl = ctmt.dulieumuontra();
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaPhieu().equals(comboMaPhieuMuon.getSelectedItem())){
				txtTenBanDocTra.setText(tl.get(i).getTenDG());
				txtMaBanDocTra.setText(tl.get(i).getMaDG());
			}

		}		

	}
	
	public void XuLiMuonTraTaiLieu(ChiTietMuonTra dg) throws SQLException{
		dg.setMaPhieu((String)comboMaPhieuMuon.getSelectedItem());
		dg.setMaTL((String)comboMaSachTra.getSelectedItem());
		dg.setMaDG(txtMaBanDocTra.getText());
		dg.setTenDG(txtTenBanDocTra.getText());
		dg.setGia(txtGiaBiaSachTra.getText());
		dg.setSLmuon(Integer.parseInt(txtSoLuongMuonSach.getText()));
		dg.setSLtra(Integer.parseInt(txtSoLuongTra.getText()));
//		dg.setPhatQuaHan(Integer.parseInt(txtPhatQuaHan.getText()));
//		dg.setPhatHongMat(txtPhatMat.getText());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			dg.setNgayMuon(sdf.parse(txtNgayMuon.getText()));
			dg.setHanTra(sdf.parse(txtNgayHetHan.getText()));
			dg.setNgayTra(sdf.parse(txtNgayTra.getText()));
		}catch(ParseException e){
			e.printStackTrace();
		}
		tra(dg);

		
	}
	
	private void hien_TenDG() throws SQLException{
		BanDoc docgia = new BanDoc();
		ArrayList<BanDoc> dg = docgia.dulieudocgia();
		for(int i = 0; i < dg.size(); i++){
			if(dg.get(i).getMaBanDoc().equals(comboBanDoc.getSelectedItem())){
				txtTenBanDocMuon.setText(dg.get(i).getHoTen());
			}

		}		
	}
	private void hien_TenTL() throws SQLException{
		Sach sach = new Sach();
		ArrayList<Sach> tl = sach.dulieusach();
		for(int i = 0; i < tl.size(); i++){
			if(tl.get(i).getMaSach().equals(comboSach.getSelectedItem())){
				txtTenSachMuon.setText(tl.get(i).getTenSach());
				txtGiaBiaSachMuon.setText(tl.get(i).getGiaBia()+"");
			}

		}		
	}

	private void tra(ChiTietMuonTra tra) throws SQLException{
		java.sql.Date ngay_muon = new java.sql.Date(tra.getNgayMuon().getTime());
		java.sql.Date han_tra = new java.sql.Date(tra.getHanTra().getTime());
		java.sql.Date ngay_tra = new java.sql.Date(tra.getNgayTra().getTime());
		String sql = "UPDATE ChiTietMuonTra SET NgayTra=?, SoLuongTra=? WHERE MaPhieu = '"+comboMaPhieuMuon.getSelectedItem()+"' AND MaSach = '"+comboMaSachTra.getSelectedItem()+"'";
		try{
			PreparedStatement prestate = conn.prepareStatement(sql);;
			prestate.setDate(1, ngay_tra);
			prestate.setInt(2, tra.getSLtra());
//			prestate.setInt(3, tra.getPhatQuaHan());
//			prestate.setString(3, tra.getPhatHongMat());
			prestate.executeUpdate();
		}catch (SQLException e) {
			System.err.println(e.getMessage( ));
		}

	}
	
	private void XuLiTraSach() throws ParseException, SQLException{
		ChiTietMuonTra dg=new ChiTietMuonTra();

		dg.setMaPhieu((String)comboMaPhieuMuon.getSelectedItem());
		dg.setMaTL((String)comboMaSachTra.getSelectedItem());
		dg.setMaDG(txtMaBanDocTra.getText());
		dg.setTenDG(txtTenBanDocTra.getText());
		dg.setGia(txtGiaBiaSachTra.getText());
		dg.setSLmuon(Integer.parseInt(txtSoLuongMuonSach.getText()));
		dg.setSLtra(Integer.parseInt(txtSoLuongTra.getText()));
//		dg.setPhatQuaHan(Integer.parseInt(txtPhatQuaHan.getText()));
//		dg.setPhatHongMat(txtPhatMat.getText());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			dg.setNgayMuon(sdf.parse(txtNgayMuonSach.getText()));
			dg.setHanTra(sdf.parse(txtNgayHetHanTra.getText()));
			dg.setNgayTra(sdf.parse(txtNgayTra.getText()));
		}catch(ParseException e){
			e.printStackTrace();
		}
		TraSach(dg);
	}
	//đưa dữ liệu lấy được từ textfield vào cơ sở dữ liệu
	private void TraSach(ChiTietMuonTra tra) throws SQLException{
		ConnectionSQL obj = new ConnectionSQL();
		Connection con = obj.connect();
		java.sql.Date ngay_muon = new java.sql.Date(tra.getNgayMuon().getTime());
		java.sql.Date han_tra = new java.sql.Date(tra.getHanTra().getTime());
		java.sql.Date ngay_tra = new java.sql.Date(tra.getNgayTra().getTime());
		String sql = "UPDATE ChiTietMuonTra SET NgayTra=?, SoLuongTra=?,PhatMat=? WHERE MaPhieu = '"+comboMaPhieuMuon.getSelectedItem()+"' AND MaSach = '"+comboMaSachTra.getSelectedItem()+"'";
		try{
			PreparedStatement prestate = con.prepareStatement(sql);;
			prestate.setDate(1, ngay_tra);
			prestate.setInt(2, tra.getSLtra());
//			prestate.setInt(3, tra.getPhatQuaHan());
			prestate.setString(3, tra.getPhatHongMat());
			prestate.executeUpdate();
			con.close();
		}catch (SQLException e) {
			System.err.println(e.getMessage( ));
		}

	}



	public void addControls(){
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		con.add(pnMain);
		tab=new JTabbedPane();
		pnMain.add(tab);

		/*---------------QUẢN LÍ SÁCH-----------------------*/

		JPanel pnQuanLiSach=new JPanel();
		pnQuanLiSach.setLayout(new BorderLayout());
		JPanel pnNorthSach=new JPanel();
		JPanel pnCenterSach=new JPanel();
		JPanel pnSouthSach=new JPanel();
		pnQuanLiSach.add(pnNorthSach,BorderLayout.NORTH);
		pnQuanLiSach.add(pnCenterSach,BorderLayout.CENTER);
		pnQuanLiSach.add(pnSouthSach,BorderLayout.SOUTH);

		pnNorthSach.setLayout(new BorderLayout());
		JPanel pnChiTiet=new JPanel();
		pnNorthSach.add(pnChiTiet,BorderLayout.CENTER);
		JPanel pnThucHien=new JPanel();
		pnNorthSach.add(pnThucHien,BorderLayout.EAST);

		pnChiTiet.setLayout(new BoxLayout(pnChiTiet,BoxLayout.Y_AXIS));
		JPanel pnSach=new JPanel();
		JLabel lblSach=new JLabel("Thông tin sách");
		lblSach.setForeground(Color.BLUE);
		Font ft=new Font("arial",Font.BOLD,20);
		lblSach.setFont(ft);
		pnSach.add(lblSach);
		pnChiTiet.add(pnSach);

		JPanel pnMaSach=new JPanel();
		JLabel lblMaSach=new JLabel("Mã sách:");
		txtMaSach=new JTextField(20);
		pnMaSach.add(lblMaSach);
		pnMaSach.add(txtMaSach);
		pnChiTiet.add(pnMaSach);

		JPanel pnTenSach=new JPanel();
		JLabel lblTenSach=new JLabel("Tên sách:");
		txtTenSach=new JTextField(20);
		pnTenSach.add(lblTenSach);
		pnTenSach.add(txtTenSach);
		pnChiTiet.add(pnTenSach);

		JPanel pnTacGia=new JPanel();
		JLabel lblTacGia=new JLabel("Tác giả:");
		txtTacGia=new JTextField(20);
		pnTacGia.add(lblTacGia);
		pnTacGia.add(txtTacGia);
		pnChiTiet.add(pnTacGia);

		JPanel pnNXB=new JPanel();
		JLabel lblNXB=new JLabel("NXB:");
		txtNXB=new JTextField(20);
		pnNXB.add(lblNXB);
		pnNXB.add(txtNXB);
		pnChiTiet.add(pnNXB);

		JPanel pnTheLoai=new JPanel();
		JLabel lblTheLoai=new JLabel("Thể loại:");
		txtTheLoai=new JTextField(20);
		pnTheLoai.add(lblTheLoai);
		pnTheLoai.add(txtTheLoai);
		pnChiTiet.add(pnTheLoai);

		JPanel pnGiaBia=new JPanel();
		JLabel lblGiaBia=new JLabel("Giá bìa:");
		txtDonGia=new JTextField(20);
		pnGiaBia.add(lblGiaBia);
		pnGiaBia.add(txtDonGia);
		pnChiTiet.add(pnGiaBia);

		JPanel pnSoLuong=new JPanel();
		JLabel lblSoLuong=new JLabel("Số lượng:");
		txtSoLuong=new JTextField(20);
		pnSoLuong.add(lblSoLuong);
		pnSoLuong.add(txtSoLuong);
		pnChiTiet.add(pnSoLuong);

		JPanel pnTinhTrang=new JPanel();
		JLabel lblTinhTrang=new JLabel("Tình trạng:");
		txtTinhTrang=new JTextField(20);
		pnTinhTrang.add(lblTinhTrang);
		pnTinhTrang.add(txtTinhTrang);
		pnChiTiet.add(pnTinhTrang);

		lblMaSach.setPreferredSize(lblSoLuong.getPreferredSize());
		lblTacGia.setPreferredSize(lblSoLuong.getPreferredSize());
		lblTenSach.setPreferredSize(lblSoLuong.getPreferredSize());
		lblNXB.setPreferredSize(lblSoLuong.getPreferredSize());
		lblGiaBia.setPreferredSize(lblSoLuong.getPreferredSize());
		lblTheLoai.setPreferredSize(lblSoLuong.getPreferredSize());

		pnThucHien.setLayout(new BoxLayout(pnThucHien, BoxLayout.Y_AXIS));


		JPanel pnButtonTaoMoiSach=new JPanel();
		btnTaoMoiSach=new JButton("Tạo mới sách");
		pnButtonTaoMoiSach.add(btnTaoMoiSach);
		pnThucHien.add(pnButtonTaoMoiSach);

		JPanel pnButtonLuu=new JPanel();
		btnLuuSach=new JButton("Lưu sách");
		pnButtonLuu.add(btnLuuSach);
		pnThucHien.add(pnButtonLuu);

		JPanel pnButtonXoa=new JPanel();
		btnXoaSach=new JButton("Xóa sách");
		pnButtonXoa.add(btnXoaSach);
		pnThucHien.add(pnButtonXoa);
		
		JPanel pnButtonXuatFile=new JPanel();
		btnXuatFileSach=new JButton("Thêm file");
		pnButtonXuatFile.add(btnXuatFileSach);
		pnThucHien.add(pnButtonXuatFile);

//		JPanel pnTimKiemSach=new JPanel();
//		btnTimKiemSach=new JButton("Tìm kiếm sách");
//		pnTimKiemSach.add(btnTimKiemSach);
//		pnThucHien.add(pnTimKiemSach);

		pnCenterSach.setLayout(new BorderLayout());
		dtmSach=new DefaultTableModel();
		dtmSach.addColumn("Mã sách");
		dtmSach.addColumn("Tên sách");
		dtmSach.addColumn("Tác giả");
		dtmSach.addColumn("Thể Loại");
		dtmSach.addColumn("NXB");
		dtmSach.addColumn("Giá bìa");
		dtmSach.addColumn("Tình trạng");
		dtmSach.addColumn("Số lượng");

		tbSach=new JTable(dtmSach);
		JScrollPane scSach=new JScrollPane(tbSach, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnCenterSach.add(scSach,BorderLayout.CENTER);

		TitledBorder borderThongTinChiTiet=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thông tin chi tiết");
		pnChiTiet.setBorder(borderThongTinChiTiet);
		TitledBorder borderThucHien=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thực hiện");
		pnThucHien.setBorder(borderThucHien);

		btnTaoMoiSach.setIcon(new ImageIcon("hinhanh/themsach.png"));
		btnLuuSach.setIcon(new ImageIcon("hinhanh/luusach.png"));
		btnXoaSach.setIcon(new ImageIcon("hinhanh/xoasach.png"));
//		btnTimKiemSach.setIcon(new ImageIcon("hinhanh/search.png"));
		btnXuatFileSach.setIcon(new ImageIcon("hinhanh/save.png"));

		TitledBorder borderDanhSachChiTietSach=new TitledBorder(BorderFactory.createLineBorder(Color.RED), "Danh sách chi tiết sách");
		pnCenterSach.setBorder(borderDanhSachChiTietSach);

		
		btnLuuSach.setPreferredSize(btnTaoMoiSach.getPreferredSize());
		btnXoaSach.setPreferredSize(btnTaoMoiSach.getPreferredSize());
		btnXuatFileSach.setPreferredSize(btnTaoMoiSach.getPreferredSize());
		
	

		/*--------------------------QUẢN LÍ BẠN ĐỌC----------------------------*/

		JPanel pnQuanLiBanDoc=new JPanel();
		pnQuanLiBanDoc.setLayout(new BorderLayout());
		JPanel pnNorthBanDoc=new JPanel();
		JPanel pnCenterBanDoc=new JPanel();
		JPanel pnSouthBanDoc=new JPanel();
		pnQuanLiBanDoc.add(pnNorthBanDoc,BorderLayout.NORTH);
		pnQuanLiBanDoc.add(pnCenterBanDoc,BorderLayout.CENTER);
		pnQuanLiBanDoc.add(pnSouthBanDoc,BorderLayout.SOUTH);

		pnNorthBanDoc.setLayout(new BorderLayout());
		JPanel pnChiTietBanDoc=new JPanel();
		pnNorthBanDoc.add(pnChiTietBanDoc,BorderLayout.CENTER);
		JPanel pnThucHienBanDoc=new JPanel();
		pnNorthBanDoc.add(pnThucHienBanDoc,BorderLayout.EAST);

		pnChiTietBanDoc.setLayout(new BoxLayout(pnChiTietBanDoc,BoxLayout.Y_AXIS));
		JPanel pnBanDoc=new JPanel();
		JLabel lblBanDoc=new JLabel("Thông tin bạn đọc");
		lblBanDoc.setForeground(Color.BLUE);
		Font ft1=new Font("arial",Font.BOLD,20);
		lblBanDoc.setFont(ft1);
		pnBanDoc.add(lblBanDoc);
		pnChiTietBanDoc.add(pnBanDoc);

		JPanel pnMaBanDoc=new JPanel();
		JLabel lblMaBanDoc=new JLabel("Mã bạn đọc:");
		txtMaBanDoc=new JTextField(20);
		pnMaBanDoc.add(lblMaBanDoc);
		pnMaBanDoc.add(txtMaBanDoc);
		pnChiTietBanDoc.add(pnMaBanDoc);

		JPanel pnHoTen=new JPanel();
		JLabel lblHoTen=new JLabel("Họ tên:");
		txtHoTen=new JTextField(20);
		pnHoTen.add(lblHoTen);
		pnHoTen.add(txtHoTen);
		pnChiTietBanDoc.add(pnHoTen);


		JPanel pnNgaySinh=new JPanel();
		JLabel lblNgaySinh=new JLabel("Ngày sinh:");
		txtNgaySinh=new JTextField(20);
		pnNgaySinh.add(lblNgaySinh);
		pnNgaySinh.add(txtNgaySinh);
		pnChiTietBanDoc.add(pnNgaySinh);


		JPanel pnQueQuan=new JPanel();
		JLabel lblQueQuan=new JLabel("Quê quán:");
		txtQueQuan=new JTextField(20);
		pnQueQuan.add(lblQueQuan);
		pnQueQuan.add(txtQueQuan);
		pnChiTietBanDoc.add(pnQueQuan);


		JPanel pnEmail=new JPanel();
		JLabel lblEmail=new JLabel("Email:");
		txtEmail=new JTextField(20);
		pnEmail.add(lblEmail);
		pnEmail.add(txtEmail);
		pnChiTietBanDoc.add(pnEmail);


		JPanel pnSDT=new JPanel();
		JLabel lblSDT=new JLabel("SĐT:");
		txtSoDienThoai=new JTextField(20);
		pnSDT.add(lblSDT);
		pnSDT.add(txtSoDienThoai);
		pnChiTietBanDoc.add(pnSDT);


		JPanel pnGioiTinh=new JPanel();
		JLabel lblGioiTinh=new JLabel("Giới tính:");
		//		rNam=new JRadioButton("Nam");
		//		rNu=new JRadioButton("Nữ");
		//		groupGioiTinh=new ButtonGroup();
		//		groupGioiTinh.add(rNam);
		//		groupGioiTinh.add(rNu);
		//		pnGioiTinh.add(lblGioiTinh);
		//		pnGioiTinh.add(rNam);
		//		pnGioiTinh.add(rNu);
		pnChiTietBanDoc.add(pnGioiTinh);
		txtGioiTinh=new JTextField(20);
		pnGioiTinh.add(lblGioiTinh);
		pnGioiTinh.add(txtGioiTinh);
		pnChiTietBanDoc.add(pnGioiTinh);

		lblHoTen.setPreferredSize(lblMaBanDoc.getPreferredSize());
		lblNgaySinh.setPreferredSize(lblMaBanDoc.getPreferredSize());
		lblQueQuan.setPreferredSize(lblMaBanDoc.getPreferredSize());
		lblSDT.setPreferredSize(lblMaBanDoc.getPreferredSize());
		lblEmail.setPreferredSize(lblMaBanDoc.getPreferredSize());
		lblGioiTinh.setPreferredSize(lblMaBanDoc.getPreferredSize());

		pnThucHienBanDoc.setLayout(new BoxLayout(pnThucHienBanDoc, BoxLayout.Y_AXIS));
		JPanel pnButtonThemBanDoc=new JPanel();
		btnTaoMoiBanDoc=new JButton("Tạo mới");
		pnButtonThemBanDoc.add(btnTaoMoiBanDoc);
		pnThucHienBanDoc.add(pnButtonThemBanDoc);

		JPanel pnButtonLuuBanDoc=new JPanel();
		btnLuuBanDoc=new JButton("Lưu");
		pnButtonLuuBanDoc.add(btnLuuBanDoc);
		pnThucHienBanDoc.add(pnButtonLuuBanDoc);

		JPanel pnButtonXoaBanDoc=new JPanel();
		btnXoaBanDoc=new JButton("Xóa");
		pnButtonXoaBanDoc.add(btnXoaBanDoc);
		pnThucHienBanDoc.add(pnButtonXoaBanDoc);
		
		JPanel pnButtonThemFileBanDoc=new JPanel();
		btnThemFileBanDoc=new JButton("Thêm file");
		pnButtonThemFileBanDoc.add(btnThemFileBanDoc);
		pnThucHienBanDoc.add(pnButtonThemFileBanDoc);

		pnCenterBanDoc.setLayout(new BorderLayout());
		dtmBanDoc=new DefaultTableModel();
		dtmBanDoc.addColumn("Mã bạn đọc");
		dtmBanDoc.addColumn("Họ tên");
		dtmBanDoc.addColumn("Số điện thoại");
		dtmBanDoc.addColumn("Email");
		dtmBanDoc.addColumn("Ngày sinh");
		dtmBanDoc.addColumn("Quê quán");
		dtmBanDoc.addColumn("Giới tính");


		tbBanDoc=new JTable(dtmBanDoc);
		JScrollPane scBanDoc=new JScrollPane(tbBanDoc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnCenterBanDoc.add(scBanDoc,BorderLayout.CENTER);


		TitledBorder borderThongTinChiTietBanDoc=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thông tin chi tiết");
		pnChiTietBanDoc.setBorder(borderThongTinChiTietBanDoc);
		TitledBorder borderThucHienBanDoc=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thực hiện");
		pnThucHienBanDoc.setBorder(borderThucHienBanDoc);

		btnTaoMoiBanDoc.setIcon(new ImageIcon("hinhanh/themuser.png"));
		btnLuuBanDoc.setIcon(new ImageIcon("hinhanh/saveuser.png"));
		btnXoaBanDoc.setIcon(new ImageIcon("hinhanh/xoauser.png"));
		btnThemFileBanDoc.setIcon(new ImageIcon("hinhanh/save.png"));

		btnTaoMoiBanDoc.setPreferredSize(btnThemFileBanDoc.getPreferredSize());
		btnLuuBanDoc.setPreferredSize(btnThemFileBanDoc.getPreferredSize());
		btnXoaBanDoc.setPreferredSize(btnThemFileBanDoc.getPreferredSize());
		TitledBorder borderDanhSachChiTietBanDoc=new TitledBorder(BorderFactory.createLineBorder(Color.RED), "Danh sách chi tiết bạn đọc");
		pnCenterBanDoc.setBorder(borderDanhSachChiTietBanDoc);

		/*--------------------------QUẢN LÍ MƯỢN SÁCH----------------------------*/

		JPanel pnQuanLiMuonSach=new JPanel();
		pnQuanLiMuonSach.setLayout(new BorderLayout());
		JPanel pnNorthMuonSach=new JPanel();
		JPanel pnCenterMuonSach=new JPanel();
		JPanel pnSouthMuonSach=new JPanel();
		pnQuanLiMuonSach.add(pnNorthMuonSach,BorderLayout.NORTH);
		pnQuanLiMuonSach.add(pnCenterMuonSach,BorderLayout.CENTER);
		pnQuanLiMuonSach.add(pnSouthMuonSach,BorderLayout.SOUTH);

		pnNorthMuonSach.setLayout(new BorderLayout());
		JPanel pnChiTietMuonSach=new JPanel();
		pnNorthMuonSach.add(pnChiTietMuonSach,BorderLayout.CENTER);

		pnChiTietMuonSach.setLayout(new BoxLayout(pnChiTietMuonSach,BoxLayout.Y_AXIS));
		JPanel pnMuonSach=new JPanel();
		JLabel lblMuonSach=new JLabel("Thông tin mượn sách");
		lblMuonSach.setForeground(Color.BLUE);
		Font ft2=new Font("arial",Font.BOLD,20);
		lblMuonSach.setFont(ft2);
		pnMuonSach.add(lblMuonSach);
		pnChiTietMuonSach.add(pnMuonSach);

		JPanel pnMaPhieuMuon=new JPanel();
		JLabel lblMaPhieuMuon=new JLabel("Mã phiếu mượn:");
		txtMaPhieuMuon=new JTextField(20);
		pnMaPhieuMuon.add(lblMaPhieuMuon);
		pnMaPhieuMuon.add(txtMaPhieuMuon);
		pnChiTietMuonSach.add(pnMaPhieuMuon);

		JPanel pnMaSachCanMuon=new JPanel();
		JLabel lblMaSachCanMuon=new JLabel("Mã sách mượn:");
		txtMaSachCanMuon=new JTextField(20);
		comboSach=new JComboBox();
		comboSach.setPreferredSize(new Dimension(220,20));
		MaTaiLieuMuon(comboSach);
		
		pnMaSachCanMuon.add(lblMaSachCanMuon);
//		pnMaSachCanMuon.add(txtMaSachCanMuon);
		pnMaSachCanMuon.add(comboSach);
		
		pnChiTietMuonSach.add(pnMaSachCanMuon);
		
		JPanel pnTenSachMuon=new JPanel();
		JLabel lblTenSachMuon=new JLabel("Tên sách:");
		txtTenSachMuon=new JTextField(20);
		pnTenSachMuon.add(lblTenSachMuon);
		pnTenSachMuon.add(txtTenSachMuon);
		pnChiTietMuonSach.add(pnTenSachMuon);
		

		JPanel pnMaBanDocMuon=new JPanel();
		JLabel lblMaBanDocMuon=new JLabel("Mã bạn đọc:");
		comboBanDoc=new JComboBox();
		comboBanDoc.setPreferredSize(new Dimension(220,20));
		MaBandoc(comboBanDoc);
		txtMaBanDocMuon=new JTextField(20);
		
		pnMaBanDocMuon.add(lblMaBanDocMuon);
//		pnMaBanDocMuon.add(txtMaBanDocMuon);
		pnMaBanDocMuon.add(comboBanDoc);
		
		pnChiTietMuonSach.add(pnMaBanDocMuon);
		
		
		JPanel pnTenBanDocMuon=new JPanel();
		JLabel lblTenBanDocMuon=new JLabel("Tên bạn đọc:");
		txtTenBanDocMuon=new JTextField(20);
		pnTenBanDocMuon.add(lblTenBanDocMuon);
		pnTenBanDocMuon.add(txtTenBanDocMuon);
		pnChiTietMuonSach.add(pnTenBanDocMuon);

		JPanel pnNgayMuon=new JPanel();
		JLabel lblNgayMuon=new JLabel("Ngày mượn:");
		txtNgayMuon=new JTextField(20);
		pnNgayMuon.add(lblNgayMuon);
		pnNgayMuon.add(txtNgayMuon);
		pnChiTietMuonSach.add(pnNgayMuon);


		JPanel pnSoLuongMuon=new JPanel();
		JLabel lblSoLuongMuon=new JLabel("Số lượng mượn:");
		txtSoLuongMuon=new JTextField(20);
		pnSoLuongMuon.add(lblSoLuongMuon);
		pnSoLuongMuon.add(txtSoLuongMuon);
		pnChiTietMuonSach.add(pnSoLuongMuon);
		
		JPanel pnGiaSachMuon=new JPanel();
		JLabel lblGiaSachMuon=new JLabel("Giá:");
		txtGiaBiaSachMuon=new JTextField(20);
		pnGiaSachMuon.add(lblGiaSachMuon);
		pnGiaSachMuon.add(txtGiaBiaSachMuon);
		pnChiTietMuonSach.add(pnGiaSachMuon);
		

		JPanel pnNgayHetHan=new JPanel();
		JLabel lblNgayHetHan=new JLabel("Ngày hết hạn:");
		txtNgayHetHan=new JTextField(20);
		pnNgayHetHan.add(lblNgayHetHan);
		pnNgayHetHan.add(txtNgayHetHan);
		pnChiTietMuonSach.add(pnNgayHetHan);

		lblMaPhieuMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblMaBanDocMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblMaSachCanMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblNgayMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblNgayHetHan.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblTenBanDocMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblTenSachMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblGiaSachMuon.setPreferredSize(lblSoLuongMuon.getPreferredSize());
		lblNgayHetHan.setPreferredSize(lblSoLuongMuon.getPreferredSize());

		JPanel pnThucHienPhieuMuon=new JPanel();
		pnThucHienPhieuMuon.setLayout(new FlowLayout());
		pnCenterMuonSach.add(pnThucHienPhieuMuon);
	
		JPanel pnButtonTaoMoiPhieuMuon=new JPanel();
		btnTaoMoiPhieuMuon=new JButton("Tạo mới");
		pnButtonTaoMoiPhieuMuon.add(btnTaoMoiPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonTaoMoiPhieuMuon);

		JPanel pnButtonLuuPhieuMuon=new JPanel();
		btnLuuPhieuMuon=new JButton("Mượn");
		pnButtonLuuPhieuMuon.add(btnLuuPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonLuuPhieuMuon);
		
		JPanel pnButtonSuaPhieuMuon=new JPanel();
		btnSuaPhieuMuon=new JButton("Sửa");
		pnButtonSuaPhieuMuon.add(btnSuaPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonSuaPhieuMuon);
		
		JPanel pnButtonThemFilePhieuMuon=new JPanel();
		btnThemFilePhieuMuon=new JButton("Thêm file");
		pnButtonThemFilePhieuMuon.add(btnThemFilePhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonThemFilePhieuMuon);
		
		JPanel pnButtonInPhieuMuon=new JPanel();
		btnInPhieuMuon=new JButton("In phiếu");
		pnButtonInPhieuMuon.add(btnInPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonInPhieuMuon);

		JPanel pnButtonXoaPhieuMuon=new JPanel();
		btnXoaPhieuMuon=new JButton("Xóa");
		pnButtonXoaPhieuMuon.add(btnXoaPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonXoaPhieuMuon);
		pnChiTietMuonSach.add(pnThucHienPhieuMuon);
		
		JPanel pnButtonCapNhatPhieuMuon=new JPanel();
		btnCapNhatPhieuMuon=new JButton("");
		pnButtonCapNhatPhieuMuon.add(btnCapNhatPhieuMuon);
		pnThucHienPhieuMuon.add(pnButtonCapNhatPhieuMuon);
		pnChiTietMuonSach.add(pnThucHienPhieuMuon);
		
		pnCenterMuonSach.setLayout(new BorderLayout());
		dtmPhieuMuon=new DefaultTableModel();
		dtmPhieuMuon.addColumn("Mã phiếu");
		dtmPhieuMuon.addColumn("Mã bạn đọc");
		dtmPhieuMuon.addColumn("Tên bạn đọc");
		dtmPhieuMuon.addColumn("Mã sách");
		dtmPhieuMuon.addColumn("Tên sách");
		dtmPhieuMuon.addColumn("Giá");
	    dtmPhieuMuon.addColumn("Ngày mượn");
		dtmPhieuMuon.addColumn("Số lượng mượn");
		dtmPhieuMuon.addColumn("Hạn trả");
		tbPhieuMuon=new JTable(dtmPhieuMuon);
		JScrollPane sc=new JScrollPane(tbPhieuMuon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnCenterMuonSach.add(sc,BorderLayout.CENTER);


		TitledBorder borderThongTinChiTietPhieuMuon=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thông tin chi tiết phiếu mượn");
		pnChiTietMuonSach.setBorder(borderThongTinChiTietPhieuMuon);

		btnTaoMoiPhieuMuon.setIcon(new ImageIcon("hinhanh/savephieu.png"));
		btnLuuPhieuMuon.setIcon(new ImageIcon("hinhanh/muon1.png"));
		btnXoaPhieuMuon.setIcon(new ImageIcon("hinhanh/xoaphieu.png"));
		btnThemFilePhieuMuon.setIcon(new ImageIcon("hinhanh/save.png"));
		btnSuaPhieuMuon.setIcon(new ImageIcon("hinhanh/xemphieumuon.png"));
		btnInPhieuMuon.setIcon(new ImageIcon("hinhanh/inphieu.png"));
		btnCapNhatPhieuMuon.setIcon(new ImageIcon("hinhanh/capnhat.png"));
//		TitledBorder borderThucHienPhieuMuon=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thực hiện phiếu mượn");
//		pnThucHienPhieuMuon.setBorder(borderThucHienPhieuMuon);


		btnLuuPhieuMuon.setPreferredSize(btnThemFilePhieuMuon.getPreferredSize());
		btnXoaPhieuMuon.setPreferredSize(btnThemFilePhieuMuon.getPreferredSize());
		btnTaoMoiPhieuMuon.setPreferredSize(btnThemFilePhieuMuon.getPreferredSize());
		
		TitledBorder borderDanhSachChiTietPhieuMuon=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Danh sách chi tiết phiếu mượn");
		pnCenterMuonSach.setBorder(borderDanhSachChiTietPhieuMuon);
		
		

		/*--------------------------QUẢN LÍ TRẢ SÁCH----------------------------*/


		JPanel pnQuanLiTraSach=new JPanel();
		pnQuanLiTraSach.setLayout(new BorderLayout());
		JPanel pnNorthTraSach=new JPanel();
		JPanel pnCenterTraSach=new JPanel();
		JPanel pnSouthTraSach=new JPanel();
		pnQuanLiTraSach.add(pnNorthTraSach,BorderLayout.NORTH);
		pnQuanLiTraSach.add(pnCenterTraSach,BorderLayout.CENTER);
		pnQuanLiTraSach.add(pnSouthTraSach,BorderLayout.SOUTH);

		pnNorthTraSach.setLayout(new BorderLayout());
		JPanel pnChiTietTraSach=new JPanel();
		pnNorthTraSach.add(pnChiTietTraSach,BorderLayout.CENTER);

		pnChiTietTraSach.setLayout(new BoxLayout(pnChiTietTraSach,BoxLayout.Y_AXIS));
		JPanel pnTraSach=new JPanel();
		JLabel lblTraSach=new JLabel("Thông tin trả sách");
		lblTraSach.setForeground(Color.BLUE);
		Font ft3=new Font("arial",Font.BOLD,20);
		lblTraSach.setFont(ft3);
		pnTraSach.add(lblTraSach);
		pnChiTietTraSach.add(pnTraSach);
		
		JPanel pnMaPhieuMuonTra=new JPanel();
		JLabel lblMaPhieuMuonTra=new JLabel("Mã phiếu:");
		comboMaPhieuMuon=new JComboBox();
		comboMaPhieuMuon.setPreferredSize(new Dimension(220, 20));
		MaPhieu(comboMaPhieuMuon);
		
		pnMaPhieuMuonTra.add(lblMaPhieuMuonTra);
		pnMaPhieuMuonTra.add(comboMaPhieuMuon);
		
		pnChiTietTraSach.add(pnMaPhieuMuonTra);

		JPanel pnMaBanDocTra=new JPanel();
		JLabel lblMaBanDocTra=new JLabel("Mã bạn đọc:");
		txtMaBanDocTra=new JTextField(20);
		pnMaBanDocTra.add(lblMaBanDocTra);
		pnMaBanDocTra.add(txtMaBanDocTra);
		pnChiTietTraSach.add(pnMaBanDocTra);
		
		JPanel pnTenBanDocTra=new JPanel();
		JLabel lblTenBanDocTra=new JLabel("Tên bạn đọc:");
		txtTenBanDocTra=new JTextField(20);
		pnTenBanDocTra.add(lblTenBanDocTra);
		pnTenBanDocTra.add(txtTenBanDocTra);
		pnChiTietTraSach.add(pnTenBanDocTra);

		JPanel pnMaSachCanTra=new JPanel();
		JLabel lblMaSachCanTra=new JLabel("Mã sách:");
//		txtMaSachTra=new JTextField(20);
		comboMaSachTra=new JComboBox<>();
		comboMaSachTra.setPreferredSize(new Dimension(220, 20));
		MaSach(comboMaSachTra);
		
		pnMaSachCanTra.add(lblMaSachCanTra);
//		pnMaSachCanTra.add(txtMaSachTra);
		pnMaSachCanTra.add(comboMaSachTra);
		
		pnChiTietTraSach.add(pnMaSachCanTra);
		
		JPanel pnTenSachTra=new JPanel();
		JLabel lblSachTra=new JLabel("Tên sách:");
		txtTenSachTra=new JTextField(20);
		pnTenSachTra.add(lblSachTra);
		pnTenSachTra.add(txtTenSachTra);
		pnChiTietTraSach.add(pnTenSachTra);
		
		JPanel pnGiaBiaSachTra=new JPanel();
		JLabel lblGiaBiaTra=new JLabel("Giá bìa:");
		txtGiaBiaSachTra=new JTextField(20);
		pnGiaBiaSachTra.add(lblGiaBiaTra);
		pnGiaBiaSachTra.add(txtGiaBiaSachTra);
		pnChiTietTraSach.add(pnGiaBiaSachTra);


		JPanel pnNgayMuonSach=new JPanel();
		JLabel lblNgayMuonSach=new JLabel("Ngày mượn:");
		txtNgayMuonSach=new JTextField(20);
		pnNgayMuonSach.add(lblNgayMuonSach);
		pnNgayMuonSach.add(txtNgayMuonSach);
		pnChiTietTraSach.add(pnNgayMuonSach);
		
		JPanel pnSoLuongMuonSach=new JPanel();
		JLabel lblSoLuongMuonSach=new JLabel("Số lượng mượn:");
		txtSoLuongMuonSach=new JTextField(20);
		pnSoLuongMuonSach.add(lblSoLuongMuonSach);
		pnSoLuongMuonSach.add(txtSoLuongMuonSach);
		pnChiTietTraSach.add(pnSoLuongMuonSach);
		
		JPanel pnNgayHetHanTra=new JPanel();
		JLabel lblNgayHetHanTra=new JLabel("Hạn trả:");
		txtNgayHetHanTra=new JTextField(20);
		pnNgayHetHanTra.add(lblNgayHetHanTra);
		pnNgayHetHanTra.add(txtNgayHetHanTra);
		pnChiTietTraSach.add(pnNgayHetHanTra);

		JPanel pnNgayTraSach=new JPanel();
		JLabel lblNgayTraSach=new JLabel("Ngày trả:");
		txtNgayTra=new JTextField(20);
		pnNgayTraSach.add(lblNgayTraSach);
		pnNgayTraSach.add(txtNgayTra);
		pnChiTietTraSach.add(pnNgayTraSach);

		JPanel pnSoLuongTra=new JPanel();
		JLabel lblSoLuongTra=new JLabel("Số lượng trả:");
		txtSoLuongTra=new JTextField(20);
		pnSoLuongTra.add(lblSoLuongTra);
		pnSoLuongTra.add(txtSoLuongTra);
		pnChiTietTraSach.add(pnSoLuongTra);
		
//		JPanel pnPhatQuaHan=new JPanel();
//		JLabel lblPhatQuaHan=new JLabel("Phạt quá hạn:");
//		txtPhatQuaHan=new JTextField(20);
//		pnPhatQuaHan.add(lblPhatQuaHan);
//		pnPhatQuaHan.add(txtPhatQuaHan);
//		pnChiTietTraSach.add(pnPhatQuaHan);
		
//		JPanel pnPhatMat=new JPanel();
//		JLabel lblPhatMat=new JLabel("Phạt mất:");
//		txtPhatMat=new JTextField(20);
//		pnPhatMat.add(lblPhatMat);
//		pnPhatMat.add(txtPhatMat);
//		pnChiTietTraSach.add(pnPhatMat);
		
		TitledBorder borderThongTinChiTietTraSach=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thông tin trả sách");

		pnChiTietTraSach.setBorder(borderThongTinChiTietTraSach);

		

		lblMaBanDocTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblMaSachCanTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblNgayTraSach.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblNgayMuonSach.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblMaPhieuMuonTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblSoLuongMuonSach.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
//		lblPhatQuaHan.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblTenBanDocTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
//		lblPhatMat.setPreferredSize(lblPhatQuaHan.getPreferredSize());
		lblSoLuongTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblNgayHetHanTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblGiaBiaTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		lblSachTra.setPreferredSize(lblSoLuongMuonSach.getPreferredSize());
		

		JPanel pnThucHienTraSach=new JPanel();
		pnThucHienTraSach.setLayout(new FlowLayout());
		pnChiTietTraSach.add(pnThucHienTraSach);
		JPanel pnButtonTaoMoiTraSach=new JPanel();
		btnTaoMoiTraSach=new JButton("Tạo mới");
		pnButtonTaoMoiTraSach.add(btnTaoMoiTraSach);
		pnThucHienTraSach.add(pnButtonTaoMoiTraSach);

		JPanel pnButtonLuuTraSach=new JPanel();
		btnLuuTraSach=new JButton("Trả");
		pnButtonLuuTraSach.add(btnLuuTraSach);
		pnThucHienTraSach.add(pnButtonLuuTraSach);

//		TitledBorder borderThucHienTraSach=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thực hiện trả sách");
//		pnThucHienTraSach.setBorder(borderThucHienTraSach);
		

		JPanel pnButtonPhatQuaHan=new JPanel();
		btnPhatMuonQuaHan=new JButton("Phạt");
		pnButtonPhatQuaHan.add(btnPhatMuonQuaHan);
		pnThucHienTraSach.add(pnButtonPhatQuaHan);
		
		JPanel pnButtonPhieuTra=new JPanel();
		btnInPhieuTra=new JButton("Phiếu trả");
		pnButtonPhieuTra.add(btnInPhieuTra);
		pnThucHienTraSach.add(pnButtonPhieuTra);
		
		JPanel pnButtonCapNhat=new JPanel();
		btnCapNhatTra=new JButton("");
		pnButtonCapNhat.add(btnCapNhatTra);
		pnThucHienTraSach.add(pnButtonCapNhat);

		pnCenterTraSach.setLayout(new BorderLayout());
		dtmTra=new DefaultTableModel();
		dtmTra.addColumn("Mã phiếu mượn");
		dtmTra.addColumn("Mã bạn đọc");
		dtmTra.addColumn("Tên độc giả");
		dtmTra.addColumn("Mã sách");
		dtmTra.addColumn("Tên sách");
		dtmTra.addColumn("Giá bìa");
		dtmTra.addColumn("Ngày mượn");
		dtmTra.addColumn("Số lượng mượn");
		dtmTra.addColumn("Ngày hết hạn");
		dtmTra.addColumn("Ngày trả");
		dtmTra.addColumn("Số lượng trả");
		dtmTra.addColumn("Phạt quá hạn");
//		dtmTra.addColumn("Phạt mất sách");


		tbTra=new JTable(dtmTra);
		JScrollPane scTra=new JScrollPane(tbTra, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnCenterTraSach.add(scTra,BorderLayout.CENTER);

//		TitledBorder borderThongTinChiTietTra=new TitledBorder(BorderFactory.createLineBorder(Color.RED),"Thông tin chi tiết trả sách");
//		pnChiTietTraSach.setBorder(borderThongTinChiTietTra);

		btnTaoMoiTraSach.setIcon(new ImageIcon("hinhanh/savephieu.png"));
		btnLuuTraSach.setIcon(new ImageIcon("hinhanh/tra.png"));
		btnPhatMuonQuaHan.setIcon(new ImageIcon("hinhanh/phat.png"));
		btnCapNhatTra.setIcon(new ImageIcon("hinhanh/capnhat.png"));
		btnInPhieuTra.setIcon(new ImageIcon("hinhanh/inphieu.png"));


		btnLuuTraSach.setPreferredSize(btnTaoMoiTraSach.getPreferredSize());
		TitledBorder borderDanhSachChiTietTraSach=new TitledBorder(BorderFactory.createLineBorder(Color.RED), "Danh sách chi tiết trả sách");
		pnCenterTraSach.setBorder(borderDanhSachChiTietTraSach);
		
		JPanel pnThongKe=new JPanel();
		pnThongKe.setLayout(new BorderLayout());
		JPanel pnNorthThongKe=new JPanel();
		JPanel pnCenterThongKe=new JPanel();
		JPanel pnSouthThongKe=new JPanel();
		pnThongKe.add(pnNorthThongKe,BorderLayout.NORTH);
		pnThongKe.add(pnCenterThongKe,BorderLayout.CENTER);
		pnThongKe.add(pnSouthThongKe,BorderLayout.SOUTH);
		
		JLabel lblThongKe=new JLabel("Danh mục thống kê-Báo cáo");
		lblThongKe.setForeground(Color.BLUE);
		Font ft4=new Font("arial",Font.BOLD,30);
		lblThongKe.setFont(ft4);
		pnNorthThongKe.add(lblThongKe);
		
		pnCenterThongKe.setLayout(new BorderLayout());
		JPanel pnTK=new JPanel();
		pnTK.setLayout(new FlowLayout());
		pnCenterThongKe.add(pnTK,BorderLayout.NORTH);
		JLabel lblThongKe1=new JLabel("Chọn thống kê:");
		btnChonThongKe=new JButton("Chọn");
		btnChonThongKe.setIcon(new ImageIcon("hinhanh/icontk.png"));
		comboThongKeTheoPhieuMuon=new JComboBox<>();
		comboThongKeTheoPhieuMuon.setPreferredSize(new Dimension(300,40));
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng độc giả theo ngày mượn");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng sách theo ngày mượn");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng phiếu mượn theo ngày mượn");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng độc giả theo ngày trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng sách theo ngày trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng phiếu mượn theo ngày trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng độc giả theo hạn trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng sách theo hạn trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng phiếu mượn theo hạn trả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng tác giả");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng nhà xuất bản");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng thể loại");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng độc giả mượn quá hạn");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lượng sách theo giá bìa");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số lần độc giả bị phạt");
		comboThongKeTheoPhieuMuon.addItem("Thống kê số sách mượn của độc giả");
		pnTK.add(lblThongKe1);
		pnTK.add(comboThongKeTheoPhieuMuon);
		pnTK.add(btnChonThongKe);
		JPanel pnAnhNen=new JPanel();
		pnCenterThongKe.add(pnAnhNen,BorderLayout.CENTER);
		JLabel lblAnhNen=new JLabel();
		ImageIcon ii=new ImageIcon("hinhanh/anhnentk.jpg");
		lblAnhNen.setIcon(ii);
		pnAnhNen.add(lblAnhNen);
		
		
		
	
		
		JPanel pnTimKiem=new JPanel();
		pnTimKiem.setLayout(new BorderLayout());
		JPanel pnNorthTimKiem=new JPanel();
		JPanel pnCenterTimKiem=new JPanel();
		JPanel pnSouthTimKiem=new JPanel();
		pnTimKiem.add(pnNorthTimKiem,BorderLayout.NORTH);
		pnTimKiem.add(pnCenterTimKiem,BorderLayout.CENTER);
		pnTimKiem.add(pnSouthTimKiem,BorderLayout.SOUTH);
		
		JLabel lblTimKiem=new JLabel("Danh mục tìm kiếm thông tin");
		lblTimKiem.setForeground(Color.BLUE);
		Font ft5=new Font("arial",Font.BOLD,30);
		lblTimKiem.setFont(ft5);
		pnNorthTimKiem.add(lblTimKiem);
		
		pnCenterTimKiem.setLayout(new BorderLayout());
		JPanel pnTimKiemTT=new JPanel();
		pnTimKiemTT.setLayout(new FlowLayout());
		pnCenterTimKiem.add(pnTimKiemTT,BorderLayout.NORTH);
		JLabel lblTimKiem1=new JLabel("Chọn tìm kiếm:");
		btnChonTimKiem=new JButton("Chọn");
		btnChonTimKiem.setIcon(new ImageIcon("hinhanh/iconsearch1.png"));
		comboTimKiem=new JComboBox<>();
		comboTimKiem.setPreferredSize(new Dimension(300,40));
		comboTimKiem.addItem("Tìm kiếm thông tin sách");
		comboTimKiem.addItem("Tìm kiếm thông tin bạn đọc");
		comboTimKiem.addItem("Tìm kiếm thông tin phiếu mượn");
		pnTimKiemTT.add(lblTimKiem1);
		pnTimKiemTT.add(comboTimKiem);
		pnTimKiemTT.add(btnChonTimKiem);
		
		JPanel pnAnhNenTK=new JPanel();
		pnCenterTimKiem.add(pnAnhNenTK,BorderLayout.CENTER);
		JLabel lblAnhNenTK=new JLabel();
		ImageIcon i=new ImageIcon("hinhanh/thuvien2.jpg");
		lblAnhNenTK.setIcon(i);
		pnAnhNenTK.add(lblAnhNenTK);
		
		
		
		
		ImageIcon icon = new ImageIcon("hinhanh/book.png");
		tab.addTab("Quản lí sách", icon, pnQuanLiSach); 
		
		ImageIcon icon1 = new ImageIcon("hinhanh/adduser.png");
		tab.addTab("Quản lí bạn đọc", icon1, pnQuanLiBanDoc);
		
		ImageIcon icon2 = new ImageIcon("hinhanh/phieumuon1.png");
		tab.addTab("Phiếu mượn", icon2, pnQuanLiMuonSach);
		
		ImageIcon icon3 = new ImageIcon("hinhanh/chitiet.png");
		tab.addTab("Chi tiết mượn trả", icon3, pnQuanLiTraSach);
		
		ImageIcon icon4 = new ImageIcon("hinhanh/thongket.png");
		tab.addTab("Thống kê-Báo cáo", icon4, pnThongKe);

		ImageIcon icon5 = new ImageIcon("hinhanh/timkiem.png");
		tab.addTab("Tìm kiếm", icon5, pnTimKiem);




	}
	

	public void addEvents(){
		
		btnPhatMuonQuaHan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String upDate = "UPDATE ChiTietMuonTra SET PhatQuaHan=datediff(day,NgayHetHan,NgayTra)*500 WHERE NgayHetHan<NgayTra";
			    PreparedStatement prestatement;
				try {
					prestatement = conn.prepareStatement(upDate);
					prestatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Đã phạt");
					HienThiToanBoThongTinTraSach();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Phạt thất bại");
				}
			}
		});
		
		btnChonTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String option=comboTimKiem.getSelectedItem().toString();
				switch(option){
				case "Tìm kiếm thông tin sách":
				{
					TimKiemSach ui=new TimKiemSach("Tìm kiếm thông tin sách");
					ui.showWindow();
					break;
				}
				case "Tìm kiếm thông tin bạn đọc":
				{
					TimKiemBandoc ui=new TimKiemBandoc("Tìm kiếm thông tin bạn đọc");
					ui.showWindow();
				    break;
				}
				case "Tìm kiếm thông tin phiếu mượn":
				{
					TimKiemPhieuMuon ui=new TimKiemPhieuMuon("Tìm kiếm thông tin phiếu mượn");
					ui.showWindow();
					break;
				}
				}
			}
		});
		btnChonThongKe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String option=comboThongKeTheoPhieuMuon.getSelectedItem().toString();
				
				
				switch(option){
				case "Thống kê số lượng phiếu mượn theo ngày mượn":
				{
					ThongKeTheoNgayMuon ui=new ThongKeTheoNgayMuon("Thống kê phiếu mượn theo ngày mượn");
					ui.showWindow();
					break;
				}
				
				case "Thống kê số lượng tác giả":
				{
					ThongKeTheoTacGia ui=new ThongKeTheoTacGia("Thống kê theo tác giả");
					ui.showWindow();
					break;
				}
				
				case "Thống kê số lượng nhà xuất bản":
				{
					ThongKeTheoNhaXuatBan ui=new ThongKeTheoNhaXuatBan("Thống kê theo nhà xuất bản");
					ui.showWindow();
					break;
				}
				
				case "Thống kê số lượng thể loại":
				{
					ThongKeTheoTheLoai ui=new ThongKeTheoTheLoai("Thống kê theo thể loại");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng sách theo ngày mượn":
				{
					ThongKeSoLuongSachTheoNgayMuon ui=new ThongKeSoLuongSachTheoNgayMuon("Thống kê số lượng sách theo ngày mượn");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng độc giả theo ngày mượn":
				{
					ThongKeSoLuongDocGiaTheoNgayMuon ui=new ThongKeSoLuongDocGiaTheoNgayMuon("Thống kê số lượng độc giả theo ngày mượn");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng sách theo ngày trả":
				{
					ThongKeSoLuongSachTheoNgayTra ui=new ThongKeSoLuongSachTheoNgayTra("Thống kê số lượng sách theo ngày trả");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng độc giả theo ngày trả":
				{
					ThongKeSoLuongDocGiaTheoNgayTra ui=new ThongKeSoLuongDocGiaTheoNgayTra("Thống kê số lượng độc giả theo ngày trả");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng phiếu mượn theo ngày trả":
				{
					ThongKeSoLuongPhieuMuonTheNgayTra ui=new ThongKeSoLuongPhieuMuonTheNgayTra("Thống kê số lượng phiếu mượn theo ngày trả");
					ui.showWindow();
					break;
					
				}
				case "Thống kê số lượng sách theo hạn trả":
				{
					ThongKeSoLuongSachTheoHanTra ui=new ThongKeSoLuongSachTheoHanTra("Thống kê số lượng sách theo hạn trả");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng độc giả theo hạn trả":
				{
					ThongKeSoLuongDocGiaTheoHanTra ui=new ThongKeSoLuongDocGiaTheoHanTra("Thống kê số lượng độc giả theo hạn trả");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng phiếu mượn theo hạn trả":
				{
					ThongKeSoLuongPhieuMuonTheoHanTra ui=new ThongKeSoLuongPhieuMuonTheoHanTra("Thống kê số lượng phiếu mượn theo hạn trả");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng độc giả mượn quá hạn":
				{
					ThongKeSoLuongDocGiaMuonQuaHan ui=new ThongKeSoLuongDocGiaMuonQuaHan("Thống kê số lượng độc giả mượn quá hạn");
					ui.showWindow();
					break;
				}
				case "Thống kê số lượng sách theo giá bìa":
				{
					ThongKeSoLuongSachTheoGiaBia ui=new ThongKeSoLuongSachTheoGiaBia("Thống kê số lượng sách theo giá bìa");
					ui.showWindow();
					break;
				}
				case "Thống kê số lần độc giả bị phạt":
				{
					ThongKeDocGiaTheoSoTienPhat ui=new ThongKeDocGiaTheoSoTienPhat("Thống kê số lần độc giả bị phạt");
					ui.showWindow();
					break;
				}
				case "Thống kê số sách mượn của độc giả":
				{
					ThongKeSoSachMuonCuaDocGia ui=new ThongKeSoSachMuonCuaDocGia("Thống kê số sách mượn của độc giả");
					ui.showWindow();
					break;
				}
				}
			}
		});
		comboMaPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					HienDuLieuDocGiaTheoMaPhieuMuon();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		comboMaSachTra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					HienDuLieuTheoMaSachTra();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		comboBanDoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					hien_TenDG();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		comboSach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					hien_TenTL();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		btnLuuSach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sach sach=new Sach();
				xuLiLuuSach(sach);
			}
		});

		btnXoaSach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiXoaSach();
			}
		});

		
		tbSach.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tbSach.getSelectedRow();
				if(row==-1)return;
				String ma=tbSach.getValueAt(row, 0)+"";
				hienThiChiTiet(ma);
			}
		});

		btnTaoMoiSach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtMaSach.setText("");
				txtTenSach.setText("");
				txtTacGia.setText("");
				txtTheLoai.setText("");
				txtNXB.setText("");
				txtDonGia.setText("");
				txtSoLuong.setText("");
				txtTinhTrang.setText("");
				txtMaSach.requestFocus();
			}
		});


		tbBanDoc.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tbBanDoc.getSelectedRow();
				if(row==-1)return;
				String maBanDoc=tbBanDoc.getValueAt(row, 0)+"";
				hienThiChiTietBanDoc(maBanDoc);
			}
		});
		btnLuuBanDoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					xuLiLuuBanDoc();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnXoaBanDoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiXoaBanDoc();
			}
		});

		btnTaoMoiBanDoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtMaBanDoc.setText("");
				txtHoTen.setText("");
				txtSoDienThoai.setText("");
				txtNgaySinh.setText("");
				txtGioiTinh.setText("");
				txtQueQuan.setText("");
				txtEmail.setText("");
				txtMaBanDoc.requestFocus();
			}
		});
		
		btnThemFileBanDoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReadToExcelBanDoc();
			}
		});
		
		btnCapNhatPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				comboBanDoc.removeAllItems();
				comboSach.removeAllItems();
				MaBandoc(comboBanDoc);
				MaSach(comboSach);
			}
		});
		tbPhieuMuon.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tbPhieuMuon.getSelectedRow();
				if(row==-1)return;
				String maPhieuMuon=tbPhieuMuon.getValueAt(row, 0)+"";
				String maSachMuon=tbPhieuMuon.getValueAt(row, 3)+"";
				hienThiChiTietPhieuMuon(maPhieuMuon,maSachMuon);
				
			}
		});
		
		btnThemFilePhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ReadToExcelPhieuMuon();
			}
		});
		btnSuaPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					XuLiSuaPhieuMuon();
					JOptionPane.showMessageDialog(null, "Sửa phiếu mượn thành công");
					hienThiToanBoThongTinPhieuMuon();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnLuuPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					XuLiLuuPhieuMuon();
					JOptionPane.showMessageDialog(null, "Mượn sách thành công");
					hienThiToanBoThongTinPhieuMuon();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Mượn sách thất bại");
				}
			}
		});
	
		btnXoaPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiXoaPhieuMuon();
			}
		});
		
		btnInPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				XuatPhieuMuon ui=new XuatPhieuMuon("Xuất phiếu mượn");
				ui.showWindow();
			}
		});
		
		btnCapNhatTra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				HienThiToanBoThongTinTraSach();
				comboMaPhieuMuon.removeAllItems();
				comboMaSachTra.removeAllItems();
				MaPhieu(comboMaPhieuMuon);
				MaTaiLieuMuon(comboMaSachTra);
			}
		});
		
		btnInPhieuTra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				XuatPhieuTra ui=new XuatPhieuTra("In phiếu trả");
				ui.showWindow();
			}
		});
		tbTra.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tbTra.getSelectedRow();
				if(row==-1)return;
				String maPhieu=tbTra.getValueAt(row, 0)+"";
				String maSach=tbTra.getValueAt(row, 3)+"";
			   hienThiChiTietTraSach(maPhieu, maSach);
			}
		});
		
		btnXuatFileSach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReadToExcelSach();
			}
		});
		
		btnTaoMoiPhieuMuon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				XuLiTaoMoiPhieuMuon();
			}
		});
		
		btnLuuTraSach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					XuLiTraSach();
					JOptionPane.showMessageDialog(null, "Trả sách thành công");
					HienThiToanBoThongTinTraSach();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Trả sách thất bại");
				} 
			}
		});
		
		btnTaoMoiTraSach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
					txtTenBanDocTra.setText("");
					comboMaPhieuMuon.setSelectedItem("Chọn mã phiếu");
					txtMaBanDocTra.setText("");
					comboMaSachTra.setSelectedItem("Chọn mã tài liệu");
					txtTenBanDocTra.setText("");
					txtNgayMuon.setText("");
					txtSoLuongMuonSach.setText("");
					txtNgayHetHanTra.setText("");
					txtNgayTra.setText("");
					txtSoLuongTra.setText("");
				}
			});
		
		
	}



	protected void XuLiSuaPhieuMuon() throws SQLException {
		ChiTietMuonTra dg=new ChiTietMuonTra();
		dg.setMaPhieu(txtMaPhieuMuon.getText());
		dg.setMaTL((String)comboSach.getSelectedItem());
		dg.setMaDG((String)comboBanDoc.getSelectedItem());
		dg.setTenTL(txtTenSachMuon.getText());
		dg.setTenDG(txtTenBanDocMuon.getText());
		dg.setGia(txtGiaBiaSachMuon.getText());
		dg.setSLmuon(Integer.parseInt(txtSoLuongMuon.getText()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			dg.setNgayMuon(sdf.parse(txtNgayMuon.getText()));
			dg.setHanTra(sdf.parse(txtNgayHetHan.getText()));
		}catch(ParseException e){
			e.printStackTrace();
		}
		SuaPhieuMuon(dg);
	}

	protected void XuLiLuuPhieuMuon() throws SQLException {
		ChiTietMuonTra dg=new ChiTietMuonTra();
		dg.setMaPhieu(txtMaPhieuMuon.getText());
		dg.setMaTL((String)comboSach.getSelectedItem());
		dg.setMaDG((String)comboBanDoc.getSelectedItem());
		dg.setTenTL(txtTenSachMuon.getText());
		dg.setTenDG(txtTenBanDocMuon.getText());
		dg.setGia(txtGiaBiaSachMuon.getText());
		dg.setSLmuon(Integer.parseInt(txtSoLuongMuon.getText()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			dg.setNgayMuon(sdf.parse(txtNgayMuon.getText()));
			dg.setHanTra(sdf.parse(txtNgayHetHan.getText()));
		}catch(ParseException e){
			e.printStackTrace();
		}
		muon(dg);
	}

	private void muon(ChiTietMuonTra muon) throws SQLException {
		ConnectionSQL obj = new ConnectionSQL();
		Connection con = obj.connect();
		java.sql.Date ngay_muon = new java.sql.Date(muon.getNgayMuon().getTime());
		java.sql.Date han_tra = new java.sql.Date(muon.getHanTra().getTime());
		String sql = "INSERT INTO ChiTietMuonTra" +"(MaPhieu, MaDocGia,TenDocGia, MaSach,TenSach, GiaBia, NgayMuon, SoLuongmuon, NgayHetHan)"+ 
					 "VALUES" + "(?,?,?,?,?,?,?,?,?)";
		try{
			PreparedStatement prestate = con.prepareStatement(sql);
			prestate.setString(1, muon.getMaPhieu());
			prestate.setString(2, muon.getMaDG());
			prestate.setString(3, muon.getTenDG());
			prestate.setString(4, muon.getMaTL());
			prestate.setString(5, muon.getTenTL());
			prestate.setString(6, muon.getGia());
			prestate.setDate(7, ngay_muon);
			prestate.setInt(8, muon.getSLmuon());
			prestate.setDate(9, han_tra);
			prestate.executeUpdate();
			con.close();
		}catch (SQLException e) {
			System.err.println(e.getMessage( ));
		}

	}
	
	private void SuaPhieuMuon(ChiTietMuonTra muon) throws SQLException {
		ConnectionSQL obj = new ConnectionSQL();
		Connection con = obj.connect();
		java.sql.Date ngay_muon = new java.sql.Date(muon.getNgayMuon().getTime());
		java.sql.Date han_tra = new java.sql.Date(muon.getHanTra().getTime());
		String sql = "UPDATE ChiTietMuonTra SET TenDocGia=?, TenSach=?, GiaBia=?, NgayMuon=?, SoLuongmuon=?, NgayHetHan=? where  MaPhieu=? and MaDocGia=? and MaSach=?";
				
		try{
			PreparedStatement prestate = con.prepareStatement(sql);
			prestate.setString(1, muon.getTenDG());
			prestate.setString(2, muon.getTenTL());
			prestate.setString(3, muon.getGia());
			prestate.setDate(4, ngay_muon);
			prestate.setInt(5, muon.getSLmuon());
			prestate.setDate(6, han_tra);
			prestate.setString(7, muon.getMaPhieu());
			prestate.setString(8, muon.getMaDG());
			prestate.setString(9, muon.getMaTL());
			prestate.executeUpdate();
			con.close();
		}catch (SQLException e) {
			System.err.println(e.getMessage( ));
		}

	}

	protected void XuLiTaoMoiPhieuMuon() {
		txtMaPhieuMuon.setText("");
		comboBanDoc.setSelectedItem("Chọn mã độc giả");
		comboSach.setSelectedItem("Chọn mã tài liệu");
		txtTenBanDocMuon.setText("");
		txtTenSachMuon.setText("");
		txtGiaBiaSachMuon.setText("");
		txtSoLuongMuon.setText("");
		txtNgayMuon.setText("");
		txtNgayHetHan.setText("");

	}


	protected void xuLiXoaPhieuMuon() {
		PhieuMuonService pmService=new PhieuMuonService();
		ChiTietMuonTra pm=new ChiTietMuonTra();
		pm.setMaPhieu(txtMaPhieuMuon.getText());
		if( pmService.xoaPhieuMuon(pm)>0){
			JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thành công");
			hienThiToanBoThongTinPhieuMuon();
		}
		else{
			JOptionPane.showMessageDialog(null, "Xóa phiếu mượn không thành công");
		}
	}

	protected void xuLiXoaBanDoc() {
		BanDocService bdService=new BanDocService();
		BanDoc bd=new BanDoc();
		bd.setMaBanDoc(txtMaBanDoc.getText());
		if( bdService.xoaBandoc(bd)>0){
			JOptionPane.showMessageDialog(null, "Xóa thành công");
			hienThiToanBoThongTinBanDoc();
		}
		else{
			JOptionPane.showMessageDialog(null, "Xóa không thành công");
		}
	}

	protected void xuLiLuuBanDoc(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		BanDoc bd=new BanDoc();
		bd.setMaBanDoc(txtMaBanDoc.getText());
		bd.setHoTen(txtHoTen.getText());
		bd.setSoDienThoai(txtSoDienThoai.getText());
		bd.setEmail(txtEmail.getText());
		try{
			bd.setNgaySinh(sdf.parse(txtNgaySinh.getText()));
		}catch(Exception e){
			e.printStackTrace();
		}
		bd.setQueQuan(txtQueQuan.getText());
		bd.setGioiTinh(txtGioiTinh.getText());
		BanDocService banDocService=new BanDocService();
		if(kiemTraMaTonTaiBanDoc(txtMaBanDoc.getText())){
			int ret= JOptionPane.showConfirmDialog(null, 
					"Mã ["+txtMaBanDoc.getText()+"] đã tồn tại. Bạn có muốn cập nhật không?","Thông báo",
					JOptionPane.YES_NO_OPTION);
			if(ret==JOptionPane.NO_OPTION)return;
			if(banDocService.suaBanDoc(bd)>0){
				JOptionPane.showMessageDialog(null, "Sửa bạn đọc thành công");
				hienThiToanBoThongTinBanDoc();
			}
			else{
				JOptionPane.showMessageDialog(null, "Sửa bạn đọc thất bại");
			}
		}
		else{
			if(banDocService.luuBanDoc(bd)>0){
				JOptionPane.showMessageDialog(null, "Lưu bạn đọc thành công");
				hienThiToanBoThongTinBanDoc();
			}
			else{
				JOptionPane.showMessageDialog(null, "Lưu bạn đọc thất bại");
			}
		}


	}

	protected void xuLiLuuSach(Sach sach) {
		sach=new Sach();
		sach.setMaSach(txtMaSach.getText());
		sach.setTenSach(txtTenSach.getText());
		sach.setTacGia(txtTacGia.getText());
		sach.setTheLoai(txtTheLoai.getText());
		sach.setNXB(txtNXB.getText());
		sach.setGiaBia(Integer.parseInt(txtDonGia.getText()));
		sach.setTinhTrangMuon(txtTinhTrang.getText());
		sach.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
		SachService sachService=new SachService();
		if(kiemTraMaTonTai(txtMaSach.getText())){
			int ret= JOptionPane.showConfirmDialog(null, 
					"Mã ["+txtMaSach.getText()+"] đã tồn tại. Bạn có muốn cập nhật không?","Thông báo",
					JOptionPane.YES_NO_OPTION);
			if(ret==JOptionPane.NO_OPTION)return;
			if(sachService.suaSach(sach)>0){
				JOptionPane.showMessageDialog(null, "Sửa sách thành công");
				hienThiToanBoThongTinSach();
			}
			else{
				JOptionPane.showMessageDialog(null, "Sửa sách thất bại");
			}
		}
		else{
			if(sachService.luuSach(sach)>0){
				JOptionPane.showMessageDialog(null, "Lưu sách thành công");
				hienThiToanBoThongTinSach();
			}
			else{
				JOptionPane.showMessageDialog(null, "Lưu sách thất bại");
			}
		}
	}
	
	protected void xuLiLuuSachFile(Sach sach) {
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
			prestatement.setInt(9,0);
			prestatement.execute();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	protected void xuLiLuuBanDocFile(BanDoc bd) {
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
			prestatement.execute();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	protected void xuLiLuuPhieuMuonFile(ChiTietMuonTra ctmt) {
		try{
			java.sql.Date ngay_muon=new java.sql.Date(ctmt.getNgayMuon().getTime());
			java.sql.Date ngay_het_han=new java.sql.Date(ctmt.getHanTra().getTime());
			String sql="insert into DocGia values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement prestatement=conn.prepareStatement(sql);
			prestatement.setString(1, ctmt.getMaPhieu());
			prestatement.setString(2, ctmt.getMaDG());
			prestatement.setString(3, ctmt.getTenDG());
			prestatement.setString(4, ctmt.getMaTL());
			prestatement.setString(5, ctmt.getTenTL());
			prestatement.setString(6, ctmt.getGia());
			prestatement.setDate(7, ngay_muon);
			prestatement.setInt(8, ctmt.getSLmuon());
			prestatement.setDate(9,ngay_het_han);
			prestatement.execute();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private boolean kiemTraMaTonTaiPhieuMuon(String maPhieu,String maBanDoc){
		try{
			String sql="select *from ChiTietMuonTra where (MaPhieu=? AND MaDocGia=?)";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, maPhieu);
			preStatement.setString(2, maBanDoc);
			ResultSet rs=preStatement.executeQuery();
			return rs.next();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	private boolean kiemTraMaTonTai(String maSach){
		try{
			String sql="select *from Sach where MaSach=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, maSach);
			ResultSet rs=preStatement.executeQuery();
			return rs.next();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	private boolean kiemTraMaTonTaiBanDoc(String maBanDoc){
		try{
			String sql="select *from DocGia where MaDocGia=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, maBanDoc);
			ResultSet rs=preStatement.executeQuery();
			return rs.next();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	protected void xuLiXoaSach() {
		SachService sachService=new SachService();
		Sach sach=new Sach();
		sach.setMaSach(txtMaSach.getText());
		if(sachService.xoaSach(sach)>0){
			JOptionPane.showMessageDialog(null, "Xóa thành công");
			hienThiToanBoThongTinSach();
		}
		else{
			JOptionPane.showMessageDialog(null, "Xóa không thành công");
		}
	}
	
	
	private void ReadToExcelSach(){
		JFileChooser choose=new JFileChooser();
        File dir=new File("D:\\Excel");
        choose.setCurrentDirectory(dir);
        FileFilter filter=new FileNameExtensionFilter("Cơ sở dữ liệu mới", "csv");
        choose.setFileFilter(filter);
        choose.setDialogTitle("Nhập cơ sở dữ liệu");
        choose.showOpenDialog(this);
        if (choose.getSelectedFile()==null) {
            return ;
        }
        try {
            InputStream in=new FileInputStream(choose.getSelectedFile());
            Reader r=new InputStreamReader(in, "UTF8");
            BufferedReader br=new BufferedReader(r);
            String s=new String();
            while ((s=br.readLine())!=null) {                
                Sach ds=new Sach();
                char []c=s.toCharArray();
                int begin=0;
                int d=0;
                for (int i = 0; i < c.length; i++) {
                    if (c[i]== ',') {
                        if (d==0) {
                            ds.setMaSach(s.substring(0, i));
                            begin=i;
                            d++;
                        }
                        else if (d==1) {
                            ds.setTenSach(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==2) {
                            ds.setTacGia(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==3) {
                            ds.setTheLoai(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==4) {
                            ds.setNXB(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==5) {
                            ds.setGiaBia(Integer.parseInt(s.substring(begin+1, i)));
                            begin=i;
                            d++;
                        }
                        else if (d==6) {
                            ds.setTinhTrangMuon(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==7) {
                            ds.setSoLuong(Integer.parseInt(s.substring(begin+1, i)));
                        }                        
                    }
                }
                xuLiLuuSachFile(ds);
                hienThiToanBoThongTinSach();
            }
            
            
        } catch (Exception e) {
        }
	}
	
	private void ReadToExcelBanDoc(){
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		JFileChooser choose=new JFileChooser();
        File dir=new File("D:\\Excel");
        choose.setCurrentDirectory(dir);
        FileFilter filter=new FileNameExtensionFilter("Cơ sở dữ liệu mới", "csv");
        choose.setFileFilter(filter);
        choose.setDialogTitle("Nhập cơ sở dữ liệu");
        choose.showOpenDialog(this);
        if (choose.getSelectedFile()==null) {
            return ;
        }
        try {
            InputStream in=new FileInputStream(choose.getSelectedFile());
            Reader r=new InputStreamReader(in, "UTF8");
            BufferedReader br=new BufferedReader(r);
            String s=new String();
            while ((s=br.readLine())!=null) {                
                BanDoc ds=new BanDoc();
                char []c=s.toCharArray();
                int begin=0;
                int d=0;
                for (int i = 0; i < c.length; i++) {
                    if (c[i]== ',') {
                        if (d==0) {
                            ds.setMaBanDoc(s.substring(0, i));
                            begin=i;
                            d++;
                        }
                        else if (d==1) {
                            ds.setHoTen(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==2) {
                            ds.setSoDienThoai(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==3) {
                            ds.setEmail(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==4) {
                            ds.setNgaySinh(sdf.parse(s.substring(begin+1, i)));
            
                            begin=i;
                            d++;
                        }
                        
                        else if (d==5) {
                            ds.setQueQuan(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==6) {
                            ds.setGioiTinh(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }       
                        else if(d==7){
                        	
                        }
                    }
                }
                xuLiLuuBanDocFile(ds);
                hienThiToanBoThongTinBanDoc();
            }            
        } catch (Exception e) {
        }
	}
	
	private void ReadToExcelPhieuMuon(){
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		JFileChooser choose=new JFileChooser();
        File dir=new File("D:\\Excel");
        choose.setCurrentDirectory(dir);
        FileFilter filter=new FileNameExtensionFilter("Cơ sở dữ liệu mới", "csv");
        choose.setFileFilter(filter);
        choose.setDialogTitle("Nhập cơ sở dữ liệu");
        choose.showOpenDialog(this);
        if (choose.getSelectedFile()==null) {
            return ;
        }
        try {
            InputStream in=new FileInputStream(choose.getSelectedFile());
            Reader r=new InputStreamReader(in, "UTF8");
            BufferedReader br=new BufferedReader(r);
            String s=new String();
            while ((s=br.readLine())!=null) {                
                ChiTietMuonTra ds=new ChiTietMuonTra();
                char []c=s.toCharArray();
                int begin=0;
                int d=0;
                for (int i = 0; i < c.length; i++) {
                    if (c[i]== ',') {
                        if (d==0) {
                            ds.setMaPhieu(s.substring(0, i));
                            begin=i;
                            d++;
                        }
                        else if (d==1) {
                            ds.setMaDG(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==2) {
                            ds.setTenDG(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==3) {
                            ds.setMaTL(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        else if (d==4) {
                            ds.setTenTL(s.substring(begin+1, i));
                            begin=i;
                            d++;
                        }
                        
                        else if (d==5) {
                            ds.setGia((s.substring(begin+1, i)));
                        }  
                        else if (d==6) {
                            ds.setNgayMuon(sdf.parse(s.substring(begin+1, i)));
                            begin=i;
                            d++;
                        }
                        else if (d==7) {
                            ds.setSLmuon(Integer.parseInt(s.substring(begin+1, i)));
                        }  
                        else if (d==8) {
                            ds.setHanTra(sdf.parse(s.substring(begin+1, i)));
                            begin=i;
                            d++;
                        }
                        else if(d==9){
                        	
                        }
                    }
                }
                xuLiLuuPhieuMuonFile(ds);
                hienThiToanBoThongTinPhieuMuon();
            }
            
            
        } catch (Exception e) {
        }
	}


	public void showWindow(){
		this.setSize(1500,1000);
		this.setBackground(Color.CYAN);
		this.setBackground(Color.lightGray);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
