package postoffice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eugene on 11/1/16.
 */
public class AddressImpl implements Address {
    private final static AtomicInteger currentId = new AtomicInteger(0);
    private Integer id;

    public AddressImpl() {
        id = currentId.getAndIncrement();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressImpl address = (AddressImpl) o;

        return getId().equals(address.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id.toString() +
                '}';
    }
}
