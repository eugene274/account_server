package server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 * Created by eugene on 10/21/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TokenStatusUpdater
    extends TokenService
        implements Runnable{
    private static final Logger LOG = LogManager.getLogger("tokenupdr");

    private static final Integer INACTIVE_AFTER = 2;

    private void activityCheck(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -INACTIVE_AFTER);
        tokens().stream()
                .filter(token -> token.isActive() && token.getLastRequestAt().before(now))
                .forEach(token -> {
                    token.setActive(false);
                    LOG.debug(String.format("set '%s' inactive due to '%d' minutes of inactivity", token, INACTIVE_AFTER));
                });
    }


    @Override
    public void run() {
        LOG.debug("starting new token updater loop");
        while (true){
            activityCheck();


            try {
                Thread.currentThread().sleep(30_000);
            } catch (InterruptedException e) {
                LOG.error("token updater interrupted, exiting");
                return;
            }
        }
    }
}
