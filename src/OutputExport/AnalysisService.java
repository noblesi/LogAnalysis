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

	private final AnalysisResult analysisResult;
	private final List<LogDTO> logList;

	public AnalysisService(String path) throws IOException {
		analysisResult = new AnalysisResult();
		logList = new LogParse(path).getLogList();

		setFileInfo(path);
		countKey();
		countBrowser();
		countStatusCode();
		countPeakHour();
		countForBidden403();
		countBooks500();
	}

	private void setFileInfo(String path) throws IOException {
		File file = new File(path);
		analysisResult.setSourceFileName(file.getName());

		BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		String createdDate = attrs.creationTime()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		analysisResult.setLogCreatedDate(createdDate);
	}

	public void countKey() {
		Map<String, Integer> countMap = new HashMap<String, Integer>();

		for (LogDTO dto : logList) {
			String key = dto.getKey();
			if (key != null && !"".equals(key)) {
				countMap.put(key, countMap.getOrDefault(key, 0) + 1);
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

	public void countBrowser() {
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		Map<String, Double> perMap = new HashMap<String, Double>();

		for (LogDTO dto : logList) {
			String browser = dto.getBrowser();
			countMap.put(browser, countMap.getOrDefault(browser, 0) + 1);
		}

		for (String key : countMap.keySet()) {
			perMap.put(key, ((double) countMap.get(key) / logList.size()) * 100);
		}

		analysisResult.setBrowserCount(countMap);
		analysisResult.setBrowserRate(perMap);
	}

	public void countStatusCode() {
		int success200Count = 0;
		int fail404Count = 0;

		for (LogDTO dto : logList) {
			StatusCode code = dto.getResponseResult();

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

	public void countPeakHour() {
		Map<Integer, Integer> hourMap = new HashMap<Integer, Integer>();

		for (LogDTO dto : logList) {
			if (dto.getTime() != null) {
				int hour = dto.getTime().getHour();
				hourMap.put(hour, hourMap.getOrDefault(hour, 0) + 1);
			}
		}

		int peakHour = -1;
		int maxCnt = 0;

		for (Integer hour : hourMap.keySet()) {
			if (hourMap.get(hour) > maxCnt) {
				peakHour = hour;
				maxCnt = hourMap.get(hour);
			}
		}

		analysisResult.setPeakHour(peakHour);
	}

	public void countForBidden403() {
		int forbiddenCnt = 0;

		for (LogDTO dto : logList) {
			if (dto.getResponseResult() == StatusCode.FORBIDDEN) {
				forbiddenCnt++;
			}
		}

		double forbiddenRate = 0.0;
		if (!logList.isEmpty()) {
			forbiddenRate = ((double) forbiddenCnt / logList.size()) * 100;
		}

		analysisResult.setForbidden403Count(forbiddenCnt);
		analysisResult.setForbidden403Rate(forbiddenRate);
	}

	public void countBooks500() {
		int booksRequestCnt = 0;
		int books500Cnt = 0;

		for (LogDTO dto : logList) {
			if ("books".equals(dto.getUrl())) {
				booksRequestCnt++;
				if (dto.getResponseResult() == StatusCode.SERVER_ERROR) {
					books500Cnt++;
				}
			}
		}

		double books500Rate = 0.0;
		if (booksRequestCnt > 0) {
			books500Rate = ((double) books500Cnt / booksRequestCnt) * 100;
		}

		analysisResult.setBooks500Count(books500Cnt);
		analysisResult.setBooks500Rate(books500Rate);
	}

	public RangeResult analysisRange(int startLine, int endLine) {
		RangeResult range = new RangeResult();
		range.setStartLine(startLine);
		range.setEndLine(endLine);

		Map<String, Integer> countMap = new HashMap<String, Integer>();

		for (LogDTO dto : logList) {
			int line = dto.getLineNumber();
			if (line >= startLine && line <= endLine) {
				String key = dto.getKey();
				if (key != null && !"".equals(key)) {
					countMap.put(key, countMap.getOrDefault(key, 0) + 1);
				}
			}
		}

		String topKey = "";
		int topCount = 0;

		for (String key : countMap.keySet()) {
			if (countMap.get(key) > topCount) {
				topKey = key;
				topCount = countMap.get(key);
			}
		}

		range.setTopKey(topKey);
		range.setTopKeyCount(topCount);
		return range;
	}

	public RangeResult countKey(int start, int end) {
		if (start < 1) {
			start = 1;
		}

		if (end > logList.size()) {
			end = logList.size();
		}

		return analysisRange(start, end);
	}

	public AnalysisResult getAnalysisResult() {
		return analysisResult;
	}
}
