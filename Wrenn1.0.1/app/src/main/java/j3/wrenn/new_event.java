package j3.wrenn;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private int travel_time;

    //jesus-may4
    String transportation;
    protected String formatted_destLocation;
    protected String formatted_originLocation;
    protected EditText mdestLocation;

    String str_name, str_location, str_time, str_date, str_mode, str_notification;
    private int aYear, aMonth, aDay, aHour, aMinute;
    Context context = this;

    private String[] units;
    private int [] values;
    private int count;

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
                transportation = spinner_trans.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transportation = "car";
            }
        });
        //added by jesus
        mName = (EditText)findViewById(R.id.event_name);
        mLocation = (EditText)findViewById(R.id.event_location);
        mdestLocation = (EditText)findViewById(R.id.dest_event_location);
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
                String str_dest_location = mdestLocation.getText().toString().trim();
                formatted_originLocation = str_location.replace(' ', '+');
                formatted_destLocation = str_dest_location.replace(' ', '+');




                /*beginning api request*/

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Log.w("API REQUEST: ", "origin: "+formatted_destLocation+"  dest: "+formatted_originLocation+"/n");
                Log.w("TRANSPORTATION: ", transportation);
                StringRequest request = new StringRequest(Request.Method.GET,
                        "https://maps.googleapis.com/maps/api/directions/json?origin="+formatted_originLocation+"&destination="+formatted_destLocation+"+&mode="+transportation.toLowerCase()+"&key=AIzaSyATWJCzw4DzmB1xx5h9LAk6G4Fkr5BiKvI\n",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                // Display the response string.
                                try {
                                    JSONObject jObject = null;
                                    jObject = new JSONObject(response);
                                    JSONArray routes_array = jObject.getJSONArray("routes");

                                    for(int i = 0; i < routes_array.length(); ++i)
                                    {
                                        JSONObject routes_obj = routes_array.getJSONObject(i);
                                        if(routes_obj.has("legs"))
                                        {
                                            JSONArray legs = routes_obj.getJSONArray("legs");

//                                            Log.w("LEGS ", legs.toString());
                                            JSONObject duration_obj = legs.getJSONObject(0);

//                                            Log.w("LEG[0]: ", duration_obj.toString());
                                            JSONObject duration = duration_obj.getJSONObject("duration");
//                                            Log.w("DURATION: ", duration.toString());
                                            String duration_time = duration.getString("text");
//                                            Log.w("TIME: ", duration_time);
//                                            String travel_time = duration_obj.getString("text");
//                                            Log.w("HOW LONG ", travel_time);
                                            String[] parsed_time = duration_time.split("\\s+");

                                            units = new String[10];
                                            values  = new int[10];
                                            count = 0;

                                            for(int j = 0; j < parsed_time.length; ++j)
                                            {

//                                                Log.w("ELEM: ", parsed_time[j]);
                                                if(j%2 == 0)
                                                {

                                                    values[count] = Integer.parseInt(parsed_time[j]);
                                                    units[count] = parsed_time[j+1];
                                                    Log.w("FINAL TIME: ", values[count]+""+units[count]);
                                                    count+=1;

                                                }
                                            }
                                            String date = (String) mCalendar.getText();
                                            String[] vals = date.split("-");
                                            mDay = Integer.parseInt(vals[0]);
                                            mMonth = Integer.parseInt(vals[1]);
                                            mYear = Integer.parseInt(vals[2]);

                                            String time = (String) mTimePicker.getText();
                                            String[] t_val = time.split(":");
                                            mHour = Integer.parseInt(t_val[0]);
                                            mMinute = Integer.parseInt(t_val[1]);
                                            Log.d("units: ", ""+units.toString()+ " "+values.toString());


                                            Log.d("NEW_EVENT", "PRINT INTEGER VARS FOR DATE"+aHour+":"+aMinute+"");
                                            DBOperations db = new DBOperations(context);
                                            db.db_insert(db, str_name, str_location, mYear, mMonth, mDay, mHour, mMinute, str_mode, str_notification);
                                            Log.d("new_event", "CREATED EVENT SUCCESS");

                                            boolean flag_min = false, flag_hour = false, flag_day = false;
                                            for(int j = 0; j < count; ++j)
                                            {
//                    Log.w("SAVED: " ,values[j]+" "+units[j]);
                                                if(units[j].equals("min") || units[j].equals("mins"))
                                                {
//                                                    travel_time += (values[j]);
                                                    aMinute = mMinute - values[j];
                                                    if(aMinute < 0)
                                                    {
                                                        aHour = mHour - 1;
                                                        aMinute = 60 + aMinute;
                                                        flag_hour = true;
                                                        Log.w("MOdified","hour: "+aHour);
                                                    }
                                                    Log.w("MOdified","Minute: "+aMinute);
                                                    flag_min = true;
                                                }
                                                if(units[j].equals("hour") || units[j].equals("hours"))
                                                {
//                                                    travel_time += ((values[j])*60);
                                                    aHour = mHour - values[j];
                                                    if(aHour < 0)
                                                    {
                                                        aDay = mDay - 1;
                                                        aHour = 24 + aHour;
                                                        flag_day = true;
                                                        Log.w("MOdified","day: "+aDay);
                                                    }
                                                    Log.w("MOdified","Hour: "+aHour);
                                                    flag_hour = true;
                                                }
                                                if(units[j].equals("day") || units[j].equals("days"))
                                                {
                                                    //convert days to min
//                                                    travel_time += ((values[j])*1440);
                                                    aDay = mDay - values[j];
                                                    flag_day = true;
                                                    //fix month

                                                }


                                            }
                                            if(!flag_day)
                                            {
                                                aDay = mDay;
                                            }
                                            if(!flag_hour)
                                            {
                                                aHour = mHour;
                                            }
                                            if(!flag_min)
                                            {
                                                aMinute = mMinute;
                                            }

//                Toast.makeText(new_event.this, "submitted info",Toast.LENGTH_LONG).show();


                                            Calendar c = Calendar.getInstance();
                                            c.set(Calendar.HOUR_OF_DAY, aHour);
                                            c.set(Calendar.MINUTE, aMinute);
                                            c.set(Calendar.SECOND, 0);
                                            c.set(Calendar.YEAR, mYear);
                                            c.set(Calendar.DAY_OF_MONTH, aDay);
                                            c.set(Calendar.MONTH, mMonth - 1);
                                            Log.d("NEW_EVENT", "aHour: " + Integer.toString(aHour) +" amin: "+ Integer.toString(aMinute)+" aday: "+aDay+" amonth: "+aMonth);
                                            Log.d("NEW_EVENT", "mHour: " + Integer.toString(mHour) +" Mmin: "+ Integer.toString(mMinute)+" Mday: "+mDay+" Mmonth: "+mMonth);
                                            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                            //TEST FOR BROADCAST SERVICE
                                            Intent notMessage = new Intent(context, NotificationMessage.class);
                                            notMessage.putExtra("msg", str_name);
                                            PendingIntent pi = PendingIntent.getService(context, 0, notMessage, PendingIntent.FLAG_UPDATE_CURRENT);
                                            Log.d("AlarmSERVICE", "GET TIME IN MILLIS() " + c.getTime());
                                            am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                            Toast.makeText(getBaseContext(), "AlarmManager GET TIME " + c.getTime(), Toast.LENGTH_LONG).show();

                                            Toast.makeText(getBaseContext(), "Event Successfully Created", Toast.LENGTH_LONG).show();
                                            finish();



                                        }
                                        else
                                        {
                                            Log.w("NOT LEGS: ", routes_obj.toString());
                                        }

                                    }

//                                    String dist_minutes = distanceObject.getString("text");
//                                    Log.w("DURATION: ", dist_minutes);
                                }
                                catch(JSONException Jsonexception)
                                {
                                    Log.w("EXCEPTION: ","ERROR WITH JSON !!!!!");
                                }


//                                Toast.makeText(getApplicationContext(),"RESPONSE "+ response, Toast.LENGTH_SHORT).show();
//                                Log.w("API RESPONSE: ", response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"ERROR "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                // Add the request to the RequestQueue.
                requestQueue.add(request);


            /*finished making api request*/












//                String str_time = mTime.getText().toString().trim();
//                String str_date = mDate.getText().toString().trim();
//                String str_mode = mMode.getText().toString().trim();
//                String str_notification = mNotification.getText().toString().trim();






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


