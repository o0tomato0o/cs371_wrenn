package j3.wrenn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class new_event extends Activity {
    //added by jesus
    protected EditText mName;
    protected EditText mLocation;
    protected EditText mTime;
    protected EditText mDate;
    protected EditText mMode;
    protected EditText mNotification;
    protected Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        //added by jesus
        mName = (EditText)findViewById(R.id.event_name);
        mLocation = (EditText)findViewById(R.id.event_location);
        mTime = (EditText)findViewById(R.id.event_time);
        mDate = (EditText) findViewById(R.id.event_date);
        mMode = (EditText)findViewById(R.id.event_mode);
        mNotification = (EditText)findViewById(R.id.event_notification);
        mCreateButton = (Button)findViewById(R.id.button);
        //initialize parse
        Parse.initialize(this, "BBr1Gxg1QXLrDAVZZlb6W8OVfwUQEEHCtuOUd8PA", "fgLZMkqVfO5giVGzkPAUiZjjV7hUGyf14BoVVERZ");

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();


        //listen for Button
        mCreateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //toast
//                Toast.makeText(new_event.this, "submitted info",Toast.LENGTH_LONG).show();

                //store event to parse
                String str_name = mName.getText().toString().trim();
                String str_location = mLocation.getText().toString().trim();
                String str_time = mTime.getText().toString().trim();
                String str_date = mDate.getText().toString().trim();
                String str_mode = mMode.getText().toString().trim();
                String str_notification = mNotification.getText().toString().trim();
                ParseObject eventObject = new ParseObject("EventObject");
                eventObject.put("event_name", str_name);
                eventObject.put("event_location",str_location);
                eventObject.put("event_time",str_time);
                eventObject.put("event_date",str_date);
                eventObject.put("event_mode",str_mode);
                eventObject.put("event_notification",str_notification);

//                eventObject.saveInBackground();
//                Toast.makeText(new_event.this, "submitted info",Toast.LENGTH_LONG).show();

                eventObject.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            //myObjectSavedSuccessfully();
                            Toast.makeText(new_event.this, "Your Event was saved successfully",Toast.LENGTH_LONG).show();
                        } else {
                            //myObjectSaveDidNotSucceed();
                            Toast.makeText(new_event.this, "Error: There was a problem saving the Event",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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
    }
}

