package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.ListarReservasActivity;

/**
 * Created by NicolasAndres on 13/2/2017.
 */

public class TestReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("reciber", "recibido");
        long currentTime = System.currentTimeMillis();
        if(currentTime % 3 == 0) {
            ArrayList<Reserva> reservas = (ArrayList<Reserva>) intent.getExtras().get("reservas");
            sendNotification(context, reservas);
        }
    }

    private void sendNotification(Context context, ArrayList<Reserva> reservas) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String tono = preference.getString("tono_notificacion", null);
        Uri ringone = (tono == null) ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) :
                Uri.parse(tono);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, ListarReservasActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("reservas", reservas);
        intent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Reservalo.com")
                        .setContentText("Reserva lista")
                        .setSound(ringone)
                        .setAutoCancel(true);
        notificationManager.notify(1, mBuilder.build());
    }
}
