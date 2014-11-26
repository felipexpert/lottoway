package br.com.felipeAplicativos.logica.linguagem.idiomas;

import java.util.HashMap;
import java.util.Map;

import static br.com.felipeAplicativos.logica.linguagem.Text.*;

import br.com.felipeAplicativos.logica.info.Info;
import br.com.felipeAplicativos.logica.linguagem.Linguagem;
import br.com.felipeAplicativos.logica.linguagem.Text;

public class Portugues implements Linguagem{

	private Map<Text, String> textos;
	
	private static Portugues INSTANCE;
	
	static {
		INSTANCE = new Portugues();
	}
	
	private Portugues() {
		textos = new HashMap<>();
		
		textos.put(K, "Rei");
		textos.put(A, "Ás");
		textos.put(ADD_BOT, "Adicionar bot");
		textos.put(ADD_GRANA, "Adicionar grana");
		textos.put(ADICIONAR_DINHEIRO, "Adicionar dinheiro");
		textos.put(ADICIONAR_PARTICIPANTE, "Adicionar participante");
		textos.put(ALTEROU_O_PALPITE, "alterou o palpite");
		textos.put(APOSTAR, "Apostar");
		textos.put(APOSTA_VOTO_EXECPTION, "Voto de aposta inválida");
		textos.put(BACK, "Voltar");
		textos.put(BEM_VINDO, "Bem-vindo");
		textos.put(BOT_PALPITE_EXCEPTION, "Não é possível alterar o palpite de um bot");
		textos.put(COD_IMAGEM_EXC, "O código da imagem não foi inserido");
		textos.put(COMO_JOGAR, "Como jogar");
		textos.put(CONFIRMAR, "Confirmar");
		textos.put(COPAS, "Copas");
		textos.put(CURTO, "Curto");
		textos.put(DE, "de");
		textos.put(DURADOURO, "Duradouro");
		textos.put(ENTRADA, "Entrada");
		textos.put(ESCOLHER_CARTAS, "Escolher cartas");
		textos.put(ESPADAS, "Espadas");
		textos.put(ESTA_CARTA_JA_ESTA_ESTRE_AS_ESCOLHIDAS, "Esta carta já está entre as escolhidas");
		textos.put(FALHA, "Falha");
		textos.put(FALIDOS, "Falidos");
		textos.put(FALIU_COM, "faliu com");
		textos.put(GO, "Ir");
		textos.put(GRANA_PARTIC_EXC, "Grana inconpatível");
		textos.put(INFO_APOSTAS, "Em cada rodada são retiradas 5 (cinco) cartas aleatórias do baralho, se alguma das suas for escolhida, você vence");
		textos.put(INFO_MODO_JOGO, "Quanto mais curta é a modalidade, maiores são as apostas");
		textos.put(INFO_PARTICIPANTES, "Após criar o participante, clique duas vezes nele para escolher o palpite");
		textos.put(INSIRA_SEU_NOME, "Insira seu nome");
		textos.put(INSIRA_A_GRANA_DO_BOT, "Insira a grana do bot");
		textos.put(INSIRA_SUA_GRANA, "Insira sua grana");
		textos.put(J, "Valete");
		textos.put(JOGADOR, "JOGADOR");
		textos.put(LINGUAGEM, "Linguagem");
		textos.put(LOTERIA_MANEIRA, "Loteria Maneira");
		textos.put(MEIO_CURTO, "Meio curto");
		textos.put(MEMOS_DE_DOIS_JOGADORES_EXCEPTION, "Insira pelo menos 2 (dois) jogadores");
		textos.put(MUITO_CURTO, "Muito curto");
		textos.put(MUITO_DURADOURO, "Muito duradouro");
		textos.put(NAO_ESTA_CONFIRMADO, "Não está confirmado(a)");
		textos.put(NENHUM_ITEM_SELECIONADO, "Selecione um item da lista");
		textos.put(NOME_INAVIDO_EXC, "Nome inválido");
		textos.put(OUROS, "Ouros");
		textos.put(PALPITE_INVALIDO_EXCEPTION, "Palpite inválido");
		textos.put(PALPITE_PENDENTE_EXCEPTION, "Todos os jogadores precisam escolher o palpite");
		textos.put(PARTICIPANTE, "Participante");
		textos.put(PARTIC_INEXISTENTE_EXCEPTION, "Participante inexistente");
		textos.put(SELECIONANDO_CARTAS_ALEATORIAMENTE, "Selecionando cartas aleatoriamente");
		textos.put(PAUS, "Paus");
		textos.put(PERDEDORES, "Perdedores");
		textos.put(PERDEU_COM, "perdeu com");
		textos.put(PRONTO, "pronto");
		textos.put(Q, "Dama");
		textos.put(QUANTO_QUER_APOSTAR, "Quanto quer apostar");
		textos.put(RAPELOU_TODO_MUNDO_FATURANDO, "rapelou todo mundo, faturando");
		textos.put(REFAZER, "Refazer");
		textos.put(REGRAS,   "****************************************************************************************\n\n" + 
								   "REGRAS:\n" +
								   "   Neste jogo cada participante escolhe cinco palpites,\n" +
								   "depois de definidos os palpites, o computador retirará\n" +
								   "cinco cartas aleatorias do baralho e se caso você acertar\n" +
								   "pelo menos uma com seu palpite, você é vitorioso na rodada.\n\n" +
				   Info.CRIADOR.getInfo() + " " + Info.CONTATO.getInfo() + ", versão " + Info.VERSAO.getInfo() +
		   "\n\n****************************************************************************************\n");
		textos.put(REGULAR, "Regular");
		textos.put(REMOVER_PARTICIPANTE, "Remover participante");
		textos.put(RESULTADO, "Resultado");
		textos.put(SELECAO_DE_CARTAS_COMPLETA_PRESSIONE_CONFIRMAR, "Seleção de cartas completa, pressione confirmar");
		textos.put(TITULO_INFORMACAO, "Informação");
		textos.put(TOTAL, "Total");
		textos.put(UMA_PARTIDA_ESTA_CARREGADA_AGORA_DESEJA_REALMENTE_SAIR, "Uma partida está carregada na memória do sistema, deseja realmente partir?");
		textos.put(VALOR_CARTA_EXCEPTION, "Valor de carta incoerente");
		textos.put(VALOR_DA_APOSTA, "Valor da aposta");
		textos.put(VALOR_MODALIDADE_EXCEPTION, "Este valor não pertence há nenhuma modalidade");
		textos.put(VENCEDORES, "Vencedores");
		textos.put(VENCEU_COM, "venceu com");
		textos.put(VOCE_FOI_REDIRECIONADO_PARA_TELA_DE_PARTICIPANTES, "Você foi redirecionado para a tela de participantes");
		textos.put(VOCE_NAO_TEM_TODO_ESSE_DINHEIRO, "Vocé não tem todo esse dinheiro");
		textos.put(WARNING, "Atenção");
		
	}
	
	public static Portugues getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String word(Text text) {
		return textos.get(text);
	}

	@Override
	public String toString() {
		return "Português";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Portugues)
			return true;
		else
			return false;
	}
	
}
