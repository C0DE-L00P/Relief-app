package com.stem.relief;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.stem.relief.MainScreen.database;
import static com.stem.relief.MainScreen.myRef;

public class Goals extends Activity{

    public static List<String> inProgressGoals;
    List<String> doneGoals;
    ListView listView1;
    ListView listView2;

    public Context mContext;

    public static boolean updateRequired;

    ArrayAdapter adapter;
    ArrayAdapter adapter2;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String CHANNEL_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals);

        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) { Toast.makeText(getApplicationContext(), "Please, Check your connection", Toast.LENGTH_SHORT).show();}

        mContext = getApplicationContext();

        listView1 = findViewById(R.id.in_progress_list_view);
        listView2 = findViewById(R.id.done_list_view);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                pullToRefresh.setRefreshing(false);
            }
        });

        inProgressGoals = new ArrayList<String>();
        doneGoals = new ArrayList<String>();

        myRef.child(mAuth.getCurrentUser().getUid()).child("in_progress_goals").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                inProgressGoals.add(String.valueOf(dataSnapshot.getValue()));

                Log.i("resulting", String.valueOf(dataSnapshot.getValue()));

                adapter = new CustomArrayAdapter(Goals.this,inProgressGoals,mAuth);
                listView1.setAdapter(adapter);
                Utility.setListViewHeightBasedOnChildren(listView1);

                if (updateRequired){recreate();
                    updateRequired = false;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {recreate(); }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



        myRef.child(mAuth.getCurrentUser().getUid()).child("goals_done").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                doneGoals.add(String.valueOf(dataSnapshot.getValue()));

                Log.i("Done resulting", String.valueOf(dataSnapshot.getValue()));

                adapter2 = new CustomArrayAdapter2(Goals.this,doneGoals);
                listView2.setAdapter(adapter2);
                Utility.setListViewHeightBasedOnChildren(listView2);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {recreate(); }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DaTuM","Resume done");
    }

    public void removeDoneList(View view){
        myRef.child(mAuth.getCurrentUser().getUid()).child("goals_done").removeValue();
    }

    public void addGoal(View view){
        ViewDialog addScreen = new ViewDialog();
        addScreen.showDialog(this, "Add goal",mAuth.getCurrentUser().getUid());
    }
}


class ViewDialog {

    private static String _goal;

    public ViewDialog(){

    }

    public void showDialog(Activity activity, String msg,final String Uid){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        Goals.updateRequired = true;

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        final EditText dialogGoal = (EditText) dialog.findViewById(R.id.text_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goal = dialogGoal.getText().toString();

                if (_goal.trim().length() > 0){
                    myRef.child(Uid).child("in_progress_goals").push().setValue(_goal);

                    dialog.dismiss();
                }else{
                    Snackbar.make(v, "Can't be blank", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
