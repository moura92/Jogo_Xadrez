package aplicação;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExceçãoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PeçaXadrez> captura = new ArrayList<>();

		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadrez, captura);
				System.out.println();
				System.out.print("Origem: ");
				PosiçãoXadrez origem = UI.lerPosiçãoXadrez(sc);

				boolean[][] movimentoPossivel = partidaXadrez.movimentoPossivel(origem);
				UI.limparTela();
				UI.imprimirtabuleiro(partidaXadrez.getPeças(), movimentoPossivel);

				System.out.println();
				System.out.print("Destino: ");
				PosiçãoXadrez destino = UI.lerPosiçãoXadrez(sc);

				PeçaXadrez peçaCapturada = partidaXadrez.jogadaXadrezDesempenho(origem, destino);

				if (peçaCapturada != null) {
					captura.add(peçaCapturada);
				}

				if (partidaXadrez.getPromoção() != null) {
					System.out.print("Digite a peça da Promoção (B - C - R - Q): ");
					String tipo = sc.nextLine().toUpperCase();// toUpperCase() -> converte a letra que o usuario digitar
																// para maiuscula.
					while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("Q")) {
						System.out.print("Comando Inválido! Digite a peça da Promoção (B - C - R - Q): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.substituirPeçaPromoção(tipo);
				}
			} catch (ExceçãoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partidaXadrez, captura);

	}
}