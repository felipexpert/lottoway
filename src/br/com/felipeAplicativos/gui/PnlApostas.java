package br.com.felipeAplicativos.gui;

import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import static br.com.felipeAplicativos.logica.linguagem.Text.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.som.SF2_05;
import br.com.felipeAplicativos.som.SF2_09;
import br.com.felipeAplicativos.som.SFSuperMario;

class PnlApostas extends JPanel implements ActionListener, LinguaManipulavel{

	/**
	 * 
	 */
	private JPanel pnlTudo;
	private JPanel pnlApostaInfo;
	private GridLayout glStatus;
	private float valorAposta;
	private boolean apostando;
	private boolean focado;
	
	private static final long serialVersionUID = -6698735742280493418L;
	private JPanel pnlPlayerInfo;
	private JButton btnApostar, btnBack, btnConfirmar;
	private List<PnlInfoPlayer> players;
	private JLabel lblInfo, lblResultado;

	/**
	 * Create the panel.
	 */
	public PnlApostas() {
		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(new FlowLayout());
		
		setLayout(new BorderLayout());
		lblInfo = new JLabel();
		setInfo();
		pnlInfo.add(lblInfo);
		add(BorderLayout.NORTH, pnlInfo);
		
		pnlPlayerInfo = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		pnlPlayerInfo.setLayout(gbl);
		
		JScrollPane scroll = new JScrollPane(pnlPlayerInfo);
		//aqui
		glStatus = new GridLayout(1, 0, 0, 10);
		pnlTudo = new JPanel(glStatus);
		
		pnlTudo.add(scroll);
		
		add(BorderLayout.CENTER, pnlTudo);
		
		btnApostar = new JButton();
		btnApostar.addActionListener(this);
		
		btnConfirmar = new JButton();
		btnConfirmar.addActionListener(this);
		
		
		add(BorderLayout.SOUTH, btnApostar);
		
		btnBack = new JButton();
		btnBack.addActionListener(this);
		
		add(BorderLayout.WEST, btnBack);
		
		players = new ArrayList<>();
		
		updateText();
		
		ficaVerificando();
		
		//updateUI();
	}
	
