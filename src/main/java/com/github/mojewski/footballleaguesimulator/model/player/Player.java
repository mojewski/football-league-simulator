package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.state.PlayerState;
import com.github.mojewski.footballleaguesimulator.model.player.state.RetiredState;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class Player {
    //TODO: forma wplywajaca na effectiveoverall, transferservice
    private Long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private int form;

    private final Country country;
    private final Position position;

    private final PlayerAttributes attributes;
    private int overall;
    private final PlayerStats stats;

    private boolean isForSale;

    private Team team;
    private final int injuryChance;
    private PlayerState currentState;

    private int stamina;
    private PlayerContract contract;

    public Player(PlayerBuilder builder) {
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.injuryChance = builder.getInjuryChance();
        this.country = builder.getCountry();
        this.position = builder.getPosition();
        this.age = builder.getAge();
        this.team = builder.getTeam();
        this.currentState = builder.getCurrentState();
        this.attributes = builder.getAttributes();
        this.stats = builder.getStats();
        this.stamina = builder.getStamina();
        this.contract = builder.getContract();
        recalculateOverall();
    }

    public void recalculateOverall() {
        if (this.attributes != null && this.position != null) {
            this.overall = this.attributes.calculateOverall(this.position);
        }
    }

    public int getEffectiveOverall() {
        if (this.attributes == null || this.position == null) {
            return 0;
        }
        return this.attributes.calculateEffectiveOverall(this.position, this.stamina);
    }

    public void setState(PlayerState state) {
        this.currentState = state;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean decideRetirement(int randomRoll) {
        if (isRetired()) return true;
        if (randomRoll <= getRetiredProbability()) {
            this.currentState = new RetiredState();
        }
        return isRetired();
    }

    private int getRetiredProbability() {
        if (this.age < 32) return 0;
        if (this.age <= 35) return 10;
        if (this.age <= 38) return 50;
        if (this.age <= 40) return 70;
        return 100;
    }

    public boolean hasActiveContract() {
        return contract != null && !contract.isExpired();
    }

    public void signContract(PlayerContract newContract, Team newTeam) {
        this.contract = newContract;
        if (this.team != null && this.team != newTeam) {
            this.team.removePlayer(this);
        }
        if (newTeam != null) {
            newTeam.addPlayer(this);
        }
    }

    public void terminateContract() {
        this.contract = null;
        if (this.team != null) {
            Team oldTeam = this.team;
            this.team = null;
            oldTeam.removePlayer(this);
        }
    }

    public void setForSale(boolean forSale) {
        this.isForSale = forSale;
    }

    public void setStamina(int stamina) {
        this.stamina = Math.min(99, Math.max(1, stamina));
    }

    public void updateStamina(int staminaModifier) {
        setStamina(this.stamina + staminaModifier);
    }

    public void regenerateStamina(int amount) {
        updateStamina(Math.abs(amount));
    }

    public void drainStamina(int amount) {
        updateStamina(-Math.abs(amount));
    }

    public int getEffectiveInjuryChance() {
        if (this.stamina >= 60) {
            return this.injuryChance;
        }

        double exhaustionFactor = (60.0 - this.stamina) / 60.0;
        double multiplier = 1.0 + (exhaustionFactor * 2.0);

        return (int) Math.round(this.injuryChance * multiplier);
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getInjuryChance() { return injuryChance; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
    public int getForm() { return form; }
    public boolean isForSale() { return isForSale; }
    public boolean isRetired() { return currentState instanceof RetiredState; }
    public Team getTeam() { return team; }
    public PlayerState getCurrentState() { return currentState; }
    public PlayerAttributes getAttributes() { return attributes; }
    public PlayerStats getStats() { return stats; }
    public int getStamina() { return stamina; }
    public PlayerContract getContract() { return contract; }
    public int getOverall() { return overall; }
}