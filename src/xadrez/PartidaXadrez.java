package xadrez;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciopartida();
	}
	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i < tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return matriz;
	}
	private void iniciopartida() {
		tabuleiro.lugarpeça(new Torre(tabuleiro, Cor.branco), new Posição(2, 1));
		tabuleiro.lugarpeça(new Rei(tabuleiro, Cor.preto), new Posição(0, 4));
		tabuleiro.lugarpeça(new Rei(tabuleiro, Cor.preto), new Posição(7, 4));
	}
}