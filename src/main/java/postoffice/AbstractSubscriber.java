package postoffice;

import java.util.logging.Logger;

/**
 * Created by eugene on 11/1/16.
 */
public abstract class AbstractSubscriber implements Subscriber {
    private final Address address = new AddressImpl();

    protected static Logger LOG = Logger.getLogger("Subscriber");

    @Override
    public final Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "address=" + getAddress() +
                '}';
    }
}
