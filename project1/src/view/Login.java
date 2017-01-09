package view;

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
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	JButton btnLogin,btnThoat;
	JTextField txtUser;
	JPasswordField txtPass;
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	
	public Login(String title){
		super(title);
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
		con.setBackground(Color.BLUE);
		JPanel pnMain=new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
//		JPanel pnBackground=new JPanel();
//		pnMain.add(pnBackground);
//		JLabel lblBackground=new JLabel();
//		lblBackground.setOpaque(true);
//		lblBackground.setBackground(Color.GRAY);
//		pnBackground.add(lblBackground);
		
		
		JPanel pnHeader=new JPanel();
		pnMain.add(pnHeader);
		JLabel lblHeader=new JLabel("Chương trình quản lí thư viện");
		lblHeader.setForeground(Color.BLUE);
		Font fontTieuDe=new Font("arial", Font.BOLD, 20);
		lblHeader.setFont(fontTieuDe);
		pnHeader.add(lblHeader);
		
		JPanel pnUser=new JPanel();
		pnUser.setLayout(new FlowLayout());
		JLabel lblUser=new JLabel("Username");
		txtUser=new JTextField(14);
		
		pnUser.add(lblUser);
		pnUser.add(txtUser);
		pnMain.add(pnUser);
		
		JPanel pnPass=new JPanel();
		pnPass.setLayout(new FlowLayout());
		JLabel lblPass=new JLabel("Password");
		txtPass =new JPasswordField(14);
		
		pnPass.add(lblPass);
		pnPass.add(txtPass);
		pnMain.add(pnPass);
		
		JPanel pnButton=new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnLogin=new JButton("Login");
		btnLogin.setIcon(new ImageIcon("hinhanh/login.png"));
		btnThoat=new JButton("Exit");
		btnThoat.setIcon(new ImageIcon("hinhanh/exit.png"));
		pnButton.add(btnLogin);
		pnButton.add(btnThoat);
		pnMain.add(pnButton);
		con.add(pnMain);
	}
	
	public void addEvents(){
		btnThoat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLiDangNhap();
			}
		});
		
	}
	
	protected void xuLiDangNhap() {
		try{
			String sql="select *from TaiKhoanDangNhap where ID=? and PassWord=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, txtUser.getText());
			preStatement.setString(2, txtPass.getText());
			result=preStatement.executeQuery();
			if(result.next()){
				FormQuanLiThuVien ui=new FormQuanLiThuVien("Chương trình quản lí thư viện");
				ui.showWindow();
			}
			else{
				JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không đúng!", "Xác nhận tài khoản",JOptionPane.OK_OPTION);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void showWindow(){
		this.setSize(400,250);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

}
