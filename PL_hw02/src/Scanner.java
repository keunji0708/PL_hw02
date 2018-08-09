import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Scanner {
	public enum TokenType {
		ID(3), INT(2);
		private final int finalState;

		TokenType(int finalState) {
			this.finalState = finalState;
		}
	}

	public static class Token {
		public final TokenType type;
		public final String lexme;

		Token(TokenType type, String lexme) {
			this.type = type;
			this.lexme = lexme;
		}

		@Override
		public String toString() {
			return String.format("[%s: %s]", type.toString(), lexme);
		}
	}

	private int transM[][];
	private String source;
	private StringTokenizer st;

	public Scanner(String source) {
		this.transM = new int[4][128];
		this.source = source == null ? "" : source;
		initTM();
	}

	private void initTM() { // 배열에 값을 저장
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 128; j++) {
				if (i == 0) { // 상태가 0인 경우
					if (j == '-')
						transM[i][j] = 1;
					else if (j >= '0' && j <= '9') 
						transM[i][j] = 2;
					else if ((j >= 'a' && j <= 'z') || (j >= 'A' && j <= 'Z'))
						transM[i][j] = 3;
					else
						transM[i][j] = -1;
					
				} else if (i == 1) { // 상태가 1인 경우
					if (j >= '0' && j <= '9')
						transM[i][j] = 2;
					else
						transM[i][j] = -1;
				
				} else if (i == 2) { // 상태가 2인 경우
					if (j >= '0' && j <= '9')
						transM[i][j] = 2;
					else
						transM[i][j] = -1;
				
				} else if (i == 3) { // 상태가 3인 경우
					if (j >= '0' && j <= '9' || (j >= 'a' && j <= 'z') || (j >= 'A' && j <= 'Z'))
						transM[i][j] = 3;
					else
						transM[i][j] = -1;
					
				} else
					transM[i][j] = -1;
			}
		}

	}

	private Token nextToken() {
		int stateOld = 0, stateNew;

		// 토큰이 더 있는지 검사
		if (!st.hasMoreTokens())
			return null;

		// 그 다음 토큰을 받음
		String temp = st.nextToken();
		Token result = null;

		for (int i = 0; i < temp.length(); i++) {
			// 문자열의 문자를 하나씩 가져와 상태 판별
			stateNew = transM[stateOld][temp.charAt(i)];
			if (stateNew == -1) {
				// 입력된 문자의 상태가 reject 이므로 에러메세지 출력후 return함
				System.out.println(String.format("acceptStateerror %s\n", temp));
				return null;
			}
			stateOld = stateNew;
		}

		for (TokenType t : TokenType.values()) {
			if (t.finalState == stateOld) {
				result = new Token(t, temp);
				break;
			}
		}
		return result;
	}

	public List<Token> tokenize() {
		List<Token> tokens = new ArrayList<Token>();

		while (st.hasMoreTokens()) { // 토큰이 있는지 확인
			tokens.add(nextToken()); // 리스트에 토큰을 추가해줌
		}
		return tokens; // 리스트를 리턴
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// RecursionLinkedList list = new RecursionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("C:\\Users\\keunj\\Desktop\\as02.txt");
			BufferedReader br = new BufferedReader(fr);
			String source = br.readLine(); // 텍스트 파일을 읽어옴

			StringTokenizer stringtoken = new StringTokenizer(source);
			Scanner s = new Scanner(source);

			s.st = stringtoken; // 메인에 있는 StringTokenizer을 클래스 StringTokenizer와 연결
			List<Token> tokens = s.tokenize(); // = tokenize()의 리턴 값을 tokens에 저장

			for (int i = 0; i < tokens.size(); i++) {
				System.out.println(tokens.get(i)); // 리스트에 있는 토큰 출력
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("학번: 201601982 / 이름: 김은지");

	}

}
