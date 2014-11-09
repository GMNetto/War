package br.uff.es2.war.network.client;

/**
 * Maps a message from a protocol to an Event.
 * TODO:abstract protocol
 * @author Arthur Pitzer
 */
public interface MessageToEventConverter {

    Object toEvent(String message);
    
}
