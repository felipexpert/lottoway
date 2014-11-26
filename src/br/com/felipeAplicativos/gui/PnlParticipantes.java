package br.com.felipeAplicativos.gui;

import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.felipeAplicativos.gui.exceptions.MenosDeDoisJogadoresException;
import br.com.felipeAplicativos.gui.exceptions.PalpitePendenteException;
import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Bot;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.NomeParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.ParticipanteInexistenteException;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

class PnlParticipantes extends JPanel implements ActionListener, LinguaManipulavel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234772778439841970L;
	
	private JList<Participante> participantesList;
	private DefaultListModel<Participante> dlm;
	private JButton btnAddParticipante, btnRemoverParticipante, btnEscolherCartas, btnAdicionarDinheiro;
	private JButton btnBack, btnGo;
	private JButton btnAddBot;
	private JLabel lblInfoParticipantes;
	/**
	 * Create the panel.
	 */
	public PnlParticipantes() {
		JPanel pnlCentral = new JPanel(new GridLayout(2, 1));
		//JPanel pnlLista = new JPanel();
		
		dlm = new DefaultListModel<Participante>();
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlInfo = new JPanel();
		add(pnlInfo, BorderLayout.NORTH);
		
		lblInfoParticipantes = new JLabel();
		pnlInfo.add(lblInfoParticipantes);
		//pnlLista.setLayout(new BorderLayout(0, 0));
		
		participantesList = new JList<>(dlm); 
		participantesList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int index = participantesList.locationToIndex(e.getPoint());
					if(index != -1) {
						escolherCartas();
					}
				}
			}
		});
		//aqui (preciso tambem criar metodos para adicionar e remover itens da dlm)
		JScrollPane scroll = new JScrollPane(participantesList);		
		
		@SuppressWarnings("serial")
		JLabel imagem = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("imagens/cartas.jpg")).getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		
		pnlCentral.add(imagem);
		pnlCentral.add(scroll);
		
		add(pnlCentral, BorderLayout.CENTER);
		
		JPanel pnlAcoes = new JPanel();
		add(pnlAcoes, BorderLayout.SOUTH);
		
		btnAddParticipante = new JButton();
		btnAddParticipante.addActionListener(this);
		pnlAcoes.setLayout(new GridLayout(0, 5, 0, 0));
		pnlAcoes.add(btnAddParticipante);
		
		btnRemoverParticipante = new JButton();
		btnRemoverParticipante.addActionListener(this);
		
		btnEscolherCartas = new JButton();
		btnEscolherCartas.addActionListener(this);
		pnlAcoes.add(btnEscolherCartas);
		
		btnAdicionarDinheiro = new JButton();
		btnAdicionarDinheiro.addActionListener(this);
		pnlAcoes.add(btnAdicionarDinheiro);
		pnlAcoes.add(btnRemoverParticipante);
		
		btnAddBot = new JButton();
		btnAddBot.addActionListener(this);
		pnlAcoes.add(btnAddBot);
		
		btnBack = new JButton();
		btnBack.addActionListener(this);
		add(btnBack, BorderLayout.WEST);
		
		btnGo = new JButton();
		btnGo.addActionListener(this);
		add(btnGo, BorderLayout.EAST);
		
		updateText();
	}
	
	void updateList() {
		dlm.clear();
		for(Participante p : Loteria.getInstance().getParticipantes()) {
			dlm.addElement(p);
		}
	}
	
	private void criarParticipante() {
		String nome = Mensagens.inputMessage00(this);
		if(nome != null) {
				String grana = Mensagens.inputMessage01(this);
				if(grana != null) {
				Participante p = null;
				try {
					Loteria.getInstance().addParticipante(p = new Participante(nome, grana));
					updateList();
					FrmLoteria.getInstance().adicionarPlayer(Loteria.getInstance().getParticipanteIndex(p), p);
					//aqui
				} catch (NomeParticipanteInvalidoException
						| GranaParticipanteInvalidoException
						| ApostaVotoException e) {
					Mensagens.falhaMessage(this, e.getMessage());
				}
			}
		}
	}
	
	private void removerParticipante() {
		if(participantesList.getSelectedIndex() != -1) {
			try {
				int index = participantesList.getSelectedIndex();
				Loteria.getInstance().removeParticipante(index);
				FrmLoteria.getInstance().removerPlayer(index);
				updateList();
			} catch (ParticipanteInexistenteException e) {
				Mensagens.falhaMessage(this, e.getMessage());
			}
		} else {
			Mensagens.nenhumItemMessage(this);
		}
	}
	
	private void adicionarDinheiro() {
		if(participantesList.getSelectedIndex() != -1) {
			String maisGrana = Mensagens.inputMessage02(this);
			Participante p = null;
			try {
				p = Loteria.getInstance().getParticipante(participantesList.getSelectedIndex());
				p.PorMaisGrana(maisGrana);
				updateList();
				FrmLoteria.getInstance().updatePlayerInfo(Loteria.getInstance().getParticipanteIndex(p), FrmLoteria.UPDATE_GRANA);
			} catch (GranaParticipanteInvalidoException
					| ParticipanteInexistenteException e) {
				Mensagens.falhaMessage(this, e.getMessage());
			}
		} else {
			Mensagens.nenhumItemMessage(this);
		}
	}
	
	private void escolherCartas() {
		if(participantesList.getSelectedIndex() != -1) {
			Participante p = participantesList.getSelectedValue();
			FrmLoteria.getInstance().setarCartasPanel(p);
		} else {
			Mensagens.nenhumItemMessage(this);
		}
	}
	
	private void go() {
		try {
			if(Loteria.getInstance().getQtdParticipantes() < 2)
				throw new MenosDeDoisJogadoresException();
			
			if(Loteria.getInstance().palpitesPendentes())
				throw new PalpitePendenteException();
			
			FrmLoteria.getInstance().apostar(); //!
		} catch(MenosDeDoisJogadoresException | PalpitePendenteException e) {
			Mensagens.falhaMessage(this, e.getMessage());
		}
	}

	private void criarBot() {
		String grana = Mensagens.inputMessage03(this);
		if(grana != null) {
			Bot bot;
			try {
				Loteria.getInstance().addParticipante(bot = new Bot(grana));
				updateList();
				int index = Loteria.getInstance().getParticipanteIndex(bot);
				FrmLoteria.getInstance().adicionarPlayer(index, bot);
				FrmLoteria.getInstance().updatePlayerInfo(index, FrmLoteria.UPDATE_CARTAS);
			} catch (NomeParticipanteInvalidoException
					| GranaParticipanteInvalidoException
					| ApostaVotoException e) {
				Mensagens.falhaMessage(this, e.getMessage());
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAddParticipante) {
			criarParticipante();
		} else if(e.getSource() == btnRemoverParticipante) {
			removerParticipante(); 
		} else if(e.getSource() == btnAdicionarDinheiro) {
			adicionarDinheiro();
		} else if(e.getSource() == btnBack) {
			FrmLoteria.getInstance().voltarParaModoJogo();
		} else if(e.getSource() == btnGo) {
			go();
		} else if(e.getSource() == btnAddBot) {
			criarBot();
		} else {
			escolherCartas();
		}
		
	}

	@Override
	public void updateText() {
		lblInfoParticipantes.setText(word(Text.INFO_PARTICIPANTES));
		btnAddBot.setText(word(Text.ADD_BOT));
		btnAddParticipante.setText(word(Text.ADICIONAR_PARTICIPANTE));
		btnAdicionarDinheiro.setText(word(Text.ADICIONAR_DINHEIRO));
		btnBack.setText("<" + word(Text.BACK));
		btnEscolherCartas.setText(word(Text.ESCOLHER_CARTAS));
		btnGo.setText(word(Text.GO) + ">");
		btnRemoverParticipante.setText(word(Text.REMOVER_PARTICIPANTE));
	}

}
