package com.github.mojewski.footballleaguesimulator.model.match;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MatchPresenter {
    private static final int DEFAULT_DELAY = 200;

    public void presentEventsByMinute(MatchStats stats) {
        presentEventsByMinute(stats, DEFAULT_DELAY);
    }

    public void presentEventsByMinute(MatchStats stats, int delay) {
        List<MatchEvent> events = stats.getEvents();

        int firstHalfEnd = 45 + stats.getFirstHalfStoppage();
        int secondHalfEnd = 90 + stats.getSecondHalfStoppage();

        log.info("ROZPOCZĘCIE MECZU");

        for(int i = 1; i <= firstHalfEnd; i++) {
            logEvent(i, events);
            sleepUnchecked(delay);
        }

        log.info("PRZERWA");
        sleepUnchecked(1000);

        for(int i = 46; i <= secondHalfEnd; i++) {
            logEvent(i, events);
            sleepUnchecked(delay);
        }

        log.info("KONIEC MECZU (Wynik: {} - {}", stats.getHomeGoals(), stats.getAwayGoals());
    }

    private void logEvent(int minute, List<MatchEvent> events) {
        List<MatchEvent> eventsInMinute = events.stream()
                .filter(event -> event.minute() == minute)
                .toList();

        for (MatchEvent event : eventsInMinute) {
            log.info(formatEvent(event));
        }
    }

    private String formatEvent(MatchEvent event) {
        String teamName = event.team().getName();
        String primaryPlayer = event.primaryPlayer() != null ? event.primaryPlayer().getName() : "";
        String secondaryPlayer = event.secondaryPlayer() != null ? event.secondaryPlayer().getName() : "";

        return switch (event.type()) {
            case GOAL -> String.format("[%2d'] GOL (%s) - %s%s",
                    event.minute(), teamName, primaryPlayer,
                    !secondaryPlayer.isBlank() ? " (Asysta: " + secondaryPlayer + ")" : "");

            case YELLOW_CARD -> String.format("[%2d'] Żółta kartka (%s) - %s",
                    event.minute(), teamName, primaryPlayer);

            case RED_CARD -> String.format("[%2d'] CZERWONA KARTKA (%s) - %s",
                    event.minute(), teamName, primaryPlayer);

            case SUBSTITUTION -> String.format("[%2d'] Zmiana (%s) - Zszedł: %s, Wszedł: %s",
                    event.minute(), teamName, primaryPlayer, secondaryPlayer);

            case INJURY -> String.format("[%2d'] Kontuzja (%s) - %s",
                    event.minute(), teamName, primaryPlayer);
        };
    }

    private void sleepUnchecked(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Prezentacja meczu została przerwana", e);
        }
    }
}
