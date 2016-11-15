package postoffice;

/**
 * Created by eugene on 11/1/16.
 */
public interface Message {
    Address getDestination();
    Address getSource();
    void exec();
}
