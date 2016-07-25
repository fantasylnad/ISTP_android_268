package com.example.user.myapplication.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lab430 on 16/7/24.
 */
public class OwningPokemonDataManager {
    Context mContext;
    Resources mRes;
    String packageName;
    ArrayList<PokemonInfo> pokemonInfos;
    ArrayList<String> pokemonNames;

    public OwningPokemonDataManager(Context context) {
        mContext = context;
        mRes = mContext.getResources();
        packageName = context.getPackageName();
        loadTestingData();

        pokemonNames = new ArrayList<>();
        for(PokemonInfo pokemonInfo : pokemonInfos) {
            pokemonNames.add(pokemonInfo.name);
        }
    }

    private void loadTestingData() {
        pokemonInfos = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(mContext.getAssets().open("pokemon_data.csv"));
            BufferedReader reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dataFields = line.split(",");
                PokemonInfo pokemonInfo = new PokemonInfo();
                pokemonInfo.imgId = mRes.getIdentifier(dataFields[0],"drawable",packageName);
                pokemonInfo.name = dataFields[1];
                pokemonInfo.level = Integer.valueOf(dataFields[2]);
                pokemonInfo.currentHP = Integer.valueOf(dataFields[3]);
                pokemonInfo.maxHP = Integer.valueOf(dataFields[4]);
                pokemonInfos.add(pokemonInfo);
            }
        }
        catch(Exception e) {
            Log.d("testCsv", e.getLocalizedMessage());
        }


    }


    public ArrayList<String> getPokemonNames() {
        return pokemonNames;
    }

    public ArrayList<PokemonInfo> getPokemonInfos() {
        return pokemonInfos;
    }


}
