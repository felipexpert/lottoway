package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class ValorCartaInexistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2118129616442664422L;
	
	public ValorCartaInexistenteException() {
		super(Loteria.word(Text.VALOR_CARTA_EXCEPTION));
	}

}
