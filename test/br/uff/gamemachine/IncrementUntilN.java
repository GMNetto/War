package br.uff.gamemachine;

class IncrementUntilN implements GameState<Integer> {

    private final int n;
    
    public IncrementUntilN(int n) {
	this.n = n;
    }
    
    @Override
    public GameState<Integer> execute(Integer context) {
	if(++context < n)
	    return this;
	return null;
    }

}
