package j3.wrenn;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

//Source: https://www.youtube.com/watch?v=cZ3yRY8jkHQ
public class MainActivity extends TabActivity {

    private static final String TAG = "MainActivity";
    private TabHost mTabHost;
    private List<String> event;

    //added by jesus
    private static Button create_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        //TAB1
        intent = new Intent(this, Tab1Activity.class);
        spec = mTabHost.newTabSpec("tab1")
                       .setIndicator("Today")
                       .setContent(intent);
        mTabHost.addTab(spec);

        //TAB2
        intent = new Intent(this, Tab2Activity.class);
        spec = mTabHost.newTabSpec("tab2")
                       .setIndicator("Tomorrow")
                       .setContent(intent);
        mTabHost.addTab(spec);

        //Tab3
        intent = new Intent(this, Tab3Activity.class);
        spec = mTabHost.newTabSpec("tab3")
                       .setIndicator("Calendar")
                       .setContent(intent);
        mTabHost.addTab(spec);

        // Initially first tab will be selected
        mTabHost.setCurrentTab(2);
        //mTabHost.setCurrentTab(0);

        OnClickButtonLlistener();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void OnClickButtonLlistener()
    {
        create_event = (Button) findViewById(R.id.create_button);
        create_event.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent("j3.wrenn.new_event");
                        startActivity(intent);
                    }

                }
        );
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
