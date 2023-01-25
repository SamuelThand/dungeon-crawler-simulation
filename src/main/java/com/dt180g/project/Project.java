package com.dt180g.project;

/**
 * The main starting point for Project.
 * Responsible for creation of game running entity and trigger its simulation.
 * @author Erik Ström
 */
public final class Project {
    private Project() { }

    /**
     * Create game running entity and trigger its simulation.
     * @param args command arguments. Not relevant here.
     */
    public static void main(final String... args) {
        new GameRunner().runGame();
    }
}
