package com.example.josh.week4daily3.di;


import com.example.josh.week4daily3.View.MainActivity;

import dagger.Component;


@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);


}
