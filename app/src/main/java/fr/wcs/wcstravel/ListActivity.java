package fr.wcs.wcstravel;

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

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference("checkpoint5/travels/");

        mTrav = this.getIntent().getParcelableExtra("info");

        if (mTrav !=null) {
            resultCompList.setText(mTrav.getAirline());
            resultDateDes.setText(mTrav.getDeparture_date());
            resultDateDep.setText(mTrav.getReturn_date());
            resultPrice.setText(mTrav.getPrice());
        }
    }
}
