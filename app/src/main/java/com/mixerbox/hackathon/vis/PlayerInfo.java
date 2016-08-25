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

public class PlayerInfo extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> positonList;
    private String[] positions = {"Wing Spiker", "Setter", "Middle Blocker", "Opposite", "Libero"};
    private String positionStr;

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
                Intent intent = new Intent();
                intent.putExtra("_INDEX", index);
                intent.putExtra("_NAME", ((EditText) findViewById(R.id.player_name)).getText().toString());
                intent.putExtra("_NICKNAME", ((EditText) findViewById(R.id.player_nickname)).getText().toString());

                positionStr = spinner.getSelectedItem().toString();
                positionStr = positionStr.replace(" ", "_");
                positionStr = positionStr.toUpperCase();

                intent.putExtra("_POSITION", positionStr);
                intent.putExtra("_NUMBER", ((EditText) findViewById(R.id.player_number)).getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        setTitle("Player Information");
    }
}
