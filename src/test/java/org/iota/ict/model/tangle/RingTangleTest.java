package org.iota.ict.model.tangle;

import org.iota.ict.Ict;
import org.iota.ict.IctTestTemplate;
import org.iota.ict.model.transaction.Transaction;
import org.iota.ict.model.transaction.TransactionBuilder;
import org.iota.ict.utils.properties.EditableProperties;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class RingTangleTest extends IctTestTemplate {

    @Test
    public void testSameTimestamp() {
        int ringTangleCapacity = 10;

        EditableProperties properties = new EditableProperties();
        properties.tangleCapacity(ringTangleCapacity);
        properties.maxHeapSize(1.0);
        Ict ict = createIct(properties);

        TransactionBuilder builder = new TransactionBuilder();
        for (int i = 0; i < ringTangleCapacity * 2; i++) {
            ict.submit(builder.build());
            int amountSubmitted = i + 1;
            int expectedTangleSize = Math.min(amountSubmitted + 1, ringTangleCapacity); // +1 for NULL transaction
            Assert.assertEquals("Unexpected amount of transactions.", expectedTangleSize, ict.getTangle().size());
        }
    }

    @Test
    public void testMaxNumberOfTransactions() {
        int ringTangleCapacity = 10;
        int offset = ringTangleCapacity / 2;
        int totalTransactions = ringTangleCapacity * 2;

        EditableProperties properties = new EditableProperties();
        properties.maxHeapSize(1.0);
        properties.tangleCapacity (ringTangleCapacity);
        Ict ict = createIct(properties);

        List<Transaction> transactionsOrderedByTimestamps = generateTransactionsOrderedByTimestamps(totalTransactions);

        Set<Transaction> previousTransactions = new HashSet<>(transactionsOrderedByTimestamps.subList(0, offset + ringTangleCapacity - 1));
        Set<Transaction> tangleContentBefore = new HashSet<>(transactionsOrderedByTimestamps.subList(offset, offset + ringTangleCapacity - 1));

        Set<Transaction> newTransactions = new HashSet<>(transactionsOrderedByTimestamps.subList(offset + ringTangleCapacity - 1, transactionsOrderedByTimestamps.size()));
        Set<Transaction> tangleContentAfter = new HashSet<>(transactionsOrderedByTimestamps.subList(transactionsOrderedByTimestamps.size() - ringTangleCapacity + 1, transactionsOrderedByTimestamps.size()));


        submit(ict, previousTransactions);
        assertTangleContainsExactlyPlusNullTx(ict.getTangle(), tangleContentBefore);

        submit(ict, newTransactions);
        assertTangleContainsExactlyPlusNullTx(ict.getTangle(), tangleContentAfter);
    }

    private static void submit(Ict ict, Iterable<Transaction> transactions) {
        for (Transaction transaction : transactions)
            ict.submit(transaction);
    }

    private static void assertTangleContainsExactlyPlusNullTx(Tangle tangle, Set<Transaction> transactionsToContain) {
        Assert.assertNotNull("The NULL transaction is missing.", tangle.findTransactionLog(Transaction.NULL_TRANSACTION));
        for (Transaction transaction : transactionsToContain)
            Assert.assertNotNull("A transaction is missing.", tangle.findTransactionLog(transaction));
        assertIntEqualsWithTolerance("Unexpected amount of transactions.", transactionsToContain.size() + 1, tangle.size(), 1);
    }

    private static void assertIntEqualsWithTolerance(String message, int expected, int actual, int tolerance) {
        if(Math.abs(expected-actual) > tolerance)
            Assert.assertEquals("Unexpected amount of transactions.", expected, actual);
    }

    private static List<Transaction> generateTransactionsOrderedByTimestamps(int amount) {
        List<Transaction> transactions = new LinkedList<>();
        long timestamp = System.currentTimeMillis();
        while (amount > 0) {
            timestamp += Math.random() * 1000 + 1;
            TransactionBuilder builder = new TransactionBuilder();
            builder.issuanceTimestamp = timestamp;
            transactions.add(builder.build());
            amount--;
        }
        return transactions;
    }
}
