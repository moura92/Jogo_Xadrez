package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Cavalo extends PeçaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		p.setValores(posição.getLinha() - 1, posição.getColuna() - 2);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 2, posição.getColuna() - 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 2, posição.getColuna() + 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 1, posição.getColuna() + 2);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 1, posição.getColuna() + 2);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 2, posição.getColuna() + 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 2, posição.getColuna() - 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 1, posição.getColuna() - 2);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}
}
