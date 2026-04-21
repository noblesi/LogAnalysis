package OutputExport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parse.LogDTO;
import parse.LogParse;
import parse.StatusCode;

public class AnalysisService {

	AnalysisResult analysisResult = new AnalysisResult();
	List<LogDTO> logList = null;

	public AnalysisService() throws IOException {
		logList = new LogParse().getLogList();

		countKey();
		countBrowser();
		countStatusCode();
	}

	// 1. 최다 사용 키를 구하고 DTO 에 저장
	public void countKey() {
		Map<String, Integer> countMap = new HashMap<>();
		String key = "";

		for (LogDTO dto : logList) {
			// 값이 없는 경우 세지 않는 일 필요
			key = dto.getKey();
			if (!"".equals(key)) {
				countMap.put(key, (countMap.getOrDefault(key, 0) + 1));
			}
		}

		String topKey = "";
		int topKeyCount = 0;

		for (String temp : countMap.keySet()) {
			if (topKeyCount < countMap.get(temp)) {
				topKey = temp;
				topKeyCount = countMap.get(temp);
			}
		}

		analysisResult.setTopKey(topKey);
		analysisResult.setTopKeyCount(topKeyCount);
	}

	// 2. 브라우져 별 접속 횟수, 비율 구하고 DTO 에 저장
	public void countBrowser() {
		Map<String, Integer> countMap = new HashMap<>();
		Map<String, Double> perMap = new HashMap<>();
		String browser = "";

		for (LogDTO dto : logList) {
			browser = dto.getBrowser();
			countMap.put(browser, (countMap.getOrDefault(browser, 0) + 1));
		}

		for (String key : countMap.keySet()) {
			perMap.put(key, ((double) countMap.get(key) / logList.size()));
		}

		analysisResult.setBrowserCount(countMap);
		analysisResult.setBrowserRate(perMap);
	}

	// 3. 200 / 404 횟수 구하고 DTO 에 저장
	public void countStatusCode() {

		int success200Count = 0;
		int fail404Count = 0;
		StatusCode code;

		for (int i = 0; i < logList.size(); i++) {
			code = logList.get(i).getResponseResult();

			if (code == StatusCode.OK) {
				success200Count++;
			}

			if (code == StatusCode.NOT_FOUND) {
				fail404Count++;
			}
		}

		analysisResult.setSuccess200Count(success200Count);
		analysisResult.setFail404Count(fail404Count);
	}

	public AnalysisResult getAnalysisResult() {
		return analysisResult;
	}

}
