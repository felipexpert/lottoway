package br.com.felipeAplicativos.gui;
import static br.com.felipeAplicativos.logica.loteria.Loteria.word;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import br.com.felipeAplicativos.logica.linguagem.Text;
import br.com.felipeAplicativos.logica.loteria.Loteria;
import br.com.felipeAplicativos.logica.loteria.Participante;
import br.com.felipeAplicativos.logica.loteria.Rodada;
import br.com.felipeAplicativos.logica.loteria.carta.Carta;
import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.CodImagemIndefinidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.ParticipanteInexistenteException;
import br.com.felipeAplicativos.som.SF3_30;

public class PnlResultado extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4638397563462879474L;
	

	/**
	 * Create the panel.
	 */
	public PnlResultado(float granaApostada) {
		
		final Carta[] escolhidas = Rodada.gerarCincoCartasAleatorias();
		
		Loteria loteria = Loteria.getInstance();
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {375, 0};
		gbl.rowHeights = new int[] {130, 130, 0};
		setLayout(gbl);
		JPanel cincoCartas = new JPanel(new GridLayout(1, 5, 5, 5));
		
		for(int i = 0; i < 5; i++) {
			final int num = i;
			JLabel c = new JLabel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					try {
						g.drawImage(new ImageIcon(getClass().getResource("cartas/" + escolhidas[num].getCodImagem() + ".png")).getImage(), 0, 0, getWidth(), getHeight(), null);
					} catch (CodImagemIndefinidoException e) {
						e.printStackTrace();
					}				
				}
				
			};
			cincoCartas.add(c);
		}
		
		JScrollPane scroll1 = new JScrollPane(cincoCartas);
		
		GridBagConstraints gbc_cincoCartas = new GridBagConstraints();
		gbc_cincoCartas.fill = GridBagConstraints.BOTH;
		gbc_cincoCartas.insets = new Insets(10, 0, 0, 0);
		gbc_cincoCartas.gridx = 0;
		gbc_cincoCartas.gridy = 0;
		add(scroll1, gbc_cincoCartas);
		
		
		//list
		try {
			List<List<Participante>> result = Loteria.getInstance().jogarEObterResultado(escolhidas, granaApostada);
			
			DefaultListModel<String> dlm = new DefaultListModel<>();
			
			if(result.get(0).size() > 0) {
				dlm.addElement(word(Text.VENCEDORES));
				
				for(Participante vencedor : result.get(0)) {
					StringBuilder sb = new StringBuilder();
					sb.append("   -").append(vencedor.getNome()).append(" "+ word(Text.VENCEU_COM) +": ").append(vencedor.getPalpite().cartasCertas(escolhidas));
					dlm.addElement(sb.toString());
				}
			}
			
			if(result.get(1).size() > 0) {
				dlm.addElement("");
				dlm.addElement(word(Text.PERDEDORES));
				
				for(Participante perdedor : result.get(1)) {
					StringBuilder sb = new StringBuilder();
					sb.append("   -").append(perdedor.getNome()).append(" " + word(Text.PERDEU_COM) + ": ").append(Arrays.asList(perdedor.getPalpite().getCartas()).toString());
					dlm.addElement(sb.toString());
				}
			}
			
			if(result.get(2).size() > 0) {
				dlm.addElement("");
				dlm.addElement(word(Text.FALIDOS));
				
				for(Participante falido : result.get(2)) {
					StringBuilder sb = new StringBuilder();
					sb.append("   -").append(falido.getNome()).append(" " + word(Text.FALIU_COM) + ": ").append(Arrays.asList(falido.getPalpite().getCartas()).toString());
					dlm.addElement(sb.toString());
					
					int index = loteria.getParticipanteIndex(falido);
					
					loteria.removeParticipante(index);
					
					FrmLoteria.getInstance().removerPlayer(index);
				}
			}
			
			JList<String> lista = new JList<>(dlm);
			
			JScrollPane scroll2 = new JScrollPane(lista);
			
			GridBagConstraints gbcLista = new GridBagConstraints();
			gbcLista.fill = GridBagConstraints.BOTH;
			gbcLista.insets = new Insets(10, 0, 0, 0);
			gbcLista.gridx = 0;
			gbcLista.gridy = 1;
			
			
			add(scroll2, gbcLista);
			
			FrmLoteria.getInstance().updateGranaDeTodos();
			
			updateUI();
			
			if(loteria.getAlguemVenceu())
				avisarVencedor(result.get(0).get(0).getNome(), result.get(0).get(0).getGranaText());
		
		} catch (GranaParticipanteInvalidoException | ParticipanteInexistenteException | ApostaVotoException e1) {
			Mensagens.falhaMessage(this, e1.getMessage());
		}
		
	}
	private void avisarVencedor(String nome, String granaText) {
		try {
			Thread.sleep(2880);
			FrmLoteria.getInstance().atualizaApostaTela();
			SF3_30.play();
			Mensagens.infoMessage02(this, nome, granaText);
			SF3_30.stop();
			
			//FrmLoteria.getInstance().voltarParticipantes();
			//Utilitarios.infoMessage03(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
