package com.dmytryk.lolgoal.all.Entities;

public class BannedChampion extends Champion {

    int pickTurn;
    long teamId;

    public BannedChampion(long championId, int pickTurn, long teamId){
        super(championId);
        this.pickTurn = pickTurn;
        this.teamId = teamId;
    }


}
