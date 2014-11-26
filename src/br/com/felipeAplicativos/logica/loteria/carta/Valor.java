package br.com.felipeAplicativos.logica.loteria.carta;

import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.exceptions.CartaInvalidaException;

public enum Valor {
	A(){
		@Override
		public String toString() {
			return Loteria.word(Text.A);
		}
	},
	_1("1"), _2("2"), _3("3"), _4("4"), _5("5"), _6("6"), _7("7"), _8("8"), _9("9"), _10("10"),
	J(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.J);
		}
	},
	Q(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.Q);
		}
	},
	K(){
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Loteria.word(Text.K);
		}
	};
	
	private String text;
	
	Valor(String text) {
		this.text = text;
	}
	Valor() {}
	@Override
	public String toString() {
		return text;
	}
	
	public static Valor obterValor(String valor) throws CartaInvalidaException{
		Valor v = null;
		
		for(Valor va : values()) {
			if(va.toString().equalsIgnoreCase(valor)) {
				v = va;
				break;
			}
		}
		
		if(v == null)
			throw new CartaInvalidaException("VALOR_INVALIDO");
		
		return v;
	}
}
