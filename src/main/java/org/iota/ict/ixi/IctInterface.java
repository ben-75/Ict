package org.iota.ict.ixi;

import org.iota.ict.model.Transaction;
import org.iota.ict.network.event.GossipListener;

import java.util.Set;

public interface IctInterface {

    Set<Transaction> findTransactionsByAddress(String address);

    Set<Transaction> findTransactionsByTag(String tag);

    Transaction findTransactionByHash(String hash);

    Transaction submit(String asciiMessage);

    void submit(Transaction transaction);

    void addGossipListener(GossipListener gossipListener);

}
