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
	
	JFrame frm;
	JPanel pan;
	JLabel labId;
	JLabel labPw;
	JTextField tfId;
	JPasswordField tfPw;
	JButton okBt;
	JButton cancleBt;
	JButton inputBt;
	
	
	public LoginView() {
		
		as = new AuthService();
		
		frm = new JFrame("로그인 창");
		pan = new JPanel(new GridLayout(4,2));
		labId = new JLabel("아이디");
		labPw = new JLabel("비밀번호");
		tfId = new JTextField();
		tfPw = new JPasswordField();
		okBt = new JButton("로그인");
		cancleBt = new JButton("취소");
		inputBt = new JButton("회원가입");
		
		frm.setSize(300, 200);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.add(pan);
		pan.add(labId);
		pan.add(tfId);
		pan.add(labPw);
		pan.add(tfPw);
		pan.add(okBt);
		pan.add(cancleBt);
		pan.add(inputBt);
		inputBt.setVisible(false);
		frm.setVisible(true);
		
		// 로그인 버튼을 눌렀을 때의 이벤트 처리
		ActionListener alOk = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				id = tfId.getText();
				password = new String( tfPw.getPassword());
				as.login(inputId(), inputPassword());
				if(as.isLoginSuc()) {
					System.out.println("로그인 성공");
					frm.setVisible(false);
				}// end if
			}// end actionPerformed
		};
		
		okBt.addActionListener(alOk);
		
		// 취소 버튼 눌럿을때의 이벤트 처리
		ActionListener alCancle = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
				frm.dispose();
			}// actionPerformed
		};
		
		cancleBt.addActionListener(alCancle);
		
	}// LoginView
	
	public String inputId() {
		return id;
	}// inputId
	
	public String inputPassword() {
		return password;
	}// inputPassword
	
	public void showMessage(String msg) {
		
	}// showMessage
	
	
	/**
	 * test용 향후 삭제
	 * @param args
//	 */
//	public static void main(String[] args) {
//		new LoginView();
//	}// main
	
}// class
