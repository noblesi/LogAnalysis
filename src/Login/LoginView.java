package Login;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView {
	private String id;
	private String password;
	private AuthService as;
	
	private JFrame frm = null;
	private JPanel pan = null;
	private JLabel labId = null;
	private JLabel labPw = null;
	private JTextField tfId = null;
	private JPasswordField tfPw = null;
	private JButton btOk = null;
	private JButton btCencle = null;
	private JButton btInput = null;
	
	public LoginView() {
		as = new AuthService();
		runLoginWindow();
	}// LoginView
	
	public void runLoginWindow() {
		
		frm = new JFrame("로그인 창");
		pan = new JPanel(new GridLayout(4,3));
		labId = new JLabel("아이디");
		labPw = new JLabel("비밀번호");
		tfId = new JTextField();
		tfPw = new JPasswordField();
		btOk = new JButton("로그인");
		btCencle = new JButton("취소");
		btInput = new JButton("회원가입");
		
		
		frm.setSize(300, 200);
		frm.setLocation(0, 0);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.add(pan);
		pan.add(labId);
		pan.add(tfId);
		pan.add(labPw);
		pan.add(tfPw);
		pan.add(btOk);
		pan.add(btCencle);
		pan.add(btInput);
		
		// 로그인 버튼을 눌렀을때 이벤트 처리
		ActionListener alOkBt = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				id = tfId.getText(); 
				password = new String(tfPw.getPassword());
				System.out.println(id + " / " + password);
				as.login(inputId(), inputPassword());
			}// actionPerformed
		};
		btOk.addActionListener(alOkBt);
		
		ActionListener alCancleBt = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "프로그램을 종료 합니다.");
//				frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frm.dispose();
			}// actionPerformed
		};
		btCencle.addActionListener(alCancleBt);
		
		btInput.setVisible(false);
		frm.setVisible(true);
	}// runLoginWindow
	
	public String inputId() {
		return id;
	}// inputId
	
	public String inputPassword() {
		return password;
	}// inputPassword
	
	public void showMessage(String msg) {
		
	}// showMessage
	
}// class
