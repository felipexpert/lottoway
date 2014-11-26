package br.com.felipeAplicativos.gui;

import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Bot;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.CodImagemIndefinidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.ValorModalidadeInexistenteException;
import br.com.felipeAplicativos.som.SFCoinEffect;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PnlInfoPlayer extends JPanel implements ActionListener, LinguaManipulavel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1269248056700637412L;
	
	private long palpitesUpdates = 0L;
	private long granaUpdates = 0L;
	private long apostaVotoUpdates = 0l;
	private Participante participante;
	private JLabel lblGrana, lblNome, lblQqa;
	private JComboBox<String> cbApostasDisponiveis;
	private final DefaultComboBoxModel<String> dcm;
	
	private JButton btnConfirmar;
	
	private ImageIcon[] cartas;

	/**
	 * Create the panel.
	 */
	public PnlInfoPlayer(Participante participante) {
		this.participante = participante;
		
		dcm = new DefaultComboBoxModel<>();
		
		cartas = new ImageIcon[]{new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon()};
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {180, 480, 100, 50, 0};
		gridBagLayout.rowHeights = new int[] {130, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblNome = new JLabel();
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.fill = GridBagConstraints.CENTER;
		gbc_lblNome.insets = new Insets(0, 0, 0, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		add(lblNome, gbc_lblNome);
		
		JPanel cincoCartas = new JPanel(new GridLayout(1, 5, 5, 5));
		
		for(final ImageIcon image : cartas) {
			
			JLabel c = new JLabel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);				
				}
				
			};
			cincoCartas.add(c);
		}
		GridBagConstraints gbc_cincoCartas = new GridBagConstraints();
		gbc_cincoCartas.fill = GridBagConstraints.BOTH;
		gbc_cincoCartas.insets = new Insets(0, 0, 0, 20);
		gbc_cincoCartas.gridx = 1;
		gbc_cincoCartas.gridy = 0;
		add(cincoCartas, gbc_cincoCartas);
		
		lblGrana = new JLabel();
		updateGrana();
		GridBagConstraints gbcGrana = new GridBagConstraints();
		gbcGrana.fill = GridBagConstraints.CENTER;
		gbcGrana.insets = new Insets(0, 0, 0, 5);
		gbcGrana.gridx = 2;
		gbcGrana.gridy = 0;
		add(lblGrana, gbcGrana);
		
		lblQqa = new JLabel();
		cbApostasDisponiveis = new JComboBox<>(dcm);
		cbApostasDisponiveis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				avaliarAposta();
			}
		});
		
		JPanel aposta = new JPanel(new FlowLayout(FlowLayout.LEFT));
		aposta.add(lblQqa);
		aposta.add(cbApostasDisponiveis);
		btnConfirmar = new JButton();
		btnConfirmar.addActionListener(this);
		aposta.add(btnConfirmar);
		
		GridBagConstraints gbcAD = new GridBagConstraints();
		gbcAD.fill = GridBagConstraints.CENTER;
		gbcAD.insets = new Insets(0, 20, 0, 0);
		gbcAD.gridx = 3;
		gbcAD.gridy = 0;
		add(aposta, gbcAD);
		
		if(participante instanceof Bot)
			proibirAlterarAposta();
		
		updateText();
	}
	
	private void avaliarAposta() {
		int index = participante.ultimoIndexPossivel();
		if(cbApostasDisponiveis.getSelectedIndex() > index) {
			Mensagens.infoMessage04(this);
			cbApostasDisponiveis.setSelectedIndex(index);
		}
		
	}
	
	String getNomeParticipante() {
		return participante.getNome();
	}
	
	boolean updateCartas() {
		boolean retorno = false;
		if(palpitesUpdates < participante.getTotalPalpites()) {
			updateCartasContinua();
			palpitesUpdates = participante.getTotalPalpites();
			retorno = true;
		}
		return retorno;
	}
	
	private void updateCartasContinua() {
		for(int i = 0; i < 5; i++) {
			try {
				cartas[i].setImage(new ImageIcon(getClass().getResource("cartas/" + participante.getPalpite().getCartas()[i].getCodImagem() + ".png")).getImage());
			} catch (CodImagemIndefinidoException e) {
				e.printStackTrace();
			}
		}
		
		updateUI();
	}
	
	boolean updateGrana() {
		boolean retorno = false;
		if(granaUpdates < participante.getGranaUpdates()) {
			updateGranaContinua();
			granaUpdates = participante.getGranaUpdates();
			retorno = true;
		}
		return retorno;
	}
	
	boolean updateApostaVoto() {
		boolean retorno = false;
		if(apostaVotoUpdates < participante.getApostaVotoUpdates()) {
			updateApostaVotoContinua();
			apostaVotoUpdates = participante.getApostaVotoUpdates();
			retorno = true;
		}
		return retorno;
	}
	
	private void updateApostaVotoContinua() {
		
		String[] valores = Loteria.getInstance().getModalidade().getTextValores();
		
		for(int i = 0; i < valores.length; i++) {
			if(valores[i].equals(participante.getApostaVotoEmDinheiro())) {
				cbApostasDisponiveis.setSelectedIndex(i);
				return;
			}
		}
		assert false;
	}
	
	private void updateGranaContinua() {
		lblGrana.setText(word(Text.TOTAL) + ": " + participante.getGranaText());
	}
	
	void updateModalidade() {
		dcm.removeAllElements();
		for(String valor : Loteria.getInstance().getModalidade().getTextValores()) {
			dcm.addElement(valor);
		}
	}
	
	void proibirAlterarAposta() {
		cbApostasDisponiveis.setEnabled(false);
		btnConfirmar.setEnabled(false);
	}
	
	void permitirAlterarAposta() {
		if(!(participante instanceof Bot)) {
			cbApostasDisponiveis.setEnabled(true);
			btnConfirmar.setEnabled(true);
		}
	}
	
	boolean isConfirmado() {
		if(!btnConfirmar.isEnabled())
			return true;
		else 
			return false;
	}
	
	float getApostaOpicao() {
		try {
			return Loteria.getInstance().getModalidade().floatValue((String)cbApostasDisponiveis.getSelectedItem());
		} catch (ValorModalidadeInexistenteException e) {
			e.printStackTrace();
		}
		assert false;
		
		return 0.0f;
	}

	@Override
	public void updateText() {
		lblNome.setText(word(Text.JOGADOR) + ": " + participante.getNome());
		lblQqa.setText(word(Text.QUANTO_QUER_APOSTAR) + "?");
		btnConfirmar.setText(word(Text.CONFIRMAR));
		updateGrana();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			SFCoinEffect.play();
			participante.setApostaVoto((String)cbApostasDisponiveis.getSelectedItem());
			proibirAlterarAposta();
		} catch (ApostaVotoException exc) {
			Mensagens.falhaMessage(this, exc.getMessage());
		}		
	}
}
