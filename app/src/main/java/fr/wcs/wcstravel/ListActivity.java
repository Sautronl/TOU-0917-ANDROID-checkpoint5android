package fr.wcs.wcstravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.wcstravel.Model.TravelModel;

public class ListActivity extends AppCompatActivity {

    TextView resultCompList,resultDateDep,resultDateDes,resultPrice;
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    TravelModel mTrav = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        resultCompList = findViewById(R.id.resultCompList);
        resultDateDep = findViewById(R.id.resultDateDep);
        resultDateDes = findViewById(R.id.resultDateDes);
        resultPrice = findViewById(R.id.resultPrice);

        Intent bundle = this.getIntent();
        mTrav = bundle.getParcelableExtra("info");

        Intent bun = this.getIntent();
        String key = bun.getStringExtra("key");

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference("checkpoint5/travels/"+key);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTrav = dataSnapshot.getValue(TravelModel.class);
                resultCompList.setText(mTrav.getAirline());
                resultDateDes.setText(mTrav.getDeparture_date());
                resultDateDep.setText(mTrav.getReturn_date());
                resultPrice.setText(mTrav.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
