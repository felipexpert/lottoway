package br.com.felipeAplicativos.logica.rede;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	
	private List<ObjectOutputStream> oosList = new ArrayList<>();
	
	public Servidor() throws IOException {
		try (ServerSocket ss = new ServerSocket(5000)) {
			while(true) {
				Socket s = ss.accept();
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oosList.add(oos);
			}
		}
	}
}
