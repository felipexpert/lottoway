package br.com.felipeAplicativos.som;


public class SF3_30 {
	private static MP3 som;
	
	static {
		som = new MP3("audios/3-30.mp3");
	}
	
	private SF3_30() {}
	
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
