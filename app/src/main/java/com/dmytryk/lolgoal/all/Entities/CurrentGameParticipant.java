package com.dmytryk.lolgoal.all.Entities;

/**
 * Current game participant consists of summoner, champion and game info
 */

public class CurrentGameParticipant {

    private Summoner summoner;
    private Champion champion;
    private long championId;
    private boolean isBot = false;
    private long profileIconId;
    private Perk perk;
    //customization
    //perks
    private long summonerSpellOnD;
    private long summonerSpellOnF;
    private long teamId; //values are 100 and 200


    public CurrentGameParticipant(Summoner summoner, Champion champion, boolean bot){
        this.summoner = summoner;
        this.champion = champion;
        this.isBot = bot;
    }

    public CurrentGameParticipant(Summoner summoner,
                                  long championId,
                                  boolean isBot,
                                  long profileIconId,
                                  long summonerSpellOnD,
                                  long summonerSpellOnF,
                                  Perk perk,
                                  long teamId) {
        this.summoner = summoner;
        this.championId = championId;
        this.isBot = isBot;
        this.profileIconId = profileIconId;
        this.summonerSpellOnD = summonerSpellOnD;
        this.summonerSpellOnF = summonerSpellOnF;
        this.perk = perk;
        this.teamId = teamId;
        this.champion = new Champion(championId);
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
