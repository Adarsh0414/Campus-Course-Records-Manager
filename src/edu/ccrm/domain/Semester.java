package edu.ccrm.domain;

public enum Semester {
    SPRING("Spring Semester"),
    SUMMER("Summer Semester"),
    FALL("Fall Semester"),
    WINTER("Winter Semester");

    private final String displayName;

    Semester(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
