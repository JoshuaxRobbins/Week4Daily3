package com.example.josh.week4daily3.di;

import com.example.josh.week4daily3.Model.CompleteListener;
import com.example.josh.week4daily3.Model.UserAuthenticator;
import com.example.josh.week4daily3.View.MainActivity;


import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainActivity providesMainActivity(){
        return new MainActivity();
    }



}
