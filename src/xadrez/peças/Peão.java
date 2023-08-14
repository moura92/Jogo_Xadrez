package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Peão extends PeçaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peão(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		if (getCor() == Cor.branco) {
			p.setValores(posição.getLinha() - 1, posição.getColuna());
			if (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() - 2, posição.getColuna());
			Posição p2 = new Posição(posição.getLinha() - 1, posição.getColuna());
			if (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p) && getTabuleiro().existePosição(p2)
					&& !getTabuleiro().temUmaPeça(p2) && getContagemMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
			if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
			if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento Especial: en passant (branco)
			if (posição.getLinha() == 3) {
				Posição esquerda = new Posição(posição.getLinha(), posição.getColuna() - 1);
				if (getTabuleiro().existePosição(esquerda) && existePeçaAdversaria(esquerda)
						&& getTabuleiro().peça(esquerda) == partidaXadrez.getEnPassantVulnerable()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posição direita = new Posição(posição.getLinha(), posição.getColuna() + 1);
				if (getTabuleiro().existePosição(direita) && existePeçaAdversaria(direita)
						&& getTabuleiro().peça(direita) == partidaXadrez.getEnPassantVulnerable()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}

		} else {
			p.setValores(posição.getLinha() + 1, posição.getColuna());
			if (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() + 2, posição.getColuna());
			Posição p2 = new Posição(posição.getLinha() + 1, posição.getColuna());
			if (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p) && getTabuleiro().existePosição(p2)
					&& !getTabuleiro().temUmaPeça(p2) && getContagemMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
			if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
			if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento Especial: en passant (preta)
			if (posição.getLinha() == 4) {
				Posição esquerda = new Posição(posição.getLinha(), posição.getColuna() - 1);
				if (getTabuleiro().existePosição(esquerda) && existePeçaAdversaria(esquerda)
						&& getTabuleiro().peça(esquerda) == partidaXadrez.getEnPassantVulnerable()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posição direita = new Posição(posição.getLinha(), posição.getColuna() + 1);
				if (getTabuleiro().existePosição(direita) && existePeçaAdversaria(direita)
						&& getTabuleiro().peça(direita) == partidaXadrez.getEnPassantVulnerable()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}

		}
		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}

}
