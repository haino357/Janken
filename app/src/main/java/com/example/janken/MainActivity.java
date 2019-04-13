package com.example.janken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面を縦方向に固定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //起動時にデータをクリアする
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameResultDisplay();
    }

    public void onJankenButtonTapped(View view){
        Intent intent = new Intent(this, Result_Activity.class);
        intent.putExtra("MY_HAND", view.getId());
        startActivity(intent);
    }

    private void GameResultDisplay() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int gameWinCount = pref.getInt("WIN_COUNT", 0);
        int gameDrawCount = pref.getInt("DRAW_COUNT", 0);
        int gameLoseCount = pref.getInt("LOSE_COUNT", 0);

        TextView GameResultDisplay = (TextView) findViewById(R.id.GameResultDisplay);
        GameResultDisplay.setText(String.valueOf(gameWinCount));
        GameResultDisplay.append("勝");
        GameResultDisplay.append(String.valueOf(gameDrawCount));
        GameResultDisplay.append("分");
        GameResultDisplay.append(String.valueOf(gameLoseCount));
        GameResultDisplay.append("敗");
    }

//    public void  GameResultSaveButtonTapped(View view){
//        //戦績をRealmに保存する処理を直接記載するor処理を記載するクラスに飛ぶ
//        startActivity(new Intent(MainActivity.this, GameResultActivity.class));
//    }
}
