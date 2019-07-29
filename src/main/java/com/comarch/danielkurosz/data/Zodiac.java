package com.comarch.danielkurosz.data;

public enum Zodiac {

    ARIES(new DayOfYear(3, 21), new DayOfYear(4, 20), "aries"),
    TAURUS(new DayOfYear(4, 21), new DayOfYear(5, 21), "taurus"),
    GEMINI(new DayOfYear(5, 22), new DayOfYear(6, 21), "gemini"),
    CANCER(new DayOfYear(6, 22), new DayOfYear(7, 22), "cancer"),
    LEO(new DayOfYear(7, 23), new DayOfYear(8, 22), "leo"),
    VIRGO(new DayOfYear(8, 23), new DayOfYear(9, 23), "virgo"),
    LIBRA(new DayOfYear(9, 24), new DayOfYear(10, 23), "libra"),
    SCORPIO(new DayOfYear(10, 24), new DayOfYear(11, 22), "scorpio"),
    SAGITTARIUS(new DayOfYear(11, 23), new DayOfYear(12, 21), "sagittarius"),
    CAPRICORN(new DayOfYear(12, 22), new DayOfYear(1, 20), "capricorn"),
    AQUARIUS(new DayOfYear(1, 21), new DayOfYear(2, 19), "aquarius"),
    PISCES(new DayOfYear(2, 20), new DayOfYear(3, 20), "pisces");

    private DayOfYear from;
    private DayOfYear to;
    private int key;
    private String value;

    private Zodiac(DayOfYear from, DayOfYear to, String value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    private static class DayOfYear {
        private int day;
        private int month;

        public DayOfYear(int day, int month) {
            this.day = day;
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }
    }
}


