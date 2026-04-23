package Login;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
	private Map<String, User> userMap;

	public AuthService() {
		userMap = new HashMap<String, User>();
		loginSuc = false;
		userMap.put("admin", new User("admin","1234", Role.ADMIN));
		userMap.put("administrator", new User("administrator","12345", Role.ADMINISTRATOR));
		userMap.put("root", new User("root","1111", Role.ROOT));
		
	}// AuthService

	public User login(String id, String password) {
		if (id == null || password == null) {
			return null;
		}

		User user = userMap.get(id);
		if (user == null) {
			return null;
		}

		if (!user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}

	public Map<String, User> getUserMap() {
		return Collections.unmodifiableMap(userMap);
	}

	public void setUserMap(Map<String, User> userMap) {
		this.userMap = new HashMap<String, User>(userMap);
	}
}
