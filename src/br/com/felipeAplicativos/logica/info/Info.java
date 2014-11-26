package br.com.felipeAplicativos.logica.info;

public enum Info {
	CRIADOR("Felipe Carmona Miquilini"),
	CONTATO("felipe.miquilini@gmail.com"),
	VERSAO("1.2.5 (offline)");
	
	private String info;
	
	Info(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return info;
	}
}
