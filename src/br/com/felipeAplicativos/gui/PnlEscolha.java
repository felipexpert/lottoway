package br.com.felipeAplicativos.gui;

import static br.com.felipeAplicativos.logica.loteria.Loteria.word;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Palpite;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.logica.loteria.Rodada;
import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.BotPalpiteException;
import br.com.felipeAplicativos.logica.loteria.exceptions.PalpiteInvalidoException;
import br.com.felipeAplicativos.som.SFCoinEffect;

class PnlEscolha extends JPanel implements MouseListener, ActionListener, LinguaManipulavel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8186726232357548896L;
	
	private Participante participante;
	
	private JLabel lblParticipanteInfo;
	
	private JButton btnConfirmar, btnRefazer;
	
	
	private JLabel[] baralhoLabel = new JLabel[52];
	
	private JLabel[] escolhaLabel = new JLabel[5];	
	private Carta[] cartasEscolhidas = new Carta[5];
	
	private ImageIcon[] novaImage = new ImageIcon[5];
	
	/**
	 * Create the panel.
	 */
	public PnlEscolha() {
		
		carregaBaralhos();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		JPanel pnlParticipante = new JPanel();
		
		pnlParticipante.setLayout(new FlowLayout());
		
		pnlParticipante.add(lblParticipanteInfo = new JLabel());
		
		add(BorderLayout.NORTH, pnlParticipante);
		
		JPanel pnlEscolha = new JPanel();
		
		pnlEscolha.setLayout(new GridLayout(5, 13, 5, 5));
		
		
		for (JLabel carta : baralhoLabel) {
			pnlEscolha.add(carta);
		}
		pnlEscolha.add(new JLabel());
		pnlEscolha.add(new JLabel());
		pnlEscolha.add(new JLabel());
		pnlEscolha.add(new JLabel());
		
		for (JLabel escolha : escolhaLabel) {
			pnlEscolha.add(escolha);
		}
		
		btnConfirmar = new JButton();
		removeBtnConfirmar();
		btnConfirmar.addActionListener(this);
		btnRefazer = new JButton();
		removeBtnRefazer();
		btnRefazer.addActionListener(this);
		
		pnlEscolha.add(new JLabel());
		pnlEscolha.add(new JLabel());
		pnlEscolha.add(btnRefazer);
		pnlEscolha.add(btnConfirmar);
		
		add(BorderLayout.CENTER, pnlEscolha);
		
		updateText();
	}
	
	void setParticipante(Participante p) {
		this.participante = p;
		this.lblParticipanteInfo.setText(word(Text.PARTICIPANTE) + " " + p.getNome());
	}
	
	private void addBtnConfirmar() {
		btnConfirmar.setVisible(true);
	}
	
	private void removeBtnConfirmar() {
		btnConfirmar.setVisible(false);
	}
	
	private void addBtnRefazer() {
		btnRefazer.setVisible(true);
	}
	
	private void removeBtnRefazer() {
		btnRefazer.setVisible(false);
	}
	
	@SuppressWarnings("serial")
	private void carregaBaralhos() {
		
		//baralhoCarta = Rodada.getBaralho();
		
		for (int i = 0; i < 52; i++) {
			final ImageIcon image = new ImageIcon(getClass().getResource("cartas/" + i + ".png"));
			baralhoLabel[i] = new JLabel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			};
			baralhoLabel[i].setToolTipText(Rodada.getBaralho()[i].toString());
			baralhoLabel[i].addMouseListener(this);
		}
		
		
		for(int i = 0; i < 5; i++) {
			novaImage[i] = new ImageIcon(getClass().getResource("cartas/capa" + (Math.round(Math.random())) + ".png"));
			final ImageIcon image = novaImage[i];
			escolhaLabel[i] = new JLabel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			};
			escolhaLabel[i].setToolTipText("?");
		}		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	private void evento(MouseEvent e) {
		int index = 0;
		for (int i = 0; i < 52; i++) {
			if(e.getSource() == baralhoLabel[i]) {
				index = i;
				break;
			}
				
		}
		
		for(int i = 0; i < 5; i++) {
			if(cartasEscolhidas[i] == null) {
				
					for(int i2 = 0; i2 < i; i2++) {
						if(cartasEscolhidas[i2] == Rodada.getBaralho()[index]) {
							Mensagens.infoMessage00(this);
							return;
						}
					}
					atualizaImage(index, i);
					SFCoinEffect.play();
					
					if(i == 0) 
						addBtnRefazer();
					else if(i == 4)
						addBtnConfirmar();
					
					return;
				
			}
		}
		
		Mensagens.infoMessage01(this);
	}
	
	private void atualizaImage(int indexBaralho, int indexEscolha) {
		cartasEscolhidas[indexEscolha] = Rodada.getBaralho()[indexBaralho];
		ImageIcon image = new ImageIcon(getClass().getResource("cartas/" + indexBaralho + ".png"));
		novaImage[indexEscolha].setImage(image.getImage());
		escolhaLabel[indexEscolha].setToolTipText(cartasEscolhidas[indexEscolha].toString());
		this.updateUI();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		evento(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnConfirmar) {
			confirmar();
			
		} else {
			refazer();
		}
		
	}
	
	private void confirmar() {
		try {
			participante.setPalpite(new Palpite(cartasEscolhidas));
			FrmLoteria.getInstance().voltarDaEscolhaDeCartasParaParticipantes();
			FrmLoteria.getInstance().updatePlayerInfo(Loteria.getInstance().getParticipanteIndex(participante), FrmLoteria.UPDATE_CARTAS);
			refazer();
		} catch (PalpiteInvalidoException e) {
			Mensagens.falhaMessage(this, e.getMessage());
		} catch (BotPalpiteException e) {
			Mensagens.falhaMessage(this, e.getMessage());
			FrmLoteria.getInstance().voltarDaEscolhaDeCartasParaParticipantes();
		}
	}
	
	private void refazer() {
		cartasEscolhidas = new Carta[5];
		for (int i = 0; i < cartasEscolhidas.length; i++) {
			ImageIcon image = new ImageIcon(getClass().getResource("cartas/" + "capa" + Math.round(Math.random()) + ".png"));
			novaImage[i].setImage(image.getImage());
			escolhaLabel[i].setToolTipText("?");
			this.updateUI();
		}
		removeBtnConfirmar();
		removeBtnRefazer();
	}

	@Override
	public void updateText() {
		btnConfirmar.setText(word(Text.CONFIRMAR));
		btnRefazer.setText(word(Text.REFAZER));
		this.lblParticipanteInfo.setText(word(Text.PARTICIPANTE) + " " + (participante != null ? participante.getNome() : ""));
	}

}
