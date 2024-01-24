import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AddMenuPanelListener implements ActionListener {
	private JPanel panel = null;
	private TextSource textSource = null;
	private Player player = null;

	public AddMenuPanelListener(JPanel panel, TextSource textSource, Player player) {
		this.panel = panel;
		this.textSource = textSource;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Container container = SwingUtilities.getAncestorOfClass(JFrame.class, panel); // 현재 컴포넌트(this)의 JFrame 형태의 최상위 조상 컨테이너를 가져옴
		JFrame frame = null;
		if (container instanceof JFrame) {
			frame = (JFrame) container; // JFrame으로 캐스팅
			frame.getContentPane().removeAll(); // JFrame의 콘텐츠 팬에서 모든 컴포넌트를 제거

			MenuPanel menuPanel = new MenuPanel(textSource, player); // 메뉴패널 객체 생성
			frame.getContentPane().add(menuPanel); // 메뉴패널로 화면 전환

			frame.revalidate(); // JFrame을 다시 유효하게 만들어 컴포넌트 계층 구조의 변경을 알림
			frame.repaint(); // JFrame을 다시 그려 외관을 업데이트
		}
	}
}