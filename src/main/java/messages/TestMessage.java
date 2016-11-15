package messages;

import postoffice.AbstractMessage;
import postoffice.Address;

/**
 * Created by eugene on 11/1/16.
 */
public class TestMessage extends AbstractMessage{
    public TestMessage(Address destination, Address source) {
        super(destination, source);
    }

    @Override
    public void exec() {
        System.out.println(toString());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}
