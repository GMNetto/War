package br.uff.es2.war.events;

/**
 * Action for some argument.
 * 
 * @author Arthur Pitzer
 * 
 * @param <T>
 *            Class of the argument
 */
public interface Action<T> {

    void onAction(T args);

}
