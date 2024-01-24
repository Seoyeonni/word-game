import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;

public class EditWordPanel extends JPanel {
	private JTextField textInput = new JTextField(20); // 20글자 입력 가능한 edit 텍스트필드 객체 생성
	private JButton addButton = new JButton("ADD"); // "add" 버튼 객체 생성
	private JButton deleteButton = new JButton("DELETE"); // "delete" 버튼 객체 생성
	private JButton saveButton = new JButton("SAVE"); // "save" 버튼 객체 생성
	
	private Vector<String> newWordVector = new Vector<String>(30); // 새로운 단어를 저장하는 벡터 생성
	private JLabel wordsLabel[] = new JLabel[newWordVector.capacity()]; // 새로운 단어를 출력할 레이블 배열 생성
	
	private JButton goMenuButton = new JButton("MENU"); // 메뉴패널로 돌아갈 버튼 객체 생성

	// 배경 이미지
	private ImageIcon backgroundIcon = new ImageIcon("file\\edit.jpg"); // 배경 이미지아이콘 객체 생성
	private Image backgroundImage = backgroundIcon.getImage(); // 배경 이미지 객체 생성

	public EditWordPanel(TextSource textSource, Player player) {
		setLayout(null); // 배치관리자 삭제
		
		textInput.setBounds(400, 50, 200, 30); // textInput텍스트필드 위치 지정
		add(textInput); // textInput텍스트필드 추가
		
		addButton.setBackground(new Color(255, 255, 255)); // add버튼 배경색 지정
		addButton.setBounds(270, 100, 100, 30); // add버튼 위치 지정
		addButton.setFont(new Font("Monospaced", Font.BOLD, 15)); // add버튼 폰트 지정
		add(addButton); // add버튼 추가

		deleteButton.setBackground(new Color(255, 255, 255)); // delete버튼 배경색 지정
		deleteButton.setBounds(390, 100, 100, 30); // delete버튼 위치 지정
		deleteButton.setFont(new Font("Monospaced", Font.BOLD, 15)); // delete버튼 폰트 지정
		add(deleteButton); // delete버튼 추가
		
		saveButton.setBackground(new Color(255, 255, 255)); // save버튼 배경색 지정
		saveButton.setBounds(510, 100, 100, 30); // save버튼 위치 지정
		saveButton.setFont(new Font("Monospaced", Font.BOLD, 15)); // save버튼 폰트 지정
		add(saveButton); // save버튼 추가
		
		goMenuButton.setBackground(new Color(255, 255, 255)); // Menu버튼 배경색 지정
		goMenuButton.setBounds(630, 100, 100, 30); // Menu버튼 위치 지정
		goMenuButton.setFont(new Font("Monospaced", Font.BOLD, 15)); // Menu버튼 폰트 지정
		add(goMenuButton); // Menu버튼 추가
		
		textInput.addActionListener(new ActionListener() { // textInput 필드에 엔터키를 입력했을 때

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField eText = (JTextField) (e.getSource()); // 이벤트 소스 텍스트필트 저장
				String inputWord = eText.getText(); // 사용자가 입력한 단어를
				newWordVector.add(inputWord); // 새로운 단어를 저장하는 벡터에 저장
				eText.setText(""); // 저장 후 입력창 비우기
				printLabels(); // 모든 단어레이블 프린트
			}
		});

		addButton.addActionListener(new ActionListener() { // addButton을 눌렀을 때

			@Override
			public void actionPerformed(ActionEvent e) {
				String inputWord = textInput.getText(); // 사용자가 입력한 단어를
				newWordVector.add(inputWord); // 새로운 단어를 저장하는 벡터에 저장
				textInput.setText(""); // 저장 후 입력창 비우기
				printLabels(); // 모든 단어레이블 프린트
			}
		});
		
		deleteButton.addActionListener(new ActionListener() { // deleteButton을 눌렀을 때

			@Override
			public void actionPerformed(ActionEvent e) {
				String inputWord = textInput.getText(); // 사용자가 입력한 단어 저장

				Iterator<String> it = newWordVector.iterator();
				while (it.hasNext()) { // newWordArray에 있는 모든 단어 중에서
					if (inputWord.equals(it.next())) { // inputWord과 같은 단어가 있다면
						it.remove(); // 해당 단어 삭제
						textInput.setText(""); // 삭제 후 입력창 비우기
						break;
					}
				}
				printLabels(); // 모든 단어레이블 프린트
			}
		});

		saveButton.addActionListener(new ActionListener() { // saveButton을 눌렀을 때

			@Override
			public void actionPerformed(ActionEvent e) {
				textSource.plusWord(newWordVector); // 새로운 단어를 파일에 저장
				textInput.setText(""); // 입력창 비우기
				removeLabels(); // 모든 단어레이블 제거
			}
		});

		goMenuButton.addActionListener(new AddMenuPanelListener(this, textSource, player)); // 메뉴패널로 화면 전환
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	// 모든 단어레이블 프린트
	private void printLabels() {
		removeLabels(); // 모든 단어레이블 제거

		Iterator<String> it = newWordVector.iterator();
		int idx = 0; // 인덱스 변수
		int x = 0; // x좌표 조절 변수
		while (it.hasNext()) {
			wordsLabel[idx] = new JLabel(it.next()); // 단어 탐색
			wordsLabel[idx].setFont(new Font("Monospaced", Font.BOLD, 15)); // 폰트 지정
			wordsLabel[idx].setBounds(270 + x, 150 + (idx * 30), 100, 30); // 위치 지정
			wordsLabel[idx].setForeground(Color.WHITE); // 색깔 지정
			wordsLabel[idx].setHorizontalAlignment(JLabel.CENTER); // 센터에 맞춤
			add(wordsLabel[idx]); // 레이블 추가
			// 위치 조절에 필요한 변수값 조정
			idx++;
			if (idx % 10 == 0) {
				x += 110;
				idx = 0;
			}
		}
		repaint(); // 패널을 다시 그려서 변경 사항을 반영
	}
	
	// 모든 단어레이블 제거
	private void removeLabels() {
	    if (wordsLabel != null) {
	        for (JLabel label : wordsLabel) {
	        	if (label != null) {
	                remove(label); // 레이블 제거
	            }
	        }
	        repaint(); // 패널을 다시 그려서 변경 사항을 반영
	    }
	}
}