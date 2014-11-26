package br.com.felipeAplicativos.logica.loteria.exceptions;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;

public class CodImagemIndefinidoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7454844348971931634L;
	
	public CodImagemIndefinidoException() {
		super(Loteria.word(Text.COD_IMAGEM_EXC));
	}
	
}
