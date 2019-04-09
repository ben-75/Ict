package org.iota.ict.utils;

import org.iota.ict.eee.chain.ChainedEnvironment;
import org.iota.ict.eee.Environment;
import org.iota.ict.model.transaction.Transaction;

import java.io.File;
import java.nio.file.Path;

/**
 * Important constants which are not changed during runtime but might be changed during development or are used by
 * multiple classes are kept together here to make them easier to find and adjust.
 */
public class Constants {

    public static final Path WORKING_DIRECTORY = (new File("./")).toPath();
    ;

    public static final String ICT_VERSION = "0.7-SNAPSHOT";
    public static final String ICT_REPOSITORY = "iotaledger/ict";
    public static final String DEFAULT_PROPERTY_FILE_PATH = "ict.cfg";
    public static final String WEB_GUI_PATH = "web/dist/";

    public static final int MAX_NEIGHBOR_COUNT = 3;
    public static final int TRANSACTION_SIZE_TRITS = Transaction.Field.NONCE.tritOffset + Transaction.Field.NONCE.tritLength;
    public static final int TRANSACTION_SIZE_TRYTES = TRANSACTION_SIZE_TRITS / 3;
    public static final int TRANSACTION_SIZE_BYTES = TRANSACTION_SIZE_TRITS / 9 * 2;
    public static final int PACKET_SIZE_BYTES = TRANSACTION_SIZE_BYTES + Transaction.Field.BRANCH_HASH.byteLength;
    public static final long TIMESTAMP_DIFFERENCE_TOLERANCE_IN_MILLIS = 20000;
    public static final int MAX_AMOUNT_OF_ROUNDS_STORED = 45000; // 60sec/round --> 1 month
    public static final int API_MAX_STATS_PER_NEIGHBOR = 300;

    public static final int CURL_ROUNDS_TRANSACTION_HASH = 27;
    public static final int CURL_ROUNDS_BUNDLE_HASH = 27;

    public static RunModus RUN_MODUS = RunModus.TESTING;
    public static final int MIN_WEIGHT_MAGNITUDE = 3;

    public static final long CHECK_FOR_UPDATES_INTERVAL_MS = 6 *  3600 * 1000;

    /**
     * Specifies through which trit of the transaction hash each flag is defined.
     */
    public static final class HashFlags {
        public static final int BUNDLE_HEAD_FLAG = 1;
        public static final int BUNDLE_TAIL_FLAG = 2;
    }

    public static final class Environments {
        public static final Environment GOSSIP = new Environment("gossip");
        public static final ChainedEnvironment GOSSIP_PREPROCESSOR_CHAIN = new ChainedEnvironment("gossip_chain");
    }

    public enum RunModus {
        MAIN, TESTING, TESTING_BUT_WITH_REAL_MWM
    }
}