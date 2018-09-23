package com.juborajsarker.helpmate.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {


    EditText emailET, userNameET, fullNameET, phoneET, cityET, choosePasswordET, confirmPasswordET, urlEt;
    RadioButton generalRB, expertRB;
    Button registerBTN, retakeButton, cancelButton;
    ImageView takeSnapIV, certificateIV;
    CardView certificateImageCV, expertCV;
    CheckBox broadbandCB, computerServiceCB, carpenterCB, cookCB, cobblerCB, decoratorCB, doctorCB, designerCB, driverCB,
            electricianCB, gasTechnicianCB, laundryCB, locksmithCB, lawyerCB, marketingCB, mobileServicingCB, painterCB,
            programmerCB, plumberCB, rentACarCB;
    TextView messageTV;


    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    String uid = "";
    String email, userName, fullName, phone, city, password, url, country;
    boolean isExpert, accountIsActivate;
    String expertsList = "";
    private SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceAll;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
        Boolean isLogedIn = sharedPreferences.getBoolean("isLogedIn", false);

        firebaseAuth = FirebaseAuth.getInstance();

        init();


    }

    private void init() {

        emailET = (EditText) findViewById(R.id.email_ET);
        userNameET = (EditText) findViewById(R.id.username_ET);
        fullNameET = (EditText) findViewById(R.id.full_name_ET);
        phoneET = (EditText) findViewById(R.id.phone_ET);
        cityET = (EditText) findViewById(R.id.city_ET);
        choosePasswordET = (EditText) findViewById(R.id.choose_password_ET);
        confirmPasswordET = (EditText) findViewById(R.id.confirm_password_ET);
        urlEt = (EditText) findViewById(R.id.url_ET);

        generalRB = (RadioButton) findViewById(R.id.RB_general);
        expertRB = (RadioButton) findViewById(R.id.RB_expert);

        takeSnapIV = (ImageView) findViewById(R.id.iv_take_snap);
        certificateIV = (ImageView) findViewById(R.id.certificate_IV);

        certificateImageCV = (CardView) findViewById(R.id.certificate_image_cv);
        expertCV = (CardView) findViewById(R.id.expert_CV);

        registerBTN = (Button) findViewById(R.id.register_BTN);
        retakeButton = (Button) findViewById(R.id.retakeBTN);
        cancelButton = (Button) findViewById(R.id.cancelBTN);

        broadbandCB = (CheckBox) findViewById(R.id.cb_broadband);
        computerServiceCB = (CheckBox) findViewById(R.id.cb_computer_service);
        carpenterCB = (CheckBox) findViewById(R.id.cb_carpenter);
        cookCB = (CheckBox) findViewById(R.id.cb_cook);
        cobblerCB = (CheckBox) findViewById(R.id.cb_cobbler);
        decoratorCB = (CheckBox) findViewById(R.id.cb_decorator);
        doctorCB = (CheckBox) findViewById(R.id.cb_doctor);
        designerCB = (CheckBox) findViewById(R.id.cb_designer);
        driverCB = (CheckBox) findViewById(R.id.cb_driver);
        electricianCB = (CheckBox) findViewById(R.id.cb_electritian);
        gasTechnicianCB = (CheckBox) findViewById(R.id.cb_gas_technician);
        laundryCB = (CheckBox) findViewById(R.id.cb_laundry);
        locksmithCB = (CheckBox) findViewById(R.id.cb_locksmith);
        lawyerCB = (CheckBox) findViewById(R.id.cb_lawyer);
        marketingCB = (CheckBox) findViewById(R.id.cb_marketing);
        mobileServicingCB = (CheckBox) findViewById(R.id.cb_mobile_servicing);
        painterCB = (CheckBox) findViewById(R.id.cb_painter);
        programmerCB = (CheckBox) findViewById(R.id.cb_programmer);
        plumberCB = (CheckBox) findViewById(R.id.cb_plumber);
        rentACarCB = (CheckBox) findViewById(R.id.cb_rent_a_car);

        messageTV = (TextView) findViewById(R.id.messageTV);

        generalRB.setChecked(true);

        setOnClick();


    }

    private void setOnClick() {

        expertRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    expertCV.setVisibility(View.VISIBLE);
                }
            }
        });

        generalRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    expertCV.setVisibility(View.GONE);
                }
            }
        });


        takeSnapIV.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                takePhoto();

            }
        });


        retakeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                takePhoto();

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                certificateImageCV.setVisibility(View.GONE);
            }
        });


        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((emailValidation() && passwordValidation() && checkEmptyValidity())
                        && ( (expertRB.isChecked() && checkExpertValidity()) || (generalRB.isChecked() && !checkExpertValidity())  )) {


                    if (certificateImageCV.getVisibility() == View.GONE && urlEt.getText().toString().equals("")) {


                        urlEt.setError("PLease enter url or take snap of certificate");


                    } else {

                        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setMessage("Please wait .....");
                        progressDialog.show();

                        hideInput();


                        email = emailET.getText().toString();
                        userName = userNameET.getText().toString();
                        fullName = fullNameET.getText().toString();
                        phone = phoneET.getText().toString();
                        city = cityET.getText().toString();
                        country = getUserCountry(RegisterActivity.this);
                        password = choosePasswordET.getText().toString();
                        expertsList = getExpertsList();

                        if (urlEt.getText().toString().equals("")) {

                            url = "null";

                        } else {

                            url = urlEt.getText().toString();
                        }

                        if (expertRB.isChecked()) {

                            isExpert = true;
                            accountIsActivate = false;

                        } else if (generalRB.isChecked()) {

                            isExpert = false;
                            accountIsActivate = false;
                        }


                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                                RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(RegisterActivity.this, "Registration successful !!!", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(RegisterActivity.this,
                                                    BottomNavigationActivity.class);

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            if (user != null) {

                                                uid = user.getUid();
                                            }


                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isLogedIn", true);
                                            editor.putBoolean("accountActive", false);
                                            editor.commit();

                                            insertData(uid, email, userName, fullName, phone, city, country,
                                                    password, url, isExpert, accountIsActivate, expertsList);

                                            progressDialog.dismiss();
                                            intent.putExtra("uid", uid);
                                            startActivity(intent);
                                            finish();


                                        } else {

                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                    }


                } else {


                    Toast.makeText(RegisterActivity.this, "Please check your error !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }




    private boolean checkExpertValidity() {

        if (!broadbandCB.isChecked() && !computerServiceCB.isChecked() && !carpenterCB.isChecked() && !cookCB.isChecked() &&
                !cobblerCB.isChecked() && !decoratorCB.isChecked() && !doctorCB.isChecked() && !designerCB.isChecked() &&
                !driverCB.isChecked() && !electricianCB.isChecked() && !gasTechnicianCB.isChecked() && !laundryCB.isChecked() &&
                !locksmithCB.isChecked() && !lawyerCB.isChecked() && !marketingCB.isChecked() && !mobileServicingCB.isChecked() &&
                !painterCB.isChecked() && !programmerCB.isChecked() && !plumberCB.isChecked() && !rentACarCB.isChecked()) {

            messageTV.setText("Please select at least 1 skill. Otherwise register as a general user");
            messageTV.setTextColor(getResources().getColor(R.color.colorRed));

            return false;

        } else {
            messageTV.setText("Select in which category you are expert");
            messageTV.setTextColor(getResources().getColor(R.color.colorBlack));
            return true;
        }
    }

    private void hideInput() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean emailValidation() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String inputEmail = emailET.getText().toString().trim();

        if (inputEmail.matches(emailPattern)) {

            return true;
        } else {

            emailET.setError("This types of email is not supported :(");
            return false;
        }


    }

    private boolean passwordValidation() {

        if (choosePasswordET.getText().toString().equals(confirmPasswordET.getText().toString())) {

            return true;

        } else {

            confirmPasswordET.setError("Password doesn't match :(");
            return false;
        }

    }


    private boolean checkEmptyValidity() {

        if (emailET.getText().toString().equals("")) {

            emailET.setError("This field is required !!!");

            return false;

        } else if (emailET.getText().toString().length() > 0 && Character.isWhitespace(emailET.getText().toString().charAt(0))) {

            emailET.setError("Email cannot start with a space");
            return false;

        } else if (userNameET.getText().toString().equals("")) {

            userNameET.setError("This field is required !!!");

            return false;

        } else if (userNameET.getText().toString().length() > 0 && Character.isWhitespace(userNameET.getText().toString().charAt(0))) {

            userNameET.setError("Username cannot start with a space");
            return false;

        } else if (fullNameET.getText().toString().equals("")) {

            fullNameET.setError("This field is required !!!");

            return false;

        } else if (fullNameET.getText().toString().length() > 0 && Character.isWhitespace(fullNameET.getText().toString().charAt(0))) {

            fullNameET.setError("Full Name cannot start with a space");
            return false;

        } else if (cityET.getText().toString().equals("")) {

            cityET.setError("This field is required !!!");

            return false;

        } else if (cityET.getText().toString().length() > 0 && Character.isWhitespace(cityET.getText().toString().charAt(0))) {

            cityET.setError("City name cannot start with a space");
            return false;

        } else if (choosePasswordET.getText().toString().equals("")) {

            choosePasswordET.setError("This field is required !!!");

            return false;

        } else if (choosePasswordET.getText().toString().length() > 0 && Character.isWhitespace(choosePasswordET.getText().toString().charAt(0))) {

            choosePasswordET.setError("Password cannot start with a space");
            return false;

        } else {

            return true;
        }
    }


    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available

                Locale loc = new Locale("", simCountry);
                return loc.getDisplayCountry();

            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available

                    Locale loc = new Locale("", networkCountry);
                    return loc.getDisplayCountry();
                }
            }
        } catch (Exception e) {

            Log.d("error", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {

            Toast.makeText(this, "Please complete registration first", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);


    }


    private void takePhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);

            } else {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        } else {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //  Bitmap bMapScaled = Bitmap.createScaledBitmap(photo, 330, 189, true);
            certificateImageCV.setVisibility(View.VISIBLE);
            certificateIV.setImageBitmap(photo);
        }
    }


    public String getExpertsList() {

        String experts = "";

        if (broadbandCB.isChecked()) {

            experts = experts + "";
        }


        return experts;
    }


    private void insertData(String uid, String email, String userName, String fullName,
                            String phone, String city, String country, String password,
                            String url, boolean isExpert, boolean accountIsActivate, String expertsList) {

        if (expertRB.isChecked()){

            if (broadbandCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Broadband/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (computerServiceCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Computer/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (carpenterCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Carpenter/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (cookCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Cook/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (cobblerCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Cobbler/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (decoratorCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Decorator/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (doctorCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Doctor/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (designerCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Designer/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (driverCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Driver/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (electricianCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Electrician/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (gasTechnicianCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Gas Technician/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (laundryCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Laundry/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (locksmithCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Locksmith/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (lawyerCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Lawyer/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (marketingCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Marketing/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (mobileServicingCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Mobile Servicing/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (painterCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Painter/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (programmerCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Programmer/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (plumberCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Plumber/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }if (rentACarCB.isChecked()){

                databaseReference = FirebaseDatabase.getInstance().getReference("User/Expert/" +
                        country + "/Rent a Car/" + city);

                pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                        password, url, isExpert, accountIsActivate, expertsList);

            }







        }else if (generalRB.isChecked()){

            databaseReference = FirebaseDatabase.getInstance().getReference("User/General/" +
                    country + "/" + city);

            pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                    password, url, isExpert, accountIsActivate, expertsList);
        }




        databaseReference = FirebaseDatabase.getInstance().getReference("User/All/");
        pushData(databaseReference, uid, email, userName, fullName, phone, city, country,
                password, url, isExpert, accountIsActivate, expertsList);


    }

    private void pushData(DatabaseReference databaseReference, String uid, String email, String userName, String fullName, String phone,
                          String city, String country, String password, String url, boolean isExpert,
                          boolean accountIsActivate, String expertsList) {


        UserModel userModel = new UserModel();
        userModel.setUserID(uid);
        userModel.setEmail(email);
        userModel.setUserName(userName);
        userModel.setFullName(fullName);
        userModel.setPhone(phone);
        userModel.setCity(city);
        userModel.setCountry(country);
        userModel.setPassword(password);
        userModel.setExpert(isExpert);
        userModel.setUrl(url);
        userModel.setAccountIsActivate(accountIsActivate);
        userModel.setExpertLIst(expertsList);


        databaseReference.child(uid).setValue(userModel);

        if (certificateImageCV.getVisibility() == View.VISIBLE){


            Bitmap bitmap = getBitmapFromImage(certificateIV);
            uploadImageIntoDatabase(uid, bitmap);
        }
    }

    private void uploadImageIntoDatabase(String uid, Bitmap bitmap) {


        Bitmap imageBitmap = getBitmapFromImage(certificateIV);
//        filePath = getImageUri(RegisterActivity.this, imageBitmap);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("User/Images/Certificate/");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        ref.child(uid).putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(RegisterActivity.this, "Uploaded Image", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(RegisterActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                      //  progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });


    }


    private Bitmap getBitmapFromImage(ImageView imageView){

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        return bitmap;
    }
}
