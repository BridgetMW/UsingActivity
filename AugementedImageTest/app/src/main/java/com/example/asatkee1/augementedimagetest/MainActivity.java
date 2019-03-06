package com.example.asatkee1.augementedimagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private CustomArFragment arFragment;
    private boolean shouldAddModel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.getPlaneDiscoveryController().hide();

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
        Log.d("size", "size is" + augmentedImages.size());

        for (AugmentedImage augmentedImage : augmentedImages){
            Log.d("Debugging method update frame", "Update frame was called!");

            if (augmentedImage.getTrackingState() == TrackingState.TRACKING){
                Log.d("trucking state matched picture", "picture was founded!");

                if ((augmentedImage.getName().equals("H_letter")|| augmentedImage.getName().equals("H_letter_1") ||
                        augmentedImage.getName().equals("Housing"))){

                    Log.d("image name was matched", "model was put on picture!");

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, RBuilding.class);
                    MainActivity.this.startActivity(myIntent);
                    //shouldAddModel = false;
                } else if ((augmentedImage.getName().equals("math_book"))){

                    Log.d("image name was matched", "model was put on picture!");

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, SBuilding.class);
                    MainActivity.this.startActivity(myIntent);
                    //shouldAddModel = false;
                }
            }
        }
    }

    public boolean setupAugmentedImageDb(Config config, Session session){
        AugmentedImageDatabase augmentedImageDatabase;

        ArrayList <Bitmap> bitmap = new ArrayList<Bitmap>();

        for(int i = 0; i < loadAugmentedImage().size(); i++){
            bitmap.add(loadAugmentedImage().get(i));
        }

        if(bitmap.isEmpty()){
            return  false;
        }

        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("H_letter", bitmap.get(0));
        augmentedImageDatabase.addImage("H_letter_1", bitmap.get(1));
        augmentedImageDatabase.addImage("Housing", bitmap.get(2));
        augmentedImageDatabase.addImage("math_book", bitmap.get(3));
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }


    private ArrayList<Bitmap> loadAugmentedImage(){
        ArrayList <Bitmap> bitmaps = new ArrayList<Bitmap>();
        try (InputStream is = getAssets().open("H_letter.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }

        try (InputStream is = getAssets().open("H_letter_1.JPG")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }

        try (InputStream is = getAssets().open("Housing.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }

        try (InputStream is = getAssets().open("math_book.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }

        //Buffer
        try (InputStream is = getAssets().open("H_letter_2.JPG")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }

        if(!bitmaps.isEmpty()){
            return bitmaps;
        }
        return null;
    }



}
