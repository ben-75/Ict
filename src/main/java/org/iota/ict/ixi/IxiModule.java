package org.iota.ict.ixi;

import org.iota.ict.model.Transaction;
import org.iota.ict.network.event.GossipFilter;
import org.iota.ict.network.event.GossipListener;
import org.iota.ict.network.event.GossipReceiveEvent;
import org.iota.ict.network.event.GossipSubmitEvent;

import java.util.Set;

public abstract class IxiModule implements Runnable, IctInterface {

    private IctProxy proxy;
    private GossipFilter gossipFilter;

    protected IxiModule(IctProxy proxy) {

        this.proxy = proxy;

        initializeDefaultGossipFilter();

        addGossipListener(new GossipListener() {
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

    @Override
    public Set<Transaction> findTransactionsByAddress(String address) {
        return proxy.findTransactionsByAddress(address);
    }

    @Override
    public Set<Transaction> findTransactionsByTag(String tag) {
        return proxy.findTransactionsByTag(tag);
    }

    @Override
    public Transaction findTransactionByHash(String hash) {
        return proxy.findTransactionByHash(hash);
    }

    @Override
    public Transaction submit(String asciiMessage) {
        return proxy.submit(asciiMessage);
    }

    @Override
    public void submit(Transaction transaction) {
        proxy.submit(transaction);
    }

    @Override
    public void addGossipListener(GossipListener gossipListener) {
        proxy.addGossipListener(gossipListener);
    }

    public abstract void onTransactionReceived(GossipReceiveEvent event);
    public abstract void onTransactionSubmitted(GossipSubmitEvent event);
    public abstract void onIctShutdown();

}