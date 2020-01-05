package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int index;
    char[][] board;
    GridLayout sudoku;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        board=new char[][]{{'5','3','4','6','7','8','9','1','2'},{'6','7','2','1','9','5','3','4','8'}
//        ,{'1','9','8','3','4','2','5','6','7'},{'8','5','9','7','6','1','4','2','3'}
//        ,{'4','2','6','8','5','3','7','9','1'},{'7','1','3','9','2','4','8','5','6'}
//        ,{'9','6','1','5','3','7','2','8','4'},{'2','8','7','4','1','9','6','3','5'}
//        ,{'3','4','5','2','8','6','1','7','9'}};



        board=new char[][]{{'5','3','.','.','7','.','.','.','.'}
        ,{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'}
        ,{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        sudoku=findViewById(R.id.gridlayout);
        for(int i=0;i<9;i++)
            for (int j=0;j<9;j++)
            {
                if(board[i][j]!='.')
                {
                    index=i*9+j;
                    textView= (TextView) sudoku.getChildAt(index);
                    textView.setText(String.valueOf(board[i][j]));
                }

            }

        mainHandler = new Handler(this.getMainLooper());


    }
    public void sudokuSolve(View view)
    {
        SolveHelper solveHelper=new SolveHelper(board);
        solveHelper.start();
    }

    public void setvalue(char a,int index)
    {
        textView= (TextView) sudoku.getChildAt(index);
        textView.setText(String.valueOf(a));
    }

    class SolveHelper extends Thread{

        char[][] newboard;
        SolveHelper(char[][] board)
        {
            this.newboard=board;

        }

        @Override
        public void run() {
            super.run();
            solveSudoku(newboard);
        }

        boolean solved = false;
    public void solveSudoku(char[][] board) {
        solve(0,0);

    }
    public void placeNext(int row, int col){
        if(col == 8 && row == 8) solved = true;
        else {
            if(col == 8) solve(row+1,0);
            else solve(row,col+1);
        }
    }
    public void solve(int row, int col){
        List<Character> candidates = new ArrayList<>();
        if(newboard[row][col] == '.') {
            candidates = isValid(row,col);
            for(char a : candidates){

                final char val=a;
                newboard[row][col] = a;
                index=row*9+col;
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
                        setvalue(val,index);
//                    }
//                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                placeNext(row,col);
                if(solved)
                {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Sudoku problem solved!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;

                }
                else
                {
                    newboard[row][col] = '.';

//                    mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
                            setvalue(' ',index);
//                        }
//                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }else {
            placeNext(row,col);
        }
    }
    public List<Character> isValid(int row, int col){

        List<Character> list = new ArrayList<>();

        for(char a = '1'; a <='9'; a++){
            boolean collision = false;
            for(int i = 0; i < 9; i++){
                if(newboard[row][i] == a || newboard[i][col] == a || newboard[(row - row % 3) + i / 3][(col - col % 3) + i % 3] == a){
                    collision = true;
                    break;
                }
            }
            if(!collision) {
                list.add(a);
            }
        }
        return list;

    }}
}
