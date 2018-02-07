package com.dmytryk.lolgoal.all.Entities;

import java.util.ArrayList;

/**
 * Runes reforged
 */

public class Perk {
    private long primaryRunePath;
    private long additionalRunePath;
    private ArrayList<Long> runeIdList;

    public Perk(long primaryRunePath, long secondaryRunePath, ArrayList<Long> runeIds){
        this.additionalRunePath = secondaryRunePath;
        this.primaryRunePath = primaryRunePath;
        this.runeIdList = runeIds;
    }


    public long getPrimaryRunePath() {
        return primaryRunePath;
    }

    public long getAdditionalRunePath() {
        return additionalRunePath;
    }

    public ArrayList<Long> getRuneIdList() {
        return runeIdList;
    }


}
