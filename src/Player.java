import java.io.FileWriter;
import java.io.IOException;

public class Player {
	private String id = null; // 아이디 레퍼런스 선언
	private int score; // (스테이지) 점수 변수 생성
	private int totalScore = 0; // 최종 점수 변수 생성

	public Player(String id) {
		this.id = id; // 플레이어 아이디 저장
	}

	// (스테이지) 점수 설정
	public void setScore(int score) {
		this.score = score;
	}

	// 최종 점수 설정
	public void setTotalScore(int score) {
		this.totalScore += score;
	}

	// 아이디 리턴
	public String getId() {
		return id;
	}

	// 점수 저장 함수
	public void store(int level) {
		FileWriter fout = null;
		try {
			switch (level) {
			case 0: { // 최종 점수 파일에 출력
				fout = new FileWriter("file\\total rank.txt", true); // 파일과 연결된 출력 문자 스트림 생성, 이어쓰기 모드
				fout.write(id + ", " + Integer.toString(totalScore));
				break;
			}
			case 1: { // Level1 점수 파일에 출력
				fout = new FileWriter("file\\level1 rank.txt", true); // 파일과 연결된 출력 문자 스트림 생성, 이어쓰기 모드
				fout.write(id + ", " + Integer.toString(score));
				break;
			}
			case 2: { // Level1 점수 파일에 출력
				fout = new FileWriter("file\\level2 rank.txt", true); // 파일과 연결된 출력 문자 스트림 생성, 이어쓰기 모드
				fout.write(id + ", " + Integer.toString(score));
				break;
			}
			case 3: { // Level1 점수 파일에 출력
				fout = new FileWriter("file\\level3 rank.txt", true); // 파일과 연결된 출력 문자 스트림 생성, 이어쓰기 모드
				fout.write(id + ", " + Integer.toString(score));
				break;
			}
			}
			fout.write("\r\n"); // 한 줄 띄기
			fout.close(); // 파일 닫기
		} catch (IOException e) { // 입출력 오류
			System.out.println("입출력 오류");
			System.exit(0); // 프로그램 종료
		}
	}
}