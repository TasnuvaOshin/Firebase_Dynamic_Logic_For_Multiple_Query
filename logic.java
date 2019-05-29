package cricketworldcup.worldcup.AdminPanel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import cricketworldcup.worldcup.AdminPanel.RegistrationStatus.all_filter_model_single;
import cricketworldcup.worldcup.R;

public class TeamScoreActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private DatabaseReference Users;
    private DatabaseReference datab;
    String key;
    String userdsmcode, username, userphoneno;
    String dsmname, smname, rsmname;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_score);
        GetParticipant();
        datab = FirebaseDatabase.getInstance().getReference().child("data");
db = FirebaseDatabase.getInstance().getReference().child("user_team_score").child("1");

    }

    private void GetParticipant() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("team_selection").child("1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    key = dataSnapshot1.getKey();
                    FindOutUser(key);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void FindOutUser(final String key) {

        //Log.d("key", key);
final String  keys = key;
        Users = FirebaseDatabase.getInstance().getReference().child("Users");

        Users.orderByChild(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                        userdsmcode = String.valueOf(ds1.child("userdsmcode").getValue());
                        username = String.valueOf(ds1.child("username").getValue());
                        userphoneno = String.valueOf(ds1.child("userphoneno").getValue());
                    }
                    GetValue(userdsmcode, username, userphoneno,keys);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void GetValue(final String userdsmcode, String username, String userphoneno,String key) {
        final String dsm = userdsmcode;
        final String name = username;
        final String phone = userphoneno;
        final String user = key;
        datab.orderByChild("G").equalTo(dsm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        dsmname = String.valueOf(ds.child("A").getValue());
                        smname = String.valueOf(ds.child("B").getValue());
                        rsmname = String.valueOf(ds.child("D").getValue());
                    }
                    WriteThisData(dsm,name,phone,user,dsmname,smname,rsmname);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void WriteThisData(final String dsm,final String name,final String phone,final String user,final String dsmname,final String smname,final String rsmname) {


        String n = name;
        String p = phone;
        String id = user;
        String code = dsm;
        String dsmn = dsmname;
        String rsmn = rsmname;
        String smn = smname;
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",n);
        hashMap.put("userphoneno",p);
        hashMap.put("userdsmno",code);
        hashMap.put("dsmname",dsmn);
        hashMap.put("rsmname",rsmn);
        hashMap.put("smname",smn);
        hashMap.put("score","0");

        db.child(id).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("DONE","done");
                Toast.makeText(TeamScoreActivity.this, "DONEEEEE", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

