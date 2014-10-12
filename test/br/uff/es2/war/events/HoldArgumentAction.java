package br.uff.es2.war.events;

public class HoldArgumentAction<T> implements Action<T> {
    
    private T argument;
    
    @Override
    public void execute(T args) {
	argument = args;
    }
    
    public T getArgument() {
	return argument;
    }
}
