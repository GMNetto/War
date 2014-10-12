package br.uff.es2.war.events;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalEventBus implements EventBus {
    
    private final Map<Object, Set<Object>> bus;
    
    public LocalEventBus() {
	bus = new HashMap<Object, Set<Object>>();
    }
    
    @Override
    public <E> void subscribe(Class<E> event, Action<? super E> action){
	Set<Object> actions = bus.get(event);
	if(actions == null)
	    subscribeNew(event, action);
	else
	    actions.add(action);
    }
    
    private <E> void subscribeNew(Class<E> event, Action<? super E> action){
	Set<Object> actions = new HashSet<Object>();
	actions.add(action);
	bus.put(event, actions);
    }
    
    @Override
    public void publish(Object event){
	publish(event.getClass(), event);
	List<Class<?>> supers = new LinkedList<>();
	supers.add(event.getClass().getSuperclass());
	supers.addAll(Arrays.asList(event.getClass().getInterfaces()));
	if(supers.isEmpty())
	    return;
	for(Class<?> item : supers)
	    publish(item, event);
    }
    
    private void publish(Class<?> type, Object event){
	Set<Object> actions = bus.get(type);
	if(actions == null)
	    return;
	for(Object item : actions){
	    @SuppressWarnings("unchecked")
	    Action<Object> action = (Action<Object>) item;
	    action.execute(event);
	}
    }
}
