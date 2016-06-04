package com.blockchain.landturtle;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.blockchain.utils.Config;
import com.blockchain.utils.NetworkManager;
import com.blockchain.utils.SHAHashingExample;
import com.blockchain.utils.Utils;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by shubham_verekar on 5/17/2016.
 */
public class LandSellForm extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout layout;
    NetworkManager manager = new NetworkManager();
    ImageView landpic, titledeed;
    String survey_id, property_id, price, land_pic_path, land_titledeed_file,contact;
    Button upload_landpic, upload_titledeed, cancel, sell;
    EditText surveyno, propertyno, priceet,contactet;
    NetworkManager nm  = new NetworkManager();

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

               // if (manager.isInternetPresent(LandSellForm.this)) {

                    survey_id = surveyno.getText().toString().trim();
                    property_id = propertyno.getText().toString().trim();
                    price = priceet.getText().toString().trim();
                    contact=contactet.getText().toString().trim();
                    if (survey_id.length() <= 0 || property_id.length() <= 0 || price.length() <= 0 || contact.length() <= 0) {

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


           /*     } else {
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
                }*/

            }
        });
    }

    private void initialize() {
        landpic = (ImageView) findViewById(R.id.land_pic);
        titledeed = (ImageView) findViewById(R.id.titleddeed_pic);
        surveyno = (EditText) findViewById(R.id.survery_id);
        propertyno = (EditText) findViewById(R.id.property_id);
        priceet = (EditText) findViewById(R.id.price);
        upload_landpic = (Button) findViewById(R.id.upload_landpic);
        upload_titledeed = (Button) findViewById(R.id.upload_titledeed);
        cancel = (Button) findViewById(R.id.cancel);
        sell = (Button) findViewById(R.id.sell);
        contactet=(EditText)findViewById(R.id.contact);
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
                   // Bitmap bitmap = BitmapFactory.decodeStream(LandSellForm.this.getContentResolver().openInputStream(selectedImageUri));

                    //set file path to textview so user can see the selected file name
                    titledeed.setImageResource(R.drawable.pdf);

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
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Sell My Land");
        progress.setMessage("Please wait.. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        new Thread(new Runnable() {
            @Override
            public void run() {



                String dochash = SHAHashingExample.generateSHA256(land_titledeed_file);
                Log.d("documenthash",dochash);
                int rescode =nm.uploadFile(land_pic_path);
                if(rescode==200)
                {

                    JSONObject obj = new JSONObject();
                   try
                   {
                       obj.put("survey_no",survey_id);
                       obj.put("land_id",property_id);
                       obj.put("price",price);
                       obj.put("contact",contact);
                       obj.put("hash",dochash);
                       obj.put("land_pic_path",new File(land_pic_path).getName());
                   }
                   catch(Exception e) {

                   }
                    String res  = nm.getResponseFromServer(Config.SELLLAND_URL, obj);
                    Log.d("resposne",res);
                    try{
                       final  JSONObject response= new JSONObject(res);
                        if(response.getInt("success")==1)
                        {
                            LandSellForm.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    progress.dismiss();
                                    Toast.makeText(LandSellForm.this,"Successfully Registered!! ",Toast.LENGTH_SHORT).show();
                                    LandSellForm.this.finish();
                                }
                            });
                        }
                        else
                        {
                            LandSellForm.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try
                                    {
                                        progress.dismiss();
                                        Toast.makeText(LandSellForm.this,response.getString("message"),Toast.LENGTH_SHORT).show();

                                    }
                                    catch(Exception e){

                                    }

                                }
                            });

                        }
                    }
                    catch(Exception e) {

                        e.printStackTrace();
                    }
                }
                else
                {

                        LandSellForm.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                                Toast.makeText(LandSellForm.this,"Network Error!! please try again.. ",Toast.LENGTH_SHORT).show();
                            }
                        });


                }

            }
        }).start();



    }
}
