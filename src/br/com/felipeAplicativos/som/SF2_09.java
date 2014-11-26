package br.com.felipeAplicativos.som;


public class SF2_09 {
	private static MP3 som;
	
	static {
		som = new MP3("audios/2-09.mp3");
	}
	
	private SF2_09() {}
	
	public static void play() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				som.play();
			}
		}).start();
	}
	
	public static void stop(){
		som.stop();
	}
}
