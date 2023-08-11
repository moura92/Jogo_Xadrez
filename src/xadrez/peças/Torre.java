package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Torre extends PeçaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// verificar se tem posição Acima da peça(Torre):
		p.setValores(posição.getLinha() - 1, posição.getColuna());
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		
		// verificar se tem posição a Esquerda da peça(Torre):
		p.setValores(posição.getLinha(), posição.getColuna() - 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		
		// verificar se tem posição a Direita da peça(Torre):
		p.setValores(posição.getLinha(), posição.getColuna() + 1);
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		
		// verificar se tem posição Abaixo da peça(Torre):
		p.setValores(posição.getLinha() + 1, posição.getColuna());
		while (getTabuleiro().existePosição(p) && !getTabuleiro().temUmaPeça(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().existePosição(p) && existePeçaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}
		return matriz;
	}
}
