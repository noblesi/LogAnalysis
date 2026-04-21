package Analysis;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LogMenuView {

	/**
	 * @param userId 대경님이 넘겨줄 로그인 아이디
	 */
	public void displayMenu(String userId) {

		while (true) { // 코드 무한 반복

			// 1. 팀 규칙에 따른 메뉴 구성
			String menu = "[SIST 로그 분석 시스템]\n" // 변수 선언 및 초기화
					+ "접속 계정:" + userId + "\n"
					+ "----------------------------\n" 
					+ "1. 모든 분석 결과 화면 출력\n"
					+ "2. 보고서 파일 생성(Report)\n" 
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

			//3. 메뉴별 기능 분리

			switch (input) {
			case "1": // 박정욱님 기능 호출 (화면출력 method 호출)
				JOptionPane.showMessageDialog(null, "분석 결과 화면을 준비합니다.");
				break;
				
			case "2":
				// [보안] root 계정 권한 제한 
				if ("root".equals(userId)) {
					JOptionPane.showMessageDialog(null, "관리자(root)는 리포트 생성 권한이 없습니다.", "권한 오류",
							JOptionPane.ERROR_MESSAGE);
				} else {				
					showSavePath();
				} // end else
				break;
				
			case "3":
				System.out.println(">>> 3번 선택 :범위 분석");
				processRangeInput(); // 범위를 입력받는 method로 보냄
				break;

			default:
				JOptionPane.showMessageDialog(null, "잘못된 입력입니다. 1~3.번을 눌러주세요.");
				break;

			}// end switch

		} // while

	}// displayMenu

	// 보조 method 1
	private void showSavePath() {
		System.out.println("--console] showSavePath()진입---");
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			JOptionPane.showMessageDialog(null, "저장 위치 완료:\n" + path);
			System.out.println("선택된 경로:" + path);

		} // end if

	}// showSavePath

	// 보조 메서드 2
	private void processRangeInput() {
		System.out.println("---[console] processRangeInput() 진입---");
		String range = JOptionPane.showInputDialog(null, "범위 분석", JOptionPane.QUESTION_MESSAGE);

		if (range != null && !range.isEmpty()) {
			JOptionPane.showMessageDialog(null, "[" + range + "]라인 분석을 시작합니다.");
			System.out.println("입력된 범위:" + range);

		} // end if
	}// processRangeInput

}// class
