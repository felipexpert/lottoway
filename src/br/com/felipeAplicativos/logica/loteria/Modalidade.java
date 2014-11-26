package br.com.felipeAplicativos.logica.loteria;

import java.text.NumberFormat;

import br.com.felipeAplicativos.logica.loteria.exceptions.ValorModalidadeInexistenteException;

public enum Modalidade {
	MUITO_CURTO(new float[] {0.10f, 0.25f, 0.50f, 0.75f, 1.00f, 1.50f, 2.00f, 3.00f, 5.00f, 7.00f, 12.00f, 17.00f, 27.00f, 40.00f}),
	CURTO(new float[] {0.10f, 0.25f, 0.50f, 0.75f, 1.00f, 1.50f, 2.00f, 3.00f, 5.00f, 7.00f, 12.00f, 17.00f}),
	MEIO_CURTO(new float[] {0.10f, 0.25f, 0.50f, 0.75f, 1.00f, 1.50f, 2.00f, 3.00f, 5.00f, 7.00f}),
	REGULAR(new float[] {0.10f, 0.25f, 0.50f, 0.75f, 1.00f, 1.50f, 2.00f, 3.00f}),
	DURADOURO(new float[] {0.10f, 0.25f, 0.50f, 0.75f, 1.00f, 1.50f}),
	MUITO_DURADOURO(new float[] {0.10f, 0.25f, 0.50f, 0.75f});
	
	private float[] valores;
	
	Modalidade(float[] valores) {
		this.valores = valores;
	}
	
	public int getUltimoIndexValores() {
		return valores.length - 1;
	}
	
	public float[] getValores() {
		return valores;
	}
	
	public String[] getTextValores() {
		String[] textValores = new String[valores.length];
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		for (int i = 0; i < valores.length; i++) {
			textValores[i] = nf.format(valores[i]);
		}
		
		return textValores;
	}
	
	public float floatValue(String valor) throws ValorModalidadeInexistenteException{
		float v = 0.0f;
		
		String[] valores = getTextValores();
		float[] vs = getValores();
		for(int i = 0; i < valores.length; i++) {
			if(valores[i].equals(valor)) {
				v = vs[i];
				break;
			}
		}
		
		if(v == 0.0f)
			throw new ValorModalidadeInexistenteException();
		
		return v;
	}
	
	public float getValor(int index) {
		try {
			return valores[index];
		} catch(ArrayIndexOutOfBoundsException e) {
			return valores[0];
		}
	}
	
	public String StringValue(float valor) throws ValorModalidadeInexistenteException {
		String v = "";
		
		float[] valores = getValores();
		String[] vs = getTextValores();
		
		for(int i = 0; i < valores.length; i++) {
			if(valores[i] == valor) {
				v = vs[i];
				break;
			}
		}
		
		if(v.equals(""))
			throw new ValorModalidadeInexistenteException();
		
		return v;
	}
	public static float min() {
		return 0.1f;
	}
	
}
