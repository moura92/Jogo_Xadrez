package aplicação;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();

		while (true) {

			UI.imprimirtabuleiro(partidaXadrez.getPeças());
			System.out.println();
			System.out.print("Origem: ");
			PosiçãoXadrez origem = UI.lerPosiçãoXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosiçãoXadrez destino = UI.lerPosiçãoXadrez(sc);
			
			PeçaXadrez peçaCapturada = partidaXadrez.jogadaXadrezDesempenho(origem, destino);
		}
	}
}