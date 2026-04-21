package Analysis;

import java.io.IOException;

import OutputExport.AnalysisResult;
import OutputExport.AnalysisService;
import OutputExport.RangeResult;
import OutputExport.ReportService;

public class Run {
    
    public void start() throws IOException{
        // 추후에 JFileChooser로 교체예정, 일단 경로 하드코딩
    	String path = "C:/dev/조별과제/sist_input_1.log";
    	
    	AnalysisService analysisService = new AnalysisService(path);
    	AnalysisResult analysisResult = analysisService.getAnalysisResult();
    	
    	// 예시 범위
    	RangeResult rangeResult = analysisService.countKey(1, 100);
    	
    	ReportService reportService = new ReportService();
    	
    	// 화면 출력
    	System.out.println(reportService.makeScreenText(analysisResult, rangeResult));
    	
    	// 파일 저장
    	String savedPath = reportService.writeFile(analysisResult, rangeResult);
    	System.out.println("저장 위치 : " + savedPath);
    }
    
}
