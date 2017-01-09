package test;

import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.FormQuanLiThuVien;

public class TestFormQuanLiThuVien {
	public static void main(String[] avgs) throws ParseException{
		FormQuanLiThuVien ui=new FormQuanLiThuVien("Quản lí thư viện");
//		ui.setContentPane(new JLabel(new ImageIcon("hinhanh/background.png")));
		ui.showWindow();
	}

}
