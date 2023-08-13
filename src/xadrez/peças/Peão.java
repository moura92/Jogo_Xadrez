package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Peão extends PeçaXadrez {

	public Peão(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
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
		} 
		else {
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
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "p";
	}

}
