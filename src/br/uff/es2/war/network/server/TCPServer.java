package br.uff.es2.war.network.server;

import java.io.IOException;
import java.net.ServerSocket;

import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.TCPMessenger;

public abstract class TCPServer extends Server {

    private ServerSocket server;

    public TCPServer(int port) throws IOException {
	server = new ServerSocket(port);
    }

    @Override
    protected Messenger acceptClient() throws IOException {
	return new TCPMessenger(server.accept());
    }
}
