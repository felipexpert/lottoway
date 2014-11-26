package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class PalpiteInvalidoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5470362934855831581L;

	public PalpiteInvalidoException() {
		super(Loteria.word(Text.PALPITE_INVALIDO_EXCEPTION));
	}
}
