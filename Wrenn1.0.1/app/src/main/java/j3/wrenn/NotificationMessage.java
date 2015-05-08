package j3.wrenn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.util.Log;

/**
 * 84 Android Tutorial Working with Service and NotificationSample App
 * https://www.youtube.com/watch?v=imoapm9PARU
 * https://www.youtube.com/watch?v=ai6olBJ9kJY
 * Created by Juan on 5/5/2015.
 */
public class NotificationMessage extends Service
{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Toast.makeText(this, "OnCreate()", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "OnDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Toast.makeText(this, "OnStart()", Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
        String message = intent.getStringExtra("msg");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notIntent = new Intent(this, SplashScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notIntent, 0);

        int icon = R.drawable.notification_icon;
        String tickerText = "Leave to Event";
        long when = System.currentTimeMillis();
        Notification not = new Notification(icon, tickerText, when);
        if(message == null || message.isEmpty() )
        {
            message= "Leave to Event";
        }

        not.setLatestEventInfo(this, "Event", message, pendingIntent);
        not.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(123, not);

    }


}
