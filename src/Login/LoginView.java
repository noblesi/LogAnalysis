package Login;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView {
	private String id;
	private String password;
	
	public LoginView() {
		
		JFrame frm = new JFrame("로그인 창");
		JPanel pan = new JPanel(new GridLayout(4,2));
		JLabel labId = new JLabel("아이디");
		JLabel labPw = new JLabel("비밀번호");
		JTextField tfId = new JTextField();
		JPasswordField tfPw = new JPasswordField();
		
		frm.setSize(300, 200);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.add(pan);
		pan.add(labId);
		pan.add(tfId);
		pan.add(labPw);
		pan.add(tfPw);
		
		frm.setVisible(true);
		
	}// LoginView
	
	public String inputId() {
		return id;
	}// inputId
	
	public String inputPassword() {
		return password;
	}// inputPassword
	
	public void showMessage(String msg) {
		
	}// showMessage
	
}// class
