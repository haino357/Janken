package com.example.janken;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JankenApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
