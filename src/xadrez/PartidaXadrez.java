package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peça;
import jogotabuleiro.Posição;
import jogotabuleiro.Tabuleiro;
import xadrez.peças.Bispo;
import xadrez.peças.Cavalo;
import xadrez.peças.Peão;
import xadrez.peças.Rainha;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {

	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PeçaXadrez enPassantVulnerable;
	private PeçaXadrez promoção;

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

	public boolean getCheckMate() {
		return checkMate;
	}

	public PeçaXadrez getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public PeçaXadrez getPromoção() {
		return promoção;
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

	public boolean[][] movimentoPossivel(PosiçãoXadrez posiçãoOrigem) {
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

		if (testeCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			throw new ExceçãoXadrez("Você nao pode se colocar em chek");
		}

		PeçaXadrez peçaMovida = (PeçaXadrez) tabuleiro.peça(destino);

		// movimento especial: promoção
		promoção = null;
		if (peçaMovida instanceof Peão) {
			if (peçaMovida.getCor() == Cor.branco && destino.getLinha() == 0
					|| peçaMovida.getCor() == Cor.preto && destino.getLinha() == 7) {
				promoção = (PeçaXadrez) tabuleiro.peça(destino);
				promoção = substituirPeçaPromoção("Q");
			}
		}

		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximaVez();
		}

		// Movimento Especial: en passant
		if (peçaMovida instanceof Peão
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulnerable = peçaMovida;
		} else {
			enPassantVulnerable = null;
		}

		return (PeçaXadrez) peçaCapturada;
	}

	public PeçaXadrez substituirPeçaPromoção(String tipo) {
		if (promoção == null) {
			throw new IllegalStateException("Não há peça para ser promoção");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo inválido para promoção");
		}
		Posição pos = promoção.getPosiçãoXadrez().toPosição();
		Peça p = tabuleiro.removerPeça(pos);
		peçasNoTabuleiro.remove(p);

		PeçaXadrez novaPeça = novaPeça(tipo, promoção.getCor());
		tabuleiro.lugarpeça(novaPeça, pos);
		peçasNoTabuleiro.add(novaPeça);

		return novaPeça;
	}

	private PeçaXadrez novaPeça(String tipo, Cor cor) {
		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if (tipo.equals("Q"))
			return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peça fazerMover(Posição origem, Posição destino) {
		PeçaXadrez p = (PeçaXadrez) tabuleiro.removerPeça(origem);
		p.aumentarContagemMovimento();
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarpeça(p, destino);

		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturada.add(peçaCapturada);
		}

		// Movimento especial: Roque do lado do rei(Roque pequeno)
		// Tratando o movimento do roque pequeno:
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posição posiçãoOrigemTorre = new Posição(origem.getLinha(), origem.getColuna() + 3);
			Posição posiçãoDestinoTorre = new Posição(origem.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(posiçãoOrigemTorre);
			tabuleiro.lugarpeça(torre, posiçãoDestinoTorre);
			torre.aumentarContagemMovimento();

		}

		// Movimento especial: Roque do lado do rainha(Roque grande)
		// Tratando o movimento do roque grande:
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posição posiçãoOrigemTorre = new Posição(origem.getLinha(), origem.getColuna() - 4);
			Posição posiçãoDestinoTorre = new Posição(origem.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(posiçãoOrigemTorre);
			tabuleiro.lugarpeça(torre, posiçãoDestinoTorre);
			torre.aumentarContagemMovimento();

		}

		// Movimento especial: en passant
		if (p instanceof Peão) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == null) {
				Posição posiçãoPeão;
				if (p.getCor() == Cor.branco) {
					posiçãoPeão = new Posição(destino.getLinha() + 1, destino.getColuna());
				} else {
					posiçãoPeão = new Posição(destino.getLinha() - 1, destino.getColuna());
				}
				peçaCapturada = tabuleiro.removerPeça(posiçãoPeão);
				peçasCapturada.add(peçaCapturada);
				peçasNoTabuleiro.remove(peçaCapturada);
			}
		}

		return peçaCapturada;
	}

	private void desfazerMovimento(Posição origem, Posição destino, Peça peçaCapturada) {
		PeçaXadrez p = (PeçaXadrez) tabuleiro.removerPeça(destino);
		p.diminuirContagemMovimento();
		tabuleiro.lugarpeça(p, origem);

		if (peçaCapturada != null) {
			tabuleiro.lugarpeça(peçaCapturada, destino);
			peçasCapturada.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}

		// Movimento especial: Roque do lado do rei(Roque pequeno)
		// Tratando o movimento do roque pequeno:
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posição posiçãoOrigemTorre = new Posição(origem.getLinha(), origem.getColuna() + 3);
			Posição posiçãoDestinoTorre = new Posição(origem.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(posiçãoDestinoTorre);
			tabuleiro.lugarpeça(torre, posiçãoOrigemTorre);
			torre.diminuirContagemMovimento();

		}

		// Movimento especial: Roque do lado do rainha(Roque grande)
		// Tratando o movimento do roque grande:
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posição posiçãoOrigemTorre = new Posição(origem.getLinha(), origem.getColuna() - 4);
			Posição posiçãoDestinoTorre = new Posição(origem.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(posiçãoDestinoTorre);
			tabuleiro.lugarpeça(torre, posiçãoOrigemTorre);
			torre.diminuirContagemMovimento();

		}

		// Movimento especial: en passant
		if (p instanceof Peão) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == enPassantVulnerable) {
				PeçaXadrez peão = (PeçaXadrez) tabuleiro.removerPeça(destino);
				Posição posiçãoPeão;
				if (p.getCor() == Cor.branco) {
					posiçãoPeão = new Posição(3, destino.getColuna());
				} else {
					posiçãoPeão = new Posição(4, destino.getColuna());
				}
				tabuleiro.lugarpeça(peão, posiçãoPeão);

			}
		}

	}

	private void validarPosiçãoOrigem(Posição posição) {
		if (!tabuleiro.temUmaPeça(posição)) {
			throw new ExceçãoXadrez("Não há peça na posição de origem");
		}
		if (jogadorAtual != ((PeçaXadrez) tabuleiro.peça(posição)).getCor()) {
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
		// se o jogadorAtual for igual a Cor.branco entao ele vai ser Cor.preto caso
		// contrario vai Cor.branco
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.branco) ? Cor.preto : Cor.branco;
	}

	private PeçaXadrez Rei(Cor cor) {
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : lista) {
			if (p instanceof Rei) {
				return (PeçaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe Rei " + cor + " no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posição posiçãoRei = Rei(cor).getPosiçãoXadrez().toPosição();
		List<Peça> peçasOponente = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peça p : peçasOponente) {
			boolean[][] matriz = p.movimentoPossivel();
			if (matriz[posiçãoRei.getLinha()][posiçãoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : lista) {
			boolean[][] matriz = p.movimentoPossivel();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posição origem = ((PeçaXadrez) p).getPosiçãoXadrez().toPosição();
						Posição destino = new Posição(i, j);
						Peça capturaPeça = fazerMover(origem, destino);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(origem, destino, capturaPeça);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarpeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
		peçasNoTabuleiro.add(peça);
	}

	private void iniciopartida() {
		colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.branco));
		colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.branco));
		colocarNovaPeça('d', 1, new Rainha(tabuleiro, Cor.branco));
		colocarNovaPeça('e', 1, new Rei(tabuleiro, Cor.branco, this));
		colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.branco));
		colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.branco));
		colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.branco));
		colocarNovaPeça('a', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('b', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('c', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('d', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('e', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('f', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('g', 2, new Peão(tabuleiro, Cor.branco, this));
		colocarNovaPeça('h', 2, new Peão(tabuleiro, Cor.branco, this));

		colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.preto));
		colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.preto));
		colocarNovaPeça('d', 8, new Rainha(tabuleiro, Cor.preto));
		colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.preto, this));
		colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.preto));
		colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.preto));
		colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.preto));
		colocarNovaPeça('a', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('b', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('c', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('d', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('e', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('f', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('g', 7, new Peão(tabuleiro, Cor.preto, this));
		colocarNovaPeça('h', 7, new Peão(tabuleiro, Cor.preto, this));
	}
}