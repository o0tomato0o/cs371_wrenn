package j3.wrenn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Juan on 4/2/2015.
 */
public class Tab3Activity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TextView  tv=new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setText("This Is Tab3 Activity");

        setContentView(tv);
    }
}
