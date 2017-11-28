package com.dmytryk.lolgoal.all.Entities;

/**
 * Created by dmytryk on 28.11.17.
 */

public class CurrentGameParticipant {

    private Summoner summoner;
    private Champion champion;
    private Boolean isBot = false;

    CurrentGameParticipant(Summoner summoner, Champion champion, Boolean bot){
        this.summoner = summoner;
        this.champion = champion;
        this.isBot = bot;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public Champion getChampion() {
        return champion;
    }

    public Boolean isBot() {
        return isBot;
    }

}
