package test.br.uff.es2.war.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.uff.es2.war.model.CombatJudge;

/*
 * Assert that each combat do not have more than 3 soldiers on each side
 * 
 */
public class CombatTest {
    
    private final CombatJudge judge;
    
    public CombatTest() {
	judge = new CombatJudge();
    }
    
    @Test
    public void ATTACKER_WINS_3_ATTACKERS_3_DEFENDERS(){
	int[] attack = {4, 5, 6};
	int[] defence = {1, 2, 3};
	assertTrue(judge.judge(attack, defence));
    }
    
    @Test
    public void DEFENDER_WINS_3_ATTACKERS_3_DEFENDERS(){
	int[] attack = {1, 2, 3};
	int[] defence = {4, 5, 6};
	assertFalse(judge.judge(attack, defence));
    }
    
    public void ATTACKER_WINS_3_ATTACKERS_1_DEFENDER(){
	int[] attack = {4, 5, 6};
	int[] defence = {3};
	assertTrue(judge.judge(attack, defence));
    }
    
    @Test
    public void DEFENDER_WINS_3_ATTACKERS_1_DEFENDERS(){
	int[] attack = {1, 2, 3};
	int[] defence = {4};
	assertFalse(judge.judge(attack, defence));
    }
    
    @Test
    public void DEFENDER_WINS_IN_CASE_OF_WITHDRAW(){
	int[] attack = {1, 2, 3};
	int[] defence = {1, 2, 3};
	assertFalse(judge.judge(attack, defence));
    }
}
