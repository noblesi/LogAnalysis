package Analysis;

import java.io.IOException;

public class MainApp {

	public static void main(String[] args) {
		System.out.println("LogAnalysis Start");

		Run run = new Run();
		try {
			run.start();
		} catch (IOException e) {
			System.err.println("로그 분석 중 오류가 발생했습니다.");
			e.printStackTrace();
		}
	}
}
