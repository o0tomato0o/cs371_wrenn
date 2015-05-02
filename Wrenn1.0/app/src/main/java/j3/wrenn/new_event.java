package j3.wrenn;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;


public class new_event extends Activity {
    //added by jesus
    protected EditText mName;
    protected EditText mLocation;
//    protected EditText mTime;
//    protected EditText mDate;
//    protected EditText mMode;
//    protected EditText mNotification;
    protected Button mCreateButton;
    //beta
    Spinner spinner_not;
    Spinner spinner_trans;
    ArrayAdapter<CharSequence> adapter_not;
    ArrayAdapter<CharSequence> adapter_trans;
    protected Button mCalendar;
    protected Button mTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private double mDuration;

    String str_name, str_location, str_time, str_date, str_mode, str_notification;
    int db_mYear, db_mMonth, db_mDay, db_mHour, db_mMinute;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    //beta
        spinner_not = (Spinner)findViewById(R.id.notification_spinner);
        adapter_not = ArrayAdapter.createFromResource(this, R.array.notification_options,android.R.layout.simple_spinner_item);
        adapter_not.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_not.setAdapter(adapter_not);
        spinner_not.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_trans = (Spinner)findViewById(R.id.transportation_spinner);
        adapter_trans = ArrayAdapter.createFromResource(this, R.array.transportation_options,android.R.layout.simple_spinner_item);
        adapter_trans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_trans.setAdapter(adapter_trans);
        spinner_trans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //added by jesus
        mName = (EditText)findViewById(R.id.event_name);
        mLocation = (EditText)findViewById(R.id.event_location);
//        mTime = (EditText)findViewById(R.id.event_time);
//        mDate = (EditText) findViewById(R.id.event_date);
//        mMode = (EditText)findViewById(R.id.event_mode);
//        mNotification = (EditText)findViewById(R.id.event_notification);
        mTimePicker = (Button)findViewById(R.id.event_time);
        mCalendar = (Button)findViewById(R.id.event_date);
        mCreateButton = (Button)findViewById(R.id.button);



        //listen for Button
        mCreateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //toast
//                Toast.makeText(new_event.this, "submitted info",Toast.LENGTH_LONG).show();

                //store event to parse
                str_name = mName.getText().toString().trim();
                str_location = mLocation.getText().toString().trim();
//                String str_time = mTime.getText().toString().trim();
//                String str_date = mDate.getText().toString().trim();
//                String str_mode = mMode.getText().toString().trim();
//                String str_notification = mNotification.getText().toString().trim();

//                Toast.makeText(new_event.this, "submitted info",Toast.LENGTH_LONG).show();

                DBOperations db = new DBOperations(context);
                db.db_insert(db, str_name, str_location, str_date, str_time, str_mode, str_notification);
                Log.d("new_event", "CREATED EVENT SUCCESS");
                Toast.makeText(getBaseContext(), "Event Successfully Created", Toast.LENGTH_LONG).show();
                finish();

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


//    public void addListenerOnButton() {
//
//        mTimePicker = (Button) findViewById(R.id.event_date);
//
//        mTimePicker.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Intent browserIntent =
//                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mkyong.com"));
//                startActivity(browserIntent);
//
//            }
//
//        });
//
//    }


    //    @Override
    public void testing(View view) {
        if (view == mCalendar) {
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
//                            txtDate.setText(dayOfMonth + "-"
//                                    + (monthOfYear + 1) + "-" + year);
                            mCalendar.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        else if (view == mTimePicker) {
            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
//                            txtTime.setText(hourOfDay + ":" + minute);
                            mTimePicker.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
//        else if (view == btnCreate) {
////            SQLiteDatabase database;
////            SQLiteQueryBuilder queryBuilder;
//            Log.d(TAG, "Created the following task:\n"
//                    + "title: " + txtTitle.getText()
//                    + "\nrating: " + ratingBar.getRating()
//                    + "\ndate: " + txtDate.getText()
//                    + "\ntime: " + txtTime.getText()
//                    + "\nduration: " + mDuration);
//            insertTaskIntoDatabase();
//        }
    }

}


