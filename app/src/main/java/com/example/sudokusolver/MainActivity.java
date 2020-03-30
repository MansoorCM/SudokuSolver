package com.example.sudokusolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
    char[] dataset;
    //GridLayout sudoku;
    Handler mainHandler;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        board=new char[][]{{'.','.','9','7','4','8','.','.','.'},
//        {'7','.','.','.','.','.','.','.','.'},{'.','2','.','1','.','9','.','.','.'},
//        {'.','.','7','.','.','.','2','4','.'},{'.','6','4','.','1','.','5','9','.'},
//        {'.','9','8','.','.','.','3','.','.'},{'.','.','.','8','.','3','.','2','.'},
//        {'.','.','.','.','.','.','.','.','6'},{'.','.','.','2','7','5','9','.','.'}};



        dataset=new char[]{'5','3',' ',' ','7',' ',' ',' ',' '
        ,'6',' ',' ','1','9','5',' ',' ',' ',' ','9','8',' ',' ',' ',' ','6',' '
        ,'8',' ',' ',' ','6',' ',' ',' ','3','4',' ',' ','8',' ','3',' ',' ','1',
                '7',' ',' ',' ','2',' ',' ',' ','6',' ','6',' ',' ',' ',' ','2','8',' ',
                ' ',' ',' ','4','1','9',' ',' ','5',' ',' ',' ',' ','8',' ',' ','7','9'};
        board=new char[][]{{'5','3',' ',' ','7',' ',' ',' ',' '}
                , {'6', ' ', ' ', '1', '9', '5', ' ', ' ', ' '},{ ' ', '9', '8', ' ', ' ', ' ', ' ', '6', ' '
        },{'8',' ',' ',' ','6',' ',' ',' ','3'},{'4',' ',' ','8',' ','3',' ',' ','1'},
                {'7',' ',' ',' ','2',' ',' ',' ','6'},{' ','6',' ',' ',' ',' ','2','8',' '},
                {' ',' ',' ','4','1','9',' ',' ','5'},{' ',' ',' ',' ','8',' ',' ','7','9'}};

        recyclerView = (RecyclerView) findViewById(R.id.sudokuboard);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,9);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new sudokuboardAdapter(dataset);
        recyclerView.setAdapter(mAdapter);

        //sudoku=findViewById(R.id.sudokuboard);
