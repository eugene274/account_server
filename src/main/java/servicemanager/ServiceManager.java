package servicemanager;

import postoffice.Address;

/**
 * Created by eugene on 11/16/16.
 */
public interface ServiceManager {
    Address getServiceAddress(Class<? extends Service> serviceClass);
    void spawn(Class<? extends Service> serviceClass);
}
