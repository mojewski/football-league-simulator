package com.github.mojewski.footballleaguesimulator.service.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class Calendar {

    private LocalDate currentDate;
    private boolean isTransferWindowActive;

    public Calendar() {
        this.currentDate = LocalDate.of(2026, Month.JULY, 1);
    }

    public void advanceDay() {
        this.currentDate = this.currentDate.plusDays(1);
    }

    public void startNextSeason() {
        this.currentDate = LocalDate.of(currentDate.getYear() + 1, Month.JULY, 1);
    }

    public void skipToDate(int year, Month month, int day) {
        this.currentDate = LocalDate.of(year, month, day);
    }

    public LocalDate getFirstMatchdayDate() {
        int year = currentDate.getYear();
        return LocalDate.of(year, Month.AUGUST, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
    }

    public LocalDate getNextValidMatchFriday(LocalDate currentFriday) {
        LocalDate nextFriday = currentFriday.plusWeeks(1);

        while (isDateInBlacklist(nextFriday)) {
            nextFriday = nextFriday.plusWeeks(1);
        }

        return nextFriday;
    }

    private boolean isDateInBlacklist(LocalDate date) {
        boolean isChristmasBreak = (date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 20)
                || (date.getMonth() == Month.JANUARY && date.getDayOfMonth() <= 6);

        boolean isWinterBreak = (date.getMonth() == Month.JANUARY || date.getMonth() == Month.FEBRUARY);

        return isChristmasBreak || isWinterBreak;
    }

    public boolean isWeekend() {
        DayOfWeek day = currentDate.getDayOfWeek();
        return day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isTransferWindowActive() {
        Month month = currentDate.getMonth();
        return month == Month.JULY || month == Month.AUGUST || month == Month.JANUARY;
    }

    public void setTransferWindowActive(boolean transferWindowActive) {
        this.isTransferWindowActive = transferWindowActive;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
}