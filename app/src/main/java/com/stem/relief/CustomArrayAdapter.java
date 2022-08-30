package com.stem.relief;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.List;

import static com.stem.relief.App.CHANNEL_1_ID;
import static com.stem.relief.MainScreen.myRef;

public class CustomArrayAdapter extends ArrayAdapter {

    private View listItemView;
    private FirebaseAuth mAuth;

    public CustomArrayAdapter(Activity context, List<String> details, FirebaseAuth mAuth) {
        super(context, 0, details);
        this.mAuth = mAuth;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //To Make the InProgress Goals list
        listItemView = convertView;
        if (listItemView == null) {

            //Define the form of the item of the list
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.goal_item_in_progress, parent, false);
        }

        final String currentItem = String.valueOf(getItem(position));

        TextView goalTextView = (TextView) listItemView.findViewById(R.id.in_progress_text_view);
        goalTextView.setText(currentItem);


        ((RelativeLayout) listItemView.findViewById(R.id.in_progress_item_layout))
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        //Make a Sticky Notify

                        //Make the Toast message
                        Toast.makeText(getContext(), "Sticky Notify starts", Toast.LENGTH_SHORT).show();

                        //Intents to give link the Done - Close notification buttons to their broadcast receivers
                        Intent broadcastIntent = new Intent(getContext(), broadcast.class);
                        Intent broadcastNRIntent = new Intent(getContext(), broadcastNR.class);

                        //To register Goal title and database location in receiver
                        broadcastIntent.putExtra("toastMessage", currentItem);
                        broadcastIntent.putExtra("userID", mAuth.getCurrentUser().getUid());
                        PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        PendingIntent cancelIntent = PendingIntent.getBroadcast(getContext(), 0, broadcastNRIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                        //Check Android Version As Android 8.1 or higher uses different way to make notifications

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                            //Make notification
                            Notification notification = new NotificationCompat.Builder(getContext(),App.CHANNEL_1_ID)
                                    .setContentText(currentItem)
                                    .setContentTitle("Goal")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setSmallIcon(R.drawable.notification_ico)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .addAction(R.drawable.check_sign, "Done", actionIntent)
                                    .addAction(R.drawable.close, "Close", cancelIntent)
                                    .setOnlyAlertOnce(true)
                                    .setOngoing(true)
                                    .build();

                            //Show notification
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                            notificationManager.notify(1, notification);
                        }else{

                            //Make notification
                            Notification notification = new NotificationCompat.Builder(getContext())
                                    .setContentText(currentItem)
                                    .setContentTitle("Goal")
                                    .setSmallIcon(R.drawable.notification_ico)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .addAction(R.drawable.check_sign, "Done", actionIntent)
                                    .addAction(R.drawable.close, "Close", cancelIntent)
                                    .setOnlyAlertOnce(true)
                                    .setOngoing(true)
                                    .build();

                            //Show notification
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                            notificationManager.notify(1, notification);
                        }

                        return true;
                    }
                });

        final ImageView checkSign = listItemView.findViewById(R.id.check_sign);

        ImageView doneSign = (ImageView) listItemView.findViewById(R.id.in_progress_btn);
        doneSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSign.getVisibility() == View.GONE) {
                    checkSign.setVisibility(View.VISIBLE);
                } else {
                    checkSign.setVisibility(View.GONE);
                }


                //Thread to wait for 1 second after check as done
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            //To wait 1 second
                            sleep(1000);

                            //If Done mark is still checked Then remove goal from InProgress and add it to Done List
                            if (checkSign.getVisibility() == View.VISIBLE) {

                                //Add goal to Done List
                                myRef.child(mAuth.getCurrentUser().getUid()).child("goals_done").push().setValue(currentItem);

                                //Query to Search for the selected goal in InProgress List
                                final Query queryRef = myRef.child(mAuth.getCurrentUser().getUid()).child("in_progress_goals").orderByValue().equalTo(currentItem);

                                queryRef.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        String value = dataSnapshot.getKey();
                                        assert value != null;

                                        //Remove selected goal from InProgress List
                                        myRef.child(mAuth.getCurrentUser().getUid()).child("in_progress_goals").child(value).removeValue();
                                        queryRef.removeEventListener(this);
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
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
        });

        return listItemView;
    }
}
