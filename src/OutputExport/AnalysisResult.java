package OutputExport;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 전체 분석 결과를 저장하는 DTO
 */
public class AnalysisResult {

    private String sourceFileName;
    private String logCreatedDate;

    // 1. 최다 사용 키
    private String topKey;
    private int topKeyCount;

    // 2. 브라우저별 접속 횟수 / 비율
    private Map<String, Integer> browserCount = new LinkedHashMap<String, Integer>();
    private Map<String, Double> browserRate = new LinkedHashMap<String, Double>();

    // 3. 200 / 404 횟수
    private int success200Count;
    private int fail404Count;

    // 4. 요청이 가장 많은 시간
    private int peakHour;

    // 5. 403 횟수 / 비율
    private int forbidden403Count;
    private double forbidden403Rate;

    // 6. books + 500 횟수 / 비율
    private int books500Count;
    private double books500Rate;

    public AnalysisResult() {
    }//AnalysisResult

    public String getSourceFileName() {
        return sourceFileName;
    }//getSourceFileName

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }//setSourceFileName

    public String getLogCreatedDate() {
        return logCreatedDate;
    }//getLogCreatedDate

    public void setLogCreatedDate(String logCreatedDate) {
        this.logCreatedDate = logCreatedDate;
    }//setLogCreatedDate

    public String getTopKey() {
        return topKey;
    }//getTopKey

    public void setTopKey(String topKey) {
        this.topKey = topKey;
    }//setTopKey

    public int getTopKeyCount() {
        return topKeyCount;
    }//getTopKeyCount

    public void setTopKeyCount(int topKeyCount) {
        this.topKeyCount = topKeyCount;
    }//setTopKeyCount

    public Map<String, Integer> getBrowserCount() {
        return browserCount;
    }//getBrowserCount

    public void setBrowserCount(Map<String, Integer> browserCount) {
        this.browserCount = browserCount;
    }//setBrowserCount

    public Map<String, Double> getBrowserRate() {
        return browserRate;
    }//getBrowserRate

    public void setBrowserRate(Map<String, Double> browserRate) {
        this.browserRate = browserRate;
    }//setBrowserRate

    public int getSuccess200Count() {
        return success200Count;
    }//getSuccess200Count

    public void setSuccess200Count(int success200Count) {
        this.success200Count = success200Count;
    }//setSuccess200Count

    public int getFail404Count() {
        return fail404Count;
    }//getFail404Count

    public void setFail404Count(int fail404Count) {
        this.fail404Count = fail404Count;
    }//setFail404Count

    public int getPeakHour() {
        return peakHour;
    }//getPeakHour

    public void setPeakHour(int peakHour) {
        this.peakHour = peakHour;
    }//setPeakHour

    public int getForbidden403Count() {
        return forbidden403Count;
    }//getForbidden403Count

    public void setForbidden403Count(int forbidden403Count) {
        this.forbidden403Count = forbidden403Count;
    }//setForbidden403Count

    public double getForbidden403Rate() {
        return forbidden403Rate;
    }//getForbidden403Rate

    public void setForbidden403Rate(double forbidden403Rate) {
        this.forbidden403Rate = forbidden403Rate;
    }//setForbidden403Rate

    public int getBooks500Count() {
        return books500Count;
    }//getBooks500Count

    public void setBooks500Count(int books500Count) {
        this.books500Count = books500Count;
    }//setBooks500Count

    public double getBooks500Rate() {
        return books500Rate;
    }//getBooks500Rate

    public void setBooks500Rate(double books500Rate) {
        this.books500Rate = books500Rate;
    }//setBooks500Rate
}//class