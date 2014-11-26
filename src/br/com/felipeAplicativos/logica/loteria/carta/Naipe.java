package br.com.felipeAplicativos.logica.loteria.carta;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.exceptions.CartaInvalidaException;

public enum Naipe {
	ESPADAS(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.ESPADAS);
		}
	}, COPAS(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.COPAS);
		}
	}, PAUS(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.PAUS);
		}
	}, OUROS(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.OUROS);
		}
	};
		
	public static Naipe obterNaipe(String naipe) throws CartaInvalidaException{
		Naipe n = null;
		
		for(Naipe na : values()) {
			if(na.toString().equalsIgnoreCase(naipe)) {
				n = na;
				break;
			}
		}
		
		if(n == null)
			throw new CartaInvalidaException("NAIPE_INVALIDO");
		
		return n;
		
	}
}
