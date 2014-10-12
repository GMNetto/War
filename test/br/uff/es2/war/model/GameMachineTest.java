package br.uff.es2.war.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.uff.es2.war.model.phases.GameMachine;
import br.uff.es2.war.model.phases.GameState;

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
    public void EXECUTE_N_SUCCESSOR_STATES() {
	machine.run();
	assertEquals(new Integer(n), context);
    }

    private class IncrementUntilN implements GameState<Integer> {

	private final int n;

	public IncrementUntilN(int n) {
	    this.n = n;
	}

	@Override
	public GameState<Integer> execute(Integer context) {
	    if (++context < n)
		return this;
	    return null;
	}
    }
}
