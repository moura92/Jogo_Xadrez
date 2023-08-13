package xadrez;

import jogotabuleiro.Peça;
import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {

	private Cor cor;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public PosiçãoXadrez getPosiçãoXadrez() {
		return PosiçãoXadrez.dePosição(posição);
	}
	protected boolean existePeçaAdversaria(Posição posição) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
		return p != null && p.getCor() != cor;
	}
}
