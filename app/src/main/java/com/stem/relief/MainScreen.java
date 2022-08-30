package com.stem.relief;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.stem.relief.MainScreen.myRef;

public class MainScreen extends Activity implements DrawerLayout.DrawerListener {

    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    ImageView slider_ico;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        TextView hiMessage = findViewById(R.id.hi_message_text);
        slider_ico = findViewById(R.id.open_slider_ico);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        FirebaseUser user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        assert user != null;
        String name = user.getDisplayName();
        hiMessage.setText("Hi "+name);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(MainScreen.this,LoginPage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) { }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) { }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) { }

    @Override
    public void onDrawerStateChanged(int newState) { }



    public void GoToActivities(View view){ startActivity(new Intent(MainScreen.this,Activities.class)); }

    public void GoToGoals(View view){ startActivity(new Intent(MainScreen.this,Goals.class)); }

    public void GoToSleepCycle(View view){ startActivity(new Intent(MainScreen.this,SleepCycle.class));}

    public void SignOut(MenuItem menuItem){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this,LoginPage.class));
    }

    public void OpenSlider(View view){ mDrawerLayout.openDrawer(GravityCompat.START); }

    public void About(MenuItem menuItem){
        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View v = factory.inflate(R.layout.about, null);
        alertadd.setView(v);
        alertadd.show();
    }

    public void Exit(MenuItem menuItem){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
        finish();
    }

    public void Feedback(MenuItem menuItem){
        ViewDialog2 addScreen = new ViewDialog2();
        addScreen.showDialog(this, "Add goal",mAuth.getCurrentUser().getUid());
    }
}

class ViewDialog2 {

    private static String _goal;

    public ViewDialog2(){

    }

    public void showDialog(final Activity activity, String msg, final String Uid){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.feedback);

        TextView sendBtn = dialog.findViewById(R.id.send_btn);
        final EditText dialogGoal = (EditText) dialog.findViewById(R.id.feedback_text);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goal = dialogGoal.getText().toString();

                if (_goal.trim().length() > 0){
                    myRef.child("feedback").child(Uid).setValue(_goal);

                    Toast.makeText(activity, "Message sent", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }else{
                    Snackbar.make(v, "Can't be blank", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}