//        for(int i=0;i<9;i++)
//            for (int j=0;j<9;j++)
//            {
//                if(board[i][j]!='.')
//                {
//                    index=i*9+j;
//                    textView= (TextView) sudoku.getChildAt(index);
//                    textView.setText(String.valueOf(board[i][j]));
//                }
//
//            }

        mainHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Num current= (Num) msg.obj;
                setvalue(current.value,current.number);
            }
        };



    }

    public void sudokuSolve(View view)
    {
        SolveHelper solveHelper=new SolveHelper(board);
        solveHelper.start();
    }

    public void setvalue(char a,int index)
    {
        //textView= (TextView) sudoku.getChildAt(index);
//        if(a==' ')
//        {
//            //textView.setTextColor(getResources().getColor(R.color.colorAccent));
//        }
//        else
//        {
           // textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            //textView.setText(String.valueOf(a));
            dataset[index]=a;
            mAdapter.notifyDataSetChanged();
        Log.d("currentProgress", String.valueOf(a));
//        }


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
    void solveSudoku(char[][] board) {
        solve(0,0);

    }
    void placeNext(int row, int col){
        if(col == 8 && row == 8) solved = true;
        else {
            if(col == 8) solve(row+1,0);
            else solve(row,col+1);
        }
    }
    void solve(int row, int col){
        List<Character> candidates = new ArrayList<>();
        if(newboard[row][col] == ' ') {
            candidates = isValid(row,col);
            for(char a : candidates){

                newboard[row][col] = a;
                index=row*9+col;
                Num num= new Num(a, index);
                Message message=mainHandler.obtainMessage(1,num);
                message.sendToTarget();

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
                            Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
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
                    newboard[row][col] = ' ';


                    Num rnum= new Num(' ', index);
                    Message rmessage=mainHandler.obtainMessage(1,rnum);
                    rmessage.sendToTarget();
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

    }}
    static class Num
    {
        int number;
        char value;
        Num(char value,int number)
        {
            this.number=number;
            this.value=value;
        }
    }
}










//
//package com.example.sudokusolver;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.os.Bundle;
//        import android.os.CountDownTimer;
//        import android.os.Handler;
//        import android.os.Looper;
//        import android.os.Message;
//        import android.view.View;
//        import android.widget.GridLayout;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import java.util.ArrayList;
//        import java.util.List;

//public class MainActivity extends AppCompatActivity {
//    TextView textView;
//    int index;
//    char[][] board;
//    GridLayout sudoku;
//    Handler mainHandler;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        board=new char[][]{{'.','.','9','7','4','8','.','.','.'},
////        {'7','.','.','.','.','.','.','.','.'},{'.','2','.','1','.','9','.','.','.'},
////        {'.','.','7','.','.','.','2','4','.'},{'.','6','4','.','1','.','5','9','.'},
////        {'.','9','8','.','.','.','3','.','.'},{'.','.','.','8','.','3','.','2','.'},
////        {'.','.','.','.','.','.','.','.','6'},{'.','.','.','2','7','5','9','.','.'}};
//
//
//
//        board=new char[][]{{'5','3','.','.','7','.','.','.','.'}
//                ,{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'}
//                ,{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},
//                {'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},
//                {'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
//        sudoku=findViewById(R.id.gridlayout);
//        for(int i=0;i<9;i++)
//            for (int j=0;j<9;j++)
//            {
//                if(board[i][j]!='.')
//                {
//                    index=i*9+j;
//                    textView= (TextView) sudoku.getChildAt(index);
//                    textView.setText(String.valueOf(board[i][j]));
//                }
//
//            }
//
//        mainHandler = new Handler(Looper.getMainLooper())
//        {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                Num current= (Num) msg.obj;
//                setvalue(current.value,current.number);
//            }
//        };
//
//
//
//    }
//
//    public void sudokuSolve(View view)
//    {
//        SolveHelper solveHelper=new SolveHelper(board);
//        solveHelper.start();
//    }
//
//    public void setvalue(char a,int index)
//    {
//        textView= (TextView) sudoku.getChildAt(index);
//        if(a==' ')
//        {
//            textView.setTextColor(getResources().getColor(R.color.colorAccent));
//        }
//        else
//        {
//            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//            textView.setText(String.valueOf(a));
//        }
//
//
//    }
//
//    class SolveHelper extends Thread{
//
//        char[][] newboard;
//        SolveHelper(char[][] board)
//        {
//            this.newboard=board;
//
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            solveSudoku(newboard);
//        }
//
//        boolean solved = false;
//        void solveSudoku(char[][] board) {
//            solve(0,0);
//
//        }
//        void placeNext(int row, int col){
//            if(col == 8 && row == 8) solved = true;
//            else {
//                if(col == 8) solve(row+1,0);
//                else solve(row,col+1);
//            }
//        }
//        void solve(int row, int col){
//            List<Character> candidates = new ArrayList<>();
//            if(newboard[row][col] == '.') {
//                candidates = isValid(row,col);
//                for(char a : candidates){
//
//                    final char val=a;
//                    newboard[row][col] = val;
//                    index=row*9+col;
////                mainHandler.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        setvalue(val,index);
////                    }
////                });
//                    Num num= new Num(val, index);
//                    Message message=mainHandler.obtainMessage(1,num);
//                    message.sendToTarget();
//
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    placeNext(row,col);
//                    if(solved)
//                    {
//                        mainHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "Sudoku problem solved!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//                        try {
//                            Thread.sleep(4000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return;
//
//                    }
//                    else
//                    {
//                        newboard[row][col] = '.';
//
//
////                    mainHandler.post(new Runnable() {
////                        @Override
////                        public void run() {
////                            setvalue(' ',index);
////                        }
////                    });
//                        Num rnum= new Num(' ', index);
//                        Message rmessage=mainHandler.obtainMessage(1,num);
//                        rmessage.sendToTarget();
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//
//                }
//            }else {
//                placeNext(row,col);
//            }
//        }
//        List<Character> isValid(int row, int col){
//
//            List<Character> list = new ArrayList<>();
//
//            for(char a = '1'; a <='9'; a++){
//                boolean collision = false;
//                for(int i = 0; i < 9; i++){
//                    if(newboard[row][i] == a || newboard[i][col] == a || newboard[(row - row % 3) + i / 3][(col - col % 3) + i % 3] == a){
//                        collision = true;
//                        break;
//                    }
//                }
//                if(!collision) {
//                    list.add(a);
//                }
//            }
//            return list;
//
//        }}
//    static class Num
//    {
//        int number;
//        char value;
//        Num(char value,int number)
//        {
//            this.number=number;
//            this.value=value;
//        }
//    }
//}
