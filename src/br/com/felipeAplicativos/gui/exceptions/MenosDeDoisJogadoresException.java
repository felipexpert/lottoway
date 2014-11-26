package br.com.felipeAplicativos.gui.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class MenosDeDoisJogadoresException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4091686520106665388L;

	
	public MenosDeDoisJogadoresException() {
		super(Loteria.word(Text.MEMOS_DE_DOIS_JOGADORES_EXCEPTION));
	}
}
