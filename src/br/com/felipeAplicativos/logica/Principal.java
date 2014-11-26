package br.com.felipeAplicativos.logica;


import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Component;

import javax.swing.JOptionPane;

import br.com.felipeAplicativos.gui.FrmLoteria;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.linguagem.idiomas.English;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.som.SF3_03;

public class Principal {
	public static void main(String[] args) {
		
		Loteria.setLinguagem(English.getInstance());
		
		FrmLoteria.getInstance().setVisible(true);
		SF3_03.play();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					bemVindoMessage(null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	public static String inputPrincipal() {
		return showInputDialog(null, "Select the language you want to use the playing cards/Selecione a linguagem que você quer para usar o baralho\n\nEnglish\nPortuguês", "", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void warningMessagePrincipal() {
		showMessageDialog(null, "Invalid input/Entrada inválida", "", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void bemVindoMessage(Component c) {
		showMessageDialog(c, word(Text.REGRAS), word(Text.BEM_VINDO), JOptionPane.PLAIN_MESSAGE);
	}
}
