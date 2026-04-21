package Analysis;

import java.io.IOException;

import OutputExport.AnalysisService;
import OutputExport.ReportService;

public class mainApp {

	public static void main(String[] args) {
		System.out.println("LogAnalysis Start");
		try {
			new AnalysisService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
