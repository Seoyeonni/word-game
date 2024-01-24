import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class HelpPanel extends JPanel {
	private JLabel help = null;
	private JLabel level1 = null;
	private JLabel level2 = null;
	private JLabel level3 = null;
	
	private JButton goMenuButton = new JButton("MENU");

	// 배경 이미지
	private ImageIcon backgroundIcon = new ImageIcon("file\\help.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	public HelpPanel(TextSource textSource, Player player) {
		setLayout(null);
		help = new JLabel("<html><body>[Power NewJeans]<br>" + "어느 날, 대한민국에서 데뷔하여 많은 사랑을 받고 있는 뉴진스가<br>"
				+ "파워퍼프걸 세계로 떨어지게 된다. 이후 대한민국으로<br>" + "돌아가기 위해서는 파워퍼프걸 세계에서 최고의 아이돌이 되어야<br>"
				+ "한다는 것을 알게 된다. 뉴진스는 \"파워 뉴진스\"라는 이름으로<br>" + "최고의 뮤지션이 되기 위한 여정을 시작한다...<br>"
				+ "- 모든 레벨에서 START 버튼을 누르면 3초 뒤에 게임이 시작된다.<br>"
				+ "- Typing 모드에서는 시간과 관계 없이 연습할 수 있다.<br>"
				+ "(q: 일시정지, g: 재시작)</body></html>");
		help.setForeground(new Color(255, 255, 255));
		help.setBackground(new Color(255, 255, 255));
		help.setFont(new Font("Monospaced", Font.PLAIN, 15));
		help.setHorizontalAlignment(SwingConstants.RIGHT);
		help.setBounds(-10, 50, 500, 200);
		add(help);
		
		level1 = new JLabel("<html><body>[LEVEL1]<br>" + "파워퍼프걸 세계가 좋아하는 음악을 하기 위하여 파워 뉴진스는<br>"
				+ "노래 연습을 시작한다. 음정, 박자(음표)를 완벽하게 맞춰야만<br>" + "뮤직비디오를 찍고 데뷔할 수 있으니 최선을 다 하길 바란다!<br>" + "- 제한시간 1분<br>"
				+ "- 음표를 맞추면 +10점, 떨어뜨리면 -15점</body></html>");
		level1.setHorizontalAlignment(SwingConstants.LEFT);
		level1.setForeground(new Color(255, 255, 255));
		level1.setFont(new Font("Monospaced", Font.PLAIN, 15));
		level1.setBounds(490, 50, 500, 200);
		add(level1);
		
		level2 = new JLabel("<html><body>[LEVEL2]<br>" + "연습을 완벽히 끝낸 파워 뉴진스는 뮤직비디오를 업로드 했다.<br>"
				+ "유명해지기 위해서는 홍보를 열심히 해야만 한다.<br>" + "좋아요와 댓글을 각각 20개와 10개 이상을 얻어야만 콘서트를<br>"
				+ "진행할 수 있다. (알고리즘의 선택을 받는 행운이 있을 수도 있다!)<br>" + "- 제한시간 1분<br>"
				+ "- 좋아요(RED)를 맞추면 +10점, 떨어뜨리면 -10점<br>" + "- 댓글(BLUE)을 맞추면 +20점, 떨어뜨리면 -20점<br>"
				+ "- 알고리즘(YELLOW)을 맞추면 점수 2배, 떨어뜨리면 -20점</body></html>");
		level2.setForeground(new Color(255, 255, 255));
		level2.setFont(new Font("Monospaced", Font.PLAIN, 15));
		level2.setHorizontalAlignment(SwingConstants.RIGHT);
		level2.setBounds(-10, 350, 500, 200);
		add(level2);

		level3 = new JLabel("<html><body>[LEVEL3]<br>" + "뮤직비디오의 홍보를 완벽히 해낸 파워 뉴진스는 드디어<br>"
				+ "콘서트를 진행하게 됐다. 이번 콘서트에서 완벽한 공연을 해내면<br>" + "최고의 아이돌이 되어 대한민국으로 돌아갈 수 있다. 단, <br>"
				+ "파워 뉴진스의 팬들은 항상 호응을 해주지만, 호기심에 온<br>" + "일반 관객들은 호응을 할 수도, 야유를 할 수도 있으니 주의하자!<br>" + "- 제한시간 1분<br>"
				+ "- 팬(BLUE)을 맞추면 +100점, 떨어뜨리면 -100점<br>"
				+ "- 일반관객(RED)을 맞추면 +200점 혹은 -150점, 떨어뜨리면 -100점</body></html>");
		level3.setHorizontalAlignment(SwingConstants.LEFT);
		level3.setForeground(new Color(255, 255, 255));
		level3.setFont(new Font("Monospaced", Font.PLAIN, 15));
		level3.setBounds(490, 350, 500, 200);
		add(level3);
		
		goMenuButton.setBackground(new Color(255, 255, 255));
		goMenuButton.setBounds(450, 600, 100, 30);
		goMenuButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		add(goMenuButton);
		goMenuButton.addActionListener(new AddMenuPanelListener(this, textSource, player));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
