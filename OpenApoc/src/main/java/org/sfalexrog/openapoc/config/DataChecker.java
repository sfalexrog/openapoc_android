package org.sfalexrog.openapoc.config;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey on 08.12.2015.
 */
public enum DataChecker {
    INSTANCE;

    private Set<String> knownHashes;

    DataChecker() {
        if (knownHashes == null) {
            knownHashes = new HashSet<>();
            knownHashes.add("1b474249cd99a771e996d2c1e22dcc0d");
        }
    }

    public boolean isValidData(String dataHash) {
        return knownHashes.contains(dataHash);
    }

}
