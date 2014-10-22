package br.uff.es2.war.events;

public class HoldArgumentAction<T> implements Action<T> {
    
    private T argument;
    
    @Override
    public void onEvent(T args) {
	argument = args;
    }
    
    public T getArgument() {
	return argument;
    }
}
