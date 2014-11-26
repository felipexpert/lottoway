package br.com.felipeAplicativos.logica.loteria;

import java.text.NumberFormat;
import java.text.ParseException;

import br.com.felipeAplicativos.logica.loteria.exceptions.ApostaVotoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.BotPalpiteException;
import br.com.felipeAplicativos.logica.loteria.exceptions.GranaParticipanteInvalidoException;
import br.com.felipeAplicativos.logica.loteria.exceptions.NomeParticipanteInvalidoException;


public class Participante {
	private String nome;
	private float grana;
	private Palpite palpite;
	private long palpitesUpdates;
	private long granaUpdates;
	private long apostaVotoUpdates;
	private float apostaVoto;
	
	
	{
		palpite = null;
		palpitesUpdates = 0L;
		granaUpdates = 0L;
		apostaVoto = 0L;
	}
	public Participante(String nome, float grana) throws NomeParticipanteInvalidoException, GranaParticipanteInvalidoException, ApostaVotoException {
		setNome(nome);
		setGrana(grana);
		
		setApostaVoto(Loteria.getInstance().getModalidade().getValor(0));
	}
	
	public Participante(String nome, String grana) throws NomeParticipanteInvalidoException, GranaParticipanteInvalidoException, ApostaVotoException {
		setNome(nome);
		setGrana(grana);
		
		setApostaVoto(Loteria.getInstance().getModalidade().getValor(0));
	}
	
	public float getApostaVoto() {
		return apostaVoto;
	}
	
	public String getApostaVotoEmDinheiro() {
		return NumberFormat.getCurrencyInstance().format(apostaVoto);
	}
	
	public void setApostaVoto(float valor) throws ApostaVotoException {
		Modalidade mod = Loteria.getInstance().getModalidade();
		
		float[] valores = mod.getValores();
		
		for(float v : valores) {
			if(v == valor) {
				apostaVoto = valor;
				apostaVotoUpdates++;
				return;
			}
		}
		
		throw new ApostaVotoException();
	}
	
	public void setApostaVoto(String valorEmDinheiro) throws ApostaVotoException {
		try {
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			float valor = nf.parse(valorEmDinheiro).floatValue();
			if((getGrana() > Modalidade.min() && getGrana() < valor) || (getGrana() < Modalidade.min() && valor > Modalidade.min())) {
				throw new ApostaVotoException();
			}
			setApostaVoto(valor);
		} catch (ApostaVotoException | ParseException e) {
			throw new ApostaVotoException();
		}
	}
	
	public Palpite getPalpite() {
		return palpite;
	}
	
	public void setPalpite(Palpite p) throws BotPalpiteException {
		palpite = p;
		palpitesUpdates++;
	}
	
	public long getTotalPalpites() {
		return palpitesUpdates;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) throws NomeParticipanteInvalidoException{
		if(!nome.matches("[\\p{L}\\s*]+\\d*")) {
			throw new NomeParticipanteInvalidoException(nome);
		}
		
		this.nome = nome.toUpperCase();
	}
	
	public float getGrana() {
		return grana;
	}
	
	public String getGranaText() {
		return NumberFormat.getCurrencyInstance().format(grana);
	}
	
	public void setGrana(String grana) throws GranaParticipanteInvalidoException {
		try {
			Number number = NumberFormat.getNumberInstance().parse(grana);
			
			setGrana(number.floatValue());
			
			
		} catch (ParseException e) {
			throw new GranaParticipanteInvalidoException(grana);
		}
	}
	
	public long getGranaUpdates() {
		return granaUpdates;
	}
	
	public long getApostaVotoUpdates() {
		return apostaVotoUpdates;
	}
	
	public void setGrana(float grana) throws GranaParticipanteInvalidoException{
		if(grana < 0) {
			throw new GranaParticipanteInvalidoException(grana);
		}
		
		this.grana = grana;
		granaUpdates++;
	}
	
	public void PorMaisGrana(float grana) throws GranaParticipanteInvalidoException {
		setGrana(getGrana() + Math.abs(grana));
	}
	
	public void PorMaisGrana(String grana) throws GranaParticipanteInvalidoException {
		try {
			Number number = NumberFormat.getNumberInstance().parse(grana);
			if(number != null) {
				PorMaisGrana(number.floatValue());
			}
		} catch (ParseException e) {
			throw new GranaParticipanteInvalidoException(grana);
		}
	}
	
	public float retirarGrana(float granaApostada) throws GranaParticipanteInvalidoException{
		if(granaApostada <= 0)
			throw new GranaParticipanteInvalidoException(granaApostada);
		
		if(grana >= granaApostada) {
			//grana -= granaApostada;
			setGrana(getGrana() - granaApostada);
			return granaApostada;
		} else {
			float retirar = getGrana();
			setGrana(0.0F);
			return retirar;
		}
			
	}
	
	protected void setPalpiteAutomaticoBot(Palpite palpite) {
		this.palpite = palpite;
		palpitesUpdates++;
	}
	
	/**
	 * @return o ultimo index possível de acordo com a modalidade
	 * na instancia da Loteria (que é um Singleton).
	 */
	public int ultimoIndexPossivel() {
		float[] valores = Loteria.getInstance().getModalidade().getValores();
		int indexPossivel = 0;
		for(int z = (valores.length - 1); z >= 0; z--) {
			if(valores[z] <= getGrana()) {
				indexPossivel =  z;
				break;
			}
		}
		return indexPossivel;
	}
	
	public float retirarGrana(String granaApostada) throws GranaParticipanteInvalidoException{
		float valor = 0;
		try {
			Number number = NumberFormat.getNumberInstance().parse(granaApostada);
			
			retirarGrana(number.floatValue());
			
			assert false : "number não pôde ser convertido nem em Double nem em Long";
		} catch (ParseException e) {
			throw new GranaParticipanteInvalidoException(granaApostada);
		}
		
		return valor;
	}
	
	@Override
	public String toString() {
		//NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nome + " " + getGranaText() + (palpite != null ? " " + palpite: "");
	}
}
