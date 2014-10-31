package br.uff.es2.war.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class TCPMessenger implements Messenger {

    private final Socket socket;
    private final PrintStream out;
    private final BufferedReader in;

    public TCPMessenger(Socket socket) throws IOException {
	this.socket = socket;
	out = new PrintStream(socket.getOutputStream());
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void close() throws IOException {
	socket.close();
    }

    @Override
    public void send(String message) {
	out.println(message);
	out.flush();
    }

    @Override
    public String receive() {
	try {
	    return in.readLine();
	} catch (IOException e) {
	    return "";
	}
    }
}
