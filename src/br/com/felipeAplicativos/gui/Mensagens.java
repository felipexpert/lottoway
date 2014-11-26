package br.com.felipeAplicativos.gui;
import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import static javax.swing.JOptionPane.showMessageDialog;

import static javax.swing.JOptionPane.showInputDialog;

import java.awt.Component;

import javax.swing.JOptionPane;


import br.com.felipeAplicativos.logica.linguagem.Text;

class Mensagens {
	private Mensagens() {}
	
	public static void falhaMessage(Component c, String message) {
		showMessageDialog(c, message, word(Text.FALHA), JOptionPane.ERROR_MESSAGE);
	}
	
	public static void nenhumItemMessage(Component c) {
		showMessageDialog(c, word(Text.NENHUM_ITEM_SELECIONADO), word(Text.FALHA), JOptionPane.ERROR_MESSAGE);
	}
	
	public static int confirmMessage(Component c) {
		return JOptionPane.showConfirmDialog(c, word(Text.UMA_PARTIDA_ESTA_CARREGADA_AGORA_DESEJA_REALMENTE_SAIR), "?", JOptionPane.YES_NO_OPTION);
	}
	
	public static String inputMessage00(Component c) {
		return showInputDialog(c, word(Text.INSIRA_SEU_NOME), word(Text.ENTRADA), JOptionPane.PLAIN_MESSAGE);
	}
	
	public static String inputMessage01(Component c) {
		return showInputDialog(c, word(Text.INSIRA_SUA_GRANA), word(Text.ENTRADA), JOptionPane.PLAIN_MESSAGE);
	}
	
	public static String inputMessage02(Component c) {
		return showInputDialog(c, word(Text.ADD_GRANA), word(Text.ENTRADA), JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void infoMessage00(Component c) {
		showMessageDialog(c, word(Text.ESTA_CARTA_JA_ESTA_ESTRE_AS_ESCOLHIDAS), word(Text.TITULO_INFORMACAO), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void infoMessage01(Component c) {
		showMessageDialog(c, word(Text.SELECAO_DE_CARTAS_COMPLETA_PRESSIONE_CONFIRMAR), word(Text.TITULO_INFORMACAO), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void infoMessage02(Component c, String nome, String granaText) {
		showMessageDialog(c, nome + " " + word(Text.RAPELOU_TODO_MUNDO_FATURANDO) + ": " + granaText, word(Text.TITULO_INFORMACAO), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void infoMessage03(Component c) {
		showMessageDialog(c, word(Text.VOCE_FOI_REDIRECIONADO_PARA_TELA_DE_PARTICIPANTES), word(Text.TITULO_INFORMACAO), JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void infoMessage04(Component c) {
		showMessageDialog(c, word(Text.VOCE_NAO_TEM_TODO_ESSE_DINHEIRO), word(Text.WARNING), JOptionPane.WARNING_MESSAGE);
	}
	
	public static void infoMessage05(Component c, String mensagem) {
		showMessageDialog(c, mensagem, word(Text.TITULO_INFORMACAO), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String inputMessage03(Component c) {
		return showInputDialog(c, word(Text.INSIRA_A_GRANA_DO_BOT), word(Text.ENTRADA), JOptionPane.PLAIN_MESSAGE);
	}
}
