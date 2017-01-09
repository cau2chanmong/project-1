package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import IO.WriteFileWord;

public class ThongKeDocGiaTheoSoTienPhat extends JDialog{
	DefaultTableModel dtm=new DefaultTableModel();
	JTable tb=new JTable();
	JButton btnThongKe,btnIn;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public ThongKeDocGiaTheoSoTienPhat(String title){
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

	private void addControls() {
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		con.add(pnMain);
		
		
		JPanel pnNgayMuon=new JPanel();
		pnNgayMuon.setLayout(new BorderLayout());
		pnMain.add(pnNgayMuon);
		JPanel pnHeader=new JPanel();
		JLabel lblHeader=new JLabel("Thống kê số lần độc giả bị phạt");
		lblHeader.setForeground(Color.BLUE);
		Font ft=new Font("arial",Font.BOLD,20);
		lblHeader.setFont(ft);
		pnHeader.add(lblHeader);
		pnNgayMuon.add(pnHeader,BorderLayout.NORTH);
		dtm=new DefaultTableModel();
		dtm.addColumn("Mã độc giả");
		dtm.addColumn("Số lần phạt");
		tb=new JTable(dtm);
		JScrollPane sc=new JScrollPane(tb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnNgayMuon.add(sc,BorderLayout.CENTER);
		
		JPanel pnButton=new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnThongKe=new JButton("Thống kê");
		btnIn=new JButton("Xuất file");
		pnButton.add(btnThongKe);
		pnButton.add(btnIn);
		pnNgayMuon.add(pnButton,BorderLayout.SOUTH);
		
	}
	
	private void addEvents() {
		btnThongKe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String sql="select MaDocGia,count(PhatQuaHan) as madocgia from ChiTietMuonTra group by MaDocGia";
					preStatement=conn.prepareStatement(sql);
					result=preStatement.executeQuery();
					dtm.setRowCount(0);
					
					while(result.next()){
						Vector<Object> vec=new Vector<>();
						vec.add(result.getString("MaDocGia"));
						vec.add(Integer.toString(result.getInt("madocgia")));
						dtm.addRow(vec);
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		btnIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WriteFileWord wf=new WriteFileWord();
				wf.WriteWord(tb, "Thống kê số lần phạt của độc giả","Thống kê số lần phạt của độc giả");
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