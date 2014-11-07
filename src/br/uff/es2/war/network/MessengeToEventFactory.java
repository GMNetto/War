package br.uff.es2.war.network;

/**
 * Maps a message from a protocol to an Event.
 * TODO:abstract protocol
 * @author Arthur Pitzer
 */
public interface MessengeToEventFactory {

    Object eventTo(String message);
    
}
