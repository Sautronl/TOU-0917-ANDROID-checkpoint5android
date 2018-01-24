package fr.wcs.wcstravel;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

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

    EditText  dateDep,dateDes,conv,monnaie;
    Spinner aeroDep,aeroDes;
    Button mValid,convertButton;
    String dateStringA,dateStringB;
    String resultAeroDep,resultAeroDes;
    Double mPrice;
    private static String TAG = "MainAct";
    private TravelModel mTravel = null;
    private RecyclerView recyclerView;
    private ArrayList<TravelModel>  travelList = new ArrayList<>();
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aeroDep = findViewById(R.id.departure);
        aeroDes = findViewById(R.id.destination);
        dateDep =(EditText) findViewById(R.id.date);
        dateDes =(EditText) findViewById(R.id.dateReturn);
        conv =(EditText) findViewById(R.id.conv);
        monnaie =(EditText) findViewById(R.id.monnaie);
        mValid = findViewById(R.id.valider);
        convertButton = findViewById(R.id.convertButton);
        recyclerView = findViewById(R.id.recycler);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.model_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        aeroDep.setAdapter(adapter);

        aeroDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aeroDep.setEnabled(true);
                aeroDep.setEnabled(true);

                switch (i){
                    case 1:
                        resultAeroDep = "BOS";
                        break;
                    case 2:
                        resultAeroDep = "LAX";
                        break;
                    case 3:
                        resultAeroDep = "MIA";
                        break;
                    case 4:
                        resultAeroDep = "NYC";
                        break;
                    default:
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<CharSequence> adapterReturn = ArrayAdapter.createFromResource(this,
                R.array.model_return, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        aeroDes.setAdapter(adapterReturn);

        aeroDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aeroDes.setEnabled(true);
                aeroDes.setEnabled(true);

                switch (i){
                    case 1:
                        resultAeroDes = "BOS";
                        break;
                    case 2:
                        resultAeroDes= "LAX";
                        break;
                    case 3:
                        resultAeroDes = "MIA";
                        break;
                    case 4:
                        resultAeroDes = "NYC";
                        break;
                    default:
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String priceEdit = conv.getText().toString();
                String monnaieEdit = monnaie.getText().toString();
                if (priceEdit.isEmpty() || monnaieEdit.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.erreur, Toast.LENGTH_SHORT).show();
                }else{
                    Double prixDouble = Double.parseDouble(priceEdit);
                    Toast.makeText(MainActivity.this, convert(prixDouble,monnaieEdit), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRef.child("/checkpoint5/travels/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (travelList.size()>0){travelList.clear();}
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            mTravel = data.getValue(TravelModel.class);
                            if (mTravel.getDeparture_date().equals(dateStringA) && mTravel.getReturn_date().equals(dateStringB)
                                    && mTravel.getTravel().contains(resultAeroDep) && mTravel.getTravel().contains(resultAeroDes)){
                                travelList.add(mTravel);
                                Log.d(TAG, "onDataChange: "+ travelList.size());
                                mPrice = Double.parseDouble(mTravel.getPrice());
                            }
                        }
                        TravelAdapter travelAdapter = new TravelAdapter(travelList,MainActivity.this);
                        recyclerView.setAdapter(travelAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public String convert(Double prix,String init){
        Double eurDoll = 0.810628967;
        Double dollEuro = 1.2366;
        Double resultat;

        if (init.equals("EUR")){
            resultat = prix * eurDoll;
        }else{
            resultat = prix * dollEuro;
        }
        String total = String .valueOf(resultat);
        return total;
    }
}
