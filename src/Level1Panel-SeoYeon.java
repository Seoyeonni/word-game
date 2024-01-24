import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Level1Panel extends JPanel {
	// 매개변수로 받은 객체를 저장할 레퍼런스 선언
	private ScorePanel scorePanel = null; // 스코어 패널 레퍼런스 선언
	private TextSource textSource = null; // 텍스트 소스 레퍼런스 선언
	private Player player = null; // 플레이어 레퍼런스 선언

	private Boolean gameOn = false; // 게임 진행 상태 객체 생성
	private Boolean practice = false; // 연습인지 판단하는 객체 생성

	private GameGroundPanel gameGroundPanel = null; // GameGroundPanel 레퍼런스 선언
	private FallingThread fallingThreads[] = new FallingThread[6]; // FallingThread 객체 배열 생성

	private JTextField textInput = new JTextField(40); // 40글자까지 입력 가능한 입력창 객체 생성
	private JLabel fallingTexts[] = new JLabel[6]; // 떨어지는 단어 객체 생성

	private JButton startButton = new JButton("START"); // 시작 버튼

	// 이미지
	private ImageIcon noteIcon = new ImageIcon("file\\note.png");
	private Image noteImage = noteIcon.getImage();
	private Image fallingImg = null;
	private ImageIcon fallingIcon = null;

	// 배경 이미지
	private ImageIcon backgroundIcon = new ImageIcon("file\\level1.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	public Level1Panel(ScorePanel scorePanel, TextSource textSource, Player player, Boolean practice) {
		this.scorePanel = scorePanel; // 필드에 스코어 패널 객체 저장
		this.textSource = textSource; // 필드에 텍스트 소스 객체 저장
		this.player = player; // 필드에 플레이어 객체 저장
		this.practice = practice;

		// 이미지 설정
		fallingImg = noteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		fallingIcon = new ImageIcon(fallingImg);

		// 떨어지는 단어 설정
		for (int i = 0; i < fallingTexts.length; i++) {
			fallingTexts[i] = new JLabel("", fallingIcon, JLabel.CENTER);
			fallingTexts[i].setOpaque(false); // text의 배경 불투명 설정
			fallingTexts[i].setSize(100, 20); // text 사이즈 100x30 설정
			fallingTexts[i].setFont(new Font("Monospaced", Font.BOLD, 15));
			fallingTexts[i].setForeground(Color.WHITE);
			fallingTexts[i].setVisible(false);
		}

		setLayout(new BorderLayout()); // BorderLayout 배치관리자 설정
		gameGroundPanel = new GameGroundPanel(); // GameGroundPanel 객체 생성
		add(gameGroundPanel, BorderLayout.CENTER); // CENTER에 gameGroundPanel 객체 추가
		add(new InputPanel(), BorderLayout.SOUTH); // SOUTH에 InputPanel 객체 생성

		scorePanel.setPanel(1);

		// 단어를 입력했을 때 ActionListener
		textInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!gameOn)
					return; // 게임이 진행 중이지 않을 때는 입력 효과 없음
				JTextField eText = (JTextField) (e.getSource()); // 이벤트 소스 텍스트필트 저장
				String inputWord = eText.getText(); // 사용자가 입력한 단어 저장
				for (int i = 0; i < fallingTexts.length; i++) {
					if (fallingTexts[i].getText().equals(inputWord)) { // 단어를 맞췄다면
						scorePanel.scoreIncrease(10); // 점수 10점 증가
						eText.setText(""); // 점수 증가 후 입력창 비우기
						endWord(i); // 기존에 떨어지던 단어에 스레드 제거
						nextWord(i); // 새로운 단어에 스레드 생성
					} else if ("q".equalsIgnoreCase(inputWord)) { // "q" 키를 입력하면
						eText.setText(""); // 입력창 비우기
						pauseGame(); // 게임 일시정지
					} else if ("g".equalsIgnoreCase(inputWord)) {
						eText.setText(""); // 입력창 비우기
						reStartGame();
					} else {
						eText.setText(""); // 점수 증가 후 입력창 비우기
					}
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	// 스테이지 시작
	private void startStage() {
		if (gameOn)
			return; // 게임이 진행 중일 때는 버튼 효과 없음
		gameOn = true;
		startButton.setVisible(false);

		textInput.requestFocus(); // textInput에 입력 대기
		Timer timer = new Timer(); // 타이머 객체 생성
		TimerTask timerTast = new TimerTask() {
			int timeLimit = 43; // 제한 시간(초)

			@Override
			public void run() {
				// 1초마다
				scorePanel.setTime(timeLimit);
				timeLimit--;
				if (timeLimit == 0) {
					stopStage();
					timer.cancel(); // 타이머 취소
				}
			}
		};
		timer.schedule(timerTast, 0, 1000); // 1초마다
		for (int i = 0; i < fallingThreads.length; i++) {
			nextWord(i);
			try {
				Thread.sleep(500); // 0.5초 대기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 스테이지 종료
	private void stopStage() {
		gameOn = false;
		for (int i = 0; i < fallingThreads.length; i++) {
			endWord(i);
		}
		player.setScore(scorePanel.getScore()); // 스테이지 점수 저장
		player.store(1); // 스테이지 점수 출력
		player.setScore(0); // 스테이지 점수 0으로 초기화
		scorePanel.setTime(0); // 시간 0으로 초기화

		if (practice == false) { // 연습이 아니면
			if (scorePanel.getScore() >= 300) { // 300점 이상이면
				player.setTotalScore(scorePanel.getScore());

				String[] options = { "Yes", "No" };
				int choice = JOptionPane.showOptionDialog(null, "게임을 계속하시겠습니까?", "Next Level",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (choice == JOptionPane.YES_OPTION) { // 시작
					Level2Panel level2Panel = new Level2Panel(scorePanel, textSource, player, practice);
					addPanel(level2Panel, scorePanel);
				} else if (choice == JOptionPane.NO_OPTION) { // 메뉴로 돌아가기
					MenuPanel menuPanel = new MenuPanel(textSource, player);
					addPanel(menuPanel, null);
				}
			} else { // 300점을 못 넘겼으면 메뉴로 돌아가기
				MenuPanel menuPanel = new MenuPanel(textSource, player);
				addPanel(menuPanel, null);
			}
		} else {
			MenuPanel menuPanel = new MenuPanel(textSource, player);
			addPanel(menuPanel, null);
		}
		scorePanel.setScore(0); // 패널 점수 초기화
	}

	// 게임 일시정지 메서드
	private void pauseGame() {
		for (FallingThread thread : fallingThreads) {
			if (thread != null && thread.isAlive()) {
				thread.suspend(); // 떨어지는 단어 스레드 일시정지
			}
		}
	}

	// 게임 다시 시작
	private void reStartGame() {
		for (FallingThread thread : fallingThreads) {
			if (thread != null && thread.isAlive()) {
				thread.resume();
			}
		}
	}

	// 새로운 단어 선택 후 떨어뜨리는 함수
	private void nextWord(int idx) {
		chooseWord(idx); // 단어 선택
		int x = (int) (Math.random() * 700); // 랜덤한 x좌표 구하기
		fallingTexts[idx].setLocation(x, 10); // fallingText 위치 (x, 10)으로 설정

		fallingThreads[idx] = new FallingThread(idx); // FallingThread 객체 생성
		fallingThreads[idx].start(); // 멀티스레드 작동
	}

	private void endWord(int idx) {
		if (fallingThreads == null) {
			return; // 생성된 스레드 없음
		}
		fallingThreads[idx].interrupt(); // 스레드 강제 종료
		fallingThreads[idx] = null; // 스레드 삭제
	}

	// 단어 선택 함수
	private void chooseWord(int idx) {
		String newWord = textSource.getWord(); // 단어 선택
		fallingTexts[idx].setText(newWord); // 해당 단어로 텍스트 설정
		fallingTexts[idx].setVisible(true);
	}

	private class FallingThread extends Thread {
		private int delay = 100; // 지연 시간 0.1초
		private int idx;

		public FallingThread(int idx) {
			this.idx = idx;
		}

		@Override
		public void run() {
			while (true) { // 스레드 무한루프
				try {
					sleep(delay);
					int y = fallingTexts[idx].getY() + 2; // 2픽셀 씩 아래로 이동
					if (y >= gameGroundPanel.getHeight() - fallingTexts[idx].getHeight()) {
						scorePanel.scoreDecrease(15); // 점수 15점 감소
						endWord(idx); // 기존에 떨어지던 단어에 스레드 제거
						nextWord(idx); // 새로운 단어에 스레드 생성
						break;
					}

					fallingTexts[idx].setLocation(fallingTexts[idx].getX(), y);
					gameGroundPanel.repaint();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	private class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null); // 배치관리자 삭제
			setOpaque(false);

			// 시작 버튼을 눌렀을 때 ActionListener
			startButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					startStage();
				}
			});
			startButton.setSize(100, 30); // text 사이즈 100x30 설정
			startButton.setLocation(350, 300); // text 위치 (350, 300) 설정
			startButton.setFont(new Font("Monospaced", Font.BOLD, 15));
			startButton.setBackground(new Color(255, 255, 255));

			add(startButton);
			for (int i = 0; i < fallingTexts.length; i++) {
				add(fallingTexts[i]); // text 레이블 추가
			}
		}
	}

	private class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout()); // FlowLayout 배치관리자 설정(기본값)
			setOpaque(false);
			textInput.setPreferredSize(new Dimension(500, 30));
			add(textInput); // 입력창 추가
		}
	}

	private void addPanel(JPanel mainPanel, JPanel subPanel) {
		Container container = SwingUtilities.getAncestorOfClass(JFrame.class, this); // 현재 컴포넌트(this)의 JFrame 형태의 최상위 조상
																						// 컨테이너를 가져옴
		JFrame frame = null;

		if (container instanceof JFrame) { // 최상위 조상이 JFrame의 인스턴스인지 확인
			frame = (JFrame) container; // JFrame으로 캐스팅
			frame.getContentPane().removeAll(); // JFrame의 콘텐츠 팬에서 모든 컴포넌트를 제거

			if (subPanel != null) {
				JSplitPane hPane = new JSplitPane(); // 스플릿팬 객체 생성
				hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 좌우로 배치
				hPane.setDividerLocation(800); // 분할바 위치 800 설정
				hPane.setEnabled(false); // 분할바 이동 불가 설정
				hPane.setLeftComponent(mainPanel);
				hPane.setRightComponent(subPanel);
				frame.getContentPane().add(hPane); // 생성된 스플릿팬 부착
			} else {
				frame.getContentPane().add(mainPanel);
			}

			frame.revalidate(); // JFrame을 다시 유효하게 만들어 컴포넌트 계층 구조의 변경을 알림
			frame.repaint(); // JFrame을 다시 그려 외관을 업데이트
		}
	}
}
