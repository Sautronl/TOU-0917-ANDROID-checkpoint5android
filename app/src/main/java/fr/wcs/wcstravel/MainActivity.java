package fr.wcs.wcstravel;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.wcs.wcstravel.Model.TravelModel;


public class MainActivity extends AppCompatActivity {

    EditText aeroDep,aeroDes, dateDep,dateDes;
    Button mValid;
    String resultAeroDep,resultAeroDes,dateStringA,dateStringB,key;
    private TravelModel mTravel = null;
    private RecyclerView recyclerView;
    private ArrayList<TravelModel>  travelList = new ArrayList<>();
    private ArrayList<String> keyList = new ArrayList<>();
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aeroDep = findViewById(R.id.aeroDep);
        aeroDes = findViewById(R.id.aeroDes);
        dateDep =(EditText) findViewById(R.id.date);
        dateDes =(EditText) findViewById(R.id.dateReturn);
        mValid = findViewById(R.id.valider);
        recyclerView = findViewById(R.id.recycler);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        resultAeroDep = aeroDep.getText().toString();
        resultAeroDes = aeroDes.getText().toString();

        dateDep.setFocusable(false);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                dateDep.setText(sdf.format(myCalendar.getTime()));
                dateStringA = dateDep.getText().toString();
            }
        };

        dateDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog date =
                        new DatePickerDialog(MainActivity.this, datePickerListener,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH));
                date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);//Désactive les jours précédents
                date.show();

            }
        });

        dateDes.setFocusable(false);
        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                dateDes.setText(sdf2.format(myCalendar2.getTime()));
                dateStringB = dateDes.getText().toString();
            }
        };

        dateDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog date =
                        new DatePickerDialog(MainActivity.this, datePickerListener2,
                                myCalendar2.get(Calendar.YEAR),
                                myCalendar2.get(Calendar.MONTH),
                                myCalendar2.get(Calendar.DAY_OF_MONTH));
                date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);//Désactive les jours précédents
                date.show();

            }
        });

        mValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultAeroDep = aeroDep.getText().toString();
                resultAeroDes = aeroDes.getText().toString();
                mRef.child("/checkpoint5/travels/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (travelList.size()>0){travelList.clear();}
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            mTravel = data.getValue(TravelModel.class);
                            if (mTravel.getDeparture_date().equals(dateStringA) && mTravel.getReturn_date().equals(dateStringB)){
                                travelList.add(mTravel);
                                key = data.getKey();
                                keyList.add(key);
                            }
                        }
                        TravelAdapter travelAdapter = new TravelAdapter(travelList,MainActivity.this,keyList);
                        recyclerView.setAdapter(travelAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
