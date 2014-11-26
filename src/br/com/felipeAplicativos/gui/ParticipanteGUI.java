package br.com.felipeAplicativos.gui;

import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.List;

import br.com.felipeAplicativos.logica.loteria.Palpite;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.BotPalpiteException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.NomeParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.PalpiteInvalidoException;

@Deprecated
public class ParticipanteGUI extends Participante {

	
	private JLabel[] escolhaLabel = new JLabel[5];
	
	public ParticipanteGUI(String nome, float grana, JLabel[] escolhaLabel) throws NomeParticipanteInvalidoException, GranaParticipanteInvalidoException, ApostaVotoException {
		super(nome, grana);
		this.escolhaLabel = escolhaLabel;
	}
	
	public JLabel[] getEscolhaLabel() {
		return escolhaLabel;
	}
	
	public void setPalpite(Palpite p, JLabel[] escolhaLabel) throws PalpiteInvalidoException, BotPalpiteException {
		super.setPalpite(p);
		this.escolhaLabel = escolhaLabel;
	}
	
	public List<JLabel> cartasCertasGUI(Carta[] cartasSelecionadas) {
		List<Carta> cartasCertas = super.getPalpite().cartasCertas(cartasSelecionadas);
		
		List<JLabel> cartasCertasGUI = new ArrayList<>();
		Carta[] palpite = super.getPalpite().getCartas();
		
		A:
		for(Carta cartaCerta : cartasCertas) {
			for (int i = 0; i < palpite.length; i++) {
				if(cartaCerta.equals(palpite[i])) {
					cartasCertasGUI.add(escolhaLabel[i]);
					continue A;
				}
			}
		}
		
		return cartasCertasGUI;
	}

}
