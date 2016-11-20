package postoffice;

/**
 * Created by eugene on 11/1/16.
 */
public interface Subscriber extends Runnable {
    Address getAddress();

    default boolean equals(Subscriber subscriber){
        return getAddress().equals(subscriber.getAddress());
    }

    default void run(){}
}
