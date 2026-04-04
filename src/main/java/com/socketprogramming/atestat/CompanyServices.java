package com.socketprogramming.atestat;

public enum CompanyServices {

    MEDICAL("Servicii Medicale"),
    AUTO("Servicii Auto");

    private String displayName;

    CompanyServices(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

//TODO trebuie adaugate mult mai multe servicii
