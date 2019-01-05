package org.iota.ict.ixi;

import org.iota.ict.network.event.GossipFilter;
import org.iota.ict.network.event.GossipListener;
import org.iota.ict.network.event.GossipReceiveEvent;
import org.iota.ict.network.event.GossipSubmitEvent;

public abstract class IxiModule implements Runnable {

    protected final IctProxy ict;
    private GossipFilter gossipFilter;

    protected IxiModule(IctProxy ict) {

        this.ict = ict;

        initializeDefaultGossipFilter();

        ict.addGossipListener(new GossipListener() {
            @Override
            public void onTransactionReceived(GossipReceiveEvent e) {
                if (gossipFilter.passes(e.getTransaction()))
                    IxiModule.this.onTransactionReceived(e);
            }
            @Override
            public void onTransactionSubmitted(GossipSubmitEvent e) {
                if (gossipFilter.passes(e.getTransaction()))
                    IxiModule.this.onTransactionSubmitted(e);
            }
        });

        new Thread(this).start();

    }

    private void initializeDefaultGossipFilter() {
        gossipFilter = new GossipFilter();
        gossipFilter.setWatchingAll(true);
    }

    public void setGossipFilter(GossipFilter gossipFilter) {
        if(gossipFilter != null)
            this.gossipFilter = gossipFilter;
    }

    public IctProxy getIct() {
        return ict;
    }

    public abstract void onTransactionReceived(GossipReceiveEvent event);
    public abstract void onTransactionSubmitted(GossipSubmitEvent event);
    public abstract void onIctShutdown();

}