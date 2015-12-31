package com.example.gcmwithpicture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Purpose:- Commit in testPerformance
 */
public class DemoFragment extends Fragment {


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_main, container, false);

        final RadioButton rbImage = (RadioButton) view.findViewById(R.id.content_main_rb_image);
        final Button btnSendPush = (Button) view.findViewById(R.id.content_main_btn_send);

        btnSendPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new LongOperation().execute(rbImage.isChecked());
            }
        });


        return view;
    }

    private class LongOperation extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... params) {
            try {
                if (params[0]) {
                    int notificationId = (int) System.currentTimeMillis();

                    final NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(notificationId, setBigPictureStyleNotification());
                } else {
                    sendNotification("Normal Notification");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }

    private void sendNotification(final String message) {
        int notificationId = (int) System.currentTimeMillis();

        NotificationCompat.Builder mBuilder = null;

        mBuilder = new NotificationCompat.Builder(getActivity()).setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(getActivity().getString(R.string.app_name))
                .setWhen((System.currentTimeMillis())).setDefaults(Notification.DEFAULT_SOUND).setLights(Color.GREEN, 500, 1000).setContentText(message).setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        final NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        final Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent intent = PendingIntent.getActivity(getActivity(), notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(intent);

        mBuilder.setSmallIcon(R.drawable.ic_action_cloud);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

    private Notification setBigPictureStyleNotification() {

        int notificationId = (int) System.currentTimeMillis();

        Bitmap remote_picture = null;

        // Create the style object with BigPictureStyle subclass.
        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle("Big Picture");
        notiStyle.setSummaryText("Nice big picture.");

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL("http://lorempixel.com/700/500").getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notiStyle.bigPicture(remote_picture);
//        notiStyle..bigPicture(remote_picture);
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_action_cloud)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle("Big Picture")
                .setContentText("This is an example of a Big Picture Style.")
                .setStyle(notiStyle).build();
    }

    /**
     * Method is used for checking network availability.
     *
     * @param context
     *
     * @return isNetAvailable: boolean true for Internet availability, false otherwise
     */

    public boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {
                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {
                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {
                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {
                    if (mobileInfo != null) {
                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }

                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }

        }

        return isNetAvailable;
    }

    /**
     * Alert dialog to show common messages.
     *
     * @param title
     * @param message
     * @param context
     */
    public void displayDialog(final Context context, final String title, final String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);

        alertDialog.setMessage(message);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        if (!((Activity) context).isFinishing()) {

            alertDialog.show();
        }
    }

}
