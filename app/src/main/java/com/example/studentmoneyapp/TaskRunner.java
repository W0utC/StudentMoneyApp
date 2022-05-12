package com.example.studentmoneyapp;

import android.app.Activity;

public abstract class TaskRunner{

    private Activity activity;
    public TaskRunner(Activity activity) {
        this.activity = activity;
    }

    private void startBackground() {
        new Thread(new Runnable() {
            public void run() {

                doInBackground();
                activity.runOnUiThread(new Runnable() {
                    public void run() {

                        onPostExecute();
                    }
                });
            }
        }).start();
    }
    public void execute(){
        startBackground();
    }

    public abstract void doInBackground();
    public abstract void onPostExecute();

}

//Andere mogelijkheid is om dit te gebruiken:
//
//
//You can directly use Executors from java.util.concurrent package.
//
//ExecutorService executor = Executors.newSingleThreadExecutor();
//Handler handler = new Handler(Looper.getMainLooper());
//
//executor.execute(new Runnable() {
//    @Override
//    public void run() {
//
//        //Background work here
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                //UI Thread work here
//            }
//        });
//    }
//});
//
//Pretty simple right? You can simplify it little more if you are using Java 8 in your project.
//
//ExecutorService executor = Executors.newSingleThreadExecutor();
//Handler handler = new Handler(Looper.getMainLooper());
//
//executor.execute(() -> {
//    //Background work here
//    handler.post(() -> {
//        //UI Thread work here
//    });
//});