package xadrez;

import jogotabuleiro.Peça;
import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciopartida();
	}

	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return matriz;
	}
	
	public boolean[][] movimentoPossivel(PosiçãoXadrez posiçãoOrigem){
		Posição posição = posiçãoOrigem.toPosição();
		validarPosiçãoOrigem(posição);
		return tabuleiro.peça(posição).movimentoPossivel();
		}

	public PeçaXadrez jogadaXadrezDesempenho(PosiçãoXadrez posiçãoOrigem, PosiçãoXadrez posiçãoDestino) {
		Posição origem = posiçãoOrigem.toPosição();
		Posição destino = posiçãoDestino.toPosição();
		validarPosiçãoOrigem(origem);
		validarPosiçãoDestino(origem, destino);
		Peça peçaCapturada = fazerMover(origem, destino);
		return (PeçaXadrez) peçaCapturada;
	}

	private Peça fazerMover(Posição origem, Posição destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarpeça(p, destino);
		return peçaCapturada;
	}

	private void validarPosiçãoOrigem(Posição posição) {
		if (!tabuleiro.temUmaPeça(posição)) {
			throw new ExceçãoXadrez("Não há peça na posição de origem");
		}
		if (!tabuleiro.peça(posição).existeMovimentoPossivel()) {
			throw new ExceçãoXadrez("Não há movimentos possíveis para a peça escolhida");
		}
	}

	private void validarPosiçãoDestino(Posição origem, Posição destino) {
		if (!tabuleiro.peça(origem).movimentoPossivel(destino)) {
			throw new ExceçãoXadrez("A peça escolhida não pode se mover para a posição de destino");
		}
	}

	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarpeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
	}

	private void iniciopartida() {
		colocarNovaPeça('c', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('c', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('d', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('e', 2, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('e', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.branco));

		colocarNovaPeça('c', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('c', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('d', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('e', 7, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('e', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('d', 8, new Rei(tabuleiro, Cor.preto));
	}
}