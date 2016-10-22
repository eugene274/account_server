package server.jmx.controller;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by eugene on 10/21/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class JMXInit {
    public static MBeanServer init(){
        AccountController ac = new AccountController();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        try {
            ObjectName acname = new ObjectName("ServerManager:type=AccountServerController");
            mbs.registerMBean(new StandardMBean(ac, ControllerMBean.class), acname);
        } catch (MalformedObjectNameException | MBeanRegistrationException | InstanceAlreadyExistsException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }
        return mbs;
    }

    private JMXInit(){}
}
