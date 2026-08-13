package com.github.mojewski.footballleaguesimulator.model;

/**
 * Represents countries/nationalities available in simulator.
 * Used both for League location and Player nationality
 */
public enum Country {

    POLAND("Polska", "POL"),
    ENGLAND("Anglia", "ENG"),
    SPAIN("Hiszpania", "ESP"),
    GERMANY("Niemcy", "GER"),
    ITALY("Włochy", "ITA"),
    FRANCE("Francja", "FRA"),
    PORTUGAL("Portugalia", "POR"),
    NETHERLANDS("Holandia", "NED"),
    BELGIUM("Belgia", "BEL"),
    ARGENTINA("Argentyna", "ARG"),
    BRAZIL("Brazylia", "BRA"),
    URUGUAY("Urugwaj", "URU"),
    COLOMBIA("Kolumbia", "COL"),
    CROATIA("Chorwacja", "CRO"),
    SERBIA("Serbia", "SRB"),
    SWITZERLAND("Szwajcaria", "SUI"),
    AUSTRIA("Austria", "AUT"),
    DENMARK("Dania", "DEN"),
    NORWAY("Norwegia", "NOR"),
    SWEDEN("Szwecja", "SWE"),
    TURKEY("Turcja", "TUR"),
    GREECE("Grecja", "GRE"),
    SCOTLAND("Szkocja", "SCO"),
    JAPAN("Japonia", "JPN"),
    SOUTH_KOREA("Korea Południowa", "KOR"),
    USA("USA", "USA"),
    MEXICO("Meksyk", "MEX"),
    NIGERIA("Nigeria", "NGA"),
    SENEGAL("Senegal", "SEN"),
    MOROCCO("Maroko", "MAR");

    /** Full country name in Polish */
    private final String displayName;
    /** Official FIFA code for countries */
    private final String code;

    Country(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() { return displayName; }
    public String getCode() { return code; }

}
