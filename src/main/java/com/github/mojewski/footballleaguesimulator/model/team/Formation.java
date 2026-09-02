package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Position;

public enum Formation {
    F_4_3_3("4-3-3", 1, 4, 3, 3),
    F_4_4_2("4-4-2", 1, 4, 4, 2),
    F_4_5_1("4-5-1", 1, 4, 5, 1),
    F_3_5_2("3-5-2", 1, 3, 5, 2),
    F_3_4_3("3-4-3", 1, 3, 4, 3),
    F_5_2_3("5-3-2", 1, 5, 3, 2),
    F_5_3_2("5-3-2", 1, 5, 3, 2);

    private final String displayName;
    private final int goalkeeper;
    private final int defenders;
    private final int midfielders;
    private final int forwards;

    Formation(String displayName, int goalkeeper, int defenders, int midfielders, int forwards) {
        this.displayName = displayName;
        this.goalkeeper = goalkeeper;
        this.defenders = defenders;
        this.midfielders = midfielders;
        this.forwards = forwards;
    }

    public int getRequiredCount(Position position) {
        return switch(position) {
            case GOALKEEPER -> goalkeeper;
            case DEFENDER -> defenders;
            case MIDFIELDER -> midfielders;
            case FORWARD -> forwards;
        };
    }

    public String getDisplayName() { return displayName; }
    public int getGoalkeeper() { return goalkeeper;}
    public int getDefenders() { return defenders; }
    public int getMidfielders() { return midfielders; }
    public int getForwards() { return forwards; }
}
