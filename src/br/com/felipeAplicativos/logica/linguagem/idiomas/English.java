package br.com.felipeAplicativos.logica.linguagem.idiomas;

import static br.com.felipeAplicativos.logica.linguagem.Text.*;

import java.util.HashMap;
import java.util.Map;

import br.com.felipeAplicativos.logica.info.Info;
import br.com.felipeAplicativos.logica.linguagem.Linguagem;
import br.com.felipeAplicativos.logica.linguagem.Text;

public class English implements Linguagem{

	private Map<Text, String> textos;

	private static English INSTANCE;
	
	static {
		INSTANCE = new English();
	}
	
	private English() {
		textos = new HashMap<>();
		
		textos.put(K, "King");
		textos.put(A, "Ace");
		textos.put(ADD_BOT, "Add bot");
		textos.put(ADD_GRANA, "Add cash");
		textos.put(ADICIONAR_DINHEIRO, "Add money");
		textos.put(ADICIONAR_PARTICIPANTE, "Add participant");
		textos.put(ALTEROU_O_PALPITE, "Changed the hunch");
		textos.put(APOSTAR, "Bet");
		textos.put(APOSTA_VOTO_EXECPTION, "Invalid bet vote");
		textos.put(BACK, "Back");
		textos.put(BEM_VINDO, "Welcome");
		textos.put(BOT_PALPITE_EXCEPTION, "It's not possible to change the bot's hunch");
		textos.put(COD_IMAGEM_EXC, "The image code hasn't been inserted");
		textos.put(CONFIRMAR, "Confirm");
		textos.put(COPAS, "Cups");
		textos.put(CURTO, "Short");
		textos.put(DE, "of");
		textos.put(DURADOURO, "Long");
		textos.put(ENTRADA, "Input");
		textos.put(ESCOLHER_CARTAS, "Choose cards");
		textos.put(ESPADAS, "Spades");
		textos.put(ESTA_CARTA_JA_ESTA_ESTRE_AS_ESCOLHIDAS, "This card is already among the chosen");
		textos.put(FALHA, "Fail");
		textos.put(FALIDOS, "Bankrupts");
		textos.put(FALIU_COM, "bankrupted with");
		textos.put(GO, "Go");
		textos.put(GRANA_PARTIC_EXC, " Incompatible cash");
		textos.put(INFO_APOSTAS, "In each round are taken 5 (five) random cards from the playing cards, if any of yours be chosen, you win");
		textos.put(INFO_MODO_JOGO, "The shorter is the modality, the higher the bets");
		textos.put(INFO_PARTICIPANTES, "After created the participant, click two time upon him/her to make your hunch");
		textos.put(INSIRA_SEU_NOME, "Insert your name");
		textos.put(INSIRA_A_GRANA_DO_BOT, "Insert the bot's cash");
		textos.put(INSIRA_SUA_GRANA, "Insert your cash");
		textos.put(J, "Jack");
		textos.put(JOGADOR, "Player");
		textos.put(LINGUAGEM, "Language");
		textos.put(LOTERIA_MANEIRA, "Lotto Way");
		textos.put(MEIO_CURTO, "Medium short");
		textos.put(MEMOS_DE_DOIS_JOGADORES_EXCEPTION, "Insert at least 2 (two) players");
		textos.put(MUITO_CURTO, "Very short");
		textos.put(MUITO_DURADOURO, "Very long");
		textos.put(NENHUM_ITEM_SELECIONADO, "Select one item from list");
		textos.put(NAO_ESTA_CONFIRMADO, "Is not confirmed");
		textos.put(NOME_INAVIDO_EXC, "Invalid name");
		textos.put(OUROS, "Diamonds");
		textos.put(PALPITE_INVALIDO_EXCEPTION, "Invalid hunch");
		textos.put(PALPITE_PENDENTE_EXCEPTION, "Every player needs to have a hunch");
		textos.put(PARTICIPANTE, "Participant");
		textos.put(PARTIC_INEXISTENTE_EXCEPTION, "Participant nonexistent");
		textos.put(SELECIONANDO_CARTAS_ALEATORIAMENTE, "Selecting random cards");
		textos.put(PAUS, "Clubs");
		textos.put(PERDEDORES, "Losers");
		textos.put(PERDEU_COM, "lost with");
		textos.put(PRONTO, "ready");
		textos.put(Q, "Queen");
		textos.put(QUANTO_QUER_APOSTAR, "How much do you want to bet");
		textos.put(RAPELOU_TODO_MUNDO_FATURANDO, "is the winner, and now have");
		textos.put(REFAZER, "Remake");
		textos.put(REGRAS,   "****************************************************************************************\n\n" + 
				   "RULES:\n" +
				   "   In this game each participant choose five hunchs,\n" +
				   "after defined them, the computer will take\n" +
				   "five random cards from the playing cards set and if you match\n" +
				   "at minimum one with your hunch, you are victorious in round.\n\n" +
				   Info.CRIADOR.getInfo() + " " + Info.CONTATO.getInfo() + ", version " + Info.VERSAO.getInfo() +
		   "\n\n****************************************************************************************\n");
		textos.put(REGULAR, "Regular");
		textos.put(REMOVER_PARTICIPANTE, "Remove participant");
		textos.put(RESULTADO, "Result");
		textos.put(SELECAO_DE_CARTAS_COMPLETA_PRESSIONE_CONFIRMAR, "Selection of cards is completed, press confirm");
		textos.put(TITULO_INFORMACAO, "Information");
		textos.put(TOTAL, "Total");
		textos.put(UMA_PARTIDA_ESTA_CARREGADA_AGORA_DESEJA_REALMENTE_SAIR, "A match is loaded now in system memory, do you really want to leave?");
		textos.put(VALOR_CARTA_EXCEPTION, "Card's value incompatible");
		textos.put(VALOR_DA_APOSTA, "The value bet is");
		textos.put(VALOR_MODALIDADE_EXCEPTION, "This value does not belong to any modality");
		textos.put(VENCEDORES, "Winners");
		textos.put(VENCEU_COM, "won with");
		textos.put(VOCE_FOI_REDIRECIONADO_PARA_TELA_DE_PARTICIPANTES, "You have been redirected to participant's screen");
		textos.put(VOCE_NAO_TEM_TODO_ESSE_DINHEIRO, "You do not have all that money");
		textos.put(WARNING, "Attention");
		
	}
	
	public static English getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String word(Text text) {
		return textos.get(text);
	}
	
	@Override
	public String toString() {
		return "English";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof English)
			return true;
		else
			return false;
	}

}
