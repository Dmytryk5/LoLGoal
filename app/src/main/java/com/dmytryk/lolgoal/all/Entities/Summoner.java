package com.dmytryk.lolgoal.all.Entities;

/**
 * Summoner is player at League of Legends. All properties are requested by summoner name.
 */

public class Summoner {

    private String name;
    private long profileIconId;
    private long summonerLevel;
    private long revisionDate;
    private long id;
    private long accountId;
    private boolean isFull;

    public static Summoner getCompleteSummonerInstance(String name, long profileIconId,
                                                       long summonerLevel, long revisionDate,
                                                       long id, long accountId) {
        return new Summoner(name, profileIconId, summonerLevel, revisionDate,
                id, accountId);
    }

    public static Summoner getShortenedSummonerInstance(String name, long id) {
        return new Summoner(name, id);
    }

    public static Summoner getShortenedSummonerInstance(String name, long id, long profileIconId) {
        return new Summoner(name, id, profileIconId);
    }

    private Summoner(String name, long profileIconId, long summonerLevel, long revisionDate,
                    long id, long accountId) {
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.id = id;
        this.accountId = accountId;
        isFull = true;
    }
    
    private Summoner(String name, long summonerId){
        this.name = name;
        this.id = summonerId;
        isFull = false;
    }

    private Summoner(String name, long summonerId, long profileIcon){
        this.name = name;
        this.id = summonerId;
        this.profileIconId = profileIcon;
        isFull = false;
    }

    public void makeSummonerComplete(){
        if (isFull){
            return;
        }
        else {
            //todo request by name to fill all fields
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Summoner summoner = (Summoner) o;

        if (profileIconId != summoner.profileIconId) return false;
        if (summonerLevel != summoner.summonerLevel) return false;
        if (revisionDate != summoner.revisionDate) return false;
        if (id != summoner.id) return false;
        if (accountId != summoner.accountId) return false;
        return name.equals(summoner.name);
    }


    public String getName() {
        return name;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }




    public String readableToString(){
        return "Summoner name : " + name +
                " Summoner level : " + summonerLevel +
                " Profile icon ID : " + profileIconId +
                " Summoner ID : " + id;
    }
}
