package j3.wrenn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

//Source: https://www.youtube.com/watch?v=cZ3yRY8jkHQ
public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    private static final String TAG = "MainActivity";
//    private TabHost mTabHost;
    private List<String> event;
    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter pagerAdapter;
    FloatingActionButton actionButton;

    //added by jesus
    private static Button create_event;

    protected Dialog onCreateDialog(int num) {
        Log.d(TAG, "In onCreateDialog");
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(num) {
            case 0:
                Log.d(TAG, "Create about dialog");
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();
                break;
        }
        if(dialog == null)
            Log.d(TAG, "Uh oh! Dialog is null");
        else
            Log.d(TAG, "Dialog created: " + num + ", dialog: " + dialog);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        pager = (ViewPager) this.findViewById(R.id.viewpager);

        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            if(i == 0)
            {
                tabHost.addTab(
//                    tabHost.newTab().setIcon(getIcon(i)).setTabListener(this)

                        tabHost.newTab().setText("Today").setTabListener(this)
                );
            }
            if(i == 1)
            {
                tabHost.addTab(
//                    tabHost.newTab().setIcon(getIcon(i)).setTabListener(this)

                        tabHost.newTab().setText("Tomorrow").setTabListener(this)
                );
            }
            if(i== 2)
            {
                tabHost.addTab(
//                    tabHost.newTab().setIcon(getIcon(i)).setTabListener(this)

                        tabHost.newTab().setText("Calendar").setTabListener(this)
                );
            }



        }




        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.create_img));

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        OnClickButtonListener();


//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        ImageView itemIcon = new ImageView(this);
//        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.create_img));
//        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void OnClickButtonListener()
    {
        //create_event = (Button) findViewById(R.id.create_button);
        actionButton.setOnClickListener(
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

    @Override
    public void onTabSelected(MaterialTab tab) {
        // when the tab is clicked the pager swipe content to the tab position
        pager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {


    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
    @Override
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
        if (id == R.id.about){
            showDialog(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0)
            {
                fragment = new fragment_today();
            }
            if(position == 1)
            {
                fragment = new fragment_tomorrow();
            }
            if(position == 2)
            {
                fragment = new fragment_calendar();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return false;
//        }
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
