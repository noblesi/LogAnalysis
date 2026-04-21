package OutputExport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parse.LogDTO;
import parse.LogParse;
import parse.StatusCode;

public class AnalysisService {

	private AnalysisResult analysisResult;
	private List<LogDTO> logList;

	public AnalysisService(String path) throws IOException {
		analysisResult = new AnalysisResult();
		logList = new LogParse(path).getLogList();
		
		setFileInfo(path);
		countKey();
		countBrowser();
		countStatusCode();
		//countPeakHour();
		//countForbidden403();
		//countBooks500();
	}
	
	private void setFileInfo(String path) throws IOException {
		File file = new File(path);
		analysisResult.setSourceFileName(file.getName());
		
		BasicFileAttributes attrs = Files.readAttributes(
				file.toPath(), 
				BasicFileAttributes.class); 
		String createdDate = attrs.creationTime()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss"));
				
		analysisResult.setLogCreatedDate(createdDate);
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
				perMap.put(key, ((double) countMap.get(key) / logList.size() * 100));
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

	// 7. 시작 줄과 끝 줄을 받아 최다 사용키를 구하고 범위 결과에 저장하고 반환
	public RangeResult countKey(int start, int end) {
		Map<String, Integer> countMap = new HashMap<>();
		String key = "";

		// start 와 end 가 list 의 범위안에 없으면 각각 최솟값과 최댓값으로 설정
		if (start < 0) {
			start = 0;
			System.out.println("시작 범위가 음수이므로 0부터 찾습니다.");
		}

		if (end > logList.size()) {
			end = logList.size();
			System.out.println("끝 범위가 데이터의 개수를 초과했습니다. 최댓값까지 찾습니다.");
		}

		for (LogDTO dto : logList) {
			// 값이 없는 경우 세지 않는 일 필요
			if (dto.getLineNumber() >= start && dto.getLineNumber() <= end) {
				key = dto.getKey();
				if (!"".equals(key)) {
					countMap.put(key, (countMap.getOrDefault(key, 0) + 1));
				}
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

		return new RangeResult(start, end, topKey, topKeyCount);
	}

	

	

	public AnalysisResult getAnalysisResult() {
		return analysisResult;
	}

}
