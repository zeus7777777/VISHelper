package com.mixerbox.hackathon.vis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerInfo extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> positonList;
    private String[] positions = {"Wing Spiker", "Setter", "Middle Blocker", "Opposite", "Libero"};
    private String positionStr;
    static final int RESULT_REMOVE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        spinner = (Spinner) findViewById(R.id.player_position);
        positonList = new ArrayAdapter<String>(PlayerInfo.this, android.R.layout.simple_spinner_item, positions);
        spinner.setAdapter(positonList);

        final int index = getIntent().getIntExtra("INDEX", 0);
        String name = getIntent().getStringExtra("NAME");
        String nickName = getIntent().getStringExtra("NICKNAME");
        String position = getIntent().getStringExtra("POSITION");
        String number = getIntent().getStringExtra("NUMBER");

        if (name.length() != 0) {
            ((EditText) findViewById(R.id.player_name)).setText(name);
        }
        if (nickName.length() != 0) {
            ((EditText)findViewById(R.id.player_nickname)).setText(nickName);
        }
        if (number.length() != 0) {
            ((EditText)findViewById(R.id.player_number)).setText(number);
        }
        if (position.length() != 0) {
            if (position.charAt(0) == 'W')
                spinner.setSelection(0);
            if (position.charAt(0) == 'S')
                spinner.setSelection(1);
            if (position.charAt(0) == 'M')
                spinner.setSelection(2);
            if (position.charAt(0) == 'O')
                spinner.setSelection(3);
            if (position.charAt(0) == 'L')
                spinner.setSelection(4);
        }


        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerName = ((EditText) findViewById(R.id.player_name)).getText().toString();
                String playerNickName = ((EditText) findViewById(R.id.player_nickname)).getText().toString();
                String playerNumber = ((EditText) findViewById(R.id.player_number)).getText().toString();

                Intent intent = new Intent();
                intent.putExtra("_INDEX", index);
                intent.putExtra("_NAME", playerName);
                if (playerNickName.length() == 0) {
                    playerNickName = "" + playerName;
                }
                intent.putExtra("_NICKNAME", playerNickName);

                positionStr = spinner.getSelectedItem().toString();
                positionStr = positionStr.replace(" ", "_");
                positionStr = positionStr.toUpperCase();

                intent.putExtra("_POSITION", positionStr);
                intent.putExtra("_NUMBER", playerNumber);

                if (playerName.length() != 0) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(PlayerInfo.this, "Please input player's name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnRemove = (Button) findViewById(R.id.btn_remove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("_INDEX", index);
                setResult(RESULT_REMOVE, intent);
                finish();
            }
        });

        setTitle("Player Information");
    }
}
