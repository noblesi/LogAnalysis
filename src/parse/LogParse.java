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
 * 로그에서 필요한 데이터를 추출하여 LogDTO에 저장하고 List 에 추가한다.<br>
 */
public class LogParse {

	public static final int RESPONSE_RESULT = 1;
	public static final int URL = 2;
	public static final int BROWSER = 3;
	public static final int LOG_TIME = 4;

	private List<LogDTO> logList = new ArrayList<LogDTO>(); // LogDTO들이 저장되는 DTO
	private int lineNumber = 1; // 몇번째 라인인지 표기하기 위한 수

	public void execute() throws IOException {

		// 파일 경로로 수정 필요함
		String[] paths = { "D:/조창완/dev/academy/07.조별과제/sist_input_1.log",
				"D:/조창완/dev/academy/07.조별과제/sist_input_2.log" };

		for (String path : paths) {
			parseLog(path);
		}

	}

	/**
	 * log 를 가공해서 DTO에 담고 리스트에 저장하는 일
	 * 
	 * @param path log 파일 경로
	 * @throws IOException
	 */
	public void parseLog(String path) throws IOException {
		File logFile = new File(path);

		if (!logFile.exists()) {
			System.out.println("로그 파일 없음");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
			String log = "";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			StatusCode responseResult = null;
			String url = "";
			String key = "";
			String browser = "";
			LocalDateTime logTime = null;

			while ((log = br.readLine()) != null) {
				String[] logParts = log.split("[\\[\\]]+");

				// 배열 길이가 부족한 비정상 라인 체크
				if (logParts.length < 5) {
					lineNumber++;
					continue;
				}

				// 응답 코드 얻어오기
				responseResult = parseStatusCode(logParts[RESPONSE_RESULT]);

				// url 얻어오기
				url = parseUrl(logParts[URL]);

				// key 얻어오기
				key = parseKey(logParts[URL]);

				// 브라우져 얻어오기
				browser = logParts[BROWSER];

				// 요청 시간 얻어오기
				logTime = parseLogTime(logParts[LOG_TIME], formatter);

				// DTO 생성 및 리스트 추가
				LogDTO logInfo = new LogDTO(lineNumber++, responseResult, url, key, browser, logTime);
				logList.add(logInfo);
				System.out.println(logInfo);

			}
		}
	}

	/**
	 * 정수 문자열을 보고 응답 코드로 반환하는 일
	 * 
	 * @param code 200, 403, 404, 500 등의 코드 문자열
	 * @return StatusCode
	 */
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

	/**
	 * url 에서 endpoint (./find/ 뒤에 최종 목적지) 를 반환하는 일<br>
	 * fine/ 뒤에 더 이상 목적지가 없으면 공백을 반환한다.
	 * 
	 * @param requestPath 전체 url
	 * @return url 에서 추출한 endpoint
	 */
	private String parseUrl(String requestPath) {
		if (requestPath.contains("?")) {
			int start = requestPath.lastIndexOf("/") + 1;
			int end = requestPath.indexOf("?", start);
			return requestPath.substring(start, end);
		}
		return "";
	}

	/**
	 * url 에서 key 를 찾아 반환하는 일
	 * 
	 * @param requestPath 전체 url
	 * @return key
	 */
	private String parseKey(String requestPath) {
		if (requestPath.contains("key=")) {
			int start = requestPath.lastIndexOf("key=") + 4;
			int end = requestPath.indexOf("&", start);
			return (end != -1) ? requestPath.substring(start, end) : requestPath.substring(start);
		}
		return "";
	}

	/**
	 * log 의 요청 시간을 LocalDateTime 형으로 반환하는 일<br>
	 * 시간의 초 부분에 ora 가 있으면 00초로 바꿔서 저장한다.
	 * 
	 * @param rawTime   시간 정보가 담긴 문자열
	 * @param formatter 형식
	 * @return LocalDateTime 형의 요청 시간 정보
	 */
	private LocalDateTime parseLogTime(String rawTime, DateTimeFormatter formatter) {
		if (rawTime.contains("ora")) {
			rawTime = rawTime.replace("ora", "00");
		}

		if (rawTime.length() > 19) {
			rawTime = rawTime.substring(0, 19);
		}

		return LocalDateTime.parse(rawTime, formatter);
	}

	public List<LogDTO> getLogList() {
		return logList;
	}

	// 간단한 test 용
	public static void main(String[] args) {
		try {
			new LogParse().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}