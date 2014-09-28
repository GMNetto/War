package br.uff.gamemachine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameMachineTest {
    
    private final GameMachine<Integer> machine;
    private final Integer context;
    private final int n;
    
    public GameMachineTest() {
	n = 10;
	context = new Integer(10);
	machine = new GameMachine<Integer>(context, new IncrementUntilN(n));
    }
    
    @Test
    public void EXECUTE_N_SUCCESSOR_STATES(){
	machine.run();
	assertEquals(new Integer(n), context);
    }

}
