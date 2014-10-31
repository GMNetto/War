package br.uff.es2.war.network;

import java.io.IOException;
import java.util.Scanner;

public class ProceduralMessenger implements Messenger {
    
    private final Scanner scan;
    
    public ProceduralMessenger(String script) {
	scan = new Scanner(script);
    }
    
    @Override
    public String receive() {
	return scan.nextLine();
    }
    
    @Override
    public void send(String message) {
	System.out.println(message);
    }
    
    @Override
    public void close() throws IOException {
	scan.close();
    }
}
