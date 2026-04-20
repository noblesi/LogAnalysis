package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 로그에서 필요한 데이터를 추출하여 List 에 담는다.<br>
 * 손상된 데이터는 담지 않는다.
 */
public class LogParse {

	public static final int RESPONSE_RESULT = 1;
	public static final int URL = 2;
	public static final int BROWSER = 3;
	public static final int LOG_TIME = 4;

	private List<LogDTO> logList;
	private int lineNumber = 1;

	public LogParse() {
		logList = new ArrayList<LogDTO>();
	}

	public void excute() throws IOException {

		// 파일 경로로 수정 필요함
		String[] paths = { "D:/조창완/dev/academy/07.조별과제/sist_input_1.log",
				"D:/조창완/dev/academy/07.조별과제/sist_input_2.log" };

		for (String path : paths) {
			parseLog(path);
		}

	}

	public void parseLog(String path) throws IOException {
		File logFile = new File(path);

		if (!logFile.exists()) {
			System.out.println("로그 파일 없음");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
			String log = "";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			while ((log = br.readLine()) != null) {
				String[] logParts = log.split("[\\[\\]]+");

				// 배열 길이가 부족한 비정상 라인 체크
				if (logParts.length < 5) {
					lineNumber++;
					continue;
				}

				// 응답 코드 얻어오기
				StatusCode responseResult = parseStatusCode(logParts[RESPONSE_RESULT]);

				// url 얻어오기
				String url = parseUrl(logParts[URL]);

				// key 얻어오기
				String key = parseKey(logParts[URL]);

				// 브라우져 얻어오기
				String browser = logParts[BROWSER];

				// 요청 시간 얻어오기
				LocalDateTime logTime = null;
				try {
					String rawTime = logParts[LOG_TIME].substring(0, 19);
					logTime = LocalDateTime.parse(rawTime, formatter);

					// 성공 시 DTO 생성 및 리스트 추가
					LogDTO logInfo = new LogDTO(lineNumber++, responseResult, url, key, browser, logTime);
					logList.add(logInfo);
					System.out.println(logInfo);

				} catch (Exception e) {
					// 실패시 리스트에 담지 않고 넘어간다.
					System.err.println("[라인 " + lineNumber + " 에러] 시간 형식 오류: " + logParts[LOG_TIME]);
					lineNumber++;
				}
			}
		}
	}

	private StatusCode parseStatusCode(String code) {
		switch (code) {
		case "200":
			return StatusCode.OK;
		case "403":
			return StatusCode.FORBIDDEN;
		case "404":
			return StatusCode.NOT_FOUND;
		default:
			return StatusCode.ISE;
		}
	}

	private String parseUrl(String requestPath) {
		if (requestPath.contains("?")) {
			int start = requestPath.lastIndexOf("/") + 1;
			int end = requestPath.indexOf("?", start);
			return requestPath.substring(start, end);
		}
		return "";
	}

	private String parseKey(String requestPath) {
		if (requestPath.contains("key=")) {
			int start = requestPath.lastIndexOf("key=") + 4;
			int end = requestPath.indexOf("&", start);
			return (end != -1) ? requestPath.substring(start, end) : requestPath.substring(start);
		}
		return "";
	}

	public List<LogDTO> getLogList() {
		return logList;
	}

	public static void main(String[] args) {
		try {
			new LogParse().excute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}