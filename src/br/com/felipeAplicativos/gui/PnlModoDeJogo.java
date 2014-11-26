package br.com.felipeAplicativos.gui;
import static br.com.felipeAplicativos.logica.loteria.Loteria.word;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;

import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.Principal;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Modalidade;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class PnlModoDeJogo extends JPanel implements ActionListener, LinguaManipulavel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2288205968534860761L;
	/**
	 * Create the panel.
	 */
	private JLabel lblInformacao;
	private JButton btnCurto, btnMuitoCurto, 
					btnMeioCurto, btnRegular, 
					btnDuradouro, btnMuitoDuradouro,
					btnRegras;
	
	public PnlModoDeJogo() {
		setLayout(new BorderLayout(5, 5));
		//setBorder(new EmptyBorder(40, 40, 40, 40));
		
		JPanel pnlInfo = new JPanel();
		add(pnlInfo, BorderLayout.NORTH);
		pnlInfo.setLayout(new FlowLayout());
		
		lblInformacao = new JLabel();
		pnlInfo.add(lblInformacao);
		
		GridLayout layout = new GridLayout(1, 3, 4, 0);
		
		JPanel pnlModos1 = new JPanel(layout);
		JPanel pnlModos2 = new JPanel(layout);
		JPanel pnlCentral = new JPanel(new BorderLayout());
		
		pnlCentral.add(BorderLayout.NORTH, pnlModos1);
		pnlCentral.add(BorderLayout.SOUTH, pnlModos2);
		
		btnMuitoCurto = new JButton();
		btnMuitoCurto.addActionListener(this);
		pnlModos1.add(btnMuitoCurto);
		
		btnCurto = new JButton();
		btnCurto.addActionListener(this);
		pnlModos1.add(btnCurto);
		
		btnMeioCurto = new JButton();
		btnMeioCurto.addActionListener(this);
		pnlModos1.add(btnMeioCurto);
		
		btnRegular = new JButton();
		btnRegular.addActionListener(this);
		pnlModos2.add(btnRegular);
		
		btnDuradouro = new JButton();
		btnDuradouro.addActionListener(this);
		pnlModos2.add(btnDuradouro);
		
		btnMuitoDuradouro = new JButton();
		btnMuitoDuradouro.addActionListener(this);
		pnlModos2.add(btnMuitoDuradouro);
		
		btnRegras = new JButton();
		btnRegras.addActionListener(this);
		pnlModos2.add(btnRegras);
		
		@SuppressWarnings("serial")
		JLabel imagem = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawImage(new ImageIcon(getClass().getResource("imagens/coqueiro2.jpg")).getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		
		pnlCentral.add(BorderLayout.CENTER, imagem);
		
		add(pnlCentral);
		
		updateText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modalidade modalidade = null;
		if(e.getSource() == btnMuitoCurto) {
			modalidade = Modalidade.MUITO_CURTO;
		} else if(e.getSource() == btnCurto) {
			modalidade = Modalidade.CURTO;
		} else if(e.getSource() == btnMeioCurto) {
			modalidade = Modalidade.MEIO_CURTO;
		} else if(e.getSource() == btnRegular) {
			modalidade = Modalidade.REGULAR;
		} else if(e.getSource() == btnDuradouro) {
			modalidade = Modalidade.DURADOURO;
		} else if(e.getSource() == btnMuitoDuradouro){
			modalidade = Modalidade.MUITO_DURADOURO;
		} else {
			Principal.bemVindoMessage(null);
			return;
		}
		
		try {
			Loteria.getInstance().setModalidade(modalidade);
			FrmLoteria.getInstance().setarParticipantes();
		} catch(ApostaVotoException exc) {
			Mensagens.falhaMessage(this, exc.getMessage());
		}
	}

	@Override
	public void updateText() {
		lblInformacao.setText(word(Text.INFO_MODO_JOGO));
		btnCurto.setText(word(Text.CURTO));
		btnDuradouro.setText(word(Text.DURADOURO));
		btnMeioCurto.setText(word(Text.MEIO_CURTO));
		btnMuitoCurto.setText(word(Text.MUITO_CURTO));
		btnMuitoDuradouro.setText(word(Text.MUITO_DURADOURO));
		btnRegular.setText(word(Text.REGULAR));
		btnRegras.setText(word(Text.COMO_JOGAR));
	}

}
