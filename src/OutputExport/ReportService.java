package OutputExport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 출력 / 파일 내보내기 담당
 *
 * 권한 체크는 인증/메인 쪽에서 처리한다고 가정
 */
public class ReportService {
	/**
	 * 화면 출력용 문자열 생성 1~7번까지 출력
	 */
	public String makeScreenText(AnalysisResult result, RangeResult rangeResult) {
		return buildReportText(result, rangeResult, true);
	}// makeScreenText

	/**
	 * 파일 저장용 문제 조건상 1~6번까지만 파일에 저장
	 */
	public String writeFile(AnalysisResult result, RangeResult rangeResult) throws IOException {
		String dirPath = "c:/dev/report";
		File dir = new File(dirPath);

		if (!dir.exists()) {
			dir.mkdirs();
		} // end if

		String fileName = "report_" + System.currentTimeMillis() + ".dat";
		String fullPath = dirPath + File.separator + fileName;

		String content = buildReportText(result, rangeResult, false);

		// catch는 안함
		// filewriter close를 위해 try with resourse 사용 메인에서 예외처리 필요
		try (FileWriter fw = new FileWriter(fullPath)) {
			fw.write(content);
		} // end try

		return fullPath;
	}// writeFile

	private String buildReportText(AnalysisResult r, RangeResult range, boolean includeRange) {
		StringBuilder sb = new StringBuilder();

		sb.append("파일명(").append(nullSafe(r.getSourceFileName())).append(") log ( 생성된 날짜 ")
				.append(nullSafe(r.getLogCreatedDate())).append(" )\n");

		sb.append("1. 최다 사용키 : ").append(nullSafe(r.getTopKey())).append(" ").append(r.getTopKeyCount()).append("회\n");

		sb.append("2. 브라우저별 접속횟수, 비율\n");

		if (r.getBrowserCount() != null && !r.getBrowserCount().isEmpty()) {
			for (Map.Entry<String, Integer> entry : r.getBrowserCount().entrySet()) {
				String browser = entry.getKey();
				int count = entry.getValue();

				double rate = 0.0;
				if (r.getBrowserRate() != null && r.getBrowserRate().containsKey(browser)) {
					rate = r.getBrowserRate().get(browser);
				} // end if

				sb.append(browser).append(" - ").append(count).append(" (").append(String.format("%.1f", rate))
						.append("%)\n");
			} // end for
		} else {
			sb.append("데이터 없음\n");
		} // end else

		sb.append("3. 서비스를 성공적으로 수행한(200) 횟수, 실패(404) 횟수\n");
		sb.append("200 : ").append(r.getSuccess200Count()).append("회\n");
		sb.append("404 : ").append(r.getFail404Count()).append("회\n");

		sb.append("4. 요청이 가장 많은 시간 [").append(r.getPeakHour()).append("시]\n");

		sb.append("5. 비정상적인 요청(403)이 발생한 횟수, 비율\n");
		sb.append("횟수 : ").append(r.getForbidden403Count()).append("회\n");
		sb.append("비율 : ").append(String.format("%.1f", r.getForbidden403Rate())).append("%\n");

		sb.append("6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율\n");
		sb.append("횟수 : ").append(r.getBooks500Count()).append("회\n");
		sb.append("비율 : ").append(String.format("%.1f", r.getBooks500Rate())).append("%\n");

		if (includeRange && range != null) {
			sb.append("7. ").append(range.getStartLine()).append("~").append(range.getEndLine()).append("번째 정보\n");
			sb.append("최다사용 키의 이름과 횟수 | ").append(nullSafe(range.getTopKey())).append(" ")
					.append(range.getTopKeyCount()).append("회\n");
		} // end if

		return sb.toString();
	}// buildReportText

	private String nullSafe(String str) {
		return str == null ? "" : str;
	}// nullSafe
}// class