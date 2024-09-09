package com.enble.auth.util;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidFactory;

public class IdGenerator {

    public static final int DEFAULT_NODE_ID = 0;
    public static final int DEFAULT_NODE_BITS = 16;

    private final TsidFactory factory;

    private IdGenerator() {
        int nodeId;
        try {
            nodeId = Integer.parseInt(System.getenv("TSID_NODE_ID"));
        } catch (NumberFormatException | NullPointerException | SecurityException e) {
            nodeId = DEFAULT_NODE_ID;
        }

        this.factory = TsidFactory.builder()
                .withNodeBits(DEFAULT_NODE_BITS)
                .withNode(nodeId)
                .build();
    }

    protected static IdGenerator newInstance() {
        return new IdGenerator();
    }

    public Tsid generate() {
        return factory.create();
    }
}
