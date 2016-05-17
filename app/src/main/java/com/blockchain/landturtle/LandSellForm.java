package com.blockchain.landturtle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.utils.NetworkManager;
import com.blockchain.utils.Utils;

/**
 * Created by shubham_verekar on 5/17/2016.
 */
public class LandSellForm extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout layout;
    NetworkManager manager = new NetworkManager();
    ImageView landpic, titledeed;
    String survey_id, property_id, landtype, areasize, price, location, land_pic_path, land_titledeed_file;
    Button upload_landpic, upload_titledeed, cancel, sell;
    EditText surveyno, propertyno, landtypeet, areasizeet, priceet, locationet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landsellform);
        initialize();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        upload_landpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });
        upload_titledeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (manager.isInternetPresent(LandSellForm.this)) {

                    survey_id = surveyno.getText().toString().trim();
                    property_id = propertyno.getText().toString().trim();
                    landtype = landtypeet.getText().toString().trim();
                    areasize = areasizeet.getText().toString().trim();
                    price = priceet.getText().toString().trim();
                    location = locationet.getText().toString().trim();
                    if (survey_id.length() <= 0 || property_id.length() <= 0 || landtype.length() <= 0 || areasize.length() <= 0 || price.length() <= 0 || location.length() <= 0) {

                        snackbar = Snackbar
                                .make(layout, "All fields are mandatory!", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        snackbar.dismiss();
                                    }
                                });

                        // Changing message text color
                        snackbar.setActionTextColor(Color.parseColor("#FF9800"));

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.parseColor("#2196F3"));
                        snackbar.show();

                    } else {

                        sellLandProcess();
                    }


                } else {
                    snackbar = Snackbar
                            .make(layout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("SETTINGS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.parseColor("#FF9800"));

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#2196F3"));
                    snackbar.show();
                }

            }
        });
    }

    private void initialize() {
        landpic = (ImageView) findViewById(R.id.land_pic);
        titledeed = (ImageView) findViewById(R.id.titleddeed_pic);
        surveyno = (EditText) findViewById(R.id.survery_id);
        propertyno = (EditText) findViewById(R.id.property_id);
        landtypeet = (EditText) findViewById(R.id.landtype);
        areasizeet = (EditText) findViewById(R.id.areasize);
        priceet = (EditText) findViewById(R.id.price);
        locationet = (EditText) findViewById(R.id.location);
        upload_landpic = (Button) findViewById(R.id.upload_landpic);
        upload_titledeed = (Button) findViewById(R.id.upload_titledeed);
        cancel = (Button) findViewById(R.id.cancel);
        sell = (Button) findViewById(R.id.sell);
        layout = (CoordinatorLayout) findViewById(R.id.cordinatorlayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //switches based on the type of file selected by the user
        switch (requestCode) {

            case 1: {
                //when user selects a image
                try {
                    //get the image uri
                    Uri selectedImageUri = data.getData();
                    //get image path
                    land_pic_path = Utils.getPath(LandSellForm.this, selectedImageUri);
                    Log.d("landpicpath", land_pic_path);
                    //get the bitmap content from uri
                    Bitmap bitmap = BitmapFactory.decodeStream(LandSellForm.this.getContentResolver().openInputStream(selectedImageUri));
                    //set image bitmap to the image so user can see the selected image
                    landpic.setImageBitmap(bitmap);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;
            case 2: {
                //when user selects a residence file
                try {

                    //get the file uri
                    Uri selectedImageUri = data.getData();
                    //get file path
                    land_titledeed_file = Utils.getPath(LandSellForm.this, selectedImageUri);
                    Log.d("titledeedpath", land_titledeed_file);
                    Bitmap bitmap = BitmapFactory.decodeStream(LandSellForm.this.getContentResolver().openInputStream(selectedImageUri));

                    //set file path to textview so user can see the selected file name
                    titledeed.setImageBitmap(bitmap);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            break;
        }
    }

    private void sellLandProcess()
    {

    }
}