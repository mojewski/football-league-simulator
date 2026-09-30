package com.github.mojewski.footballleaguesimulator.service.match;

public record StoppageTime(int firstHalf, int secondHalf) {
    public int total() {
        return firstHalf + secondHalf;
    }
}