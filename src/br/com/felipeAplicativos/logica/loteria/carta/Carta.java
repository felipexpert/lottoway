package br.com.felipeAplicativos.logica.loteria.carta;

import static br.com.felipeAplicativos.logica.linguagem.Text.DE;

import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.exceptions.CartaInvalidaException;
import br.com.felipeAplicativos.logica.loteria.exceptions.CodImagemIndefinidoException;

public class Carta {
	private int codImagem;
	private Valor valor;
	private Naipe naipe;
	
	public Carta(int codImagem, Valor valor, Naipe naipe) {
		setCodImagem(codImagem);
		setValor(valor);
		setNaipe(naipe);
	}
	
	public Carta(int codImagem, String valor, String naipe) throws CartaInvalidaException {
		setCodImagem(codImagem);
		setValor(valor);
		setNaipe(naipe);
	}
	
	public Carta(Valor valor, Naipe naipe) {
		this.codImagem = -1;
		setValor(valor);
		setNaipe(naipe);
	}
	
	public Carta(String valor, String naipe) throws CartaInvalidaException {
		this.codImagem = -1;
		setValor(valor);
		setNaipe(naipe);
	}
	
	private void setCodImagem(int cod) {
		assert (cod > -1 && cod < 52);
		
		this.codImagem = cod;
	}
	
	private void setValor(String valor) throws CartaInvalidaException {
		this.valor = Valor.obterValor(valor);
	}
	
	private void setValor(Valor valor) {
		this.valor = valor;
	}
	
	private void setNaipe(String naipe) throws CartaInvalidaException {
		this.naipe = Naipe.obterNaipe(naipe);
	}
	
	private void setNaipe(Naipe naipe) {
		this.naipe = naipe;
	}
	
	public int getCodImagem() throws CodImagemIndefinidoException{
		if(codImagem == -1)
			throw new CodImagemIndefinidoException();
		
		return codImagem;
	}
	
	public Carta getCarta() {
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Carta))
			return false;
		Carta o = (Carta) obj;
		return (this.valor.equals(o.valor) && this.naipe.equals(o.naipe));
	}
	
	@Override
	public String toString() {
		return valor + " " + Loteria.word(DE) + " " + naipe;
	}
}
