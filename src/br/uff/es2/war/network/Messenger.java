package br.uff.es2.war.network;

import java.io.Closeable;

public interface Messenger extends Closeable {

    void send(String message);

    String receive();

}
