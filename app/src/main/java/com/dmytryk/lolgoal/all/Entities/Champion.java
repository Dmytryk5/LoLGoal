package com.dmytryk.lolgoal.all.Entities;

/**
 * Ingame Champion
 */

public class Champion {
    protected long championId;
    protected String name = "";

    public Champion(String name, long championId){
        this.name = name;
        this.championId = championId;
    }

    public Champion(long championId){
        this.championId = championId;
    }


}
