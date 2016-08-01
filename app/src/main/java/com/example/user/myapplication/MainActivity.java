package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Handler;

import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,TextView.OnEditorActionListener {

    TextView infoText;
    RadioGroup optionGrp;
    EditText name_editText;
    int selectedOptionIndex = 0;
    Button confirm_button;
    String[] pokemonNames = new String[]{
        "小火龍","傑尼龜","妙蛙種子"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirm_button = (Button)findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(this);

        optionGrp = (RadioGroup) findViewById(R.id.optionsGroup);
        optionGrp.setOnCheckedChangeListener(this);

        infoText = (TextView) findViewById(R.id.infoText);
        name_editText = (EditText) findViewById(R.id.name_editText);
        name_editText.setOnEditorActionListener(this);
        name_editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    public final static String optionSelectedKey = "selectedOption";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(optionSelectedKey, selectedOptionIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedOptionIndex = savedInstanceState.getInt(optionSelectedKey, 0);
        ((RadioButton)optionGrp.getChildAt(selectedOptionIndex)).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("testStage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("testStage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("testStage", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("testStage", "onDestroy");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirm_button) {

            int changeActivityInSecs = 3;
            infoText.setText(String.format("你好, 訓練家%s 歡迎來到神奇寶貝的世界, 你的夥伴是%s, 冒險將於%d秒後開始",
                    name_editText.getText().toString(),
                    pokemonNames[selectedOptionIndex],
                    changeActivityInSecs));

            Handler handler = new Handler(MainActivity.this.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, PokemonListActivity.class);
                    startActivity(intent);
                }
            }, changeActivityInSecs * 1000);

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int radioGrpID = group.getId();
        if(radioGrpID == R.id.optionsGroup) {
            switch(checkedId) {
                case R.id.option1:
                    selectedOptionIndex = 0;
                    break;
                case R.id.option2:
                    selectedOptionIndex = 1;
                    break;
                case R.id.option3:
                    selectedOptionIndex = 2;
                    break;
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(),0);

            confirm_button.performClick();
            return true;
        }

        return false;

    }
}
