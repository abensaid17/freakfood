package com.example.freakfood;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    public static  AppExecutors getInstance() {
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private static final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getmNetworkIO(){
        return mNetworkIO;
    }
}
