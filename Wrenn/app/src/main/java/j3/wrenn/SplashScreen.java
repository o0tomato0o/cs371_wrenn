package j3.wrenn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Juan on 4/3/2015.
 * Source: http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
    Source: https://www.youtube.com/watch?v=embeFCPzRCg
 */
public class SplashScreen extends Activity
{
    String now_playing, earned;
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /****** Create Thread that will sleep for 5 seconds *************/
        /*Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(SPLASH_TIME_OUT);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
        };*/

        // start thread
        //background.start();

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run(){
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);

            finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
