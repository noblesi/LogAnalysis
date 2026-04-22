package Login;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class LoginView extends JFrame implements ActionListener{
	
	private String id;
	private String password;
	
	private AuthService as;
	
	private JPanel pan;
	private JLabel labId;
	private JLabel labPw;
	private JTextField tfId;
	private JPasswordField tfPw;
	private JButton okBt;
	private JButton cancelBt;
	private JButton inputBt;
	
	public LoginView() {
		
		this.setVisible(true);
		
		as = new AuthService();
		
		pan = new JPanel(new GridLayout(4,2));
		labId = new JLabel("아이디");
		labPw = new JLabel("비밀번호");
		tfId = new JTextField();
		tfPw = new JPasswordField();
		okBt = new JButton("로그인");
		cancelBt = new JButton("취소");
		inputBt = new JButton("회원가입");
		
		this.setSize(300, 200);
		this.setResizable(false);
//		this.setLocation(W, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.getRootPane().setDefaultButton(okBt);
		this.add(pan);
		pan.add(labId);
		pan.add(tfId);
		pan.add(labPw);
		pan.add(tfPw);
		pan.add(okBt);
		pan.add(cancelBt);
		pan.add(inputBt);
		
		
		okBt.addActionListener(this);
		cancelBt.addActionListener(this);
		tfId.addActionListener(this);
		tfPw.addActionListener(this);
		
		//처음 포커스 위치
		tfId.grabFocus();
		
		inputBt.setVisible(false);
		
	}// LoginView

	public String inputId() {
		return id;
	}// inputId
	
	public String inputPassword() {
		return password;
	}// inputPassword
	
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}// showMessage

	//이벤트 처리부분
	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("이벤트 작용중" + tfPw.isFocusOwner());
		
//		tfId.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("enter"), "취소");
//		if(tfId.isFocusOwner()) {
//			tfPw.grabFocus();
//		} else if(tfPw.isFocusOwner()) {
//			okBt.grabFocus();
//			okBt.doClick();
//		}// end if
		
		switch(e.getActionCommand()) {
		case "로그인" :
//			System.out.println("로그인 버튼"); 
			id = tfId.getText();
			password = new String(tfPw.getPassword());
			as.login(inputId(), inputPassword());
			break;
		case "취소" :
//			System.out.println("취소 버튼");
			showMessage("프로그램을 종료 합니다.");
			this.dispose();
			break;
		case "회원가입" :
			break;
		}// end switch
		
	}// actionPerfromed
	
//	public static void main(String[] args) {
//		new LoginView();
//	}// main
}// class