package com.dmytryk.lolgoal.all.Entities;

import java.util.ArrayList;

/**
 * Current summoner`s game
 */

public class Game {
    ArrayList<Summoner> participants = null;
    ArrayList<Champion> bannedChampions = null;
    long gameId;
    long gameStartTime;
    long mapId;
    long gameLength;
    long gameQueueConfigId;
    String platformId;
    String gameMod;
    String gameType;
    String encryptionKey;




}
