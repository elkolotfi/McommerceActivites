package com.clientui.enums;

public enum Etats {

    PREPARE(0, "En préparation"), EXPDITED(1, "Expédiée"), DELIVERED(2, "Livrée");

    int val;
    public String name;

    Etats(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public static Etats Etat(int val) {
        for(Etats e : Etats.values()){
            if(e.val == val) return e;
        }

        return null;
    }
}
