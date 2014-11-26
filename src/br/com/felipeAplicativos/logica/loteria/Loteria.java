package br.com.felipeAplicativos.logica.loteria;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


import br.com.felipeAplicativos.logica.linguagem.Linguagem;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.linguagem.idiomas.Portugues;
import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.BotPalpiteException;
import br.com.felipeAplicativos.logica.loteria.exceptions.CartaInvalidaException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.NomeParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.PalpiteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.ParticipanteInexistenteException;


public class Loteria {
	private List<Participante> participantes;
	
	private Modalidade modalidade;
	
	private static Linguagem linguagem;
	
	private static Loteria INSTANCE;
	
	static {
		INSTANCE = new Loteria();
	}
	
	private Loteria() {
		participantes = new ArrayList<>();
		this.modalidade = Modalidade.MUITO_DURADOURO;
	}
	
	public static Linguagem getLinguagem() {
		return linguagem;
	}
	
	public static void setLinguagem(Linguagem linguagem) {
		Loteria.linguagem = linguagem;
	}
	
	
	public int getQtdParticipantes() {
		return participantes.size();
	}
	
	public boolean palpitesPendentes() {
		boolean retorno = false;
		
		for(Participante p : participantes) {
			if(p.getPalpite() == null) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}
	
	public static String word(Text text) {
		return linguagem.word(text);
	}
	
	public List<Participante> getParticipantes() {
		return participantes;
	}
	
	public int getParticipanteIndex(Participante participante) {
		return this.participantes.indexOf(participante);
	}
	
	public Participante getParticipante(int index) throws ParticipanteInexistenteException {
		Participante p = null;
		try {
			p = participantes.get(index);
		} catch(IndexOutOfBoundsException e) {
			throw new ParticipanteInexistenteException();
		}
		return p;
	}
	
	public void removeParticipante(Participante p) {
		participantes.remove(p);
	}
	
	public void removeParticipante(int index) throws ParticipanteInexistenteException {
		
		try {
			participantes.remove(index);
		} catch(IndexOutOfBoundsException e) {
			throw new ParticipanteInexistenteException();
		}
		
	}
	
	public boolean getAlguemVenceu() {
		int count = 0;
		for(Participante p : participantes) {
			if(p.getGrana() > 0) {
				count++;
				if(count > 1)
					return false;
			}
		}
		
		assert count == 1;
		
		return true;
	}
	
	public void addParticipante(Participante participante) {
		participantes.add(participante);
	}
	
	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) throws ApostaVotoException {
		for(Participante p : participantes) {
			p.setApostaVoto(0.1f);
		}
		this.modalidade = modalidade;
	}

	public static Loteria getInstance() {
		return INSTANCE;
	}
	
