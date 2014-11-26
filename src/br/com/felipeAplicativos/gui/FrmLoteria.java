package br.com.felipeAplicativos.gui;

import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import br.com.felipeAplicativos.logica.linguagem.Text;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import br.com.felipeAplicativos.gui.PnlEscolha;
import br.com.felipeAplicativos.gui.interfaces.LinguaManipulavel;
import br.com.felipeAplicativos.logica.linguagem.Linguagem;
import br.com.felipeAplicativos.logica.linguagem.idiomas.English;
import br.com.felipeAplicativos.logica.linguagem.idiomas.Portugues;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.som.SF3_03;
import br.com.felipeAplicativos.som.SF3_04;
import br.com.felipeAplicativos.som.SF3_06;
import br.com.felipeAplicativos.som.SFSuperMario;

public class FrmLoteria extends JFrame implements LinguaManipulavel{

	/**
	 * 
	 */	
	private static final long serialVersionUID = 7699447060077859758L;
	
	private JLabel lblLinguagem;
	
	private static FrmLoteria INSTANCE;
	
	private JPanel contentPane;
	
	private JButton btnConfirmarLinguagem;
	
	private PnlModoDeJogo modoJogo;
	
	private PnlEscolha escolha;
	
	private PnlParticipantes participantes;
	
	private PnlApostas apostar;
	
	private JComboBox<Linguagem> cbLinguagem;
	
	static final int UPDATE_CARTAS = 0;
	static final int UPDATE_GRANA = 1;

	/**
	 * Create the frame.
	 */
	
	static {
		INSTANCE = new FrmLoteria();
	}
	
	private FrmLoteria() {
		
		escolha = new PnlEscolha();
		participantes = new PnlParticipantes();
		modoJogo = new PnlModoDeJogo();
		apostar = new PnlApostas();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(Loteria.getInstance().getQtdParticipantes() >= 2) {
					if(Mensagens.confirmMessage(FrmLoteria.getInstance()) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} else {
					System.exit(0);
				}
			}
		});
		
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		add(BorderLayout.CENTER, modoJogo);
		
		JPanel pnlIdioma = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		DefaultComboBoxModel<Linguagem> dcmLinguagem = new DefaultComboBoxModel<>(new Linguagem[] {English.getInstance(), Portugues.getInstance()});
		
		cbLinguagem = new JComboBox<Linguagem>(dcmLinguagem);
		
		lblLinguagem = new JLabel();
		
		btnConfirmarLinguagem = new JButton();
		btnConfirmarLinguagem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Linguagem lingua = (Linguagem) cbLinguagem.getSelectedItem();
				
				if(!lingua.equals(Loteria.getLinguagem())) {
					Loteria.setLinguagem(lingua);
					updateLingua();
				}
			}
		});
		
		pnlIdioma.add(lblLinguagem);
		pnlIdioma.add(cbLinguagem);
		pnlIdioma.add(btnConfirmarLinguagem);
		
		add(BorderLayout.SOUTH, pnlIdioma);
		
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("cartas/capa" + Math.round(Math.random()) + ".png"));
		
		setIconImage(image);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		updateText();
	}
	
	private void updateLingua() {
		List<LinguaManipulavel> lista = new ArrayList<>();
		
		lista.add(this);
		lista.add(modoJogo);
		lista.add(participantes);
		lista.add(escolha);
		lista.add(apostar);
		
		for(LinguaManipulavel item : lista) {
			item.updateText();
		}
	}
	
	@Override
	public void updateText() {
		setTitle(word(Text.LOTERIA_MANEIRA));
		lblLinguagem.setText(word(Text.LINGUAGEM) + ":");
		btnConfirmarLinguagem.setText("Ok");
	}
	
	public static FrmLoteria getInstance() {
		return INSTANCE;
	}
	
	void adicionarPlayer(int index, Participante participante) {
		apostar.adicionarPlayer(index, participante);
	}
	
	void removerPlayer(int index) {
		apostar.removerPlayer(index);
	}
	
	void apostar() {
		contentPane.remove(participantes);
		FrmLoteria.getInstance().updateModalidate();
		contentPane.add(BorderLayout.CENTER, apostar);
		apostar.estaFocado();
		contentPane.updateUI();
		
		SF3_04.stop();
		SFSuperMario.play();
	}
	
	void updateGranaDeTodos() {
		apostar.updateGranaDeTodos();
	}
	
	void voltarDeApostarParaParticipantes() {
		contentPane.remove(apostar);
		participantes.updateList();
		contentPane.add(BorderLayout.CENTER, participantes);
		apostar.permitirAlterarApostas();
		contentPane.updateUI();
		
		apostar.naoEstaFocado();
		
		SFSuperMario.stop();
		SF3_04.play();
	}
	
	void updateModalidate() {
		apostar.updateModalidate();
	}
	
	void updatePlayerInfo(int index, final int op) {
		apostar.updatePlayer(index, op);
	}
	
	void setarParticipantes() {
		contentPane.remove(modoJogo);
		contentPane.add(BorderLayout.CENTER, participantes);
		contentPane.updateUI();
		
		SF3_03.stop();
		SF3_04.play();
	}
	
	void voltarParaModoJogo() {
		contentPane.remove(participantes);
		contentPane.add(BorderLayout.CENTER, modoJogo);
		contentPane.updateUI();
		
		SF3_04.stop();
		SF3_03.play();
	}
	
	void setarCartasPanel(Participante p) {
		contentPane.remove(participantes);
		contentPane.add(BorderLayout.CENTER, escolha);
		escolha.setParticipante(p);
		contentPane.updateUI();
		
		SF3_04.stop();
		SF3_06.play();
	}
	
	void atualizaApostaTela() {
		apostar.updateUI();
	}
	
	void voltarDaEscolhaDeCartasParaParticipantes() {
		contentPane.remove(escolha);
		contentPane.add(BorderLayout.CENTER, participantes);
		participantes.updateList();
		contentPane.updateUI();
		
		SF3_06.stop();
		SF3_04.play();
	}
}
