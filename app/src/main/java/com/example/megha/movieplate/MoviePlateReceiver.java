package com.example.megha.movieplate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MoviePlateReceiver extends BroadcastReceiver {
    public MoviePlateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("BroadcastReceiverTag", "Movie Plate Receiver  : Broadcast Received");
        Toast.makeText(context, "Movie Plate Receiver: Broadcast Received", Toast.LENGTH_SHORT).show();
    }
}
