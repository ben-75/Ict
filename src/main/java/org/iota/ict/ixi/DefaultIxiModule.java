package org.iota.ict.ixi;

import org.iota.ict.network.event.GossipReceiveEvent;
import org.iota.ict.network.event.GossipSubmitEvent;

public class DefaultIxiModule extends IxiModule {

    public DefaultIxiModule(IctProxy ict) {
        super(ict);
    }

    @Override
    public void onTransactionReceived(GossipReceiveEvent event) { ; }

    @Override
    public void onTransactionSubmitted(GossipSubmitEvent event) { ; }

    @Override
    public void onIctShutdown() { ; }

    @Override
    public void run() { ; }

}

