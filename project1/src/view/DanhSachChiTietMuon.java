package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SQLService.ConnectionSQL;

public class DanhSachChiTietMuon extends JDialog{
	private Vector tableData;
	private Vector tableHeader;
	private DefaultTableModel dtmChiTietPhieuMuon;
	JTable tbDanhSachChiTietMuon;
	JButton btnHienThi,btnTroVe,btnThoat;
	public DanhSachChiTietMuon(String title) {
		this.setTitle(title);
		AddControls();
		AddEvents();
//		HienThiToanBoThongTinMuon();
	}

	private void AddEvents() {
		btnHienThi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HienThiToanBoThongTinMuon();
			}
		});
		btnTroVe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FormQuanLiThuVien ui=new FormQuanLiThuVien("");
				ui.showWindow();
			}
		});
		btnThoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	private void AddControls() {
		Container con=getContentPane();
		JPanel pnMain=new JPanel(new BorderLayout());
		con.add(pnMain);
		JPanel pnNourth=new JPanel();
		JPanel pnCenter=new JPanel();
		JPanel pnSouth=new JPanel();
		pnMain.add(pnNourth,BorderLayout.NORTH);
		pnMain.add(pnCenter,BorderLayout.CENTER);
		pnMain.add(pnSouth,BorderLayout.SOUTH);
		JLabel lblThongTinMuon=new JLabel("Thông tin mượn");
		lblThongTinMuon.setForeground(Color.BLUE);
		Font ft1=new Font("arial",Font.BOLD,20);
		lblThongTinMuon.setFont(ft1);
		pnNourth.setLayout(new BorderLayout());
		JPanel pnThongTinPhieuMuon=new JPanel();
		pnNourth.add(pnThongTinPhieuMuon,BorderLayout.CENTER);
		pnThongTinPhieuMuon.add(lblThongTinMuon);
		
		pnCenter.setLayout(new BorderLayout());
		dtmChiTietPhieuMuon=new DefaultTableModel();
		dtmChiTietPhieuMuon.addColumn("Mã phiếu");
		dtmChiTietPhieuMuon.addColumn("Mã bạn đọc");
		dtmChiTietPhieuMuon.addColumn("Tên bạn đọc");
		dtmChiTietPhieuMuon.addColumn("Mã sách");
		dtmChiTietPhieuMuon.addColumn("Tên sách");
		dtmChiTietPhieuMuon.addColumn("Giá");
	    dtmChiTietPhieuMuon.addColumn("Ngày mượn");
		dtmChiTietPhieuMuon.addColumn("Số lượng mượn");
		dtmChiTietPhieuMuon.addColumn("Hạn trả");
		tbDanhSachChiTietMuon=new JTable(dtmChiTietPhieuMuon);
		JScrollPane sc=new JScrollPane(tbDanhSachChiTietMuon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnCenter.add(sc,BorderLayout.CENTER);
		
		pnSouth.setLayout(new BorderLayout());
		JPanel pnButton=new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnHienThi=new JButton("Hiển thị");
		btnTroVe=new JButton("Trở về");
		btnThoat=new JButton("Thoát");
		pnButton.add(btnHienThi);
		pnButton.add(btnTroVe);
		pnButton.add(btnThoat);
		pnSouth.add(pnButton,BorderLayout.SOUTH);
		
		
	}
	
	public void HienThiToanBoThongTinMuon() {
        Connection conn = null;
        try {
            conn = ConnectionSQL.connect();
            ResultSet rs = null;
            PreparedStatement stt = null;
            try {
//                String sql = "SELECT  MaPhieu, MaDocGia,TenDocGia, "
//                		+ "MaSach,TenSach, GiaBia, NgayMuon, SoLuongMuon, "
//                		+ "NgayHetHan FROM ChiTietMuonTra WHERE NgayTra = 0000-00-00" ;
            	String sql="select *from ChiTietMuonTra";
                rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    row.add(rs.getString("MaPhieu"));
                    row.add(rs.getString("MaDocGia"));
                    row.add(rs.getString("TenDocGia"));
                    row.add(rs.getString("MaSach"));
                    row.add(rs.getString("TenSach"));
                    row.add(rs.getInt("GiaBia"));
                    row.add(rs.getDate("NgayMuon"));
                    row.add(rs.getInt("SoLuongMuon"));
                    row.add(rs.getDate("NgayHetHan"));
                    dtmChiTietPhieuMuon.addRow(row);
                }
                
            } catch (Exception ex) {
                System.err.println("Lệnh getScoreTop sai");
                ex.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("loi ket noi");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
    }

	
	public void ShowWindows(){
		this.setSize(700,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}

}
