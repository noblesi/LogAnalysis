package Analysis;

import java.io.IOException;

import OutputExport.AnalysisService;

public class mainApp {

	public static void main(String[] args) {
		System.out.println("LogAnalysis Start");
		try {
			AnalysisService as = new AnalysisService();
			as.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
