package Analysis;

import java.io.IOException;

import Login.LoginView;
import Login.User;

public class Run {

	public void start() throws IOException {
		LoginView loginView = new LoginView();
		User loginUser = loginView.login();

		if (loginUser == null) {
			loginView.showMessage("로그인이 취소되었습니다.");
			return;
		}

		LogMenuView logMenuView = new LogMenuView();
		logMenuView.displayMenu(loginUser);
	}
}
