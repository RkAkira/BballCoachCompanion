package com.efrei.bballcoachcompanion;



import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.camera.core.CameraSelector;
import androidx.lifecycle.LifecycleOwner;

import com.efrei.bballcoachcompanion.databinding.PictureMatchActivityBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PictureMatchActivity extends Activity {

    private static final String TAG = "CameraXApp";
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PictureMatchActivityBinding viewBinding;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;

    public interface LumaListener {
        void onLumaAvailable(double luma);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = PictureMatchActivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        // Set up the listeners for take photo and video capture buttons
        viewBinding.imageCaptureButton.setOnClickListener(view -> takePhoto());
        cameraExecutor = Executors.newSingleThreadExecutor();
    }


    private boolean allPermissionsGranted() {
        // Liste des permissions nécessaires
        String[] permissions = new String[] {
                android.Manifest.permission.CAMERA,  // Exemple de permission CAMERA
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                // Ajouter d'autres permissions nécessaires ici
        };

        // Parcourir la liste des permissions
        for (String permission : permissions) {
            // Vérifier si la permission n'est pas accordée
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;  // Retourner false si une permission n'est pas accordée
            }
        }
        return true;  // Retourner true si toutes les permissions sont accordées
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            ProcessCameraProvider cameraProvider;


            // Preview
            Preview preview = new Preview.Builder().build();
            preview.setSurfaceProvider(viewBinding.viewFinder.getSurfaceProvider());
            imageCapture = new ImageCapture.Builder().build();
            ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
            imageAnalysis.setAnalyzer(cameraExecutor, new LuminosityAnalyzer(luma -> Log.d(TAG, "Average luminosity: " + luma)));

            // Select back camera as a default
            CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
            try {

                cameraProvider = cameraProviderFuture.get();
                // Unbind use cases before rebinding
                cameraProvider.unbindAll();

                // Bind use cases to camera
                cameraProvider.bindToLifecycle((LifecycleOwner) PictureMatchActivity.this, cameraSelector, preview, imageCapture, imageAnalysis);

            } catch (Exception exc) {
                Log.e(TAG, "Use case binding failed", exc);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        // Obtenir une référence stable à l'objet ImageCapture modifiable
        if (imageCapture == null) {
            return;
        }
        // Créer un nom avec horodatage et une entrée dans MediaStore
        String name = new SimpleDateFormat(FILENAME_FORMAT, Locale.FRANCE)
                .format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
        }

        // Créer un objet d'options de sortie qui contient le fichier + les métadonnées
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
                .build();

        // Configurer le gestionnaire d'image capture, qui est déclenché après que la photo ait été prise
        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onError(@NonNull ImageCaptureException exc) {
                        Log.e(TAG, "Échec de la capture de la photo : " + exc.getMessage(), exc);
                    }

                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        String msg = "Capture de la photo réussie : " + output.getSavedUri();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, msg);
                    }
                }
        );
    }

    private static class LuminosityAnalyzer implements ImageAnalysis.Analyzer {

        private final LumaListener listener;

        public LuminosityAnalyzer(LumaListener listener) {
            this.listener = listener;
        }

        private byte[] toByteArray(ByteBuffer buffer) {
            buffer.rewind();    // Rembobiner le tampon à zéro
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);   // Copier le tampon dans un tableau d'octets
            return data;        // Retourner le tableau d'octets
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {

            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = toByteArray(buffer);
            int[] pixels = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                pixels[i] = data[i] & 0xFF;
            }
            double luma = Arrays.stream(pixels).average().orElse(0);

            listener.onLumaAvailable(luma);

            image.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }





}
