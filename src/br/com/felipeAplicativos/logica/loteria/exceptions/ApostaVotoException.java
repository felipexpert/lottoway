package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class ApostaVotoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2819752769303166388L;

	public ApostaVotoException() {
		super(Loteria.word(Text.APOSTA_VOTO_EXECPTION));
	}
	
}
