package com.dmytryk.lolgoal.all.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmytryk.lolgoal.R;
import com.dmytryk.lolgoal.all.Entities.BannedChampion;
import com.dmytryk.lolgoal.all.Entities.CurrentGame;
import com.dmytryk.lolgoal.all.Entities.CurrentGameBuilder;
import com.dmytryk.lolgoal.all.Entities.CurrentGameParticipant;
import com.dmytryk.lolgoal.all.Entities.Perk;
import com.dmytryk.lolgoal.all.Entities.Summoner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameSearchActivity extends AppCompatActivity {

    private EditText editTextSummonerName;
    private Spinner spinnerServerOptions;
    private Button buttonSearchGame;
    private Button buttonSearchStatistics;

    private TextView textViewTemp;

    private final OkHttpClient httpClient = new OkHttpClient();
    private String summonerName;
    private String hostRegionalEndpoint;

    private String JSONTAG = "JSON_DEBUG";
    private String HTTPTAG = "HTTP_DEBUG";
    private String DEBUGTAG = "DEBUG";

//    private final Pattern summonerNameValidationPattern =
//            Pattern.compile("^[0-9\\\\p{L} _\\\\.]+$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        setOnClickListeners();

        //TODO FAB repeats last request
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(View.INVISIBLE);


    }


    private void makeGameSearchRequest(){

        final String URL = formSummonerNameRequest(summonerName);
        Request request = new Request.Builder().url(URL).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requestFailureExplanation(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                if (!response.isSuccessful()) {
                    unsuccessfulSummonerNameRequestExplanation(response);

                } else {

                    Summoner summoner = successfulSummonerNameRequest(response);

                    //after summonerId is available search of the match begins
                    makeSpectatorRequest(summoner);
                }
            }
        });

    }

    private Summoner successfulSummonerNameRequest(Response response) throws IOException {

        final String successfulResponseJSON;

        successfulResponseJSON = response.body().string();

//                                String tmpSummonerData = "";
        Summoner requestedSummoner = createSummonerFromJSON(successfulResponseJSON);
        //create summoner info
        displaySummonerInfo();


        final String tmpResultingString;
        if (requestedSummoner != null) {
            tmpResultingString = requestedSummoner.readableToString();
        } else {
            tmpResultingString = "Error while parsing";
        }

        //!! temporary
        GameSearchActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewTemp.setText(tmpResultingString);
            }
        });

        return requestedSummoner;


    }

    private void displaySummonerInfo() {

    }

    private Summoner createSummonerFromJSON(String successfulResponseJSON) {
        try {
            JSONObject jsonObject = new JSONObject(successfulResponseJSON);
            String summonerName = jsonObject.getString("name");
            long summonerId = jsonObject.getLong("id");
            long summonerLevel = jsonObject.getLong("summonerLevel");
            int profileIconId = jsonObject.getInt("profileIconId");
            long accountId = jsonObject.getLong("accountId");
            long revisionDate = jsonObject.getLong("revisionDate");
            return  Summoner.getCompleteSummonerInstance(summonerName, profileIconId,
                    summonerLevel, revisionDate, summonerId, accountId);
//                                    summoner = requestedSummoner;

        } catch (JSONException jsonException) {
            Log.d(JSONTAG, "JSON Exception: "
                    + jsonException.getMessage());
            return null;
        }

    }

    private void unsuccessfulSummonerNameRequestExplanation(Response response) {
        // todo switch by response codes explanation
//                                switch (response.code()){
//                                    case 400:
//                                }

        final String unsuccessfulResponse = response.toString();

        GameSearchActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    textViewTemp.setText("UNSUCCESSFUL! "
                            + unsuccessfulResponse);

                    throw new IOException("Unexpected code "
                            + unsuccessfulResponse);
                } catch (IOException e) {
                    Log.d(HTTPTAG, e.getMessage());
                }
            }
        });

    }

    private void requestFailureExplanation(@NonNull IOException e) {
        final String failureMessage = e.getMessage();


        Log.d(HTTPTAG, "Fail. " + e.getMessage());

        GameSearchActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewTemp.setText("FAIL! " + failureMessage);
            }
        });


    }

    private void makeSpectatorRequest(Summoner summoner) {

        if (summoner != null) {
            String spectatorURL = formSpectatorRequest(
                    summoner.getId());

            Log.d(HTTPTAG, "Requesting spectator URL :" + spectatorURL);

            Request spectatorRequest = new Request.Builder()
                    .url(spectatorURL).build();

                    httpClient.newCall(spectatorRequest).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            requestFailureExplanation(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            if(!response.isSuccessful()){
                                unsuccessfulSpectatorExplanation(response);
                            } else {
                                try {
                                    handleSuccessfulSpectatorResponse(response);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
//
//                }
//
////                                textViewTemp.setText("CODE SUCCESS! " + response);
//            }
//        }
//        });
        }
    }

    private void handleSuccessfulSpectatorResponse(Response response) throws IOException {

        final String responseBody = response.body().string();
        Log.d(JSONTAG, responseBody);

        CurrentGame game = createCurrentGameFromJSON(responseBody);

        if (game != null){
            updateUIWithGameInfo(game);

        }
        else {
            updateUIWithErrorExplained();

        }



//
//
//        GameSearchActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                textViewTemp.setText(responseBody);
//
//            }
//        });



    }

    private void updateUIWithErrorExplained() {

    }

    private void updateUIWithGameInfo(CurrentGame game) {

    }


    @Nullable
    private CurrentGame createCurrentGameFromJSON(String successfulResponse) {

        try {
            JSONObject JSONResponse = new JSONObject(successfulResponse);
            long gameId = JSONResponse.getLong("gameId");
            long gameStartTime = JSONResponse.getLong("gameStartTime");
            String platform = JSONResponse.getString("platform");
            String gameMode = JSONResponse.getString("gameMode");
            long mapId = JSONResponse.getLong("mapId");
            String gameType = JSONResponse.getString("gameType");
            long gameLength = JSONResponse.getLong("gameLength");
            long gameQueueConfigId = JSONResponse.getLong("gameQueueConfigId");


            JSONArray participantsJSONArray = JSONResponse.getJSONArray("participants");

            ArrayList<CurrentGameParticipant> participants = new ArrayList<>(10);
            for (int i = 0; i < participantsJSONArray.length(); i++){
                CurrentGameParticipant currentGameParticipant;
                currentGameParticipant =
                        parseParticipantArray(participantsJSONArray.getJSONObject(i));
                participants.add(currentGameParticipant);
            }

            JSONArray bannedChampsJSONArray = JSONResponse.getJSONArray("bannedChampions");
            ArrayList<BannedChampion> bannedChampions = new ArrayList<>(10);
            for (int i = 0; i < bannedChampsJSONArray.length(); i++){

                JSONObject currentBannedChampionJSONObject = bannedChampsJSONArray.getJSONObject(i);
                long championId = currentBannedChampionJSONObject.getLong("championId");
                long teamId = currentBannedChampionJSONObject.getLong("teamId");
                int pickTurn = currentBannedChampionJSONObject.getInt("pickTurn");

                bannedChampions.add(new BannedChampion(championId, pickTurn, teamId));

            }

            //builder instantiate currentgame

            CurrentGameBuilder builder = new CurrentGameBuilder();
            CurrentGame currentGame = builder.setGameId(gameId)
                    .setBannedChampions(bannedChampions).setGameLength(gameLength)
                    .setGameMod(gameMode).setGameQueueConfigId(gameQueueConfigId)
                    .setGameStartTime(gameStartTime).setGameType(gameType).setMapId(mapId)
                    .setParticipants(participants).setPlatformId(platform).createCurrentGame();


            Log.d(HTTPTAG, "RESPONSE : \n" + currentGame.toString());

            return currentGame;


        }catch (JSONException jse){
            Log.d(JSONTAG, jse.getMessage());
        }

        //if game wasnt created
        return null;
    }

    private CurrentGameParticipant parseParticipantArray(JSONObject jobject) throws JSONException {
//        JSONObject jobject = (JSONObject) o;//is this legit?
        long profileIcon = jobject.getLong("profileIcon");
        long championId = jobject.getLong("championId");
        String summonerName = jobject.getString("summonerName");
        boolean isBot = jobject.getBoolean("bot");
        //perks

        JSONObject perks = jobject.getJSONObject("perks");

        Perk perk = Perk.createPerkFromJson(perks);

        long spellOnF = jobject.getLong("spell2Id");
        long teamId = jobject.getLong("teamId");
        long spellOnD = jobject.getLong("spell1Id");
        long summonerId = jobject.getLong("summonerId");

        Summoner summoner = Summoner.getShortenedSummonerInstance(summonerName, summonerId);

        return new CurrentGameParticipant(summoner, championId, isBot, profileIcon, spellOnD,
                spellOnF, perk, teamId);
    }


    private void unsuccessfulSpectatorExplanation(final Response response) {
        if (response.code() == 404){
            Log.d(DEBUGTAG,
                    "Request was successful but summoner is not currently in game (404)");
            GameSearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        textViewTemp.setText("Summoner is not currently in game.");

                        throw new IOException("Unexpected code "
                                + response.body().string());

                    } catch (IOException e) {
                        Log.d(HTTPTAG, e.getMessage());
                    }
                }
            });
        } else {
            GameSearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        textViewTemp.setText("UNSUCCESSFUL! "
                                + response.code());

                        throw new IOException("Unexpected code "
                                + response.body().string());

                    } catch (IOException e) {
                        Log.d(HTTPTAG, e.getMessage());
                    }
                }
            });
        }
    }

    private void setOnClickListeners() {
        buttonSearchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUserInput()){
                    makeGameSearchRequest();
                }
                else {
                    Toast.makeText(GameSearchActivity.this,
                            "Summoner name is invalid", Toast.LENGTH_LONG).show();
                    // make editText red
                }
            }
        });

        buttonSearchStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo ocl
            }
        });

    }

    /** When summonerId is available we can search for summoners current match
     *
     * @return URL for spectator request
     */
    private String formSpectatorRequest(long summonerId) {

        return "https://" + hostRegionalEndpoint + "/lol/spectator/v3/active-games/by-summoner/"
                + summonerId + "?api_key="
                + getResources().getString(R.string.temporary_lol_api_key);
    }

    /**
     *  In order to get summoner current match information
     *  first we need to know summoner`s account id
     *
     *  @return URL for summoner name
     */
    private String formSummonerNameRequest(String summonerName) {
        String host;
        switch (spinnerServerOptions.getSelectedItemPosition()) {
            case 0:
                host = "euw1.api.riotgames.com";
                break;
            case 1:
                host = "ru.api.riotgames.com";
                break;
            case 2:
                host = "eun1.api.riotgames.com";
                break;
            case 3:
                host = "na1.api.riotgames.com";
                break;
            case 4:
                host = "oc1.api.riotgames.com";
                break;
            case 5:
                host = "br1.api.riotgames.com";
                break;
            case 6:
                host = "la1.api.riotgames.com";
                break;
            case 7:
                host = "la2.api.riotgames.com";
                break;
            case 8:
                host = "tr1.api.riotgames.com";
                break;
            case 9:
                host = "jp1.api.riotgames.com";
                break;
            case 10:
                host = "pbe1.api.riotgames.com";
                break;
            default:
                host = "euw1.api.riotgames.com";
                break;
        }

        hostRegionalEndpoint = host;

        String requestURL = "https://" + host + "/lol/summoner/v3/summoners/by-name/"
                + summonerName + "?api_key="
                + getResources().getString(R.string.temporary_lol_api_key);

        Log.d(HTTPTAG, "Request URL is " + requestURL);

        return requestURL;

    }

    private boolean checkUserInput() {
        summonerName = editTextSummonerName.getText().toString();
        Log.d(DEBUGTAG, "Summoner name is " + summonerName);
        return  !summonerName.equals("");
    }


    private void initUI() {
        textViewTemp = (TextView) findViewById(R.id.tv_tmp);
        editTextSummonerName = (EditText) findViewById(R.id.et_summoner_name);
        spinnerServerOptions = (Spinner) findViewById(R.id.spinner_server_options);
        buttonSearchGame = (Button) findViewById(R.id.btn_search);
        buttonSearchStatistics = (Button) findViewById(R.id.btn_search_statistics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //FIXME make real menu options
            Toast.makeText(this, "Ну нажав ти сюда і шо?",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
