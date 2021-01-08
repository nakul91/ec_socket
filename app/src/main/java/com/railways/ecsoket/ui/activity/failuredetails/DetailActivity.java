package com.railways.ecsoket.ui.activity.failuredetails;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.pojo.FailureResponse;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;
import com.railways.ecsoket.ui.activity.splash.SplashPresenter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailActivity extends BaseActivity implements DetailView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject
    DetailPresenter presenter;
    @Inject
    EcsocketDatabase
            ecsocketDatabase;
    @BindView(R.id.back_arrow)
    ImageView backArrow;


    @BindView(R.id.text_longitude)
    TextView textLongitude;
    @BindView(R.id.text_lattitude)
    TextView textLatitude;

    @BindView(R.id.before_file)
    TextView beforeText;
    @BindView(R.id.after_file)
    TextView afterText;

    @BindView(R.id.new_detail_submit)
    Button submit;

    @BindView(R.id.before_cap)
    Button beforeCap;
    @BindView(R.id.after_cap)
    Button afterCap;

    @BindView(R.id.edit_remarks)
    EditText ecName;

    double lat=0;
    double lang=0;
    int PERMISSION_ID = 44;
    int CAM_PERMISSION_ID = 55;
    boolean beforeFlag=true;
    FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    String beforeEncoded="";
    String afterEncoded="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        beforeCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeFlag=true;
                if(checkCamPermissions()){
                    openCameraIntent();
                }else{
                    requestCamPermissions();
                }
            }
        });
        afterCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeFlag=false;
                if(checkCamPermissions()){
                    openCameraIntent();
                }else{
                   requestCamPermissions();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time=Long.toString(System.currentTimeMillis());
                    JsonObject obj= new JsonObject();
                    try {
                        obj.addProperty("userId", EcSocketConstants.USER_ID);
                        obj.addProperty("failureId",getIntent().getStringExtra("ID"));
                        obj.addProperty("fixComments",ecName.getText().toString());
                        obj.addProperty("locationLattitude", textLatitude.getText().toString());
                        obj.addProperty("locationLongitude",textLongitude.getText().toString());
                        obj.addProperty("beforePicture", beforeEncoded);
                        obj.addProperty("afterPicture",afterEncoded);
                        obj.addProperty("afterPicturefileName", afterText.getText().toString());
                        obj.addProperty("beforePicturefileName",beforeText.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
                    ecsocketDatabase.insertFailId(new GsonBuilder().create().toJson(obj),"false",getIntent().getStringExtra("ID"));
                    submit.setEnabled(false);
                    updatePrefrence(getIntent().getStringExtra("ID"));
                    updateMessage();

            }

        });

    }

    private void updatePrefrence(String id) {
        Gson mGson= new Gson();
        FailureResponse failures=mGson.fromJson(mPref.getFailure(),FailureResponse.class);
        for(int i=0;i<failures.getRecords().size();i++){
            if(failures.getRecords().get(i).getFailureId().equalsIgnoreCase(id)){
                failures.getRecords().remove(i);
            }
        }
       String json = mGson.toJson(failures);
        mPref.setFailure(json);

    }

    private void updateMessage() {
        dropDownText("Failure added successfully",EcSocketConstants.SU_DD);
        new Handler().postDelayed(this::finish, 1000);
    }


    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                //mImageView.setImageBitmap(imageBitmap);
                updateBase64(imageBitmap);
            }
        }
    }

    private void updateBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        String time=Long.toString(System.currentTimeMillis());
       if(beforeFlag){
           beforeEncoded=encoded;
           beforeText.setText(formattedDate+"_"+time+".jpg");
       }else{
           afterEncoded=encoded;
           afterText.setText(formattedDate+"_"+time+".jpg");
       }
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_details;
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



    private boolean checkCamPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestCamPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                CAM_PERMISSION_ID
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
        if (requestCode == CAM_PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCameraIntent();
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
