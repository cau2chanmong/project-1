package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import IO.WriteFileWord;

public class ThongKeTheoTheLoai extends JDialog{
	DefaultTableModel dtm=new DefaultTableModel();
	JTable tb=new JTable();
	JButton btnThongKe,btnIn;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public ThongKeTheoTheLoai(String title){
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
		
		JPanel pnTheLoai=new JPanel();
		pnTheLoai.setLayout(new BorderLayout());
		pnMain.add(pnTheLoai);
		JPanel pnHeader=new JPanel();
		JLabel lblHeader=new JLabel("Thống kê thể loại");
		pnHeader.add(lblHeader);
		lblHeader.setForeground(Color.BLUE);
		Font ft=new Font("arial",Font.BOLD,20);
		lblHeader.setFont(ft);
		pnTheLoai.add(pnHeader,BorderLayout.NORTH);
		dtm=new DefaultTableModel();
		dtm.addColumn("Thể loại");
		dtm.addColumn("Số lượng");
		tb=new JTable(dtm);
		JScrollPane sc=new JScrollPane(tb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTheLoai.add(sc,BorderLayout.CENTER);
		
		JPanel pnButton=new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnThongKe=new JButton("Thống kê");
		btnIn=new JButton("Xuất file");
		pnButton.add(btnThongKe);
		pnButton.add(btnIn);
		pnTheLoai.add(pnButton,BorderLayout.SOUTH);
		
	}
	
	

	private void addEvents() {
		btnThongKe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String sql="select TheLoai,count(TheLoai) as theloai from Sach group by TheLoai";
					preStatement=conn.prepareStatement(sql);
					result=preStatement.executeQuery();
					dtm.setRowCount(0);
					
					while(result.next()){
						Vector<Object> vec=new Vector<>();
						vec.add(result.getString("TheLoai"));
						vec.add(Integer.toString(result.getInt("theloai")));
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
				wf.WriteWord(tb, "Thống kê theo thể loại","Thống kê theo thể loại");
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