	/**
	 * O primeiro item da lista é uma lista com os vencedores
	 * O segundo é uma lista com os perdedores
	 * O terceiro é uma lista com os falidos
	 * 
	 * @param cartas
	 * @param granaApostada
	 * @return uma lista com três listas
	 * @throws GranaParticipanteInvalidoException
	 * @throws ApostaVotoException 
	 */
	public List<List<Participante>> jogarEObterResultado(final Carta[] cartas, float granaApostada) throws GranaParticipanteInvalidoException, ApostaVotoException {
		Bot.addUltimoResultado(cartas);
		List<Participante> vencedores = new ArrayList<>();
		List<Participante> perdedores = new ArrayList<>();
		List<Participante> falidos = new ArrayList<>();
		
		for (Participante participante : participantes) {
			if(participante.getPalpite().isRight(cartas)) {
				vencedores.add(participante);
				if(participante instanceof Bot)
					((Bot) participante).alterarApostaVoto(true);
			}
			else {
				perdedores.add(participante);
				if(participante instanceof Bot)
					((Bot) participante).alterarApostaVoto(false);
			}
		}
		if(vencedores.size() > 0 && perdedores.size() > 0) {
			float valorArrecadadoDosPerdedores = 0;
			
			for (Participante perdedor : perdedores) {
				valorArrecadadoDosPerdedores += perdedor.retirarGrana(granaApostada);
				if(perdedor.getGrana() == 0) {
					//perdedores.remove(perdedor);
					falidos.add(perdedor);
				} 
			}
			
			float granaParaCadaVencedor = valorArrecadadoDosPerdedores / vencedores.size();
			
			for (Participante vencedor : vencedores) {
				vencedor.PorMaisGrana(granaParaCadaVencedor);
			}
		}
		List<List<Participante>> resultado = new ArrayList<>();
		resultado.add(vencedores); resultado.add(perdedores); resultado.add(falidos);
		
		//participantes.removeAll(falidos);
		
		for(Participante perdedor : perdedores) {
				if(perdedor instanceof Bot) {
					final Participante per = perdedor;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							((Bot) per).decidirSeAlteraPalpite();								
						}
					}).start();
				}
		}
		
		return resultado;
	}
	/**
	 * Cada jogador insirirá o quanto quer apostar, e este método retornará a média
	 * @param apostas
	 * @return o valor da aposta de acordo com os votos dos jogadores
	 */
	public float obterValorAposta(float... apostas) {
		float valor = 0.0f;
		
		for(float v : apostas)
			valor += v;
		
		return valor/apostas.length;
	}
	
	/**
	 * Cada jogador insirirá o quanto quer apostar, e este método retornará a média
	 * @param apostas
	 * @return o valor da aposta de acordo com os votos dos jogadores
	 */
	public float obterValorAposta(List<Float> apostas) {
		float valor = 0.0f;
		
		for(float v : apostas)
			valor += v;
		
		return valor/apostas.size();
	}
	
	public static void main(String[] args) {
	
		Loteria.setLinguagem(Portugues.getInstance());
		
		Loteria loteria = Loteria.getInstance();
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Bem-vindos à loteria\n");
		String op = "";
		float grana = 0f;
		
		System.out.println("\n******************\n" + 
								   "REGRAS:\n" +
								   "   Neste jogo cada participante escolhe cinco palpites,\n" +
								   "depois de definidos os palpites, o computador retirará\n" +
								   "cinco cartas aleatorias do baralho e se caso você acertar\n" +
								   "pelo menos uma com seu palpite, você é vitorioso na rodada.\n\n" +
							       "MODO DE JOGO\n" + 
								   "1 - curto\n" + 
								   "2 - medio\n" + 
								   "3 - longo\n" + 
								   "0 - sair\n");
		
		A:
		do {
			op = scan.nextLine();
			switch(op) {
				case "1":
					grana = 0.50f;
					break;
				case "2":
					grana = 1.0f;
					break;
				case "3":
					grana = 1.5f;
					break;
				case "0":
					System.out.println("FIM");
					System.exit(0);
					break;
				default:
					System.out.println("Opicao invalida (insira 1, 2, 3 ou 0)...");
					continue A;
			}
			break A;
		} while(true);
		
		op = "";
		
		B:
		do {
			
			System.out.println("\nLista dos participantes:" + (loteria.getParticipantes().isEmpty() ? " vazio" : ""));
			
			for(Participante p : loteria.getParticipantes()) {
				System.out.println(p);
			}
			
			System.out.println("\n1 - adicionar participante\n" + 
							     "2 - comecar loteria\n" + 
								 "0 - sair\n");
			op = scan.nextLine();
			switch(op) {
				case "1":
					boolean continua = false;
					do {
						try {
							continua = false;
							System.out.println("\nInsira o nome do participante:\n");
							String nomeParticipante = scan.nextLine();
							loteria.addParticipante(new Participante(nomeParticipante, grana));
						} catch (NomeParticipanteInvalidoException
								| GranaParticipanteInvalidoException
								| ApostaVotoException e) {
							System.out.println(e.getMessage());
							continua = true;
						}
					} while(continua);
					continue B;
				case "2":
					if(loteria.getParticipantes().size() < 2) {
						System.out.println("insira pelo menos 2 participantes antes de iniciar uma nova partida");
						continue B;
					}
					break;
				case "0":
					System.out.println("FIM");
					System.exit(0);
					break;
				default:
					System.out.println("Opicao invalida (insira 1, 2 ou 0)...");
					continue B;
					
			}
			break B;
		} while(true);
		
		List<Participante> participantes = loteria.getParticipantes();
		
		System.out.println("Baralho:\n******************\n" + Rodada.getTextoBaralho() + "\n******************\n");
		
		for(Participante p : participantes) {
			System.out.println("\n" + p.getNome() + ", insira 5 palpites de cartas");
			Carta[] cartas = new Carta[5];
			for(int i = 0; i < 5; i++) {
				System.out.println("Palpite numero " + (i + 1));
				boolean sucesso = false;
				do {
					System.out.print("Insira o valor: "); 
					String valor = scan.nextLine();
					System.out.print(", insira o naipe: ");
					String naipe = scan.nextLine();
					try {
						Carta carta = new Carta(valor, naipe);
						cartas[i] = carta;
						sucesso = true;
					} catch (CartaInvalidaException e) {
						System.out.println(e.getMessage());
					}
				} while(!sucesso);
			}
			
			try {
				p.setPalpite(new Palpite(cartas));
			} catch (PalpiteInvalidoException | BotPalpiteException e) {
				System.out.println(e.getMessage());
			}
		}
		
		op = "";
		
		//aqui
		C:
		do {
			System.out.println("\n");
			for(int i = 0; i < participantes.size(); i++)
				System.out.println(i + " - " + participantes.get(i) + " (alterar palpite)");
			System.out.println("G - gerar sorteio");
			System.out.println("E - sair");
			op = scan.nextLine();
			switch(op) {
				case "g":
				case "G":
					try {
						System.out.println("Insira quanto vai valer a partida em reais (R$):");
						
						float aposta = 0;
						boolean sucesso = false;
						do {
							try {
								float a = Float.parseFloat(scan.nextLine().replace(',', '.'));
								aposta = a;
								sucesso = true;
							} catch(NullPointerException | NumberFormatException e) {
								System.out.println("insira um valor valido...");
							}
						} while(!sucesso);
						
						//para dar suspense
						boolean sucesso2 = false;
						System.out.println("\nAguardando enquando o computador seleciona as cartas...");
						do {
							try {
								Thread.sleep(5000);
								sucesso2 = true;
							} catch(InterruptedException e) {
								System.out.println("Problema");
							}
						} while(!sucesso2);
						
						Carta[] cartas = Rodada.gerarCincoCartasAleatorias();
						
						List<List<Participante>> result;
					
						result = loteria.jogarEObterResultado(cartas, aposta);
					
						List<Participante> vencedores = result.get(0);
						List<Participante> perdedores = result.get(1);
						List<Participante> falidos = result.get(2);
						
						System.out.println("Cartas escolhidas aleatoriamente do baralho:");
						System.out.println(Arrays.asList(cartas).toString());
						
						if(vencedores.size() > 0) {
							System.out.println("\nVencedores:");
							
							for (Participante vencedor : vencedores) {
								System.out.println(vencedor.getNome() + " acertou com " + vencedor.getPalpite().cartasCertas(cartas).toString());
							}
						}
						
						if(perdedores.size() > 0) {
							System.out.println("\nPerdedores:");
							
							for (Participante perdedor : perdedores) {
								System.out.println(perdedor.getNome() + " perdeu com " + perdedor.getPalpite().toString());
							}
						}
						
						if(falidos.size() > 0) {
							System.out.println("\nFalidos:");
							for (Participante falido : falidos) {
								System.out.println(falido.getNome() + " faliu com " + falido.getPalpite().toString());
							}
						}
						
						if(participantes.size() == 1)
							for (Participante vencedorGeral : participantes) {
								NumberFormat nf = NumberFormat.getCurrencyInstance();
								System.out.println("\n" + vencedorGeral.getNome() + " rapelou todo mundo, faturando " + nf.format(new Float(vencedorGeral.getGrana()).doubleValue()) + "\nParabens!");
								scan.close();
								System.exit(0);
							}
					} catch (GranaParticipanteInvalidoException
							| ApostaVotoException e1) {
						e1.printStackTrace();
					}
					continue C;
				case "e":
				case "E":
					System.out.println("FIM");
					System.exit(0);
			}
			
			try {
				int index = Integer.parseInt(op);
				Participante participante = loteria.getParticipante(index);
				Carta[] cartas = new Carta[5];
				for(int i = 0; i < 5; i++) {
					System.out.println("Palpite numero " + (i + 1));
					boolean sucesso = false;
					do {
						System.out.print("insira o valor: "); 
						String valor = scan.nextLine();
						System.out.print(", insira o naipe: ");
						String naipe = scan.nextLine();
						try {
							Carta carta = new Carta(valor, naipe);
							cartas[i] = carta;
							sucesso = true;
						} catch (CartaInvalidaException e) {
							System.out.println(e.getMessage());
						}
					} while(!sucesso);
				}
				
				Palpite p = new Palpite(cartas);
				
				try {
					participante.setPalpite(p);
				} catch (BotPalpiteException e) {
					e.printStackTrace();
				}
				
			} catch(NumberFormatException | ParticipanteInexistenteException | PalpiteInvalidoException e) {
				if(e instanceof NumberFormatException)
					System.out.println("Opcao invalida...");
				else
					System.out.println(e.getMessage());
				continue C;
			}
			
		} while(true);
		
	}
	
}
