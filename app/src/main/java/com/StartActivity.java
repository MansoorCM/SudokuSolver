package com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sudokusolver.MainActivity;
import com.example.sudokusolver.R;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_action_name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE );
        getSupportActionBar().setIcon(R.drawable.ic_action_name);
    }
    public void sudokuActivity(View view)
    {

        Intent intent=new Intent(this, MainActivity.class);
        if(view.getId()==R.id.newpuzzle)
        {
            intent.putExtra("choice",false);
        }else
        {
            intent.putExtra("choice",true);
        }
        startActivity(intent);
    }
}
