package parse;

public enum StatusCode {
	OK(200, "성공"), FORBIDDEN(403, "권한 없음"), NOT_FOUND(404, "페이지 없음"), ISE(500, "서버 내부 오류");

	private final int code;
	private final String resultMsg;

	StatusCode(int code, String resultMsg) {
		this.code = code;
		this.resultMsg = resultMsg;
	}

	public int getCode() {
		return code;
	}

	public String getResultMsg() {
		return resultMsg;
	}

}
