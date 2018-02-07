package com.dmytryk.lolgoal.all.Entities;

import java.util.ArrayList;

public class CurrentGameBuilder {
    private ArrayList<CurrentGameParticipant> participants;
    private ArrayList<BannedChampion> bannedChampions;
    private long gameId;
    private long gameStartTime;
    private long mapId;
    private long gameLength;
    private long gameQueueConfigId;
    private String platformId;
    private String gameMod;
    private String gameType;
    private String encryptionKey;

    public CurrentGameBuilder setParticipants(ArrayList<CurrentGameParticipant> participants) {
        this.participants = participants;
        return this;
    }

    public CurrentGameBuilder setBannedChampions(ArrayList<BannedChampion> bannedChampions) {
        this.bannedChampions = bannedChampions;
        return this;
    }

    public CurrentGameBuilder setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public CurrentGameBuilder setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
        return this;
    }

    public CurrentGameBuilder setMapId(long mapId) {
        this.mapId = mapId;
        return this;
    }

    public CurrentGameBuilder setGameLength(long gameLength) {
        this.gameLength = gameLength;
        return this;
    }

    public CurrentGameBuilder setGameQueueConfigId(long gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
        return this;
    }

    public CurrentGameBuilder setPlatformId(String platformId) {
        this.platformId = platformId;
        return this;
    }

    public CurrentGameBuilder setGameMod(String gameMod) {
        this.gameMod = gameMod;
        return this;
    }

    public CurrentGameBuilder setGameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public CurrentGameBuilder setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
        return this;
    }

    public CurrentGame createCurrentGame() {
        return new CurrentGame(participants, bannedChampions, gameId, gameStartTime, mapId,
                gameLength, gameQueueConfigId, platformId, gameMod, gameType, encryptionKey);
    }
}