package Analysis;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Login.User;
import OutputExport.AnalysisResult;
import OutputExport.AnalysisService;
import OutputExport.RangeResult;
import OutputExport.ReportService;

public class LogMenuView {

	private AnalysisService analysisService;
	private final ReportService reportService;
	private User currentUser;
	private String currentLogPath;

	public LogMenuView() {
		reportService = new ReportService();
	}

	public void displayMenu(User user) {
		currentUser = user;

		while (true) {
			String menu = "[SIST 로그 분석 시스템]\n"
					+ "접속 계정 : " + currentUser.getId() + " (" + currentUser.getRole() + ")\n"
					+ "현재 로그 파일 : " + getCurrentLogName() + "\n"
					+ "----------------------------\n"
					+ "1. 모든 분석 결과 화면 출력\n"
					+ "2. 보고서 파일 생성(Report)\n"
					+ "3. 특정 라인 범위 분석\n"
					+ "4. 로그 파일 선택/교체\n"
					+ "5. 프로그램 종료\n"
					+ "----------------------------\n"
					+ "원하는 번호를 선택하세요.";

			String input = JOptionPane.showInputDialog(null, menu, "메뉴", JOptionPane.QUESTION_MESSAGE);

			if (input == null || "5".equals(input)) {
				JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
				break;
			}

			processCommand(input);
		}
	}

	private void processCommand(String input) {
		switch (input) {
		case "1":
			showAllAnalysis();
			break;
		case "2":
			createReportFile();
			break;
		case "3":
			showRangeAnalysis();
			break;
		case "4":
			changeLogFile();
			break;
		default:
			showError("1~5 사이의 번호를 입력하세요.");
			break;
		}
	}

	private void showAllAnalysis() {
		if (!requireSelectedLogFile()) {
			return;
		}

		AnalysisResult analysisResult = analysisService.getAnalysisResult();
		String reportText = reportService.makeScreenText(analysisResult, null);
		showTextDialog("분석 결과", reportText);
	}

	private void createReportFile() {
		if (!currentUser.canCreateReport()) {
			showError("문서를 생성할 수 있는 권한이 없음");
			return;
		}

		if (!requireSelectedLogFile()) {
			return;
		}

		try {
			String savedPath = reportService.writeFile(analysisService.getAnalysisResult(), null);
			showResult("리포트 파일이 생성되었습니다.\n" + savedPath);
		} catch (IOException e) {
			showError("리포트 파일 생성 중 오류가 발생했습니다.");
		}
	}

	private void showRangeAnalysis() {
		if (!requireSelectedLogFile()) {
			return;
		}

		int start = inputStartLine();
		if (start == -1) {
			return;
		}

		int end = inputEndLine();
		if (end == -1) {
			return;
		}

		if (start > end) {
			showError("시작 라인은 종료 라인보다 클 수 없습니다.");
			return;
		}

		RangeResult rangeResult = analysisService.countKey(start, end);
		AnalysisResult analysisResult = analysisService.getAnalysisResult();
		String reportText = reportService.makeScreenText(analysisResult, rangeResult);
		showTextDialog("범위 분석 결과", reportText);
	}

	private void changeLogFile() {
		String selectedPath = selectLogFilePath();
		if (selectedPath == null) {
			showError("로그 파일 선택이 취소되었습니다.");
			return;
		}

		try {
			analysisService = new AnalysisService(selectedPath);
			currentLogPath = selectedPath;
			showResult("로그 파일이 설정되었습니다.\n" + currentLogPath);
		} catch (IOException e) {
			showError("로그 파일을 불러오는 중 오류가 발생했습니다.");
		}
	}

	public int inputStartLine() {
		return parseLineNumber("시작 라인을 입력하세요.");
	}

	public int inputEndLine() {
		return parseLineNumber("종료 라인을 입력하세요.");
	}

	private int parseLineNumber(String message) {
		String val = JOptionPane.showInputDialog(null, message);

		if (val == null) {
			return -1;
		}

		try {
			int lineNumber = Integer.parseInt(val.trim());
			if (lineNumber < 1) {
				showError("1 이상의 숫자를 입력하세요.");
				return -1;
			}
			return lineNumber;
		} catch (NumberFormatException e) {
			showError("숫자만 입력 가능합니다.");
			return -1;
		}
	}

	public void showResult(String reportText) {
		JOptionPane.showMessageDialog(null, reportText, "Result", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showTextDialog(String title, String text) {
		JTextArea textArea = new JTextArea(text, 18, 45);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		textArea.setCaretPosition(0);
		textArea.setMargin(new java.awt.Insets(10, 12, 10, 12));

		JScrollPane scrollPane = new JScrollPane(textArea);
		JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean requireSelectedLogFile() {
		if (analysisService != null && currentLogPath != null && !currentLogPath.isEmpty()) {
			return true;
		}

		showError("먼저 로그 파일을 선택하세요.");
		return false;
	}

	private String selectLogFilePath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("분석할 로그 파일을 선택하세요.");

		if (currentLogPath != null) {
			chooser.setSelectedFile(new File(currentLogPath));
		}

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}

		return null;
	}

	private String getCurrentLogName() {
		if (currentLogPath == null || currentLogPath.isEmpty()) {
			return "미선택";
		}
		return new File(currentLogPath).getName();
	}

	private String safe(String value) {
		return value == null ? "" : value;
	}
}
