import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Color;

public class MenuPanel extends JPanel {

	private TextSource textSource = null; // 텍스트 소스 레퍼런스 선언
	private Player player = null; // 플레이어 레퍼런스 선언

	// 게임 관련 객체 생성
	private Box idBox = Box.createHorizontalBox(); // 아이디 관련 컴포넌트들을 담기 위한 Box 객체 생성
	private JLabel idLabel = new JLabel("ID ");
	private JTextField inputId = new JTextField(30);
	private JButton gameStartBtn = new JButton("START");

	// 연습 관련 객체 생성
	private Box levelBox = Box.createHorizontalBox(); // 레벨 관련 컴포넌트들을 담기 위한 Box 객체 생성
	private JLabel levelLabel1 = new JLabel("LEVEL ");
	private String[] level1 = { "Lv.1", "Lv.2", "Lv.3", "Typing" };
	private JComboBox<String> levelCombo1 = new JComboBox<String>(level1); // 레벨 고르는 콤보박스 객체 생성
	private JButton practiceBtn = new JButton("PRACTICE");

	// 순위 보기 관련 객체 생성
	private Box showRankBox = Box.createHorizontalBox(); // 레벨 관련 컴포넌트들을 담기 위한 Box 객체 생성
	private JLabel levelLabel2 = new JLabel("LEVEL ");
	private String[] level2 = { "Total", "Lv.1", "Lv.2", "Lv.3" };
	private JComboBox<String> levelCombo2 = new JComboBox<String>(level2); // 레벨 고르는 콤보박스 객체 생성
	private JButton showRankBtn = new JButton("RANK");

	private JButton editWordBtn = new JButton("EDIT"); // 단어 편집 버튼 객체 생성
	private JButton helpBtn = new JButton("HELP"); // 도움말 버튼 객체 생성
	private JButton exitBtn = new JButton("EXIT"); // 종료 버튼 객체 생성

	// 배경 이미지
	private ImageIcon backgroundIcon = new ImageIcon("file\\menu.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	public MenuPanel(TextSource textSource_, Player player_) {
		this.textSource = textSource_;
		this.player = player_;

		setLayout(null); // 배치관리자 삭제

		// 게임 관련 컴포넌트 설정
		idLabel.setForeground(new Color(255, 255, 255));
		idLabel.setFont(new Font("Monospaced", 1, 15));
		idBox.add(idLabel);
		idBox.add(inputId);
		idBox.setBounds(50, 430, 150, 30);

		gameStartBtn.setBackground(new Color(255, 255, 255));
		gameStartBtn.setBounds(50, 480, 150, 30);
		gameStartBtn.setFont(new Font("Monospaced", Font.BOLD, 15));

		// 연습 관련 컴포넌트 설정
		levelLabel1.setForeground(new Color(255, 255, 255));
		levelLabel1.setFont(new Font("Monospaced", Font.BOLD, 15));
		levelCombo1.setFont(new Font("Monospaced", Font.BOLD, 15));
		levelBox.add(levelLabel1);
		levelBox.add(levelCombo1);
		levelBox.setBounds(50, 530, 150, 30);

		practiceBtn.setBackground(new Color(255, 255, 255));
		practiceBtn.setBounds(50, 580, 150, 30);
		practiceBtn.setFont(new Font("Monospaced", Font.BOLD, 15));

		// 순위 보기 관련 컴포넌트 설정
		levelLabel2.setForeground(new Color(255, 255, 255));
		levelLabel2.setFont(new Font("Monospaced", Font.BOLD, 15));
		levelCombo2.setFont(new Font("Monospaced", Font.BOLD, 15));
		showRankBox.add(levelLabel2);
		showRankBox.add(levelCombo2);
		showRankBox.setBounds(800, 430, 150, 30);

		showRankBtn.setBackground(new Color(255, 255, 255));
		showRankBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		showRankBtn.setBounds(800, 480, 150, 30);

		// 편집 관련 컴포넌트 설정
		editWordBtn.setBackground(new Color(255, 255, 255));
		editWordBtn.setBounds(800, 530, 70, 30);
		editWordBtn.setFont(new Font("Monospaced", Font.BOLD, 15));

		// 도움말 관련 컴포넌트 설정
		helpBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		helpBtn.setBackground(new Color(255, 255, 255));
		helpBtn.setBounds(880, 530, 70, 30);

		// 종료 관련 컴포넌트 설정
		exitBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		exitBtn.setBackground(new Color(255, 255, 255));
		exitBtn.setBounds(800, 580, 150, 30);

		// 컴포넌트 부착
		add(idBox);
		add(gameStartBtn);
		add(levelBox);
		add(practiceBtn);
		add(editWordBtn);
		add(showRankBox);
		add(showRankBtn);
		add(helpBtn);
		add(exitBtn);

		// 게임 시작 버튼 눌렀을 때 ActionListener
		gameStartBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Player 객체 설정
				if (inputId.getText().equals("")) { // id를 입력하지 않았으면
					JOptionPane.showMessageDialog(null, "ID를 입력하세요.", "Message", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				player = new Player(inputId.getText());

				ScorePanel scorePanel = new ScorePanel(player.getId(), textSource, player);
				Boolean practice = false;
				Level1Panel level1Panel = new Level1Panel(scorePanel, textSource, player, practice);
				addPanel(level1Panel, scorePanel);
			}
		});

