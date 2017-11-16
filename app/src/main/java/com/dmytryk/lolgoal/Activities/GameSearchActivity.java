package com.dmytryk.lolgoal.Activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmytryk.lolgoal.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameSearchActivity extends AppCompatActivity {

    EditText editTextSummonerName;
    Spinner spinnerServerOptions;
    Button buttonSearchGame;
    Button buttonSearchStatistics;

    TextView textViewTemp;

    OkHttpClient httpClient = new OkHttpClient();
    String summonerName;

    private final Pattern summonerNameValidationPattern =
            Pattern.compile("^[0-9\\\\p{L} _\\\\.]+$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        setOnClickListeners();

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

    private void setOnClickListeners() {
        buttonSearchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUserInput()){
                    final String URL = formSummonerNameRequest();
                    Request request = new Request.Builder().url(URL).build();

                    httpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            final String failureMessage = e.getMessage();

                            GameSearchActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textViewTemp.setText("FAIL! " + failureMessage);
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (!response.isSuccessful()){

                                final String unsuccessfulResponse = response.toString();

                                GameSearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            textViewTemp.setText("UNSUCCESSFUL! "
                                                    + unsuccessfulResponse);
                                            throw new IOException("Unexpected code "
                                                    + unsuccessfulResponse);
                                        } catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            else {

                                final String successfulResponse = response.body().string();

                                GameSearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewTemp.setText(successfulResponse);
                                    }
                                });

                                textViewTemp.setText("CODE SUCCESS! " + response);
                            }
                        }
                    });
                }

                else {
                    Toast.makeText(GameSearchActivity.this,
                            "Summoner name is invalid", Toast.LENGTH_LONG);
                    // make edittext red
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

    /**
     *  In order to get summoner current match information
     *  first we need to know summoner`s account id
     */
    private String formSummonerNameRequest() {
        String host;
        switch (spinnerServerOptions.getSelectedItemPosition()){
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
                host ="euw1.api.riotgames.com";
                break;
        }

        String requestURL = "https://" + host + "/lol/summoner/v3/summoners/by-name/"
                + summonerName + "?api_key="
                + getResources().getString(R.string.temporary_lol_api_key);

        return requestURL;

    }

    private boolean checkUserInput() {
        summonerName = editTextSummonerName.getText().toString();
        if (summonerName != null && summonerName != "") {
            Matcher summonerNameValidationMatcher =
                    summonerNameValidationPattern.matcher(summonerName);
            if (summonerNameValidationMatcher.matches())
                return true;
            else
                return false;
        }
        else return false;
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
            Toast.makeText(this, "Ну нажав ти сюда і шо?",Toast.LENGTH_LONG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
