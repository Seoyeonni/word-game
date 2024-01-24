import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private TextSource textSource = new TextSource(this); // 텍스트 소스 객체 생성
	private Player player = null; // 플레이어 레퍼런스 선언

	public GameFrame() {
		setTitle("Power NewJeans"); // 프레임 타이틀 설정
		setSize(1000, 700); // 프레임 크기 1000x700 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 윈도우를 닫으면 프로그램 종료
		setResizable(false); // 프레임 크기 변경 불가 설정

		MenuPanel menuPanel = new MenuPanel(textSource, player); // 메뉴패널 객체 생성
		add(menuPanel); // 첫 화면을 메뉴 페이지로 설정

		setLocationRelativeTo(null); // 화면 중앙에 프레임 생성
		setVisible(true); // 화면에 프레임 출력
	}
}