	//este metodo cria uma temporizador, e depois fica esperando;
	private void contador() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				String valor = nf.format(valorAposta);
				for(int i = 3; i > -1; i--) {
					lblInfo.setText(word(VALOR_DA_APOSTA) + " " + valor + ". " + 
(word(SELECIONANDO_CARTAS_ALEATORIAMENTE)) + " " +  (i > 0 ? Integer.toString(i) : "(" + word(Text.PRONTO) + ")"));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				SF2_05.stop();
				SF2_09.play();
				
				apostarContinua();
			}
		});
		
		thread.start();
	}
	
	void estaFocado() {
		focado = true;
	}
	
	void naoEstaFocado() {
		focado = false;
	}
	
	private void verificaNecessidadeUpdate() {
		boolean houveAlteracoes = false;
		List<String> output = new ArrayList<>();
		for(PnlInfoPlayer player : players) {
			player.updateApostaVoto();
			if(!houveAlteracoes) {
				boolean alterouPalpite = false;
				if((alterouPalpite = player.updateCartas()) | player.updateGrana()) {
					houveAlteracoes = true;
					if(alterouPalpite) {
						output.add(player.getNomeParticipante());
					}
				}
			} else {
				boolean alterouPalpite = false;
				alterouPalpite = player.updateCartas();
				player.updateGrana();
				if(alterouPalpite) {
					output.add(player.getNomeParticipante());
				}
			}
		}
		
		if(houveAlteracoes) {
			updateUI();
			
			StringBuilder mensagem = new StringBuilder();
			for(String nome : output) {
				mensagem.append(nome).append(" ").append(word(Text.ALTEROU_O_PALPITE)).append("\n");
			}
			Mensagens.infoMessage05(this, mensagem.toString());
		}
		
		
	}
	
	private void apostar() {
		if(podeApostar()) {
			verificaNecessidadeUpdate();
			
			apostando = true;
			
			SFSuperMario.stop();
			SF2_05.play();
			//proibirAlterarApostas();
			btnBack.setEnabled(false);
			btnApostar.setVisible(false);
			
			valorAposta = Loteria.getInstance().obterValorAposta(apostasVotos());
			
			contador();
		}
	}
	
	private void apostarContinua() {
		
		glStatus.setRows(2);
		pnlApostaInfo = new JPanel(new BorderLayout());
		PnlResultado r = new PnlResultado(valorAposta);
		valorAposta = 0;
		JScrollPane scroll = new JScrollPane(r);
		pnlApostaInfo.add(BorderLayout.CENTER, scroll);
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		lblResultado = new JLabel(word(RESULTADO));
		p.add(lblResultado);
		pnlApostaInfo.add(BorderLayout.NORTH, p);
		pnlApostaInfo.add(BorderLayout.SOUTH, btnConfirmar);
		pnlTudo.add(pnlApostaInfo);
		
		updateUI();
		
		apostando = false;
		
	}
	
	private void ficaVerificando() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						if(focado)
							verificaNecessidadeUpdate();
						do {
							Thread.sleep(2000);
						} while(apostando);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private void setInfo() {
		lblInfo.setText(word(INFO_APOSTAS));
	}
	
	private void confirmar() {
		
		pnlTudo.remove(pnlApostaInfo);
		glStatus.setRows(1);
		pnlApostaInfo = null;
		btnBack.setEnabled(true);
		btnApostar.setVisible(true);
		
		setInfo();
		
		permitirAlterarApostas();
		
		
		
		if(Loteria.getInstance().getAlguemVenceu()) {
			SFSuperMario.play();
			FrmLoteria.getInstance().voltarDeApostarParaParticipantes();
			Mensagens.infoMessage03(this);
		} else {
			SFSuperMario.play();
		}
		
		
	}
	
	private List<Float> apostasVotos() {
		List<Float> lista = new ArrayList<>();
		for(PnlInfoPlayer player : players) {
			lista.add(player.getApostaOpicao());
		}
		
		return lista;
	}
	
	void updatePlayer(int index, int op) {
		switch(op) {
			case FrmLoteria.UPDATE_CARTAS:
				players.get(index).updateCartas();
				break;
			case FrmLoteria.UPDATE_GRANA:
				players.get(index).updateGrana();
				break;
			default:
				assert false : "updatePlayer opicao inserida invalida";
		}
	}
	
	void adicionarPlayer(int index, Participante participante) {
		PnlInfoPlayer p = new PnlInfoPlayer(participante);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.CENTER;
		gbc.insets = new Insets(30, 0, 0, 0);
		gbc.gridx = 0;
		//gbc.gridy = index;
		
		players.add(index, p);
		
		pnlPlayerInfo.add(p, gbc);
	}
	
	void updateGranaDeTodos() {
		for(PnlInfoPlayer player : players) {
			player.updateGrana();
		}
	}
	
	void removerPlayer(int index) {
		pnlPlayerInfo.remove(players.get(index));
		players.remove(index);
	}
	
	void updateModalidate() {
		for(PnlInfoPlayer player : players) {
			player.updateModalidade();
		}
	}
	
	private boolean podeApostar() {
		boolean pode = true;
		List<String> nomes = new ArrayList<>();
		
		for(PnlInfoPlayer player : players) {
			if(!player.isConfirmado()) {
				pode = false;
				nomes.add(player.getNomeParticipante());
			}
		}
		StringBuilder sb = new StringBuilder();
		for(String nome : nomes) {
			sb.append(nome).append(" ").append(word(Text.NAO_ESTA_CONFIRMADO)).append("\n");
		}
		if(!pode)
			Mensagens.infoMessage05(this, sb.toString());
			
		return pode;
	}
	
	@SuppressWarnings("unused")
	private void proibirAlterarApostas() {
		for(PnlInfoPlayer player: players) {
			player.proibirAlterarAposta();
		}
	}
	
	void permitirAlterarApostas() {
		for(PnlInfoPlayer player: players) {
			player.permitirAlterarAposta();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnApostar) {
			apostar();
		} else if(e.getSource() == btnConfirmar) {
			confirmar();
		} else { // btnBack
			FrmLoteria.getInstance().voltarDeApostarParaParticipantes();
		}
		
	}

	@Override
	public void updateText() {
		setInfo();
		btnApostar.setText(word(APOSTAR) + "!");
		btnConfirmar.setText(word(CONFIRMAR));
		btnBack.setText("<" + word(BACK));
		
		for(PnlInfoPlayer player : players) {
			player.updateText();
		}
	}
	
}
