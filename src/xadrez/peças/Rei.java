package xadrez.peças;

import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRock(Posição posição) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
		return p != null &&  p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimento() == 0;
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
		
		//Movimento especial: Roque
		if (getContagemMovimento() == 0 && !partidaXadrez.getCheck()) {
			//Movimento especial: Roque do lado do rei(Roque pequeno)
			Posição posiçãoTorre1 =  new Posição(posição.getLinha(), posição.getColuna() + 3);
			if (testeTorreRock(posiçãoTorre1)) {
				Posição p1 = new Posição(posição.getLinha(), posição.getColuna() + 1);
				Posição p2 = new Posição(posição.getLinha(), posição.getColuna() + 2);
				if ( getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null) {
					matriz[posição.getLinha()][posição.getColuna() + 2] = true;
				}
			}
			//Movimento especial: Roque do lado do rainha(Roque grande)
			Posição posiçãoTorre2 =  new Posição(posição.getLinha(), posição.getColuna() - 4);
			if (testeTorreRock(posiçãoTorre2)) {
				Posição p1 = new Posição(posição.getLinha(), posição.getColuna() - 1);
				Posição p2 = new Posição(posição.getLinha(), posição.getColuna() - 2);
				Posição p3 = new Posição(posição.getLinha(), posição.getColuna() - 3);

				if ( getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null) {
					matriz[posição.getLinha()][posição.getColuna() - 2] = true;
				}
			}
		}
		
		return matriz;
	}
}
