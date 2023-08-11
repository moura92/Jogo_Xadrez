package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// verificar se tem posição Acima da peça(REI):
		p.setValores(posição.getLinha() - 1, posição.getColuna());
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição Abaixo da peça(REI):
		p.setValores(posição.getLinha() + 1, posição.getColuna());
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição a Esquerda da peça(REI):
		p.setValores(posição.getLinha(), posição.getColuna() - 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verificar se tem posição a Direita da peça(REI):
		p.setValores(posição.getLinha(), posição.getColuna() + 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// verificar se tem posição na diagonal(Seperior Esquerda) da peça(REI):
		p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// verificar se tem posição na diagonal(Seperior Direita) da peça(REI):
		p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// verificar se tem posição na diagonal(Inferior Esquerda) da peça(REI):
		p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		
		// verificar se tem posição na diagonal(Inferior Direita) da peça(REI):
		p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
		if (getTabuleiro().existePosição(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}
}
