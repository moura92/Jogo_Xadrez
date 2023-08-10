package aplicação;

import xadrez.PeçaXadrez;

public class UI {

	public static void imprimirtabuleiro(PeçaXadrez[][] peças) {
		
		for (int i=0; i<peças.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<peças.length; j++) {
				imprimirpeça(peças[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
		
	private static void imprimirpeça(PeçaXadrez peça) {
		if (peça == null) {
			System.out.print("-");
		}else {
			System.out.println(peça);
		}
		System.out.print(" ");
	}
}
