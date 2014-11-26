package br.com.felipeAplicativos.gui.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class PalpitePendenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1272349929989329808L;

	public PalpitePendenteException() {
		super(Loteria.word(Text.PALPITE_PENDENTE_EXCEPTION));
	}
	
}
