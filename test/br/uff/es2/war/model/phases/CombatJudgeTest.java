package br.uff.es2.war.model.phases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.MockJudge;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

/**
 * @author Arthur Pitzer
 */
public class CombatJudgeTest extends GamePhaseTest{
    
    private final MockJudge judge;
    private final Territory territoryAtck;
    private final Territory territoryDefs;
    
    
    public CombatJudgeTest() {
	super();
	judge = new MockJudge();
	Player attacker = game.getPlayers()[0];
	Player defender = game.getPlayers()[1];
	territoryAtck = getAny(game.getWorld().getTerritoriesByOwner(attacker));
	territoryDefs = getAny(game.getWorld().getTerritoriesByOwner(defender));
    }

    @Override
    protected GameState<Game> createDependencies() {
	return new GameState<Game>() {
	    @Override
	    public GameState<Game> execute(Game game) {
		return new SetupPhase().execute(game).execute(game);
	    }
	};
    }

    @Override
    protected GameState<Game> createTestedPhase() {
	return new EmptyPhase(); 
    }
    
    @Override
    @Before
    public void resetGame() {
        super.resetGame();
    }
    
    @Test
    public void DEFENDER_WINS_IN_CASE_OF_WITHDRAW(){
	judge.setAttackValues(new int[]{6,5,4});
	judge.setDefenseValues(new int[]{6,5,4});
	territoryAtck.setSoldiers(4);
	territoryDefs.setSoldiers(3);
	Combat combat = new Combat(territoryAtck, territoryDefs, 3);
	judge.resolve(combat);
	assertEquals(territoryAtck.getSoldiers(), 1);
	assertEquals(territoryDefs.getSoldiers(), 3);
    }
    
    @Test
    public void DEFENDER_WINS_2_ATTACKERS_1_DEFENDER(){
	judge.setAttackValues(new int[]{3,2});
	judge.setDefenseValues(new int[]{6});
	territoryAtck.setSoldiers(3);
	territoryDefs.setSoldiers(1);
	Combat combat = new Combat(territoryAtck, territoryDefs, 2);
	judge.resolve(combat);
	assertEquals(territoryAtck.getSoldiers(), 1);
	assertEquals(territoryDefs.getSoldiers(), 1);
    }
    
    @Test
    public void ATTACKER_WINS_2_ATTACKERS_1_DEFENDER(){
	judge.setAttackValues(new int[]{3,2});
	judge.setDefenseValues(new int[]{1});
	territoryAtck.setSoldiers(3);
	territoryDefs.setSoldiers(1);
	Combat combat = new Combat(territoryAtck, territoryDefs, 2);
	judge.resolve(combat);
	assertEquals(territoryDefs.getOwner(), territoryAtck.getOwner());
	assertEquals(territoryAtck.getSoldiers(), 2);
	assertEquals(territoryDefs.getSoldiers(), 1);
    }
    
    private <T> T getAny(Set<T> collection){
	List<T> list = new ArrayList<>(collection);
	return list.get(0);
    }
}