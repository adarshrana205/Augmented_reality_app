package com.hfad.helloar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;

public class MainActivity extends AppCompatActivity{

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.Fragment);
      arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
          Anchor anchor=hitResult.createAnchor();
          ModelRenderable.builder()
                  .setSource(this,Uri.parse("ArcticFox_Posed.sfb"))
                  .build()
                  .thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable))
                  .exceptionally(throwable ->{
                      AlertDialog.Builder builder=new AlertDialog.Builder(this);
                      builder.setMessage(throwable.getMessage()).show();

                      return null;
                  } );
      });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
    AnchorNode anchorNode=new AnchorNode(anchor);
    TransformableNode transformableNode=new TransformableNode(arFragment.getTransformationSystem());
    transformableNode.setParent(anchorNode);
    transformableNode.setRenderable(modelRenderable);
    arFragment.getArSceneView().getScene().addChild(anchorNode);
    transformableNode.select();
    }

}
