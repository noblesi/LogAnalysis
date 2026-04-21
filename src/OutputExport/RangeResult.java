package OutputExport;

/**
 * 특정 라인 범위 분석 결과를 저장하는 DTO
 */
public class RangeResult {

    private int startLine;
    private int endLine;
    private String topKey;
    private int topKeyCount;

    public RangeResult() {
    }//RangeResult

    public RangeResult(int startLine, int endLine, String topKey, int topKeyCount) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.topKey = topKey;
        this.topKeyCount = topKeyCount;
    }//RangeResult

    public int getStartLine() {
        return startLine;
    }//getStartLine

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }//setStartLine

    public int getEndLine() {
        return endLine;
    }//getEndLine

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }//setEndLine

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
}//class