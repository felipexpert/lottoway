package br.com.felipeAplicativos.logica.loteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.BotPalpiteException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.NomeParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.PalpiteInvalidoException;

public class Bot extends Participante {
	private static int qtdBots;
	
	private static List<Carta[]> listCartasSorteadas;
	
	static {
		qtdBots = 0;
		listCartasSorteadas = new ArrayList<>();
	}
	public Bot(String grana) throws NomeParticipanteInvalidoException, GranaParticipanteInvalidoException, ApostaVotoException {
		super("Bot" + (++qtdBots), grana);
		setPalpiteAutomaticoBot(Palpite.gerarPalpiteAleatorio());
	}
	
	public Bot(float grana) throws NomeParticipanteInvalidoException, GranaParticipanteInvalidoException, ApostaVotoException {
		super("Bot" + (++qtdBots), grana);
		setPalpiteAutomaticoBot(Palpite.gerarPalpiteAleatorio());
	}
	
	public static void addUltimoResultado(Carta[] cartas) {
		listCartasSorteadas.add(cartas);
		
		while(listCartasSorteadas.size() > 3) {
			listCartasSorteadas.remove(0);
		}
	}
	
	public void alterarApostaVoto(boolean venceu) throws ApostaVotoException{
		int indexAtual = getIndexAtual();
		int ultimoIndexPossivel = ultimoIndexPossivel();
		float minimo = Loteria.getInstance().getModalidade().getValor(0);
		Modalidade m = Loteria.getInstance().getModalidade();
		if(getGrana() >= minimo) {
			
			if(venceu) {
				if(Math.random() <= 0.35 || indexAtual > ultimoIndexPossivel) {
					setApostaVoto(m.getValor(ultimoIndexPossivel));
				} else if(indexAtual < ultimoIndexPossivel) {
					setApostaVoto(m.getValor(indexAtual + 1));
				} 
			} else {
				if(indexAtual > ultimoIndexPossivel) {
					setApostaVoto(m.getValor(ultimoIndexPossivel));
				} else if(indexAtual != 0) {
					setApostaVoto(m.getValor(indexAtual - 1));
				} 
			}
		} else {
			setApostaVoto(minimo);
		}
	}
	
	
	
	private int getIndexAtual() {
		int index = 0;
		for(float valor : Loteria.getInstance().getModalidade().getValores()) {
			if(valor == getApostaVoto()) {
				return index;
			}
			index++;
		}
		
		assert false;
		
		return -1;
	}
	
	public boolean decidirSeAlteraPalpite() {
		boolean retorno = false;		
		
		//75 % de chances de alterar o palpite
		if(listCartasSorteadas.size() == 3 && Math.random() <= 0.35) {
			Set<Carta> cartasSelecionaveisSet = new HashSet<>();
			
			for(Carta[] palpite : listCartasSorteadas) {
				for(Carta carta : palpite) {
					cartasSelecionaveisSet.add(carta);
				}
			}
			List<Carta> cartasSelecionaveisList = new ArrayList<>();
			
			for(Carta doSet : cartasSelecionaveisSet) {
				cartasSelecionaveisList.add(doSet);
			}
			cartasSelecionaveisSet = null;
			
			while(cartasSelecionaveisList.size() > 5) {
				//esta parte gera um index válido dentro dos limites do set
				int indexASerRemovido = (int)Math.round(Math.random() * (cartasSelecionaveisList.size() - 1));
				
				cartasSelecionaveisList.remove(indexASerRemovido);
			}
			
			assert cartasSelecionaveisList.size() == 5;
			
			Carta[] palpite = new Carta[5];
			
			for(int i = 0; i < 5; i++) {
				palpite[i] = cartasSelecionaveisList.get(i);
			}
			
			try {
				setPalpiteAutomaticoBot(new Palpite(palpite));
				
				retorno = true;
			} catch (PalpiteInvalidoException e) {
				assert false;
			}
		}
		return retorno;
	}
	
	@Override
	public void setPalpite(Palpite p) throws BotPalpiteException{
		throw new BotPalpiteException();
	}
}
