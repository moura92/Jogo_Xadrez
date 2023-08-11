package jogotabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peça[][] peças;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExceçãoTabuleiro("Erro ao criar tabuleiro: deve ter pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peça peça(int linha, int coluna) {
		if (!existePosição(linha, coluna)) {
			throw new ExceçãoTabuleiro("Posição fora do tabuleiro");
		}
		return peças[linha][coluna];
	}

	public Peça peça(Posição posição) {
		if (!existePosição(posição)) {
			throw new ExceçãoTabuleiro("Posição fora do tabuleiro");
		}
		return peças[posição.getLinha()][posição.getColuna()];
	}

	public void lugarpeça(Peça peça, Posição posição) {
		if (temUmaPeça(posição)) {
			throw new ExceçãoTabuleiro("Já existe uma peça em posição" + posição);
		}
		peças[posição.getLinha()][posição.getColuna()] = peça;
		peça.posição = posição;
	}

	public Peça removerPeça(Posição posição) {
		if (!existePosição(posição)) {
			throw new ExceçãoTabuleiro("Já existe uma peça na posição");
		}
		if (peça(posição) == null) {
			return null;
		}
		Peça auxiliar = peça(posição);
		auxiliar.posição = null;
		peças[posição.getLinha()][posição.getColuna()] = null;
		return auxiliar;
	}

	private boolean existePosição(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean existePosição(Posição posição) {
		return existePosição(posição.getLinha(), posição.getColuna());
	}

	public boolean temUmaPeça(Posição posição) {
		if (!existePosição(posição)) {
			throw new ExceçãoTabuleiro("Posição fora do tabuleiro");
		}
		return peça(posição) != null;
	}
}
