package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class GranaParticipanteInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2669448579210852378L;
	
	public GranaParticipanteInvalidoException(String grana) {
		super(Loteria.word(Text.GRANA_PARTIC_EXC) + " >> " + grana);
	}
	
	public GranaParticipanteInvalidoException(float grana) {
		super(Loteria.word(Text.GRANA_PARTIC_EXC) + " >> " + grana);
	}

}
