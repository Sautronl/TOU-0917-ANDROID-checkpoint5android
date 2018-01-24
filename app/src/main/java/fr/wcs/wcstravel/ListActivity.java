package fr.wcs.wcstravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(ListActivity.this));


    }
}
