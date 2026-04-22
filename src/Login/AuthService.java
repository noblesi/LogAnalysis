package Login;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * 입력된 id와 password를 판별하여 인증절차를 진행한다.
 */
public class AuthService {
	private Map<String, User> userMap;
	
	private boolean loginSuc;
	
	public AuthService() {
		userMap = new HashMap<String, User>();
		loginSuc = false;
		userMap.put("admin", new User("admin","1234", Role.ADMIN));
		userMap.put("admin", new User("administrator","12345", Role.ADMINISTRATOR));
		userMap.put("root", new User("root","1111", Role.ROOT));
		
	}// AuthService

	/**
	 * id와 password 비교한다.
	 * @param id
	 * @param password
	 * @return User 정보 id or password가 맞지 않으면 null return
	 */
	public User login(String id, String password) {
		
		User user = null;
		
		if(userMap.containsKey(id)) {
			user = userMap.get(id);
			loginSuc = true;
			if(!user.getPassword().toString().equals(password)) {
				loginSuc = false;
				JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
			}// end if
		} else {
			JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");
			user = null;
		}// end else if
		
		return user;
	}// login
	
	public Map<String, User> getUserMap() {
		return userMap;
	}// getUserMap
	
	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}// setUserMap

	public boolean isLoginSuc() {
		return loginSuc;
	}// isLoginSuc

	public void setLoginSuc(boolean loginSuc) {
		this.loginSuc = loginSuc;
	}// setLoginSuc
	
	
	
}// class
