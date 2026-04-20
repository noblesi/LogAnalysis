package Login;

/**
 * 유저 정보를 저장한다.
 */
public class User {
	private String id;
	private String password;
	private Role role;

	public User(String id, String password, Role role) {
		this.id = id;
		this.password = password;
		this.role = role;
	}// User

	/**
	 * 권한이 root가 아니라면 report 작성 가능<br>
	 * 작성가능 : true, 작성불가능 : false
	 * @return report작성 권한
	 */
	public boolean canCreateReport() {
		boolean createReport = false;
		
		if(this.role != Role.ROOT) {
			createReport = true;
		}// end if
		
		return createReport;
	}// canCreateReport
	
	public String getId() {
		return id;
	}// getId
	
	public void setId(String id) {
		this.id = id;
	}// setId
	
	public String getPassword() {
		return password;
	}// getPassword
	
	public void setPassword(String password) {
		this.password = password;
	}// setPassword
	
	public Role getRole() {
		return role;
	}// getRole
	
	public void setRole(Role role) {
		this.role = role;
	}// setRole
}// class
