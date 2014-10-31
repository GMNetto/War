package br.uff.es2.war.events;

/**
 * Allow to decouple the source of an event from the actions taken in response.
 * 
 * ex: EventBus bus = ... bus.subscribe(String.class, new MyStringAction());
 * bus.subscribe(Object.class, new MyObjectAction()); //in other point of the
 * code bus.publish("something");
 * 
 * This will trigger both MyStringAction and MyObjectAction. The value
 * "something" will be argument of the actions.
 * 
 * @author Arthur Pitzer
 */
public interface EventBus {

    /**
     * action will be executed when an event of the given class is published.
     * Sub classes of the event will trigger the action too.
     * 
     * @param event
     *            class of an event
     * @param action
     *            action for the event
     */
    <E> void subscribe(Class<E> event, Action<? super E> action);

    /**
     * Execute all actions related to the event
     */
    void publish(Object event);

}
