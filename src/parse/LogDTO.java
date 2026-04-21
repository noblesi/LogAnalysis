package parse;

import java.time.LocalDateTime;

public class LogDTO {

	private int lineNumber; // 몇번째 라인인가.
	private StatusCode responseResult; // 응답 결과
	private String url; // url
	private String key; // key
	private String browser; // 사용 브라우져
	private LocalDateTime time; // 요청 시간

	public LogDTO(int lineNumber, StatusCode responseResult, String url, String key, String browser,
			LocalDateTime time) {
		this.lineNumber = lineNumber;
		this.responseResult = responseResult;
		this.url = url;
		this.key = key;
		this.browser = browser;
		this.time = time;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public StatusCode getResponseResult() {
		return responseResult;
	}

	public String getUrl() {
		return url;
	}

	public String getKey() {
		return key;
	}

	public String getBrowser() {
		return browser;
	}

	public LocalDateTime getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "LogDTO [lineNumber=" + lineNumber + ", responseResult=" + responseResult + ", url=" + url + ", key="
				+ key + ", browser=" + browser + ", time=" + time + "]";
	}

}
