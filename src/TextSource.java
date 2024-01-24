// 단어 벡터 생성, 단어 파일 읽기, 랜덤한 단어 리턴

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

public class TextSource {
	private Vector<String> wordVector = new Vector<String>(30000); // 단어를 저장하는 벡터 생성
	
	public TextSource(Component parent) { // 파일에서 읽기
		try {
			Scanner scanner = new Scanner(new FileReader("file\\words.txt")); // Scanner 객체 생성
			while (scanner.hasNext()) { // 읽을 수 있는 데이터가 있는 동안
				String word = scanner.nextLine(); // words 파일에서 한 줄에 있는 단어 저장
				word = word.trim(); // 혹시 공백이 있으면 제거
				wordVector.add(word); // wordVector에 저장
			}
			scanner.close(); // Scanner를 닫음으로써 words 파일도 닫음
		} catch (FileNotFoundException e) { // 파일을 읽을 수 없으면
			System.out.println("파일을 읽을 수 없습니다.");
			System.exit(0); // 프로그램 종료
		}
	}
	
	// 랜덤 단어 리턴
	public String getWord() {
		int index = (int)(Math.random() * wordVector.size()); // 랜덤한 인덱스 구하기
		return wordVector.get(index); // 해당 인덱스의 단어 리턴
	}
	
	// 새로운 단어 추가 함수
	public void plusWord(Vector<String> newWordVector) {
		try {
			FileWriter fout = new FileWriter("file\\words.txt", true); // 파일과 연결된 출력 문자 스트림 생성, 이어쓰기 모드

			Iterator<String> it = newWordVector.iterator();
			while(it.hasNext()) { // newWordArray에 있는 모든 단어를
				String word = it.next(); // 탐색하여
				wordVector.add(word); // wordVector에 저장
				fout.write(word); // words.txt에 출력
				fout.write("\r\n"); // 한 줄 띄기
			}
			
			fout.close(); // 파일 닫기
		} catch (IOException e) { // 입출력 오류
			System.out.println("입출력 오류");
			System.exit(0); // 프로그램 종료
		}
	}
}