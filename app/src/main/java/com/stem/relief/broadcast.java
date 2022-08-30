package com.stem.relief;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.FacebookSdk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import static com.stem.relief.MainScreen.myRef;
import static com.facebook.FacebookSdk.getApplicationContext;

public class broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Done Notification Button Reciever

        FacebookSdk.sdkInitialize(context);


        //Connection Check

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            String goal = intent.getStringExtra("toastMessage");
            final String userID = intent.getStringExtra("userID");

            //Add to done list
            myRef.child(userID).child("goals_done").push().setValue(goal);
            final Query queryRef = myRef.child(userID).child("in_progress_goals").orderByValue().equalTo(goal);


            //To Search for the selected goal .. remove from in progress list
            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String value = dataSnapshot.getKey();
                    assert value != null;
                    myRef.child(userID).child("in_progress_goals").child(value).removeValue();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

            //To eliminate the Notification after pressing Done Button
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(1);

        } else {
            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
        }
    }
}
