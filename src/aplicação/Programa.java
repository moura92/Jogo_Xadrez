package aplicação;

import java.util.Scanner;

import xadrez.ExceçãoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();

		while (true) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadrez);
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
			} 
			catch (ExceçãoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}