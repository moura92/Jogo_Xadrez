package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Bispo extends PeçaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// verificar se tem posição Diagonal Superior Esquerda da peça:
		p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição na Diagonal Superior Direita da peça:
		p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição na Diagonal Inferior direita da peça:
		p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição na Diagonal Inferior esquerda da peça:
		p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}
		return matriz;
	}
}
