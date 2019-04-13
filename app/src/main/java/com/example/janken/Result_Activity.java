package com.example.janken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Result_Activity extends AppCompatActivity {

    final int JANKEN_GU = 0;
    final int JANKEN_CHOKI = 1;
    final int JANKEN_PA = 2;

    Vibrator vibrator;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_result_);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        int myHand = 0;
        Intent intent = getIntent();
        int id = intent.getIntExtra("MY_HAND", 0);

        ImageView myHandImageView =
                (ImageView) findViewById(R.id.myHandImage);
        switch (id) {
            case R.id.gu:
                myHandImageView.setImageResource(R.drawable.gu);
                myHand = JANKEN_GU;
                break;
            case R.id.choki:
                myHandImageView.setImageResource(R.drawable.choki);
                myHand = JANKEN_CHOKI;
                break;
            case R.id.pa:
                myHandImageView.setImageResource(R.drawable.pa);
                myHand = JANKEN_PA;
                break;
            default:
                break;
        }

        //コンピュータの手を決める
        int comHand = getHand();
        ImageView comHandImageView = (ImageView) findViewById(R.id.comHandImage);

        switch (comHand) {
            case JANKEN_GU:
                comHandImageView.setImageResource(R.drawable.com_gu);
                break;
            case JANKEN_CHOKI:
                comHandImageView.setImageResource(R.drawable.com_choki);
                break;
            case JANKEN_PA:
                comHandImageView.setImageResource(R.drawable.com_pa);
                break;
            default:
                break;
        }

        //勝敗を判定する
        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        int gameResult = (comHand - myHand + 3) % 3;
        switch (gameResult) {
            case 0:
                //あいこの場合
                vibrator.vibrate(100);
                resultLabel.setText(R.string.result_draw);
                break;
            case 1:
                //勝った場合
                //vibrator.vibrate(200);
                mMediaPlayer = MediaPlayer.create(this, R.raw.trumpet1);
                mMediaPlayer.start();
                resultLabel.setText(R.string.result_win);
                break;
            case 2:
                //負けた場合
                vibrator.vibrate(300);
                resultLabel.setText(R.string.result_lose);
                break;
        }
        //じゃんけんの結果を保存する
        saveData(myHand, comHand, gameResult);

        //5章完了時点強化
        GameCountDisplay();
    }
    public void onBackButtonTapped(View view) {

        finish();
    }

    private void saveData(int myHand, int comHand, int gameResult) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();

        int gameCount = pref.getInt("GAME_COUNT", 0);
        int winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0);
        int lastComHand = pref.getInt("LAST_COM_HAND", 0);
        int lastGameResult = pref.getInt("GAME_RESULT", -1);

        int gameWinCount = pref.getInt("WIN_COUNT", 0);
        int gameLoseCount = pref.getInt("LOSE_COUNT", 0);
        int gameDrawCount = pref.getInt("DRAW_COUNT", 0);

        editor.putInt("GAME_COUNT", gameCount + 1);
        if (lastGameResult == 2 && gameResult == 2) {
            //コンピュータが連勝して場合
            editor.putInt("WINNING_STREAK_COUNT", winningStreakCount + 1);
        } else {
            editor.putInt("WINNING_STREAK_COUNT", 0);
        }
        editor.putInt("LAST_MY_HAND", myHand);
        editor.putInt("LAST_COM_HAND", comHand);
        editor.putInt("BEFORE_LAST_COM_HAND", lastComHand);
        editor.putInt("GAME_RESULT", gameResult);

        switch (gameResult) {
            case 0:
                // あいこの場合
                editor.putInt("DRAW_COUNT", gameDrawCount + 1);
                break;
            case 1:
                // 勝った場合
                editor.putInt("WIN_COUNT", gameWinCount + 1);
                break;
            case 2:
                // 負けた場合
                editor.putInt("LOSE_COUNT", gameLoseCount + 1);
                break;
        }

        editor.commit();

    }

    private int getHand() {
        int hand = (int) (Math.random() * 3);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int gameCount = pref.getInt("GAME_COUNT", 0);
        int winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0);
        int lastMyHand = pref.getInt("LAST_MY_HAND", 0);
        int lastComHand = pref.getInt("LAST_COM_HAND", 0);
        int beforeLastComHand = pref.getInt("BEFORE_LAST_COM_HAND", 0);
        int gameResult = pref.getInt("GAME_RESULT", -1);

        if(gameCount == 1) {
            if(gameResult == 2){
                //前回の勝負が1回目で、コンピュータが勝った場合
                //コンピュータは次に出す手を変える
                while (lastComHand == hand){
                    hand = (int)(Math.random() * 3);
                }
            }else if(gameResult == 1){
                //前回の勝負が1回目で、コンピュータが負けた場合
                //相手の出した手に勝つ手を出す
                hand = (lastMyHand - 1 + 3) * 3;
            }
        }else if(winningStreakCount > 0){
            if(beforeLastComHand == lastComHand) {
                //同じ手で連勝した場合は手を変える
                while (lastComHand == hand) {
                    hand = (int) (Math.random() * 3);
                }
            }
        }

        return hand;
    }
    private void GameCountDisplay(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int gameCount = pref.getInt("GAME_COUNT", 0) ;

        TextView GameCountDisplay = (TextView) findViewById(R.id.GameCountDisplay);
        GameCountDisplay.setText("第");
        GameCountDisplay.append(String.valueOf(gameCount));
        GameCountDisplay.append("戦");
    }
}
