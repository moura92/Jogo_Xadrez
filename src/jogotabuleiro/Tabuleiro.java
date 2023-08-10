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
		if (!existeposição(linha, coluna)) {
			throw new ExceçãoTabuleiro("Posição fora do quadro");
		}
		return  peças[linha][coluna];
	}
	public Peça peça(Posição posição) {
		if (!existeposição(posição)) {
			throw new ExceçãoTabuleiro("Posição fora do quadro");
		}
		return peças[posição.getLinha()][posição.getColuna()];
	}
	
	public void lugarpeça(Peça peça, Posição posição) {
		if(temUmaPeça(posição)) {
			throw new ExceçãoTabuleiro("Já existe uma peça em posição" + posição);
		}
		peças[posição.getLinha()][posição.getColuna()] = peça;
		peça.posição = posição;
	}
	
	private boolean existeposição(int linha, int coluna) {
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean existeposição(Posição posição) {
		return existeposição(posição.getLinha(), posição.getColuna());
	}
	
	public boolean temUmaPeça(Posição posição) {
		if (!existeposição(posição)) {
			throw new ExceçãoTabuleiro("Posição fora do tabuleiro");
		}
		return peça(posição) != null;
	}
}
