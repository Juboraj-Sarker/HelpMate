package com.juborajsarker.helpmate.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.PostAdapter;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.model.PostModel;
import com.juborajsarker.helpmate.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    View view;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    boolean isActiveUser, isExpert;
    String userName, fullName;

    List<UserModel> modelList = new ArrayList<>();
    List<PostModel> postList = new ArrayList<>();
    PostAdapter postAdapter;
    String uid;

    EditText postET;
    Spinner postCatSP;
    ImageView takeSnapIV, chooseImageIV;
    Button postBTN;
    LinearLayout errorLAYOUT;
    TextView messageTV;
    RecyclerView postRV;

    CardView postImageCV;
    ImageView contentIV;
    Button retakeBTN, cancelBTN;



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        loadData();

        return view;
    }

    private void init() {

        postET = (EditText) view.findViewById(R.id.post_text_ET);
        postCatSP = (Spinner) view.findViewById(R.id.post_cat_SP);
        takeSnapIV = (ImageView) view.findViewById(R.id.iv_take_snap);
        chooseImageIV = (ImageView) view.findViewById(R.id.choose_image_IV);
        postBTN = (Button) view.findViewById(R.id.post_BTN);

        messageTV = (TextView) view.findViewById(R.id.messageTV);
        errorLAYOUT = (LinearLayout) view.findViewById(R.id.error_LAYOUT);
        postRV = (RecyclerView) view.findViewById(R.id.post_RV);

        postImageCV = (CardView) view.findViewById(R.id.post_image_CV);
        contentIV = (ImageView) view.findViewById(R.id.content_IV);
        retakeBTN = (Button) view.findViewById(R.id.retakeBTN);
        cancelBTN = (Button) view.findViewById(R.id.cancelBTN);


        takeSnapIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideInput();

                if (isActiveUser){

                    takePhoto();

                }else {

                    Toast.makeText(getContext(), "You are not a verified user.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        chooseImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        postBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideInput();

                if (isActiveUser){

                    if (checkValidity()){

                        String postText = postET.getText().toString();
                        String pushKey = databaseReference.push().getKey();

                        databaseReference = FirebaseDatabase.getInstance()
                                .getReference("Post/" + getPostType() + "/" + pushKey);

                        pushData(uid, pushKey, postText, databaseReference);

                        databaseReference = FirebaseDatabase.getInstance().getReference("Post/All/"+ pushKey);
                        pushData(uid, pushKey, postText, databaseReference);

                        if (postImageCV.getVisibility() == View.VISIBLE){

                            storage = FirebaseStorage.getInstance();
                            storageReference = storage.getReference();
                            StorageReference imgRef = storageReference
                                    .child("Post/" + getPostType() + "/" + pushKey + "/" + pushKey +".jpg");

                            uploadImageIntoDatabase(imgRef,   contentIV, pushKey);
                            imgRef = storageReference.child("Post/All/" + pushKey + "/" + pushKey +".jpg");
                            uploadImageIntoDatabase(imgRef,  contentIV, pushKey);
                        }

                        postRV.setVisibility(View.VISIBLE);
                        proceedAsActiveUser();

                    }


                }else {

                    Toast.makeText(getContext(), "You are not a verified user", Toast.LENGTH_SHORT).show();
                }

            }
        });


        retakeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                takePhoto();

            }
        });


        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postImageCV.setVisibility(View.GONE);

            }
        });

    }


    private void loadData() {

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            uid = user.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("User/All/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserModel model = snapshot.getValue(UserModel.class);
                    modelList.add(model);

                    if (model.getUserID().equals(uid)){


                        userName = model.getUserName();
                        fullName = model.getFullName();

                        if (model.isExpert()){

                            isExpert = true;

                        }else {

                            isExpert = false;
                        }

                        if (model.isAccountIsActivate()){

                            errorLAYOUT.setVisibility(View.GONE);
                            postRV.setVisibility(View.VISIBLE);
                            isActiveUser = true;
                            proceedAsActiveUser();

                        }else {

                            errorLAYOUT.setVisibility(View.VISIBLE);
                            messageTV.setText("Your account is not verified yet !!");
                            postRV.setVisibility(View.GONE);
                            isActiveUser = false;
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void proceedAsActiveUser() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Post/All/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    PostModel post = snapshot.getValue(PostModel.class);
                    postList.add(post);
                }

                postImageCV.setVisibility(View.GONE);
                postET.setText("");
                postAdapter = new PostAdapter(getContext(), getActivity(),
                        postList, postAdapter, postRV, isExpert, uid, userName);


                RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(getContext(), 1);
                postRV.setLayoutManager(layoutManagerBeforeMeal);
                postRV.setItemAnimator(new DefaultItemAnimator());

                postAdapter.notifyDataSetChanged();
                postRV.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


     // Toast.makeText(getContext(), ""+ postList.size(), Toast.LENGTH_SHORT).show();



    }

    private void pushData(String uid, String pushKey, String postText, DatabaseReference reference) {

        PostModel post = new PostModel();

        post.setPostID(pushKey);
        post.setUserID(uid);
        post.setPostType(getPostType());
        post.setUserName(userName);
        post.setPostTime(new DateTimeConverter().getCurrentTime());
        post.setPostDate(new DateTimeConverter().getCurrentDate());
        post.setPostText(postText);
        post.setPostImageURL("url");
        post.setNoOfLike(0);

        if (postImageCV.getVisibility() == View.VISIBLE){

            post.setHasImage(true);

        }else {

            post.setHasImage(false);
        }


        reference.setValue(post);

    }

    private boolean checkValidity(){

        if (postET.getText().toString().equals("")){

            postET.setError("Please write something !!!");

            return false;

        }else if (postET.getText().toString().length() >0
                && Character.isWhitespace(postET.getText().toString().charAt(0))){

            postET.setError("Post cannot start with a space !!!");
            return false;

        }else if (postCatSP.getSelectedItemPosition() == 0){

            Toast.makeText(getActivity(), "Please select a valid post type !!!", Toast.LENGTH_SHORT).show();
            return false;

        }else {

            return true;
        }
    }

    private String getPostType(){

        String type = "";

        if (checkValidity()){

            type = postCatSP.getSelectedItem().toString();
        }

        return type;
    }

    private void hideInput() {

        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void takePhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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

            postRV.setVisibility(View.GONE);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap bMapScaled = Bitmap.createScaledBitmap(photo, 350, 190, true);
            postImageCV.setVisibility(View.VISIBLE);
            contentIV.setImageBitmap(bMapScaled);

        }
    }

    private void uploadImageIntoDatabase(StorageReference ref,  ImageView imageView, String postID) {


        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

            }

        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }

        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        });


    }

    private Bitmap getBitmapFromImage(ImageView imageView){

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        return bitmap;
    }

}
