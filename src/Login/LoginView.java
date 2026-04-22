package Login;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView {

	private String id;
	private String password;
	private final AuthService authService;

	public LoginView() {
		authService = new AuthService();
	}

	public String inputId() {
		return id;
	}

	public String inputPassword() {
		return password;
	}

	public User login() {
		while (true) {
			JTextField idField = new JTextField();
			JPasswordField passwordField = new JPasswordField();

			if (id != null) {
				idField.setText(id);
			}

			JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
			panel.add(new JLabel("아이디"));
			panel.add(idField);
			panel.add(new JLabel("비밀번호"));
			panel.add(passwordField);

			int option = JOptionPane.showConfirmDialog(
					null,
					panel,
					"로그인",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (option != JOptionPane.OK_OPTION) {
				return null;
			}

			id = idField.getText().trim();
			password = new String(passwordField.getPassword());

			if (id.isEmpty() || password.isEmpty()) {
				showMessage("아이디와 비밀번호를 모두 입력하세요.");
				continue;
			}

			User user = authService.login(inputId(), inputPassword());
			if (user != null) {
				showMessage(user.getId() + " 로그인 성공");
				return user;
			}

			showMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
}
