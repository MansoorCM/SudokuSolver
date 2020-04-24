package com.example.sudokusolver;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Solver extends AsyncTask<char[][],Integer, Boolean> {

    boolean solved = false;
    char[][] newboard;
    int x=0,checkx,checky;
    int index;
    int speed;
    UpdateBoard mListener;

    Solver(UpdateBoard mListener,int speed)
    {
        this.mListener=mListener;
        this.speed=speed;
    }

    @Override
    protected Boolean doInBackground(char[][]... chars) {

        newboard=chars[0];


        return solve(0,0);
    }

    void placeNext(int row, int col){
        if(col == 8 && row == 8) solved = true;
        else {
            if(col == 8) solve(row+1,0);
            else solve(row,col+1);
        }
    }


    boolean solve(int row, int col){
        List<Character> candidates = new ArrayList<>();
        if(newboard[row][col] == ' ') {
            if (x==0)
            {
                checkx=row;
                checky=col;
                ++x;
            }
            candidates = isValid(row,col);
            for(char a : candidates)
            {

                newboard[row][col] = a;
                index=row*9+col;
                //update sudoku board position
                publishProgress(((int) a)-48,index);
                //mListener.updateBoard(a,index);

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                placeNext(row,col);
                if(solved)
                {



//to cause a delay before showing success message
//                    try {
//                        Thread.sleep(4000);
//                        break;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    return true;

                }
                else
                {
                    newboard[row][col] = ' ';
                    publishProgress(0,index);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
            if(row==checkx &&col==checky)
            {
                MainActivity.finalTextView.setText(R.string.problem);

                return false;
            }
        }else {
            placeNext(row,col);
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mListener.updateBoard(values[0],values[1]);
    }

    List<Character> isValid(int row, int col){

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

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean)
        {
            if(MainActivity.isInValidPosition(newboard))
            {

                MainActivity.finalTextView.setText(R.string.problem);
            }else
            {
                MainActivity.finalTextView.setText(R.string.success);
            }

        }
    }

    interface UpdateBoard
    {
        void updateBoard(int value,int index);
    }
}

