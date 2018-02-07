package com.dmytryk.lolgoal.all.Entities;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Describes summoner league in both ranked queues
 *
 */
public class SummonersLeague {
    private Summoner summoner;
    private League rankedFlex;
    private League rankedSoloDuo;
    private SummonersLeague(){}

    public SummonersLeague getInstance(JSONObject jsonObject){
        return null;
    }


    private class League {
        private final int QUEUE_RANKED_SOLO_DUO = 0;
        private final int QUEUE_RANKED_FLEX = 1;


    }
}
