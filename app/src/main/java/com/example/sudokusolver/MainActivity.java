package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[][] board=new String[][]{{"5","3","4","6","7","8","9","1","2"},{"6","7","2","1","9","5","3","4","8"}
        ,{"1","9","8","3","4","2","5","6","7"},{"8","5","9","7","6","1","4","2","3"}
        ,{"4","2","6","8","5","3","7","9","1"},{"7","1","3","9","2","4","8","5","6"}
        ,{"9","6","1","5","3","7","2","8","4"},{"2","8","7","4","1","9","6","3","5"}
        ,{"3","4","5","2","8","6","1","7","9"}};
        GridLayout sudoku=findViewById(R.id.gridlayout);
        for(int i=0;i<9;i++)
            for (int j=0;j<9;j++)
            {
                index=i*9+j;
                textView= (TextView) sudoku.getChildAt(index);
                textView.setText(board[i][j]);
            }

        

    }
}
