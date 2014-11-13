package br.uff.es2.war.network;

import br.uff.es2.war.model.MockGame;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.network.server.ServerSidePlayer;
import br.uff.es2.war.network.server.ServerSideProtocol;

public class JSONWarProtocolTest {
    
    private static final String PLAYER_SCRIPT_1 = "CHOOSE_COLOR {\"name\":\"Blue\"}\n"
    						+ "DISTRIBUTE_SOLDIERS [{\"soldiers\":2,\"name\":\"B 2\",\"owner\":\"Blue\",\"continent\":\"B\",\"borders\":[\"A 1\",\"B 1\"]},{\"soldiers\":1,\"name\":\"A 2\",\"owner\":\"Blue\",\"continent\":\"A\",\"borders\":[\"A 1\",\"B 1\"]}]\n"
    						+ "DECLARE_COMBAT {\"attacking\":\"B 2\", \"defending\":\"B 1\", \"soldiers\":1}\n"
    						+ "FINISH_ATTACK\n"
    						+ "MOVE_SOLDIERS []\n";
    
    private static final String PLAYER_SCRIPT_2 = "CHOOSE_COLOR {\"name\":\"White\"}\n"
    						+ "DECLARE_COMBAT 1\n";
    
    private static Player[] createProceduralPlayers(ServerSideProtocol protocol){
	return new Player[]{
		new ServerSidePlayer(new ProceduralMessenger(PLAYER_SCRIPT_1), protocol),
		new ServerSidePlayer(new ProceduralMessenger(PLAYER_SCRIPT_2), protocol)
	};
    }
    private MockGame game;

    /*
    @Ignore
    @Test
    public void ONE_TURN() {
	ServerSideProtocol protocol = new ServerSideJSONProtocol();
	Player[] players = createProceduralPlayers(protocol);
	Game game = new MockGame(players, MockGame.createWorld(), MockGame.createColors(), MockGame.createCards());
	protocol.setGame(game);
	GameMachine<Game> machine = new GameMachine<Game>(game, new SetupPhase());
	machine.run();
    }
    */
}
