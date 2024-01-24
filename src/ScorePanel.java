import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ScorePanel extends JPanel {

	private int score = 0; // 초기 점수 0점
	private JLabel textLabel = new JLabel("SCORE"); // textLabel 객체 생성
	private JLabel scoreLabel = new JLabel(Integer.toString(score)); // scoreLabel 객체 생성

	private String id = null;
	private JLabel idLabel;

	private ImageIcon backgroundIcon = new ImageIcon("file\\score.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	// Level2
	private int like = 20; // 좋아요 20개
	private int comment = 10; // 댓글 10개
	private JLabel likeLabel = new JLabel("LIKE");
	private JLabel likeCntLabel = new JLabel(Integer.toString(like));
	private JLabel commentLabel = new JLabel("COMMENT");
	private JLabel commentCntLabel = new JLabel(Integer.toString(comment));

	// 타이머
	private int time = 0;
	private JLabel timeLabel = new JLabel("TIME");
	private JLabel timeCntLabel = new JLabel(Integer.toString(time));

	private JButton goMenuButton = new JButton("MENU");

	public ScorePanel(String id, TextSource textSource, Player player) {
		this.id = id;

		setLayout(null); // 배치관리자 삭제

		// 아이디 관련
		idLabel = new JLabel(id);
		idLabel.setForeground(new Color(255, 255, 255));
		idLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		idLabel.setSize(100, 20);
		idLabel.setLocation(10, 10);
		add(idLabel);

		// 시간 관련
		timeLabel.setForeground(new Color(255, 255, 255));
		timeLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		timeLabel.setSize(80, 20);
		timeLabel.setLocation(10, 50);
		add(timeLabel);

		timeCntLabel.setForeground(new Color(255, 255, 255));
		timeCntLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		timeCntLabel.setSize(50, 20);
		timeCntLabel.setLocation(80, 50);
		add(timeCntLabel);

		// 점수 관련
		textLabel.setForeground(new Color(255, 255, 255));
		textLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		textLabel.setSize(80, 20);
		textLabel.setLocation(10, 70);
		add(textLabel); // 패널에 textLabel 추가

		scoreLabel.setForeground(new Color(255, 255, 255));
		scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		scoreLabel.setSize(50, 20);
		scoreLabel.setLocation(80, 70);
		add(scoreLabel);

		// 좋아요 관련
		likeLabel.setForeground(new Color(255, 255, 255));
		likeLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		likeLabel.setSize(80, 20);
		likeLabel.setLocation(10, 90);
		add(likeLabel);

		likeCntLabel.setForeground(new Color(255, 255, 255));
		likeCntLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		likeCntLabel.setSize(50, 20);
		likeCntLabel.setLocation(80, 90);
		add(likeCntLabel);

		// 댓글 관련
		commentLabel.setForeground(new Color(255, 255, 255));
		commentLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		commentLabel.setSize(80, 20);
		commentLabel.setLocation(10, 110);
		add(commentLabel);

		commentCntLabel.setForeground(new Color(255, 255, 255));
		commentCntLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		commentCntLabel.setSize(50, 20);
		commentCntLabel.setLocation(80, 110);
		add(commentCntLabel);

		// 메뉴로 화면 전환 관련
		goMenuButton.setBackground(new Color(255, 255, 255));
		goMenuButton.setBounds(38, 600, 100, 30);
		goMenuButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		add(goMenuButton);
		goMenuButton.addActionListener(new AddMenuPanelListener(this, textSource, player));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	// 시간 설정
	public void setTime(int time) {
		this.time = time;
		timeCntLabel.setText(Integer.toString(time));
	}

	// 점수 설정
	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText(Integer.toString(score)); // scoreLabel 갱신
	}

	// 점수 증가 함수
	public void scoreIncrease(int score) {
		this.score += score;
		scoreLabel.setText(Integer.toString(this.score)); // scoreLabel 갱신
	}

	// 점수 감소 함수
	public void scoreDecrease(int score) {
		this.score -= score;
		scoreLabel.setText(Integer.toString(this.score)); // scoreLabel 갱신
	}

	// 댓글 개수 증가
	public void commentIncrease() {
		comment += 1;
		commentCntLabel.setText(Integer.toString(comment));
	}

	// 좋아요 개수 감소
	public void likeDecrease() {
		if (like > 0) {
			like -= 1;
			likeCntLabel.setText(Integer.toString(like));
		}
	}

	// 댓글 개수 감소
	public void commentDecrease() {
		if (comment > 0) {
			comment -= 1;
			commentCntLabel.setText(Integer.toString(comment));
		}
	}

	// 점수 반환
	public int getScore() {
		return score;
	}

	// 좋아요 개수 반환
	public int getLike() {
		return like;
	}

	// 댓글 개수 반환
	public int getComment() {
		return comment;
	}

	// 레벨에 따른 패널 변경
	public void setPanel(int level) {
		if (level == 2) {
			likeLabel.setVisible(true);
			likeCntLabel.setVisible(true);
			commentLabel.setVisible(true);
			commentCntLabel.setVisible(true);
		} else if (level == 0) { // Typing
			timeLabel.setVisible(false);
			timeCntLabel.setVisible(false);
			likeLabel.setVisible(false);
			likeCntLabel.setVisible(false);
			commentLabel.setVisible(false);
			commentCntLabel.setVisible(false);
		} else {
			likeLabel.setVisible(false);
			likeCntLabel.setVisible(false);
			commentLabel.setVisible(false);
			commentCntLabel.setVisible(false);
		}
	}
}