package org.iota.ict.ixi;

import org.iota.ict.eee.EffectDispatcher;
import org.iota.ict.model.transaction.Transaction;
import org.iota.ict.eee.EffectListener;
import org.iota.ict.network.gossip.GossipEvent;
import org.iota.ict.network.gossip.GossipListener;
import org.iota.ict.network.gossip.GossipPreprocessor;

import java.util.Set;

public interface Ixi extends EffectDispatcher {

    /**
     * Searches the local tangle for transaction associated with a specific address.
     * @param address The 81-tryte address for which to find all transactions.
     * @return All transactions found in the local tangle with the specific address field.
     * */
    Set<Transaction> findTransactionsByAddress(String address);


    /**
     * Searches the local tangle for transaction associated with a specific tag.
     * @param tag The 27-tryte tag for which to find all transactions.
     * @return All transactions found in the local tangle with the specific tag field.
     * */
    Set<Transaction> findTransactionsByTag(String tag);

    /**
     * Searches the local tangle for a specific transaction
     * @param hash The 81-tryte hash of the transaction to find.
     * @return The transaction with the respective hash or <code>null</code> if no such transaction was found.
     * */
    Transaction findTransactionByHash(String hash);

    /**
     * Adds a new transaction to the local tangle before broadcasting it to the network.
     * @param transaction The transaction to broadcast.
     * */
    void submit(Transaction transaction);

    /**
     * Determines the confidence with which a transaction is considered confirmed within the economic cluster the Ict is following.
     *
     * @param transactionHash The hash of the transaction whose approval confidence to determine.
     * @return Determined approval confidence for the transaction in the economic cluster.
     * */
    double determineApprovalConfidence(String transactionHash);
}
