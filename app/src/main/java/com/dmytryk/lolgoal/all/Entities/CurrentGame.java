package com.dmytryk.lolgoal.all.Entities;


import java.util.ArrayList;

/**
 * Current summoner`s game consists of list of participants, banned champions and other game info
 */

public class CurrentGame {
    ArrayList<CurrentGameParticipant> participants = null;
    ArrayList<BannedChampion> bannedChampions = null;

    //current game info
    long gameId;
    long gameStartTime;
    long mapId;
    long gameLength;
    long gameQueueConfigId;
    String platformId;
    String gameMod;
    String gameType;
    boolean isBuilt = false;
    //observers with encryption key
    //customization objects

    CurrentGame(ArrayList<CurrentGameParticipant> participants,
                ArrayList<BannedChampion> bannedChampions,
                long gameId, long gameStartTime,
                long mapId, long gameLength,
                long gameQueueConfigId,
                String platformId, String gameMod,
                String gameType) {
        this.participants = participants;
        this.bannedChampions = bannedChampions;
        this.gameId = gameId;
        this.gameStartTime = gameStartTime;
        this.mapId = mapId;
        this.gameLength = gameLength;
        this.gameQueueConfigId = gameQueueConfigId;
        this.platformId = platformId;
        this.gameMod = gameMod;
        this.gameType = gameType;
        this.isBuilt = true;
    }






}
