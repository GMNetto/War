package br.uff.es2.war.network;

import java.io.Closeable;

/**
 * Abstract channel that allows to send and receive a String.
 * @author Arthur Pitzer
 */
public interface Messenger extends Closeable {

    void send(String message);

    String receive();

}
