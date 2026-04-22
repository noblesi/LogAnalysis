package Analysis;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LogMenuView {

	private String currentUserId;
	private String currentUserRole;

	/**
	 * @param userObj 로그인 성공 시 AuthService로부터 전달받을 User 객체
	 */

	public void displayMenu(Object userObj) {
        //테스트용 임시 값
		this.currentUserId = (userObj != null) ? userObj.toString() : "guest";
		this.currentUserRole = "ROOT";

		while (true) {
			// 1. 팀 규칙에 따른 메뉴 구성
			String menu = "[SIST 로그 분석 시스템]\n" 
			        + "접속 계정 : " + this.currentUserId + " (" + this.currentUserRole + ")\n"
					+ "----------------------------\n" 
			        + "1. 모든 분석 결과 화면 출력\n" + "2. 보고서 파일 생성(Report)\n"
					+ "3. 특정 라인 범위 분석\n" 
					+ "4. 프로그램 종료\n" 
					+ "----------------------------\n" 
					+ "원하는 번호를 선택하세요.";

			String input = JOptionPane.showInputDialog(null, menu, "메뉴", JOptionPane.QUESTION_MESSAGE);

			// 2.종료 및 취소 처리
			if (input == null || "4".equals(input)) {
				JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
				break;
			} // end if

			processCommand(input);
		} // end while
	}// end displayMenu

	// 3. 메뉴별 분기 로직
	private void processCommand(String input) {
		switch (input) {
		case "1": // 분석결과 출력
			JOptionPane.showMessageDialog(null, "분석 결과 화면을 준비합니다.");
			break;

		case "2":
			// [보안] ROOT 계정 확인
			if ("ROOT".equalsIgnoreCase(this.currentUserRole)) {
				showError("관리자(ROOT)는 리포트 생성 권한이 없습니다.");
			} else {
				if (selectSaveLocation() == 1) {

				} // end if
			} // end else
			break;

		case "3":
			int start = inputStartLine();
			int end = inputEndLine();

			if (start != -1 && end != -1) {
				showResult(start + " ~ " + end + " 라인 분석을 시작합니다.");
			} // end if
			break;

		default:
			showError("1~4번 사이의 번호를 입력해주세요.");
			break;

		}// end switch
	} // while

	// 보조 method

	private int selectSaveLocation() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			showResult("저장 경로가 설정되었습니다:\n" + path);
			return 1;
		} // end if
		return 0;
	} // end selectSaveLocation

	// 숫자 입력 창 (시작 라인)
	public int inputStartLine() {
		String val = JOptionPane.showInputDialog(null, "시작 라인을 입력하세요.");
		try {
			return (val == null || val.isEmpty()) ? -1 : Integer.parseInt(val);
		} catch (NumberFormatException e) {
			showError("숫자만 입력 가능합니다.");
			return -1;
		} // end catch
	} // end inputStartLine

	// 숫자 입력 창 (종료 라인)
	public int inputEndLine() {
		String val = JOptionPane.showInputDialog(null, "종료 라인을 입력하세요.");
		try {
			return (val == null || val.isEmpty()) ? -1 : Integer.parseInt(val);
		} catch (NumberFormatException e) {
			showError("숫자만 입력 가능합니다.");
			return -1;
		} // end catch
	} // end inputEndLine

	//  공통 알림 창
	public void showResult(String reportText) {
		JOptionPane.showMessageDialog(null, reportText, "Result", JOptionPane.INFORMATION_MESSAGE);
	} // end showResult

	//  공통 에러 창
	public void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	} // end showError

} // end class