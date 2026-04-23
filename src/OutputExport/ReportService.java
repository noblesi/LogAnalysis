package OutputExport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ReportService {

	public String makeScreenText(AnalysisResult result, RangeResult rangeResult) {
		return buildScreenText(result, rangeResult);
	}

	public String writeFile(AnalysisResult result, RangeResult rangeResult) throws IOException {
		String dirPath = "c:/dev/report";
		File dir = new File(dirPath);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileName = "report_" + System.currentTimeMillis() + ".dat";
		String fullPath = dirPath + File.separator + fileName;
		String content = buildReportText(result, rangeResult, false);

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fullPath), StandardCharsets.UTF_8))) {
			writer.write(content);
		}

		return fullPath;
	}

	private String buildScreenText(AnalysisResult result, RangeResult range) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ 로그 분석 결과 ]\n");
		sb.append(repeat("-", 44)).append("\n");
		sb.append("파일명      : ").append(extractBaseFileName(result.getSourceFileName())).append("\n");
		sb.append("생성 일시   : ").append(nullSafe(result.getLogCreatedDate())).append("\n");
		sb.append(repeat("-", 44)).append("\n\n");

		sb.append("1. 최다 사용 키\n");
		sb.append("   - 키 이름 : ").append(nullSafe(result.getTopKey())).append("\n");
		sb.append("   - 사용 수 : ").append(result.getTopKeyCount()).append("회\n\n");

		sb.append("2. 브라우저별 접속 통계\n");
		if (result.getBrowserCount() != null && !result.getBrowserCount().isEmpty()) {
			for (Map.Entry<String, Integer> entry : result.getBrowserCount().entrySet()) {
				String browser = entry.getKey();
				int count = entry.getValue();
				double rate = 0.0;

				if (result.getBrowserRate() != null && result.getBrowserRate().containsKey(browser)) {
					rate = result.getBrowserRate().get(browser);
				}

				sb.append(String.format("   - %-12s : %5d회 (%5.1f%%)%n", browser, count, rate));
			}
		} else {
			sb.append("   - 데이터 없음\n");
		}
		sb.append("\n");

		sb.append("3. 응답 코드 통계\n");
		sb.append("   - 200 성공 : ").append(result.getSuccess200Count()).append("회\n");
		sb.append("   - 404 실패 : ").append(result.getFail404Count()).append("회\n\n");

		sb.append("4. 요청이 가장 많은 시간\n");
		sb.append("   - 피크 시간 : ").append(result.getPeakHour()).append("시\n\n");

		sb.append("5. 비정상 요청(403)\n");
		sb.append("   - 발생 횟수 : ").append(result.getForbidden403Count()).append("회\n");
		sb.append("   - 발생 비율 : ").append(String.format("%.1f", result.getForbidden403Rate())).append("%\n\n");

		sb.append("6. books URL의 500 오류\n");
		sb.append("   - 발생 횟수 : ").append(result.getBooks500Count()).append("회\n");
		sb.append("   - 발생 비율 : ").append(String.format("%.1f", result.getBooks500Rate())).append("%\n");

		if (range != null) {
			sb.append("\n");
			sb.append("7. 특정 라인 범위 분석\n");
			sb.append("   - 범위      : ").append(range.getStartLine()).append(" ~ ").append(range.getEndLine()).append("번째 라인\n");
			sb.append("   - 최다 키   : ").append(nullSafe(range.getTopKey())).append("\n");
			sb.append("   - 사용 수   : ").append(range.getTopKeyCount()).append("회\n");
		}

		return sb.toString();
	}

	private String buildReportText(AnalysisResult result, RangeResult range, boolean includeRange) {
		StringBuilder sb = new StringBuilder();

		sb.append("파일명(")
				.append(extractBaseFileName(result.getSourceFileName()))
				.append(") log ( 생성된 날짜 ")
				.append(nullSafe(result.getLogCreatedDate()))
				.append(" )\n");

		sb.append("1. 최다 사용키 : ")
				.append(nullSafe(result.getTopKey()))
				.append(" ")
				.append(result.getTopKeyCount())
				.append("회\n");

		sb.append("2. 브라우저별 접속횟수, 비율\n");
		if (result.getBrowserCount() != null && !result.getBrowserCount().isEmpty()) {
			for (Map.Entry<String, Integer> entry : result.getBrowserCount().entrySet()) {
				String browser = entry.getKey();
				int count = entry.getValue();
				double rate = 0.0;

				if (result.getBrowserRate() != null && result.getBrowserRate().containsKey(browser)) {
					rate = result.getBrowserRate().get(browser);
				}

				sb.append(browser)
						.append(" - ")
						.append(count)
						.append(" (")
						.append(String.format("%.1f", rate))
						.append("%)\n");
			}
		} else {
			sb.append("데이터 없음\n");
		}

		sb.append("3. 서비스를 성공적으로 수행한(200) 횟수, 실패(404) 횟수\n");
		sb.append("200 : ").append(result.getSuccess200Count()).append("회\n");
		sb.append("404 : ").append(result.getFail404Count()).append("회\n");

		sb.append("4. 요청이 가장 많은 시간 [").append(result.getPeakHour()).append("시]\n");

		sb.append("5. 비정상적인 요청(403)이 발생한 횟수, 비율\n");
		sb.append("횟수 : ").append(result.getForbidden403Count()).append("회\n");
		sb.append("비율 : ").append(String.format("%.1f", result.getForbidden403Rate())).append("%\n");

		sb.append("6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율\n");
		sb.append("횟수 : ").append(result.getBooks500Count()).append("회\n");
		sb.append("비율 : ").append(String.format("%.1f", result.getBooks500Rate())).append("%\n");

		if (includeRange && range != null) {
			sb.append("7. ").append(range.getStartLine()).append("~").append(range.getEndLine()).append("번째 정보\n");
			sb.append("최다 사용 키 : ").append(nullSafe(range.getTopKey())).append(" ")
					.append(range.getTopKeyCount()).append("회\n");
		}

		return sb.toString();
	}

	private String extractBaseFileName(String fileName) {
		String safeFileName = nullSafe(fileName);
		int extensionIndex = safeFileName.lastIndexOf('.');
		if (extensionIndex == -1) {
			return safeFileName;
		}
		return safeFileName.substring(0, extensionIndex);
	}

	private String repeat(String value, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(value);
		}
		return sb.toString();
	}

	private String nullSafe(String str) {
		return str == null ? "" : str;
	}
}
