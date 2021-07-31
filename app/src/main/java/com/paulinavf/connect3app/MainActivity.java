package com.paulinavf.connect3app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends Activity {

    int [] board = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean redTurn = true, gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean hasWon (int number){
        if(board[0]==board[1]&&board[1]==board[2]&&board[2]==number ||
                board[3]==board[4]&&board[4]==board[5]&&board[5]==number ||
                        board[6]==board[7]&&board[7]==board[8]&&board[8]==number){
            return true;
        }else if(board[0]==board[3]&&board[3]==board[6]&&board[6]==number ||
                     board[1]==board[4]&&board[4]==board[7]&&board[7]==number ||
                        board[2]==board[5]&&board[5]==board[8]&&board[8]==number){
            return true;
        }else if(board[0]==board[4]&&board[4]==board[8]&&board[8]==number ||
                     board[2]==board[4]&&board[4]==board[6]&&board[6]==number){
            return true;
        }else
            return false;
    }

    public void dropIn(View view){
        if(gameActive) {
            ImageView img = (ImageView) view;
            int idNum = img.getId();
            Resources res = img.getResources();
            String id = res.getResourceEntryName(idNum);
            int sqr = Integer.parseInt(String.valueOf(id.charAt(id.length() - 1)));

            if (board[sqr] == 0) {
                if (redTurn) {
                    img.setTranslationY(-8000);
                    img.setImageResource(R.drawable.red);
                    img.animate().translationYBy(8000).rotation(3600).setDuration(600);
                    board[sqr] = 1;
                    redTurn = false;
                    if (hasWon(1)) {
                        TextView txt = (TextView) findViewById(R.id.txtWon);
                        txt.setText("Red WON!");
                        gameActive=false;
                        ConstraintLayout lay = (ConstraintLayout) findViewById(R.id.popLay);
                        lay.setVisibility(View.VISIBLE);
                    }
                } else {
                    img.setTranslationY(-8000);
                    img.setImageResource(R.drawable.yellow);
                    img.animate().translationYBy(8000).rotation(3600).setDuration(600);
                    board[sqr] = 2;
                    redTurn = true;
                    if (hasWon(2)) {
                        TextView txt = (TextView) findViewById(R.id.txtWon);
                        txt.setText("Yellow WON!");
                        gameActive=false;
                        ConstraintLayout lay = (ConstraintLayout) findViewById(R.id.popLay);
                        lay.setVisibility(View.VISIBLE);
                    }
                }
                boolean tied = true;
                for(int i=0; i<board.length; i++) {
                    if (board[i] == 0)
                        tied = false;
                }
                if(tied) {
                    TextView txt = (TextView) findViewById(R.id.txtWon);
                    txt.setText("You've TIED!");
                    gameActive = false;
                    ConstraintLayout lay = (ConstraintLayout) findViewById(R.id.popLay);
                    lay.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void restart (View view){
        //Para no dejar que se active el botón restart si ya acabó el juego, solo play again
        if(!gameActive&&view==findViewById(R.id.btnAgain) || gameActive) {
            if(view==findViewById(R.id.btnAgain)) {
                ConstraintLayout lay = (ConstraintLayout) findViewById(R.id.popLay);
                lay.setVisibility(View.INVISIBLE);
                gameActive=true;
            }

            for (int i = 0; i < board.length; i++)
                board[i] = 0;

            ConstraintLayout boardLay = (ConstraintLayout) findViewById(R.id.boardLay);
            for (int i = 0; i < boardLay.getChildCount(); i++) {
                ImageView img = (ImageView) boardLay.getChildAt(i);
                img.setImageResource(0);
            }
        }
    }

}