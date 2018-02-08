package com.dmytryk.lolgoal.all.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    public static Perk createPerkFromJson(JSONObject perkJSON) throws JSONException{
        long perkStyle = perkJSON.getLong("perkStyle");
        long perkSubStyle = perkJSON.getLong("perkSubStyle");
        JSONArray perkIds = perkJSON.getJSONArray("perkIds");
        ArrayList<Long> runeIds = new ArrayList<>();

        for (int i = 0; i < perkIds.length(); i++ ){
            runeIds.add(perkIds.getLong(i));
        }

        return new Perk(perkStyle, perkSubStyle, runeIds);

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
