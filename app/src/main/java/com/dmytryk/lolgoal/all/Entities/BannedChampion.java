package com.dmytryk.lolgoal.all.Entities;

/**
 * Created by dmytryk on 28.11.17.
 */

public class BannedChampion extends Champion {

    int pickTurn;
    long teamId;

    BannedChampion(long championId, int pickTurn, long teamId){
        super(championId);
        this.pickTurn = pickTurn;
        this.teamId = teamId;
    }

}
