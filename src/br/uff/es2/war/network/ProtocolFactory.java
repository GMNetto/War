package br.uff.es2.war.network;

import br.uff.es2.war.network.client.ClientSideProtocol;
import br.uff.es2.war.network.server.ServerSideProtocol;

public final class ProtocolFactory {
    
    public static ServerSideProtocol defaultJSONServerSideProtocol(){
	return new ServerSideProtocol(new DefaultProtocolMessages(), new JSONEncoder(), new JSONDecoder());
    }
    
    public static ClientSideProtocol defaultJSONClientSideProtocol(){
	return new ClientSideProtocol(new DefaultProtocolMessages(), new JSONEncoder(), new JSONDecoder());
    }

}
