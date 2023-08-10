package jogotabuleiro;

public class ExceçãoTabuleiro extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExceçãoTabuleiro(String mensagem) {
		super(mensagem);
	}
}
