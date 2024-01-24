import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

public class RankPanel extends JPanel {
	private HashMap<String, Integer> infoMap = new HashMap<String, Integer>(100); // 정보를 저장하는 해쉬맵 객체 생성
	private int level; // 레벨 변수 생성
	private JButton goMenuButton = new JButton("MENU"); // 메뉴 버튼 객체 생성

	// 배경 이미지
	private ImageIcon backgroundIcon = new ImageIcon("file\\rank.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	public RankPanel(int level, TextSource textSource, Player player) {
		this.level = level;
		Scanner scanner = null;

		try {
			switch (level) {
			case 0: { // 최종
				scanner = new Scanner(new FileReader("file\\total rank.txt")); // Scanner 객체 생성
				break;
			}
			case 1: { // level1
				scanner = new Scanner(new FileReader("file\\level1 rank.txt")); // Scanner 객체 생성
				break;
			}
			case 2: { // level2
				scanner = new Scanner(new FileReader("file\\level2 rank.txt")); // Scanner 객체 생성
				break;
			}
			case 3: { // level3
				scanner = new Scanner(new FileReader("file\\level3 rank.txt")); // Scanner 객체 생성
				break;
			}
			}
			while (scanner.hasNext()) { // 읽을 수 있는 데이터가 있는 동안
				String info = scanner.nextLine(); // rank 파일에서 한 줄에 있는 단어 저장

				// id와 score를 ", "로 나누어서 배열에 저장
				String[] tokens = info.split(", ");
				String id = tokens[0];
				int score = Integer.parseInt(tokens[1]);
				infoMap.put(id, score); // infoMap에 저장
			}
			scanner.close(); // Scanner를 닫음으로써 words 파일도 닫음
		} catch (FileNotFoundException e) { // 파일을 읽을 수 없으면
			System.out.println("파일을 읽을 수 없습니다.");
			System.exit(0); // 프로그램 종료
		}

		printTop10(); // Top10 출력
		goMenuButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		goMenuButton.setBackground(new Color(255, 255, 255));
		goMenuButton.setBounds(750, 600, 150, 30);
		add(goMenuButton);
		goMenuButton.addActionListener(new AddMenuPanelListener(this, textSource, player));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	// Top10 출력
	private void printTop10() {
		// entrySet()을 사용하여 HashMap을 Set으로 변환한 후, stream()을 사용하여 스트림을 생성
		// 그리고 sorted() 메서드를 사용하여 값에 따라 내림차순으로 정렬하고, limit(10) 메서드를 사용하여 상위 10개의 엔트리를 추출

		// HashMap에서 값이 큰 순서대로 정렬된 List 생성
		List<HashMap.Entry<String, Integer>> sortedList = infoMap.entrySet().stream()
				.sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toList());

		// 상위 10개의 데이터를 추출
		List<HashMap.Entry<String, Integer>> top10List = sortedList.stream().limit(10).collect(Collectors.toList());

		// infoLabel에 JLabel 객체 만들어서 저장
		JLabel infoLabel[] = new JLabel[10];
		int i = 0;
		for (HashMap.Entry<String, Integer> entry : top10List) {
			infoLabel[i] = new JLabel(entry.getKey() + " " + entry.getValue());
			i++;
		}
		
		setLayout(null); // 배치관리자 삭제

		// Top10 글자 레이블 설정
		JLabel top10Label = new JLabel("Top10"); // 레이블 생성
		top10Label.setFont(new Font("Monospaced", Font.BOLD, 30)); // 폰트 지정
		top10Label.setHorizontalAlignment(SwingConstants.CENTER); // 센터 지정
		top10Label.setBounds(450, 100, 100, 30); // 위치 지정
		add(top10Label); // 레이블 추가

		// Top10 출력
		for (int j = 0; j < top10List.size(); j++) {
			if (j < 5)
				infoLabel[j].setBounds(350, 150 + (j * 30), 150, 30);
			else {
				infoLabel[j].setBounds(500, 150 + ((j - 5) * 30), 150, 30);
			}
			infoLabel[j].setFont(new Font("Monospaced", 1, 15)); // 폰트 지정
			infoLabel[j].setHorizontalAlignment(JLabel.CENTER); // 센터 지정
			add(infoLabel[j]); // 레이블 추가
		}
	}
}