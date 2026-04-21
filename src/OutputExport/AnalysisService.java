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
		countPeakHour();
		countforBidden403();
		countBooks500();
		
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
	
	//4. 요청이 가장 많은 시간 
	public void countPeakHour() {
		Map<Integer, Integer> hourMap = new HashMap<Integer, Integer>();
		
		for(LogDTO dto : logList ) {
			if(dto.getTime() != null) {
				int hour = dto.getTime().getHour();
				
				hourMap.put(hour, hourMap.getOrDefault(hour,0)+1);
			}//end if 
		}//end for
		
		int peakHour = -1;
		int maxCnt = 0;
		
		for (Integer hour : hourMap.keySet()) {
	        if (hourMap.get(hour) > maxCnt) {
	            peakHour = hour;
	            maxCnt = hourMap.get(hour);
	        }//end if 
	    }//end for

	    // AnalysisResult에 저장
	    analysisResult.setPeakHour(peakHour);
	}//countPeakHour
	//5. 비정상적인 요청이 발생한 횟수 비율 
	public void countforBidden403() {
		int forbiddenCnt = 0;
		for(LogDTO dto : logList ) {
			if (dto.getResponseResult() == StatusCode.FORBIDDEN) {
				forbiddenCnt++;
			}//end if 
		}//end for 
		double forbiddenRate = 0.0;
		if(!logList.isEmpty()) {
			forbiddenRate = ((double)forbiddenCnt/logList.size())*100;
		}//end if 
		analysisResult.setForbidden403Count(forbiddenCnt);
		analysisResult.setForbidden403Rate(forbiddenRate);
	}//forbidden403Count
	//6. Books에 대한 요청중 url중 에러가 발생한 횟수,비율 구하기
	public void countBooks500() {
		int booksRequestCnt = 0;
		int books500Cnt = 0;
		
		for(LogDTO dto : logList) {
			if("books".equals(dto.getUrl())) {
				booksRequestCnt++;
				if(dto.getResponseResult() == StatusCode.SERVER_ERROR) {
					books500Cnt++;
				}//end if
			}//end if
		}//end for 
		
		double books500Rate = 0.0;
		if(booksRequestCnt > 0) {
			books500Rate =((double)books500Cnt/logList.size())*100;
		}//end if
		
		analysisResult.setBooks500Count(books500Cnt);
		analysisResult.setBooks500Rate(books500Rate);
	}//books500Count
	//7.입력되는 라인에 해당하는 정보출력
	public RangeResult analysisRange(int StartLine, int endLine) {
//		RangeResult range = new RangeResult();
//		
//		range.setStartLine(StartLine);
//		range.setEndLine(endLine);
//		
//		Map<String, Integer> keyCountMap = new HashMap<String, Integer>();
//		
//		for(LogDTO dto : logList) {
//			int line = dto.getLineNumber();
//		}//end for
		return null;
	}//rangeResult
	
	public AnalysisResult getAnalysisResult() {
		return analysisResult;
	}

}
