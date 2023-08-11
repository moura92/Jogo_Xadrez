package jogotabuleiro;

public abstract class Peça {

	protected Posição posição;
	private Tabuleiro tabuleiro;

	public Peça(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posição = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movimentoPossivel();

	public boolean movimentoPossivel(Posição posição) {
		return movimentoPossivel()[posição.getLinha()][posição.getColuna()];
	}

	public boolean existeMovimentoPossivel() {
		boolean[][] matriz = movimentoPossivel();
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
