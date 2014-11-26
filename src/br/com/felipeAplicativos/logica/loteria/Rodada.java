package br.com.felipeAplicativos.logica.loteria;

import java.util.HashSet;
import java.util.Set;

import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.carta.Naipe;
import br.com.felipeAplicativos.logica.loteria.carta.Valor;
import br.com.felipeAplicativos.logica.loteria.exceptions.CodImagemIndefinidoException;


public class Rodada {
	static {
		Carta[] baralho = {new Carta(0, Valor.A, Naipe.COPAS), new Carta(1, Valor._2, Naipe.COPAS), new Carta(2, Valor._3, Naipe.COPAS), new Carta(3, Valor._4, Naipe.COPAS), new Carta(4, Valor._5, Naipe.COPAS), new Carta(5, Valor._6, Naipe.COPAS), new Carta(6, Valor._7, Naipe.COPAS), new Carta(7, Valor._8, Naipe.COPAS), new Carta(8, Valor._9, Naipe.COPAS), new Carta(9, Valor._10, Naipe.COPAS), new Carta(10, Valor.J, Naipe.COPAS), new Carta(11, Valor.Q, Naipe.COPAS), new Carta(12, Valor.K, Naipe.COPAS), 
				   new Carta(13, Valor.A, Naipe.OUROS), new Carta(14, Valor._2, Naipe.OUROS), new Carta(15, Valor._3, Naipe.OUROS), new Carta(16, Valor._4, Naipe.OUROS), new Carta(17, Valor._5, Naipe.OUROS), new Carta(18, Valor._6, Naipe.OUROS), new Carta(19, Valor._7, Naipe.OUROS), new Carta(20, Valor._8, Naipe.OUROS), new Carta(21, Valor._9, Naipe.OUROS), new Carta(22, Valor._10, Naipe.OUROS), new Carta(23, Valor.J, Naipe.OUROS), new Carta(24, Valor.Q, Naipe.OUROS), new Carta(25, Valor.K, Naipe.OUROS),
				   new Carta(26, Valor.A, Naipe.ESPADAS), new Carta(27, Valor._2, Naipe.ESPADAS), new Carta(28, Valor._3, Naipe.ESPADAS), new Carta(29, Valor._4, Naipe.ESPADAS), new Carta(30, Valor._5, Naipe.ESPADAS), new Carta(31, Valor._6, Naipe.ESPADAS), new Carta(32, Valor._7, Naipe.ESPADAS), new Carta(33, Valor._8, Naipe.ESPADAS), new Carta(34, Valor._9, Naipe.ESPADAS), new Carta(35, Valor._10, Naipe.ESPADAS), new Carta(36, Valor.J, Naipe.ESPADAS), new Carta(37, Valor.Q, Naipe.ESPADAS), new Carta(38, Valor.K, Naipe.ESPADAS),
				   new Carta(39, Valor.A, Naipe.PAUS), new Carta(40, Valor._2, Naipe.PAUS), new Carta(41, Valor._3, Naipe.PAUS), new Carta(42, Valor._4, Naipe.PAUS), new Carta(43, Valor._5, Naipe.PAUS), new Carta(44, Valor._6, Naipe.PAUS), new Carta(45, Valor._7, Naipe.PAUS), new Carta(46, Valor._8, Naipe.PAUS), new Carta(47, Valor._9, Naipe.PAUS), new Carta(48, Valor._10, Naipe.PAUS), new Carta(49, Valor.J, Naipe.PAUS), new Carta(50, Valor.Q, Naipe.PAUS), new Carta(51, Valor.K, Naipe.PAUS)};
		Rodada.baralho = baralho;
	}
	private static Carta[] baralho;
	
	private Rodada() {}
	
	public static Carta[] gerarCincoCartasAleatorias() {
		Set<Integer> posicoes = new HashSet<>();
		while(posicoes.size() < 5) {
			posicoes.add((int) Math.round(Math.random() * 51));
		}
		
		Carta[] cartas = new Carta[5];
		int i = 0;
		A:
		for(Integer posicao : posicoes) {
			cartas[i] = baralho[posicao];
			i++;
			if(i == 5)
				break A;
		}
		
		return cartas;
	}
	
	public int obterCodImagem(Carta carta) {
		int retorno = -1;
		try {
		for(Carta c : baralho) {
			if(c.equals(carta)) {
				retorno = c.getCodImagem();
				break;
			}
		}
		} catch(CodImagemIndefinidoException e) {
			assert false;
		}
		
		assert(retorno != -1);
		
		return retorno;
		
	}
	
	public static Carta[] getBaralho() {
		return baralho;
	}
	
	public static String getTextoBaralho() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(Carta carta: baralho) {
			sb.append(carta.toString() + "   ");
			i++;
			switch(i) {
				case 13:
				case 26:
				case 39:
					sb.append("\n\n");
					break;
			}
		}
		return sb.toString();
	}
}
