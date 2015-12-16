package com.bidasco;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Michael Woidak
 */
public enum Proposal {
    ZAENKER("Fleischerei Zänker"),

    ASIA("Asia Food"),

    DOENER("Döner"),

    HENNER("Henner Sandwiches"),

    SUBWAY("Subway Sandwiches"),

    EAT("Eat"),

    MATHILDA_PASTA("Mathilda Pasta"),

    ALLES_PASST_DA("Alles Passt Da"),

    COGNITO("Cognito"),

    KOCHEN("Selber Kochen");

    private static final List<Proposal> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private final String name;

    Proposal(String s) {
        name = s;
    }

    public static Proposal getRandomProposal() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
