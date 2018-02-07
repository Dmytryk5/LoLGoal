package com.dmytryk.lolgoal.all.Entities;

/**
 * Summoner is player at League of Legends. All properties are requested by summoner name.
 */

public class Summoner {

    private String name;
    private int profileIconId;
    private long summonerLevel;
    private long revisionDate;
    private long id;
    private long accountId;


    public Summoner(String name, int profileIconId, long summonerLevel, long revisionDate,
                    long id, long accountId) {
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.id = id;
        this.accountId = accountId;
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

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + profileIconId;
        result = 31 * result + (int) (summonerLevel ^ (summonerLevel >>> 32));
        result = 31 * result + (int) (revisionDate ^ (revisionDate >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (accountId ^ (accountId >>> 32));
        return result;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
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
