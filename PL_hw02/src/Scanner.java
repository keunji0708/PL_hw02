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

	private void initTM() { // �迭�� ���� ����
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 128; j++) {
				if (i == 0) { // ���°� 0�� ���
					if (j == '-')
						transM[i][j] = 1;
					else if (j >= '0' && j <= '9') 
						transM[i][j] = 2;
					else if ((j >= 'a' && j <= 'z') || (j >= 'A' && j <= 'Z'))
						transM[i][j] = 3;
					else
						transM[i][j] = -1;
					
				} else if (i == 1) { // ���°� 1�� ���
					if (j >= '0' && j <= '9')
						transM[i][j] = 2;
					else
						transM[i][j] = -1;
				
				} else if (i == 2) { // ���°� 2�� ���
					if (j >= '0' && j <= '9')
						transM[i][j] = 2;
					else
						transM[i][j] = -1;
				
				} else if (i == 3) { // ���°� 3�� ���
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

		// ��ū�� �� �ִ��� �˻�
		if (!st.hasMoreTokens())
			return null;

		// �� ���� ��ū�� ����
		String temp = st.nextToken();
		Token result = null;

		for (int i = 0; i < temp.length(); i++) {
			// ���ڿ��� ���ڸ� �ϳ��� ������ ���� �Ǻ�
			stateNew = transM[stateOld][temp.charAt(i)];
			if (stateNew == -1) {
				// �Էµ� ������ ���°� reject �̹Ƿ� �����޼��� ����� return��
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

		while (st.hasMoreTokens()) { // ��ū�� �ִ��� Ȯ��
			tokens.add(nextToken()); // ����Ʈ�� ��ū�� �߰�����
		}
		return tokens; // ����Ʈ�� ����
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// RecursionLinkedList list = new RecursionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("C:\\Users\\keunj\\Desktop\\as02.txt");
			BufferedReader br = new BufferedReader(fr);
			String source = br.readLine(); // �ؽ�Ʈ ������ �о��

			StringTokenizer stringtoken = new StringTokenizer(source);
			Scanner s = new Scanner(source);

			s.st = stringtoken; // ���ο� �ִ� StringTokenizer�� Ŭ���� StringTokenizer�� ����
			List<Token> tokens = s.tokenize(); // = tokenize()�� ���� ���� tokens�� ����

			for (int i = 0; i < tokens.size(); i++) {
				System.out.println(tokens.get(i)); // ����Ʈ�� �ִ� ��ū ���
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("�й�: 201601982 / �̸�: ������");

	}

}
