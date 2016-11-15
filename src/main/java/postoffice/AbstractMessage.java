package postoffice;

/**
 * Created by eugene on 11/1/16.
 */
public abstract class AbstractMessage implements Message {
    private final Address destination;
    private final Address source;

    @Override
    public Address getDestination() {
        return destination;
    }

    @Override
    public Address getSource() {
        return source;
    }

    public AbstractMessage(Address destination, Address source) {
        this.destination = destination;
        this.source = source;
    }

    @Override
    public String toString(){
        return String.format(
                "Message{from=%s,to=%s}",
                String.valueOf(getSource()),
                String.valueOf(getDestination())
        );
    }
}
