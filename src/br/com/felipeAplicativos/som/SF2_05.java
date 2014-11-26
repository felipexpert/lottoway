package br.com.felipeAplicativos.som;


public class SF2_05 {
	private static MP3 som;
	
	static {
		som = new MP3("audios/2-05.mp3");
	}
	
	private SF2_05() {}
	
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
