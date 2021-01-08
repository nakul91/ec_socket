package com.railways.ecsoket.ui.activity.newtest;

import android.Manifest;
import android.animation.AnimatorSet;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.pojo.ReasonsResponse;
import com.railways.ecsoket.pojo.Remakr;
import com.railways.ecsoket.pojo.RemarkSection;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TestActivity extends BaseActivity implements TestView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject
    TestPresenter presenter;
    @Inject
    EcsocketDatabase
            ecsocketDatabase;

    @BindView(R.id.box_condition)
    LinearLayout boxCondition;
    @BindView(R.id.socket_condition)
    LinearLayout socketConditio;
    @BindView(R.id.autopair_condition)
    LinearLayout autoPairCondition;
    @BindView(R.id.emc_status)
    LinearLayout emcStatus;
    @BindView(R.id.before_cap)
    Button beforeCap;
    @BindView(R.id.before_file)
    TextView beforeText;
    @BindView(R.id.ecsocket_type)
    LinearLayout socketType;


    @BindView(R.id.text_box_condition)
    TextView textBoxCondition;
    @BindView(R.id.text_socket_condition)
    TextView textSocketCondition;
    @BindView(R.id.text_autopair_condition)
    TextView textAutoPairCondition;
    @BindView(R.id.text_emc_status)
    TextView textEmcStatus;

    @BindView(R.id.text_ecsocket_type)
    TextView textSocketType;


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
    @BindView(R.id.text_block)
    TextView textBlock;

    @BindView(R.id.box_painted)
    LinearLayout boxPainted;
    @BindView(R.id.text_box_painted)
    TextView textPainted;


    @BindView(R.id.ecsocket_main)
    LinearLayout socketMain;
    @BindView(R.id.text_ecsocket)
    TextView textSocket;

    String sectionInchargeId="";
    String sectionId="";
    String blockId="";
    String socketId="";

    String autoRemarkInchargeId="";
    String emcRemarkId="";
    String boxRemarkId="";
    String socketRemarkId="";



    @BindView(R.id.text_longitude)
    TextView textLongitude;
    @BindView(R.id.text_lattitude)
    TextView textLatitude;

    @BindView(R.id.back_arrow)
    ImageView back;

    @BindView(R.id.edit_remarks)
    EditText editRemark;


    @BindView(R.id.new_test_submit)
    Button submit;

    double lat=0;
    double lang=0;

    int PERMISSION_ID = 44;
    int CAM_PERMISSION_ID = 55;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    FusedLocationProviderClient mFusedLocationClient;
    String beforeEncoded="";



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

        boxCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownOk(textBoxCondition,1);
            }
        });
        socketConditio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownOk(textSocketCondition,2);
            }
        });
        autoPairCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownOk(textAutoPairCondition,3);
            }
        });
        emcStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownOk(textEmcStatus,4);
            }
        });

        socketType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownOk(textSocketType);
            }
        });

        sectinIncharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownRecycler(textSectionIncharge,1);
            }
        });

        boxPainted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownPainted(textPainted);
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

        socketMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blockId.isEmpty()){
                    dropDownText("Select block", EcSocketConstants.ER_DD);
                }else {
                    dropDownRecycler(textSocket, 4);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time=Long.toString(System.currentTimeMillis());
                String datetime= Calendar.getInstance().getTime().toString();
                if(!blockId.isEmpty() && !socketId.isEmpty()){
                    JsonObject obj= new JsonObject();
                    try {
                        obj.addProperty("userId",EcSocketConstants.USER_ID);
                        obj.addProperty("sectionInchargeId",sectionInchargeId);
                        obj.addProperty("sectionId",sectionId);
                        obj.addProperty("blockSectionId", blockId);
                        obj.addProperty("ecId",socketId);
                        obj.addProperty("dateOfTesting",datetime);
                        obj.addProperty("testLocationLattitude",textLatitude.getText().toString());
                        obj.addProperty("testLocationLongitude",textLongitude.getText().toString());
                        obj.addProperty("boxCondition",textBoxCondition.getText().toString().split("-")[0]);
                        obj.addProperty("boxCRemarks",boxRemarkId);
                        obj.addProperty("socketCondition",textSocketCondition.getText().toString().split("-")[0]);
                        obj.addProperty("socketRemarks",socketRemarkId);
                        obj.addProperty("autoPairCondition",textAutoPairCondition.getText().toString().split("-")[0]);
                        obj.addProperty("auroPairRemarks",autoRemarkInchargeId);
                        obj.addProperty("EMCStatus",textEmcStatus.getText().toString().split("-")[0]);
                        obj.addProperty("EMCRemarks",emcRemarkId);
                        obj.addProperty("ecType",textSocketType.getText().toString());
                        obj.addProperty("anyReamrks",editRemark.getText().toString());
                        obj.addProperty("dataId",time);
                        obj.addProperty("painted",textPainted.getText().toString());
                        obj.addProperty("testPicture",beforeEncoded);
                        obj.addProperty("testPictureFileName",beforeText.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
                    ecsocketDatabase.insertSocketId(new GsonBuilder().create().toJson(obj),"false",time);
                    submit.setEnabled(false);
                    updateMessage();
                }else{
                    dropDownText("Add details to submit form",EcSocketConstants.ER_DD);
                }
            }
        });



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        beforeCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCamPermissions()){
                    openCameraIntent();
                }else{
                    requestCamPermissions();
                }
            }
        });

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




    private void updateMessage() {
        dropDownText("Testing updated successfully",EcSocketConstants.SU_DD);
        new Handler().postDelayed(this::finish, 1000);
    }



    @Override
    protected int getLayout() {
        return R.layout.test_activity;
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



    public void dropDownOk(TextView textview,int type) {

        final DialogPlus gridDialog;
        // do something
        gridDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_ok))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();

        TextView okText=(TextView) gridDialog.findViewById(R.id.ok);
        TextView notOkText=(TextView) gridDialog.findViewById(R.id.not_ok);
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("OK");
                gridDialog.dismiss();
            }
        });
        notOkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("NOT OK");
                gridDialog.dismiss();
                new Handler().postDelayed((Runnable) () -> {
                    dropDownReason(type,textview);
                },1000);

            }
        });


        gridDialog.show();

    }


    public void dropDownPainted(TextView textview) {

        final DialogPlus gridDialog;
        // do something
        gridDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_ok))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();

        TextView okText=(TextView) gridDialog.findViewById(R.id.ok);
        TextView notOkText=(TextView) gridDialog.findViewById(R.id.not_ok);
        okText.setText("Yes");
        notOkText.setText("No");
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("Yes");
                gridDialog.dismiss();
            }
        });
        notOkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("No");
                gridDialog.dismiss();
            }
        });


        gridDialog.show();

    }


    public void dropDownReason(int type,TextView textview) {
        Log.e("RESONS",type+"");

        final DialogPlus gridsDialog;
        // do something
        gridsDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_recycler))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        ListView selectionList=(ListView) gridsDialog.findViewById(R.id.selection);

         List<Remakr> names=null;
         names = getReasons(type);
        GridReasonAdapter gridViewAdapter = new GridReasonAdapter(TestActivity.this,names);
        selectionList.setAdapter(gridViewAdapter);

        List<Remakr> finalNames = names;
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              updateValues(finalNames.get(position).getRemarkId(),type);
                textview.setText(textview.getText().toString()+" - "+finalNames.get(position).getRemark());
                gridsDialog.dismiss();
            }
        });


        gridsDialog.show();

    }

    private void updateValues(String remarkId, int type) {
        if(type==1){
            boxRemarkId=remarkId;
        }else if(type==2){
            socketRemarkId=remarkId;
        }else if(type==3){
            autoRemarkInchargeId=remarkId;
        }else if(type==4){
            emcRemarkId=remarkId;
        }
    }

    private List<Remakr> getReasons(int type) {
        String reason="";
        List<Remakr> list= new ArrayList<>();
        if(type==1){
            reason="BOX";
        }else if(type==2){
            reason="SOCKET";
        }else if(type==3){
            reason="AUTOPAIR";
        }else if(type==4){
            reason="EMC";
        }
        Gson mGson= new Gson();
        ReasonsResponse reasons=mGson.fromJson(mPref.getReasons(),ReasonsResponse.class);

        for(int i=0;i<reasons.getSection().size();i++){
            if(reasons.getSection().get(i).getRemarkSection().equalsIgnoreCase(reason)){
                list= reasons.getSection().get(i).getRemakrs();
            }

        }
        Log.e("RESONS",list.size()+"");
        return list;
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


        GridViewAdapter gridViewAdapter = new GridViewAdapter(TestActivity.this,names);
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
        if(type==4){
            socketId  = ecsocketDatabase.getSocketDetails(s);
            Log.e("SOCKETIIIID","iD"+socketId);
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


    public class GridReasonAdapter extends BaseAdapter {
        Context context;
        List<Remakr> selections;
        LayoutInflater inflter;

        private GridReasonAdapter(Context applicationContext, List<Remakr> selections ) {
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
            return selections.get(i);
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
            checkedTextView.setText(selections.get(position).getRemark());


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



    private void updateBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        String time=Long.toString(System.currentTimeMillis());

            beforeEncoded=encoded;
            beforeText.setText(formattedDate+"_"+time+".jpg");

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

    public void DownOk(TextView textview) {

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

}
