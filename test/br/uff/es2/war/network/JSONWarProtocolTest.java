package br.uff.es2.war.network;

import org.junit.Ignore;
import org.junit.Test;

import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.MockGame;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.World;
import br.uff.es2.war.model.phases.GameMachine;
import br.uff.es2.war.model.phases.SetupPhase;

public class JSONWarProtocolTest {
    
    private static final String PLAYER_SCRIPT_1 = "CHOOSE_COLOR {\"name\":\"Blue\"}\n"
    						+ "DISTRIBUTE_SOLDIERS [{\"soldiers\":2,\"name\":\"B 2\",\"owner\":\"Blue\",\"continent\":\"B\",\"borders\":[\"A 1\",\"B 1\"]},{\"soldiers\":1,\"name\":\"A 2\",\"owner\":\"Blue\",\"continent\":\"A\",\"borders\":[\"A 1\",\"B 1\"]}]\n"
    						+ "DECLARE_COMBAT {\"attacking\":\"B 2\", \"defending\":\"B 1\", \"soldiers\":1}\n"
    						+ "FINISH_ATTACK\n"
    						+ "MOVE_SOLDIERS []\n";
    
    private static final String PLAYER_SCRIPT_2 = "CHOOSE_COLOR {\"name\":\"White\"}\n"
    						+ "DECLARE_COMBAT 1\n";
    
    private static Player[] createProceduralPlayers(WarProtocol protocol){
	return new Player[]{
		new RemotePlayer(new ProceduralMessenger(PLAYER_SCRIPT_1), protocol),
		new RemotePlayer(new ProceduralMessenger(PLAYER_SCRIPT_2), protocol)
	};
    }
    private MockGame game;
    
    @Ignore
    @Test
    public void FullRun() {
	World world = MockGame.createWorld();
	WarProtocol protocol = new JSONWarProtocol(world);
	Player[] players = createProceduralPlayers(protocol);
        game = new MockGame(players, world, MockGame.createColors(), MockGame.createCards());
	GameMachine<Game> machine = new GameMachine<Game>(game, new SetupPhase());
	machine.run();
    }
}
