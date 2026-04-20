package parse;

import java.time.LocalDateTime;

public class LogDTO {

	private int lineNumber;
	private StatusCode responseResult; // 응답 결과
	private String url; // url 요청 텍스트
	private String key; // key
	private String browser; // 사용 브라우져
	private LocalDateTime time; // 요청 시간

	public LogDTO(int lineNumber, StatusCode responseResult, String url, String key, String browser,
			LocalDateTime time) {
		super();
		this.lineNumber = lineNumber;
		this.responseResult = responseResult;
		this.url = url;
		this.key = key;
		this.browser = browser;
		this.time = time;
	}

	public StatusCode getResponseResult() {
		return responseResult;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setResponseResult(StatusCode responseResult) {
		this.responseResult = responseResult;
	}

	public String geturl() {
		return url;
	}

	public void seturl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "LogDTO [lineNumber=" + lineNumber + ", responseResult=" + responseResult + ", url=" + url + ", key="
				+ key + ", browser=" + browser + ", time=" + time + "]";
	}

}
