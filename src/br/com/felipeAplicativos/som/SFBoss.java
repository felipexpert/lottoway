package br.com.felipeAplicativos.som;


public class SFBoss {
	
	private static MP3 som;
	
	static {
		som = new MP3("audios/boss.mp3");
	}
	
	private SFBoss() {}
	
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
