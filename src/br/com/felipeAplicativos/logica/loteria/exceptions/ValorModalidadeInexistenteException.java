package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class ValorModalidadeInexistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2987145399966036095L;
	
	public ValorModalidadeInexistenteException() {
		super(Loteria.word(Text.VALOR_MODALIDADE_EXCEPTION));
	}

}
