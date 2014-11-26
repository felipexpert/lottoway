package br.com.felipeAplicativos.som;



public class SFSuperMario {
	
	private static MP3 som;
	
	static {
		som = new MP3("audios/musicaSuperMario.mp3");
	}
	
	private SFSuperMario() {}
	
	public static void play() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				som.playContinuo();
			}
		}).start();
	}
	
	public static void stop(){
		som.stop();
	}
	
}
