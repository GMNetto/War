package br.uff.es2.war.events;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LocalEventBusTest {

    private EventBus bus;

    @Before
    public void resetEventBus() {
	bus = new LocalEventBus();
    }

    @Test
    public void PUBLISHED_EVENT_TRIGGERS_SUBSCRIBED_ACTIONS() {
	Object event = new Object();
	HoldArgumentAction<Object> action1 = new HoldArgumentAction<>();
	HoldArgumentAction<Object> action2 = new HoldArgumentAction<>();
	bus.subscribe(Object.class, action1);
	bus.subscribe(Object.class, action2);
	bus.publish(event);
	assertEquals(event, action1.getArgument());
	assertEquals(event, action2.getArgument());
    }

    @Test
    public void PUBLISHED_EVENT_TRIGGERS_ONLY_ACTIONS_OF_THE_CORRECT_TYPE() {
	String event = "event";
	HoldArgumentAction<Integer> intAction = new HoldArgumentAction<>();
	HoldArgumentAction<String> strAction = new HoldArgumentAction<>();
	bus.subscribe(Integer.class, intAction);
	bus.subscribe(String.class, strAction);
	bus.publish(event);
	assertEquals(strAction.getArgument(), event);
	assertEquals(intAction.getArgument(), null);
    }

    @Test
    public void PUBLISHED_EVENT_TRIGGERS_ACTIONS_OF_SUPER_TYPE_EVENTS() {
	Object event = "event";
	HoldArgumentAction<Object> objAction = new HoldArgumentAction<>();
	HoldArgumentAction<String> strAction = new HoldArgumentAction<>();
	bus.subscribe(Object.class, objAction);
	bus.subscribe(String.class, strAction);
	bus.publish(event);
	assertEquals(strAction.getArgument(), event);
	assertEquals(objAction.getArgument(), event);
    }

    @Test
    public void PUBLISHED_EVENT_DOES_NOT_TRIGGER_ACTIONS_OF_SUB_TYPE_EVENTS() {
	Object event = new Object();
	HoldArgumentAction<Object> objAction = new HoldArgumentAction<>();
	HoldArgumentAction<String> strAction = new HoldArgumentAction<>();
	bus.subscribe(Object.class, objAction);
	bus.subscribe(String.class, strAction);
	bus.publish(event);
	assertEquals(strAction.getArgument(), null);
	assertEquals(objAction.getArgument(), event);
    }

}