		// 연습 시작 버튼 눌렀을 때 ActionListener
		practiceBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Player 객체 설정
				if (inputId.getText().equals("")) { // id를 입력하지 않았으면
					JOptionPane.showMessageDialog(null, "ID를 입력하세요.", "Message", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				player = new Player(inputId.getText());

				int level = levelCombo1.getSelectedIndex() + 1; // 선택한 레벨 저장

				switch (level) {
				case 1: {
					ScorePanel scorePanel = new ScorePanel(player.getId(), textSource, player);
					Boolean practice = true;
					Level1Panel level1Panel = new Level1Panel(scorePanel, textSource, player, practice);
					addPanel(level1Panel, scorePanel);
					break;
				}
				case 2: {
					ScorePanel scorePanel = new ScorePanel(player.getId(), textSource, player);
					Boolean practice = true;
					Level2Panel level2Panel = new Level2Panel(scorePanel, textSource, player, practice);
					addPanel(level2Panel, scorePanel);
					break;
				}
				case 3: {
					ScorePanel scorePanel = new ScorePanel(player.getId(), textSource, player);
					Boolean practice = true;
					Level3Panel level3Panel = new Level3Panel(scorePanel, textSource, player, practice);
					addPanel(level3Panel, scorePanel);
					break;
				}
				case 4: {
					ScorePanel scorePanel = new ScorePanel(player.getId(), textSource, player);
					Boolean practice = true;
					TypingPanel typingPanel = new TypingPanel(scorePanel, textSource, player, practice);
					addPanel(typingPanel, scorePanel);
					break;
				}
				}
			}
		});

		// 순위 보기 버튼 눌렀을 때 ActionListener
		showRankBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int level = levelCombo2.getSelectedIndex(); // 선택한 레벨 저장

				RankPanel rankPanel = new RankPanel(level, textSource, player);
				addPanel(rankPanel, null);
			}
		});

		// 단어 편집 버튼 눌렀을 때 ActionListener
		editWordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditWordPanel editWordPanel = new EditWordPanel(textSource, player);
				addPanel(editWordPanel, null);
			}
		});

		// 도움말 버튼 눌렀을 때 ActionListener
		helpBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpPanel helpPanel = new HelpPanel(textSource, player);
				addPanel(helpPanel, null);
			}
		});

		// 종료 버튼 눌렀을 때 ActionListener
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // 정상적인 종료
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
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