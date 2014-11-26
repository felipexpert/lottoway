package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class NomeParticipanteInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865052537298124619L;
	
	public NomeParticipanteInvalidoException(String nome) {
		super(Loteria.word(Text.NOME_INAVIDO_EXC) + " >> " + nome);
	}

}
