package postoffice;

/**
 * Created by eugene on 11/1/16.
 */
public interface Subscriber extends Runnable {
    public Address getAddress();

    default boolean equals(Subscriber subscriber){
        return getAddress().equals(subscriber.getAddress());
    }

    default void run(){}
}
