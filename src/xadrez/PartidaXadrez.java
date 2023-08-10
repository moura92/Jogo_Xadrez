package xadrez;

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
	
	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarpeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
	}
	private void iniciopartida() {
		colocarNovaPeça('c', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('c', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('d', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('e', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('e', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.branco));

		colocarNovaPeça('c', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('c', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('d', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('e', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('e', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('d', 8, new Rei(tabuleiro, Cor.preto));
	}
}