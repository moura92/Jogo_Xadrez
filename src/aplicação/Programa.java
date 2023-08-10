package aplicação;

import xadrez.PartidaXadrez;

public class Programa {
	
	public static void main(String[] args) {
	
		PartidaXadrez partidaxadrez = new PartidaXadrez();
		UI.imprimirtabuleiro(partidaxadrez.getPeças());
	}
}