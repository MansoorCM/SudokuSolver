package com.example.sudokusolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int index;
    static char[][] board;
    static char[] dataset;
    //GridLayout sudoku;
    Handler mainHandler;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int speed;
    InputNumbers numbers;
    TextView finalTextView;
    static int checkx,checky;
    int x=0;
    static boolean choice=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);
        Intent intent=getIntent();
        boolean choice=intent.getBooleanExtra("choice",false);
        initialize(choice);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        board=new char[][]{{'.','.','9','7','4','8','.','.','.'},
//        {'7','.','.','.','.','.','.','.','.'},{'.','2','.','1','.','9','.','.','.'},
//        {'.','.','7','.','.','.','2','4','.'},{'.','6','4','.','1','.','5','9','.'},
//        {'.','9','8','.','.','.','3','.','.'},{'.','.','.','8','.','3','.','2','.'},
//        {'.','.','.','.','.','.','.','.','6'},{'.','.','.','2','7','5','9','.','.'}};





        recyclerView = (RecyclerView) findViewById(R.id.sudokuboard);
        numbers=findViewById(R.id.numberimageView);
        finalTextView=findViewById(R.id.finalTextView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,9);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new sudokuboardAdapter(dataset);
        recyclerView.setAdapter(mAdapter);

        mainHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Num current= (Num) msg.obj;
                setvalue(current.value,current.number);
            }
        };


        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        boolean vis_mode=preferences.getBoolean(SettingsActivity.KEY_VIZ,false);
        if(vis_mode)
        {
            speed=10;
        }else
        {
            speed=0;
        }

    }

    public void initialize(boolean choice)
    {
        if(choice)
        {
            dataset=new char[81];
            for(int i=0;i<81;i++)
            {
                dataset[i]=' ';
            }
            board=new char[9][9];
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    board[i][j]=' ';
                }
            }
        }else
        {
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
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settings)
        {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sudokuSolve(View view)
    {
        numbers.setVisibility(View.GONE);
        finalTextView.setVisibility(View.VISIBLE);
        //finalTextView.setText(R.string.success);
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
    public static void inputNum(int number)
    {
        int idx=sudokuboardAdapter.selected;

        if (idx!=-1)
        {
            if(choice || !sudokuboardAdapter.constants.contains(idx))
            {
                number+=48;
                char val= (char) number;
                Log.d("clicked number", String.valueOf(val));

                dataset[idx]= (char) number;
                int x=idx/9;
                int y=idx%9;
                board[x][y]=(char) number;
                mAdapter.notifyDataSetChanged();
                Log.d("this", String.valueOf(board[x][y]));
            }

        }else
        {
            Log.d("this", "select a square on board");
        }

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
            if(isInValidPosition(newboard))
            {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, R.string.problem, Toast.LENGTH_SHORT).show();
                        finalTextView.setText(R.string.problem);
                    }
                });
                return;

            }
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
                Num num= new Num(a, index);
                Message message=mainHandler.obtainMessage(1,num);
                message.sendToTarget();

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                placeNext(row,col);
                if(solved)
                {
                    if(isInValidPosition(newboard))
                    {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, R.string.problem, Toast.LENGTH_SHORT).show();
                                finalTextView.setText(R.string.problem);
                            }
                        });
                        return;
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                            finalTextView.setText(R.string.success);
                        }
                    });


//to cause a delay before showing success message
//                    try {
//                        Thread.sleep(4000);
//                        break;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    return;

                }
                else
                {
                    newboard[row][col] = ' ';


                    Num rnum= new Num(' ', index);
                    Message rmessage=mainHandler.obtainMessage(1,rnum);
                    rmessage.sendToTarget();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
            if(row==checkx &&col==checky)
            {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, R.string.problem, Toast.LENGTH_SHORT).show();
                        finalTextView.setText(R.string.problem);
                    }
                });
                return;
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

    //to pass a number in looper it must be a POJO(object)
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
    static boolean isInValidPosition(char[][] testBoard)
    {
        for(int row=0;row<9;row++)
        {
            for(int col=0;col<9;col++)
            {
                boolean collision = false;
                char value=testBoard[row][col];
                if(value!=' ')
                {
                    for(int i = 0; i < 9; i++){
                        if((i!=col && testBoard[row][i] == value) ||
                                (i!=row && testBoard[i][col] == value) ||
                                (!(((row - row % 3) + i / 3)==row || ((col - col % 3) + i % 3)==col) && testBoard[(row - row % 3) + i / 3][(col - col % 3) + i % 3] == value)){
                            collision = true;
                            break;
                        }
                    }
                    if(collision) {
                        return true;
                    }

                }

            }
        }
        return false;
    }



//    static boolean isValidPosition(char[][] testBoard)
//    {
//        for(int row=0;row<9;row++)
//        {
//            for(int col=0;col<9;col++)
//            {
//                boolean collision = false;
//                char value=testBoard[row][col];
//                if(value!=' ')
//                {
//                    for(int i = 0; i < 9; i++){
//                        if((i!=col && testBoard[row][i] == value) ||
//                                (i!=row && testBoard[i][col] == value) ||
//                                (!(((row - row % 3) + i / 3)==row || ((col - col % 3) + i % 3)==col) && testBoard[(row - row % 3) + i / 3][(col - col % 3) + i % 3] == value)){
//                            collision = true;
//                            break;
//                        }
//                    }
//                    if(collision) {
//                        return false;
//                    }
//
//                }
//
//            }
//        }
//        return true;
//    }
}