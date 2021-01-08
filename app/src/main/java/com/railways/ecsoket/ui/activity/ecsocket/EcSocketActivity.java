package com.railways.ecsoket.ui.activity.ecsocket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.ui.activity.home.HomeActivity;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;

public class EcSocketActivity extends BaseActivity implements EcSocketView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject
    EcSocketPresenter presenter;
    @Inject
    EcsocketDatabase
            ecsocketDatabase;

    @BindView(R.id.section_incharge)
    LinearLayout sectinIncharge;
    @BindView(R.id.text_section_incharge)
    TextView textSectionIncharge;

    @BindView(R.id.section_main)
    LinearLayout sectionMain;
    @BindView(R.id.text_section)
    TextView textSection;


    @BindView(R.id.block_main)
    LinearLayout blockMain;

    @BindView(R.id.ec_type)
    LinearLayout ecType;

    @BindView(R.id.text_block)
    TextView textBlock;

    @BindView(R.id.text_ec_type)
    TextView textecType;

    @BindView(R.id.text_longitude)
    TextView textLongitude;
    @BindView(R.id.text_lattitude)
    TextView textLatitude;

    @BindView(R.id.new_socket_submit)
    Button submit;

    @BindView(R.id.edit_remarks)
    EditText ecName;


    String sectionInchargeId="";
    String sectionId="";
    String blockId="";



    @BindView(R.id.back_arrow)
    ImageView back;

    double lat=0;
    double lang=0;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lat=getIntent().getDoubleExtra("lat",0);
        lang=getIntent().getDoubleExtra("lang",0);

        textLatitude.setText("Latitude: "+lat);
        textLongitude.setText("Longitude: "+lang);
        sectinIncharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownRecycler(textSectionIncharge,1);
            }
        });

        sectionMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sectionInchargeId.isEmpty()){
                    dropDownText("Select section incharge", EcSocketConstants.ER_DD);
                }else {
                    dropDownRecycler(textSection, 2);
                }
            }
        });

        blockMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sectionId.isEmpty()){
                    dropDownText("Select section", EcSocketConstants.ER_DD);
                }else {
                    dropDownRecycler(textBlock, 3);
                }
            }
        });

        ecType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dropDownOk(textecType);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time=Long.toString(System.currentTimeMillis());
                if(!blockId.isEmpty() && !ecName.getText().toString().isEmpty()){
                    JsonObject obj= new JsonObject();
                    try {
                        obj.addProperty("userId",EcSocketConstants.USER_ID);
                        obj.addProperty("blockSectionId",blockId);
                        obj.addProperty("ecLocation",ecName.getText().toString());
                        obj.addProperty("ecLattitude", textLatitude.getText().toString());
                        obj.addProperty("ecLongitude",textLongitude.getText().toString());
                        obj.addProperty("ecType",textecType.getText().toString());
                        obj.addProperty("dataId",time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
                    ecsocketDatabase.insertNewSocketId(new GsonBuilder().create().toJson(obj),"false",time);
                    submit.setEnabled(false);
                    updateMessage();
                }else{
                    dropDownText("Add details to submit form",EcSocketConstants.ER_DD);
                }
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


    }

    private void updateMessage() {
        dropDownText("EC-Socket updated successfully",EcSocketConstants.SU_DD);
        new Handler().postDelayed(this::finish, 1000);
    }


    @Override
    protected int getLayout() {
        return R.layout.socket_activity;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);

    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        presenter.detachView();

    }



    public void dropDownOk(TextView textview) {

        final DialogPlus gridDialog;
        // do something
        gridDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_ok))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();

        TextView okText=(TextView) gridDialog.findViewById(R.id.ok);
        okText.setText("FRP");
        TextView notOkText=(TextView) gridDialog.findViewById(R.id.not_ok);
        notOkText.setText("IRON");
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("FRP");
                gridDialog.dismiss();
            }
        });
        notOkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("IRON");
                gridDialog.dismiss();
            }
        });


        gridDialog.show();

    }


    public void dropDownRecycler(TextView textview,int Type) {

        final DialogPlus gridDialog;
        // do something
        gridDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_recycler))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        ListView selectionList=(ListView) gridDialog.findViewById(R.id.selection);

        ArrayList<String> names=null;
        if(Type==1){
            names  = ecsocketDatabase.getSectionList();
        }
        if(Type==2){
            names  = ecsocketDatabase.getSectionDataList(sectionInchargeId);
        }
        if(Type==3){
            names  = ecsocketDatabase.getBlockList(sectionId);
        }
        if(Type==4){
            names  = ecsocketDatabase.getSocketList(blockId);
        }


        GridViewAdapter gridViewAdapter = new GridViewAdapter(EcSocketActivity.this,names);
        selectionList.setAdapter(gridViewAdapter);

        ArrayList<String> finalNames = names;
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textview.setText(finalNames.get(position));
                updateId(finalNames.get(position),Type);
                gridDialog.dismiss();
            }
        });


        gridDialog.show();

    }

    private void updateId(String s, int type) {
        if(type==1){
            sectionInchargeId  = ecsocketDatabase.getSectionDetails(s);
        }
        if(type==2){
            sectionId  = ecsocketDatabase.getSectionDataDetails(s);
        }
        if(type==3){
            blockId  = ecsocketDatabase.getBlockDetails(s);
        }

    }


    public class GridViewAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> selections;
        LayoutInflater inflter;

        private GridViewAdapter(Context applicationContext, ArrayList<String> selections ) {
            this.context = applicationContext;
            this.selections = selections;
            inflter = (LayoutInflater.from(applicationContext));

        }

        @Override
        public int getCount() {
            return selections.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.form_option_text_view, parent, false);
            }
            TextView checkedTextView = (TextView) view.findViewById(R.id.selection_text);
            checkedTextView.setText(selections.get(position));


            return view;
        }
    }



    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    textLatitude.setText(location.getLatitude()+"");
                                    textLongitude.setText(location.getLongitude()+"");
                                    requestNewLocationData();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(40000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.e("Updating the location","location"+locationResult.getLocations());
            Location mLastLocation = locationResult.getLastLocation();
            textLatitude.setText(mLastLocation.getLatitude()+"");
            textLongitude.setText(mLastLocation.getLongitude()+"");


            double wayLatitude=0;
            double wayLongitude=0;
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                }
            }
            textLatitude.setText(wayLatitude+"");
            textLongitude.setText(wayLongitude+"");

        }
    };


    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
}
