package xadrez;

import jogotabuleiro.Peça;
import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {

	private Cor cor;
	private int contagemMovimento;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimento() {
		return contagemMovimento;
	}
	
	public void aumentarContagemMovimento() {
		contagemMovimento++;
	}
	
	public void diminuirContagemMovimento() {
		contagemMovimento--;
	}
	
	public PosiçãoXadrez getPosiçãoXadrez() {
		return PosiçãoXadrez.dePosição(posição);
	}
	protected boolean existePeçaAdversaria(Posição posição) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
		return p != null && p.getCor() != cor;
	}
}
