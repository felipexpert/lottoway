package br.com.felipeAplicativos.logica.loteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.PalpiteInvalidoException;


public class Palpite {
	
	private Carta[] cartas;
	
	public Palpite(Carta[] cartas) throws PalpiteInvalidoException {
		setCartas(cartas);
	}
	
	private void setCartas(Carta[] cartas) throws PalpiteInvalidoException {
		if(cartas == null || cartas.length != 5)
			throw new PalpiteInvalidoException();
		else
			this.cartas = cartas;
	}
	
	public List<Carta> cartasCertas(Carta[] cartasSelecionadas) {
		List<Carta> cartasCorretas = new ArrayList<>();
		for(Carta carta : cartas) {
			for(Carta cartaSelecionada : cartasSelecionadas) {
				if(carta.equals(cartaSelecionada))
					cartasCorretas.add(carta);
			}
		}
		return cartasCorretas;
	}
	
	public boolean isRight(Carta[] cartasSelecionadas) {
		for(Carta carta : cartas) {
			for(Carta cartaSelecionada : cartasSelecionadas) {
				if(carta.equals(cartaSelecionada))
					return true;
			}
		}
		return false;
	}
	
	public Carta[] getCartas() {
		return cartas;
	}
	
	public static Palpite gerarPalpiteAleatorio() {
		Palpite palpite = null;
		boolean continuar;
		do {
			continuar = false;
			try {
				palpite = new Palpite(Rodada.gerarCincoCartasAleatorias());
			} catch (PalpiteInvalidoException e) {
				continuar = true;
			}
		} while(continuar);
		
		return palpite;
	}
	
	@Override
	public String toString() {
		return Arrays.asList(cartas).toString();
	}
}
