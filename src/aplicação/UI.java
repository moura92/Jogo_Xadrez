package aplicação;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	// Cores do Texto:
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// Cores do Fundo:
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limparTela() { // metodo para limpar o tabuleiro anterior.
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosiçãoXadrez lerPosiçãoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosiçãoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException(
					"Erro ao instanciar a posição de xadrez. Os valores válidos são de 'a1' a 'h8'");
		}
	}

	public static void imprimirPartida(PartidaXadrez partidaXadrez, List<PeçaXadrez> captura) {
		imprimirtabuleiro(partidaXadrez.getPeças());
		System.out.println();
		imprimirPeçaCapturada(captura);
		System.out.println();
		System.out.println("Vez: " + partidaXadrez.getVez());

		if (!partidaXadrez.getCheckMate()) {
			System.out.println("Aguardando jogador: " + partidaXadrez.getJogadorAtual());

			if (partidaXadrez.getCheck()) {
				System.out.println("CHECK!");
			}
		}else {
			System.out.println("CHECK MATE !");
			System.out.println("Vencedor: " + partidaXadrez.getJogadorAtual());
		}
	}

	public static void imprimirtabuleiro(PeçaXadrez[][] peças) {

		for (int i = 0; i < peças.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				imprimirPeça(peças[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	public static void imprimirtabuleiro(PeçaXadrez[][] peças, boolean[][] movimentoPossivel) {

		for (int i = 0; i < peças.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				imprimirPeça(peças[i][j], movimentoPossivel[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	private static void imprimirPeça(PeçaXadrez peça, boolean fundo) {
		if (fundo) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peça == null) {
			System.out.print("-" + ANSI_RESET);
		} else {

			if (peça.getCor() == Cor.branco) {
				System.out.print(ANSI_WHITE + peça + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peça + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void imprimirPeçaCapturada(List<PeçaXadrez> captura) {
		List<PeçaXadrez> branco = captura.stream().filter(x -> x.getCor() == Cor.branco).collect(Collectors.toList());
		List<PeçaXadrez> preto = captura.stream().filter(x -> x.getCor() == Cor.preto).collect(Collectors.toList());
		System.out.println("Peça Capturada: ");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray())); // jeito para imprimir um Array de valores no java
		System.out.print(ANSI_RESET);

		System.out.print("Preto: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray())); // jeito para imprimir um Array de valores no java
		System.out.print(ANSI_RESET);
	}
}
