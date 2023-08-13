package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peça;
import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {

	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturada = new ArrayList<>();


	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.branco;
		iniciopartida();
	}
	
	public int getVez() {
		return vez;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
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
		
		if(testeCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			throw new ExceçãoXadrez("Você nao pode se colocar em chek");
		}
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		proximaVez();
		return (PeçaXadrez) peçaCapturada;
	}

	private Peça fazerMover(Posição origem, Posição destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarpeça(p, destino);
		
		if(peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturada.add(peçaCapturada);
		}
		return peçaCapturada;
	}
	
	private void desfazerMovimento(Posição origem, Posição destino, Peça capturaPeça) {
		PeçaXadrez p = (PeçaXadrez)tabuleiro.removerPeça(destino);
		tabuleiro.lugarpeça(p, origem);
		
		if (capturaPeça != null) {
			tabuleiro.lugarpeça(capturaPeça, destino);
			peçasCapturada.remove(capturaPeça);
			peçasNoTabuleiro.add(capturaPeça);
		}
	}

	private void validarPosiçãoOrigem(Posição posição) {
		if (!tabuleiro.temUmaPeça(posição)) {
			throw new ExceçãoXadrez("Não há peça na posição de origem");
		}
		if (jogadorAtual != ((PeçaXadrez)tabuleiro.peça(posição)).getCor()) {
							 // Downcast
			throw new ExceçãoXadrez("A peça escolhida não é sua");
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
	
	private void proximaVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.branco) ? Cor.preto : Cor.branco;
					 // OPERAÇÃO CONDICIONAL TERNARIA:
					 // se o jogadorAtual for igual a Cor.branco entao ele vai ser Cor.preto caso contrario vai Cor.branco
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.branco) ? Cor.preto : Cor.branco;
	}
	
	private PeçaXadrez Rei (Cor cor) {
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : lista) {
			if (p instanceof Rei) {
				return (PeçaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe Rei " + cor + " no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posição posiçãoRei = Rei(cor).getPosiçãoXadrez().toPosição();
		List<Peça> peçasOponente = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peça p : peçasOponente) {
			boolean[][] matriz = p.movimentoPossivel();
			if (matriz[posiçãoRei.getLinha()][posiçãoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarpeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
		peçasNoTabuleiro.add(peça);
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