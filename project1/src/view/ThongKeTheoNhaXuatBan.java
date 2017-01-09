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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import IO.WriteFileWord;

public class ThongKeTheoNhaXuatBan extends JDialog{
	DefaultTableModel dtm=new DefaultTableModel();
	JTable tb=new JTable();
	JButton btnThongKe,btnIn;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	public ThongKeTheoNhaXuatBan(String title){
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
		
		JPanel pnNXB=new JPanel();
		pnNXB.setLayout(new BorderLayout());
		pnMain.add(pnNXB);
		JPanel pnHeader=new JPanel();
		JLabel lblHeader=new JLabel("Thống kê nhà xuất bản");
		pnHeader.add(lblHeader);
		lblHeader.setForeground(Color.BLUE);
		Font ft=new Font("arial",Font.BOLD,20);
		lblHeader.setFont(ft);
		pnNXB.add(pnHeader,BorderLayout.NORTH);
		dtm=new DefaultTableModel();
		dtm.addColumn("Nhà xuất bản");
		dtm.addColumn("Số lượng");
		tb=new JTable(dtm);
		JScrollPane sc=new JScrollPane(tb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnNXB.add(sc,BorderLayout.CENTER);
		
		JPanel pnButton=new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnThongKe=new JButton("Thống kê");
		btnIn=new JButton("Xuất file");
		pnButton.add(btnThongKe);
		pnButton.add(btnIn);
		pnNXB.add(pnButton,BorderLayout.SOUTH);
		
	}
	
	private ResultSet TongSoLuong(){
		try{
			String sql="select count(NXB) as nhaxuatban from Sach group by NXB";
			preStatement=conn.prepareStatement(sql);
		    return preStatement.executeQuery();
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	private void addEvents() {
		btnThongKe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String sql="select NXB,count(NXB) as nhaxuatban from Sach group by NXB";
					preStatement=conn.prepareStatement(sql);
					result=preStatement.executeQuery();
					dtm.setRowCount(0);
					
					while(result.next()){
						Vector<Object> vec=new Vector<>();
						vec.add(result.getString("NXB"));
						vec.add(Integer.toString(result.getInt("nhaxuatban")));
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
				wf.WriteWord(tb, "Thống kê theo nhà xuất bản","Thống kê theo nhà xuất bản");
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
