package xadrez;

import jogotabuleiro.Posição;

public class PosiçãoXadrez{
	
	private char coluna;
	private int linha;
	
	public PosiçãoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha <1 || linha > 8) {
			throw new ExceçãoXadrez("Erro ao instanciar a posição de xadrez. Os valores válidos são de 'a1' a 'h8'");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posição toPosição() {
		return new Posição(8 - linha, coluna - 'a');
	}

	protected static PosiçãoXadrez dePosição(Posição posição) {
		return new PosiçãoXadrez((char)('a' - posição.getColuna()), 8 - posição.getLinha());
	}
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}