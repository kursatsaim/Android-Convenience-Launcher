package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.os.ParcelFileDescriptor;
import android.text.InputType;
import android.util.Pair;
import android.util.Size;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FaceRecognition extends AppCompatActivity  {
    FaceDetector detector;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    ImageView face_preview;
    Interpreter tfLite;
    TextView reco_name,preview_info,textAbove_preview;
    Button recognize,camera_switch, actions;
    ImageButton add_face;
    CameraSelector cameraSelector;
    boolean developerMode=false;
    float distance= 1.0f;
    boolean start=true,flipX=false;
    Context context=FaceRecognition.this;
    int cam_face=CameraSelector.LENS_FACING_FRONT; //Default Back Camera

    int[] intValues;
    int inputSize=112;  //Input size for model
    boolean isModelQuantized=false;
    float[][] embeedings;
    float IMAGE_MEAN = 128.0f;
    float IMAGE_STD = 128.0f;
    int OUTPUT_SIZE=192; //Output size of model
    private Handler handler = new Handler();
    private static int SELECT_PICTURE = 1;
    ProcessCameraProvider cameraProvider;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public String UserName="";
    public String UserName2="";
    String modelFile="mobile_face_net.tflite"; //model name
    List<MyObserver> observers = new ArrayList<>();
    private List<MyObserver2> observerz;

    private HashMap<String, SimilarityClassifier.Recognition> registered = new HashMap<>(); //saved Faces
    public int x = 0;

    public FaceRecognition() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registered=readFromSP(); //Load saved faces from memory when app starts
        setContentView(R.layout.activity_face_recognition);
        face_preview =findViewById(R.id.imageView);
        reco_name =findViewById(R.id.textView);
        preview_info =findViewById(R.id.textView2);
        textAbove_preview =findViewById(R.id.textAbovePreview);
        add_face=findViewById(R.id.imageButton);
        add_face.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPref = getSharedPreferences("Distance",Context.MODE_PRIVATE);
        distance = sharedPref.getFloat("distance",1.00f);


        face_preview.setVisibility(View.INVISIBLE);
        recognize=findViewById(R.id.button3);
        camera_switch=findViewById(R.id.button5);
        actions=findViewById(R.id.button2);
        textAbove_preview.setText("Algılanan yüz:");
//        preview_info.setText("        Recognized Face:");
        //Camera Permission
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        //On-screen Action Button
        actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Lütfen seçiniz:");

                // add a checkbox list
                String[] names= {"Yüz listesini göster","Yüz listesini güncelle","Yüzleri yükle","Yüzleri getir","Bütün yüzleri sil"};

                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which)
                        {
                            case 0:
                                displaynameListview();
                                break;
                            case 1:
                                updatenameListview();
                                break;
                            case 2:
                                insertToSP(registered,0); //mode: 0:save all, 1:clear all, 2:update all
                                break;
                            case 3:
                                registered.putAll(readFromSP());
                                break;
                            case 4:
                                clearnameList();
                                break;
                        }

                    }
                });


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("İptal", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //On-screen switch to toggle between Cameras.
        camera_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cam_face==CameraSelector.LENS_FACING_FRONT) {
                    cam_face = CameraSelector.LENS_FACING_BACK;
                    flipX=true;
                }
                else {
                    cam_face = CameraSelector.LENS_FACING_FRONT;
                    flipX=false;
                }
                cameraProvider.unbindAll();
                cameraBind();
            }
        });

        add_face.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFace();
            }
        }));


        recognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recognize.getText().toString().equals("Yüzü algıla"))
                {
                    start=true;
                    textAbove_preview.setText("Eklenen yüz:");
                    recognize.setText("Yüz ekle");
                    add_face.setVisibility(View.INVISIBLE);
                    reco_name.setVisibility(View.VISIBLE);
                    face_preview.setVisibility(View.INVISIBLE);
                    preview_info.setText("");
                    //preview_info.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textAbove_preview.setText("Yüz önizlemesi: ");
                    recognize.setText("Yüzü algıla");
                    add_face.setVisibility(View.VISIBLE);
                    reco_name.setVisibility(View.INVISIBLE);
                    face_preview.setVisibility(View.VISIBLE);
                    preview_info.setText("1.Yüzünüzü kameranın önüne getirin.\n\n2.Yüzünüzün önizlemesi burada çıkacaktır\n\n3.Kaydetmek için Yüz ekle butonuna basın.");


                }

            }
        });

        //Load model
        try {
            tfLite=new Interpreter(loadModelFile(FaceRecognition.this,modelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Initialize Face Detector
        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .build();
        detector = FaceDetection.getClient(highAccuracyOpts);

        cameraBind();



    }

    private void testHyperparameter()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Hyperparameter:");

        // add a checkbox list
        String[] names= {"Maximum Nearest Neighbour Distance"};

        builder.setItems(names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which)
                {
                    case 0:
//                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                        hyperparameters();
                        break;

                }

            }

        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void developerMode()
    {
        if (developerMode) {
            developerMode = false;
            Toast.makeText(context, "Developer Mode OFF", Toast.LENGTH_SHORT).show();
        }
        else {
            developerMode = true;
            Toast.makeText(context, "Developer Mode ON", Toast.LENGTH_SHORT).show();
        }
    }
    private void addFace()
    {
        {

            start=false;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("İsim girin:");

            // Set up the input
            final EditText input = new EditText(context);

            input.setInputType(InputType.TYPE_CLASS_TEXT );
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(context, input.getText().toString(), Toast.LENGTH_SHORT).show();

                    //Create and Initialize new object with Face embeddings and Name.
                    SimilarityClassifier.Recognition result = new SimilarityClassifier.Recognition(
                            "0", "", -1f);
                    result.setExtra(embeedings);
                    add_face.setVisibility(View.INVISIBLE);
                    reco_name.setVisibility(View.VISIBLE);
                    face_preview.setVisibility(View.INVISIBLE);
                    preview_info.setText("");
                    registered.put( input.getText().toString(),result);
                    start=true;

                }
            });
            builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    start=true;
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }
    private  void clearnameList()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setTitle("Bütün tanınan yüzleri silmek istediğinize emin misiniz?");
        builder.setPositiveButton("Hepsini sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registered.clear();
                Toast.makeText(context, "Kayıtlar silindi", Toast.LENGTH_SHORT).show();
            }
        });
        insertToSP(registered,1);
        builder.setNegativeButton("İptal",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updatenameListview()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(registered.isEmpty()) {
            builder.setTitle("Yeni yüz eklenmedi.");
            builder.setPositiveButton("OK",null);
        }
        else{
            builder.setTitle("Silinecek kaydı seçin:");

            // add a checkbox list
            String[] names= new String[registered.size()];
            boolean[] checkedItems = new boolean[registered.size()];
            int i=0;
            for (Map.Entry<String, SimilarityClassifier.Recognition> entry : registered.entrySet())
            {
                //System.out.println("NAME"+entry.getKey());
                names[i]=entry.getKey();
                checkedItems[i]=false;
                i=i+1;

            }

            builder.setMultiChoiceItems(names, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    // user checked or unchecked a box
                    //Toast.makeText(MainActivity.this, names[which], Toast.LENGTH_SHORT).show();
                    checkedItems[which]=isChecked;

                }
            });


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // System.out.println("status:"+ Arrays.toString(checkedItems));
                    for(int i=0;i<checkedItems.length;i++)
                    {
                        //System.out.println("status:"+checkedItems[i]);
                        if(checkedItems[i])
                        {
//                                Toast.makeText(MainActivity.this, names[i], Toast.LENGTH_SHORT).show();
                            registered.remove(names[i]);
                        }

                    }
                    insertToSP(registered,2); //mode: 0:save all, 1:clear all, 2:update all
                    Toast.makeText(context, "Kayıtlar güncellendi", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("İptal", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void hyperparameters()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Euclidean Distance");
        builder.setMessage("0.00 -> Perfect Match\n1.00 -> Default\nTurn On Developer Mode to find optimum value\n\nCurrent Value:");
        // Set up the input
        final EditText input = new EditText(context);

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        SharedPreferences sharedPref = getSharedPreferences("Distance",Context.MODE_PRIVATE);
        distance = sharedPref.getFloat("distance",1.00f);
        input.setText(String.valueOf(distance));
        // Set up the buttons
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, input.getText().toString(), Toast.LENGTH_SHORT).show();

                distance= Float.parseFloat(input.getText().toString());


                SharedPreferences sharedPref = getSharedPreferences("Distance",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("distance", distance);
                editor.apply();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }


    private void displaynameListview()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // System.out.println("Registered"+registered);
        if(registered.isEmpty())
            builder.setTitle("Yeni yüz eklenmedi.");
        else
            builder.setTitle("Kayıtlar:");

        // add a checkbox list
        String[] names= new String[registered.size()];
        boolean[] checkedItems = new boolean[registered.size()];
        int i=0;
        for (Map.Entry<String, SimilarityClassifier.Recognition> entry : registered.entrySet())
        {
            //System.out.println("NAME"+entry.getKey());
            names[i]=entry.getKey();
            checkedItems[i]=false;
            i=i+1;

        }
        builder.setItems(names,null);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Kamera izni verildi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Kamera izni reddedildi", Toast.LENGTH_LONG).show();
            }
        }
    }

    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    //Bind camera and preview view
    private void cameraBind()
    {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        previewView=findViewById(R.id.previewView);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this in Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(cam_face)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(640, 480))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) //Latest frame is shown
                        .build();

        Executor executor = Executors.newSingleThreadExecutor();
        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override

            public void analyze(@NonNull ImageProxy imageProxy) {
                try {
                    Thread.sleep(0);  //Camera preview refreshed every 10 millisec(adjust as required)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InputImage image = null;


                @SuppressLint("UnsafeExperimentalUsageError")
                // Camera Feed-->Analyzer-->ImageProxy-->mediaImage-->InputImage(needed for ML kit face detection)

                //Image mediaImage = imageProxy.getImage();
                Image mediaImage = imageProxy.getImage();

                if (mediaImage != null) {
                    image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
//                    System.out.println("Rotation "+imageProxy.getImageInfo().getRotationDegrees());
                }

//                System.out.println("ANALYSIS");

                //Process acquired image to detect faces
                Task<List<Face>> result =
                        detector.process(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<List<Face>>() {
                                            @Override
                                            public void onSuccess(List<Face> faces) {

                                                if(faces.size()!=0) {

                                                    Face face = faces.get(0); //Get first face from detected faces
//                                                    System.out.println(face);

                                                    //mediaImage to Bitmap
                                                    Bitmap frame_bmp = toBitmap(mediaImage);

                                                    int rot = imageProxy.getImageInfo().getRotationDegrees();

                                                    //Adjust orientation of Face
                                                    Bitmap frame_bmp1 = rotateBitmap(frame_bmp, rot, false, false);



                                                    //Get bounding box of face
                                                    RectF boundingBox = new RectF(face.getBoundingBox());

                                                    //Crop out bounding box from whole Bitmap(image)
                                                    Bitmap cropped_face = getCropBitmapByCPU(frame_bmp1, boundingBox);

                                                    if(flipX)
                                                        cropped_face = rotateBitmap(cropped_face, 0, flipX, false);
                                                    //Scale the acquired Face to 112*112 which is required input for model
                                                    Bitmap scaled = getResizedBitmap(cropped_face, 112, 112);

                                                    if(start)
                                                        recognizeImage(scaled); //Send scaled bitmap to create face embeddings.
//                                                    System.out.println(boundingBox);

                                                }
                                                else
                                                {
                                                    if(registered.isEmpty())
                                                        reco_name.setText("Yüz ekle");
                                                    else
                                                        reco_name.setText("Yüz algılanmadı!");
                                                }

                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        })
                                .addOnCompleteListener(new OnCompleteListener<List<Face>>() {
                                    @Override
                                    public void onComplete(@NonNull Task<List<Face>> task) {

                                        imageProxy.close(); //v.important to acquire next frame for analysis
                                    }
                                });


            }
        });


        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);


    }

    public void recognizeImage(final Bitmap bitmap) {

        // set Face to Preview
        face_preview.setImageBitmap(bitmap);

        //Create ByteBuffer to store normalized image

        ByteBuffer imgData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4);

        imgData.order(ByteOrder.nativeOrder());

        intValues = new int[inputSize * inputSize];

        //get pixel values from Bitmap to normalize
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        imgData.rewind();

        for (int i = 0; i < inputSize; ++i) {
            for (int j = 0; j < inputSize; ++j) {
                int pixelValue = intValues[i * inputSize + j];
                if (isModelQuantized) {
                    // Quantized model
                    imgData.put((byte) ((pixelValue >> 16) & 0xFF));
                    imgData.put((byte) ((pixelValue >> 8) & 0xFF));
                    imgData.put((byte) (pixelValue & 0xFF));
                } else { // Float model
                    imgData.putFloat((((pixelValue >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat((((pixelValue >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat(((pixelValue & 0xFF) - IMAGE_MEAN) / IMAGE_STD);

                }
            }
        }
        //imgData is input to our model
        Object[] inputArray = {imgData};

        Map<Integer, Object> outputMap = new HashMap<>();


        embeedings = new float[1][OUTPUT_SIZE]; //output of model will be stored in this variable

        outputMap.put(0, embeedings);

        tfLite.runForMultipleInputsOutputs(inputArray, outputMap); //Run model



        float distance_local = Float.MAX_VALUE;
        String id = "0";
        String label = "?";

        //Compare new face with saved Faces.
        if (registered.size() > 0) {

            final List<Pair<String, Float>> nearest = findNearest(embeedings[0]);//Find 2 closest matching face

            if (nearest.get(0) != null) {

                final String name = nearest.get(0).first; //get name and distance of closest matching face
                // label = name;
                distance_local = nearest.get(0).second;

                if(distance_local<distance) { //If distance between Closest found face is more than 1.000 ,then output UNKNOWN face.
                    reco_name.setText(name);
                    UserName2 = name;

                    if(name.equals("baba"))
                    {
                        if (x == 0) {
                            Intent intent = new Intent(this, Dad.class);
                            startActivity(intent);
                            x++;
                            finish();
                        }
                        else {finish();}

                    }

                    /*for (MyObserver2 observer : observerz) {
                        observer.onUpdate(UserName2); // "MyObserver" yerine "MyObserver2" yazın
                    }*/
                    //setUserName2(UserName2);
                    //Toast.makeText(this,"Lets go ife girmeme time",5).show();
                }
                else
                    reco_name.setText("Bilinmeyen yüz.");
//                    System.out.println("nearest: " + name + " - distance: " + distance_local);




            }

        }




    }


    public String getUserName2() {
        return UserName2;
    }

    //Compare Faces by distance between face embeddings
    private List<Pair<String, Float>> findNearest(float[] emb) {
        List<Pair<String, Float>> neighbour_list = new ArrayList<Pair<String, Float>>();
        Pair<String, Float> ret = null; //to get closest match
        Pair<String, Float> prev_ret = null; //to get second closest match
        for (Map.Entry<String, SimilarityClassifier.Recognition> entry : registered.entrySet())
        {

            final String name = entry.getKey();
            final float[] knownEmb = ((float[][]) entry.getValue().getExtra())[0];

            float distance = 0;
            for (int i = 0; i < emb.length; i++) {
                float diff = emb[i] - knownEmb[i];
                distance += diff*diff;
            }
            distance = (float) Math.sqrt(distance);
            if (ret == null || distance < ret.second) {
                prev_ret=ret;
                ret = new Pair<>(name, distance);
            }
        }
        if(prev_ret==null) prev_ret=ret;
        neighbour_list.add(ret);
        neighbour_list.add(prev_ret);

        return neighbour_list;

    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private static Bitmap getCropBitmapByCPU(Bitmap source, RectF cropRectF) {
        Bitmap resultBitmap = Bitmap.createBitmap((int) cropRectF.width(),
                (int) cropRectF.height(), Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(resultBitmap);

        // draw background
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.WHITE);
        cavas.drawRect(
                new RectF(0, 0, cropRectF.width(), cropRectF.height()),
                paint);

        Matrix matrix = new Matrix();
        matrix.postTranslate(-cropRectF.left, -cropRectF.top);

        cavas.drawBitmap(source, matrix, paint);

        if (source != null && !source.isRecycled()) {
            source.recycle();
        }

        return resultBitmap;
    }

    private static Bitmap rotateBitmap(
            Bitmap bitmap, int rotationDegrees, boolean flipX, boolean flipY) {
        Matrix matrix = new Matrix();

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees);

        // Mirror the image along the X or Y axis.
        matrix.postScale(flipX ? -1.0f : 1.0f, flipY ? -1.0f : 1.0f);
        Bitmap rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // Recycle the old bitmap if it has changed.
        if (rotatedBitmap != bitmap) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }

    //IMPORTANT. If conversion not done ,the toBitmap conversion does not work on some devices.
    private static byte[] YUV_420_888toNV21(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int ySize = width*height;
        int uvSize = width*height/4;

        byte[] nv21 = new byte[ySize + uvSize*2];

        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer(); // Y
        ByteBuffer uBuffer = image.getPlanes()[1].getBuffer(); // U
        ByteBuffer vBuffer = image.getPlanes()[2].getBuffer(); // V

        int rowStride = image.getPlanes()[0].getRowStride();
        assert(image.getPlanes()[0].getPixelStride() == 1);

        int pos = 0;

        if (rowStride == width) { // likely
            yBuffer.get(nv21, 0, ySize);
            pos += ySize;
        }
        else {
            long yBufferPos = -rowStride; // not an actual position
            for (; pos<ySize; pos+=width) {
                yBufferPos += rowStride;
                yBuffer.position((int) yBufferPos);
                yBuffer.get(nv21, pos, width);
            }
        }

        rowStride = image.getPlanes()[2].getRowStride();
        int pixelStride = image.getPlanes()[2].getPixelStride();

        assert(rowStride == image.getPlanes()[1].getRowStride());
        assert(pixelStride == image.getPlanes()[1].getPixelStride());

        if (pixelStride == 2 && rowStride == width && uBuffer.get(0) == vBuffer.get(1)) {
            // maybe V an U planes overlap as per NV21, which means vBuffer[1] is alias of uBuffer[0]
            byte savePixel = vBuffer.get(1);
            try {
                vBuffer.put(1, (byte)~savePixel);
                if (uBuffer.get(0) == (byte)~savePixel) {
                    vBuffer.put(1, savePixel);
                    vBuffer.position(0);
                    uBuffer.position(0);
                    vBuffer.get(nv21, ySize, 1);
                    uBuffer.get(nv21, ySize + 1, uBuffer.remaining());

                    return nv21; // shortcut
                }
            }
            catch (ReadOnlyBufferException ex) {
                // unfortunately, we cannot check if vBuffer and uBuffer overlap
            }

            // unfortunately, the check failed. We must save U and V pixel by pixel
            vBuffer.put(1, savePixel);
        }

        // other optimizations could check if (pixelStride == 1) or (pixelStride == 2),
        // but performance gain would be less significant

        for (int row=0; row<height/2; row++) {
            for (int col=0; col<width/2; col++) {
                int vuPos = col*pixelStride + row*rowStride;
                nv21[pos++] = vBuffer.get(vuPos);
                nv21[pos++] = uBuffer.get(vuPos);
            }
        }

        return nv21;
    }

    private Bitmap toBitmap(Image image) {

        byte[] nv21=YUV_420_888toNV21(image);


        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        //System.out.println("bytes"+ Arrays.toString(imageBytes));

        //System.out.println("FORMAT"+image.getFormat());

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    //Save Faces to Shared Preferences.Conversion of Recognition objects to json string
    private void insertToSP(HashMap<String, SimilarityClassifier.Recognition> jsonMap,int mode) {
        if(mode==1)  //mode: 0:save all, 1:clear all, 2:update all
            jsonMap.clear();
        else if (mode==0)
            jsonMap.putAll(readFromSP());
        String jsonString = new Gson().toJson(jsonMap);
//        for (Map.Entry<String, SimilarityClassifier.Recognition> entry : jsonMap.entrySet())
//        {
//            System.out.println("Entry Input "+entry.getKey()+" "+  entry.getValue().getExtra());
//        }
        SharedPreferences sharedPreferences = getSharedPreferences("HashMap", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("map", jsonString);
        //System.out.println("Input josn"+jsonString.toString());
        editor.apply();
        Toast.makeText(context, "Yüz kaydedildi", Toast.LENGTH_SHORT).show();
    }

    //Load Faces from Shared Preferences.Json String to Recognition object
    private HashMap<String, SimilarityClassifier.Recognition> readFromSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("HashMap", MODE_PRIVATE);
        String defValue = new Gson().toJson(new HashMap<String, SimilarityClassifier.Recognition>());
        String json=sharedPreferences.getString("map",defValue);
        // System.out.println("Output json"+json.toString());
        TypeToken<HashMap<String,SimilarityClassifier.Recognition>> token = new TypeToken<HashMap<String,SimilarityClassifier.Recognition>>() {};
        HashMap<String,SimilarityClassifier.Recognition> retrievedMap=new Gson().fromJson(json,token.getType());
        // System.out.println("Output map"+retrievedMap.toString());

        //During type conversion and save/load procedure,format changes(eg float converted to double).
        //So embeddings need to be extracted from it in required format(eg.double to float).
        for (Map.Entry<String, SimilarityClassifier.Recognition> entry : retrievedMap.entrySet())
        {
            float[][] output=new float[1][OUTPUT_SIZE];
            ArrayList arrayList= (ArrayList) entry.getValue().getExtra();
            arrayList = (ArrayList) arrayList.get(0);
            for (int counter = 0; counter < arrayList.size(); counter++) {
                output[0][counter]= ((Double) arrayList.get(counter)).floatValue();
            }
            entry.getValue().setExtra(output);

            //System.out.println("Entry output "+entry.getKey()+" "+entry.getValue().getExtra() );

        }
//        System.out.println("OUTPUT"+ Arrays.deepToString(outut));
        //Toast.makeText(context, "Yüzler kaydedildi", Toast.LENGTH_SHORT).show();
        return retrievedMap;
    }

    //Load Photo from phone storage
    private void loadphoto()
    {
        start=false;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    //Similar Analyzing Procedure
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try {
                    InputImage impphoto=InputImage.fromBitmap(getBitmapFromUri(selectedImageUri),0);
                    detector.process(impphoto).addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                        @Override
                        public void onSuccess(List<Face> faces) {

                            if(faces.size()!=0) {
                                recognize.setText("Yüzü algıla");
                                add_face.setVisibility(View.VISIBLE);
                                reco_name.setVisibility(View.INVISIBLE);
                                face_preview.setVisibility(View.VISIBLE);
                                preview_info.setText("1.Yüzünüzü kameranın önüne getirin.\n\n2.Yüzünüzün önizlemesi burada çıkacaktır\n\n3.Kaydetmek için Yüz ekle butonuna basın.");
                                Face face = faces.get(0);
//                                System.out.println(face);

                                //write code to recreate bitmap from source
                                //Write code to show bitmap to canvas

                                Bitmap frame_bmp= null;
                                try {
                                    frame_bmp = getBitmapFromUri(selectedImageUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Bitmap frame_bmp1 = rotateBitmap(frame_bmp, 0, flipX, false);

                                //face_preview.setImageBitmap(frame_bmp1);


                                RectF boundingBox = new RectF(face.getBoundingBox());


                                Bitmap cropped_face = getCropBitmapByCPU(frame_bmp1, boundingBox);

                                Bitmap scaled = getResizedBitmap(cropped_face, 112, 112);
                                // face_preview.setImageBitmap(scaled);

                                recognizeImage(scaled);
                                addFace();
//                                System.out.println(boundingBox);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            start=true;
                            Toast.makeText(context, "Yüz eklenemedi.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    face_preview.setImageBitmap(getBitmapFromUri(selectedImageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public String getUserName() {
        return UserName;
    }


    /*public void addObservera(MyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void updateName(String newName) {

    }*/


    /*public void setUserName2(String newUserName2) {
        this.UserName2 = newUserName2;
        notifyObservers(newUserName2); // Call notifyObservers when UserName2 changes
    }*/

   /* private void notifyObservers(String newUserName2) {
        for (MyObserver observer : observers) {
            observer.update(this, newUserName2); // Pass both the observable and the new name
        }
    }*/

    public void addObserver(MyObserver2 observer) {
        observerz.add(observer);
    }

    public void removeObserver(MyObserver2 observer) {
        observerz.remove(observer);
    }

    public void onUserNameChanged(String name) { // Kullanıcı adı değiştiğinde observer'ları bilgilendir
        for (MyObserver2 observer : observerz) {
            observer.onUpdate(name);
        }
    }


}