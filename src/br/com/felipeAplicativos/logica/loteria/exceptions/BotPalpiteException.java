package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class BotPalpiteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155439612847144765L;

	
	public BotPalpiteException() {
		super(Loteria.word(Text.BOT_PALPITE_EXCEPTION));
	}
}
