package com.dmytryk.lolgoal.all.Entities;

/**
 * Current game participant consists of summoner, champion and game info
 */

public class CurrentGameParticipant {

    private Summoner summoner;
    private Champion champion;
    private Boolean isBot = false;
    private long profileIconId;
    //customization
    //perks
    private long summonerSpellOnD;
    private long summonerSpellOnF;
    private long teamId;


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
