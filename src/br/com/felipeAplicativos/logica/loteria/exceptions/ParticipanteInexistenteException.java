package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class ParticipanteInexistenteException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8137095703668472506L;

	public ParticipanteInexistenteException() {
		super(Loteria.word(Text.PARTIC_INEXISTENTE_EXCEPTION));
	}
}
