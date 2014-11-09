package br.uff.es2.war.network;

import br.uff.es2.war.network.client.ClientSideProtocol2;
import br.uff.es2.war.network.json.JSONEncoder;
import br.uff.es2.war.network.server.ServerSideProtocol;

public final class ProtocolFactory {
    
    public static ServerSideProtocol defaultJSONServerSideProtocol(){
	return new ServerSideProtocol(new DefaultProtocolMessages(), new JSONEncoder2(), new JSONDecoder2());
    }
    
    public static ClientSideProtocol2 defaultJSONClientSideProtocol(){
	return new ClientSideProtocol2(new DefaultProtocolMessages(), new JSONEncoder2(), new JSONDecoder2());
    }

}
