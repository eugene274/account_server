package postoffice;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * Created by eugene on 11/1/16.
 */
public class Postman {
    private final static Map<Address,ConcurrentLinkedQueue<Message>> mailboxes =
            new HashMap<>();

    private static Logger LOG = Logger.getLogger("POSTMAN");

    private static Queue<Message> getMailbox(Address address) {
        ConcurrentLinkedQueue<Message> messages = mailboxes.get(address);
        return messages;
    }

    public static void sendMessage(Message message) {
        try {
            getMailbox(message.getDestination()).add(message);
            LOG.info(message.toString() + " added to queue");
        }
        catch (NullPointerException e){
            LOG.warning(message.getDestination() + " wrong address");
        }
    }

    public static Message getMessage(Subscriber subscriber){
        return getMailbox(subscriber.getAddress()).poll();
    }

    public static void register(Subscriber subscriber) {
        register(subscriber.getAddress());
    }

    public static void register(Address address){
        mailboxes.putIfAbsent(address, new ConcurrentLinkedQueue<>());
        LOG.info(address.toString() + " registered, total " + mailboxes.size());
    }
}
