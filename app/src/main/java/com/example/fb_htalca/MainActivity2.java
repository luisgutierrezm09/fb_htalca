package com.example.fb_htalca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG ="" ;
    private final String[] permisos = { Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final int ACTIVITY_CAMARA = 50;
    private final int ACTIVITY_GALERIA = 60;
    private Bitmap bitmap;
private FirebaseAuth mAuth;
private FirebaseUser usuario;
private TextView  mensaje, uri;
private ImageView foto;
private  int contador;

private FirebaseDatabase database;
private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        contador =0;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        uri = findViewById(R.id.uri);
      foto  = findViewById(R.id.foto1);
        mensaje = findViewById(R.id.crearMensaje);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos, 100);
        }

        bitmap = null;

    }
    public void CrearRegalo(View view){
    contador ++;
            reference.child("regalos").child(usuario.getUid()).child("regalo_"+contador).child("uri").setValue(uri.getText().toString());
        reference.child("regalos").child(usuario.getUid()).child("regalo_"+contador).child("mensaje").setValue(mensaje.getText().toString());
        String claveunica = reference.push().getKey();
        reference.child("regalos").child(usuario.getUid()).child("regalo_"+contador).child("idSecreto").setValue(claveunica);
        Toast.makeText(this,"Regalo creado con id :"+contador,Toast.LENGTH_LONG).show();
    }

    public void llamar(View view){
        Intent intent = new Intent(this, MainActivity3.class);
     //   intent.putExtra("noticias", lista);

        startActivity(intent);
    }
    public void editarperfil(View view){
        Intent intent = new Intent(this, MainActivity5.class);
        //   intent.putExtra("noticias", lista);

        startActivity(intent);
    }
    public void CerrarSesion(View view){
        mAuth.signOut();
        finish();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACTIVITY_CAMARA:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    foto.setImageBitmap(bitmap);
                }
                break;

            case ACTIVITY_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    foto.setImageURI(ruta);
                }
                break;
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            /*if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Se necesita acceso a la cámara. Por favor conceda el permiso", Toast.LENGTH_LONG).show();
            }*/

            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de lectura de memoria", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de escritura de memoria", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void TomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ACTIVITY_CAMARA);

    }
    public void GuardarFoto(View view){
        if(bitmap != null){

            File archivoFoto = null;
            OutputStream streamSalida = null;
            String r ="";
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();

                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba";

                values.put(MediaStore.Images.Media.DISPLAY_NAME, nombreArchivo);
                values.put(MediaStore.Images.Media.MIME_TYPE, "Image/jpg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);

                Uri coleccion = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri fotoUri = resolver.insert(coleccion, values);
                r=fotoUri.toString()+nombreArchivo;
                try{
                    streamSalida = resolver.openOutputStream(fotoUri);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(fotoUri, values, null, null);
            } else {

                String ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba.jpg";
                archivoFoto = new File(ruta, nombreArchivo);
                r=ruta+"/"+nombreArchivo;
                try{
                    streamSalida = new FileOutputStream(archivoFoto);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            }

            boolean fotoOk = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamSalida);

            if(fotoOk){
                Toast.makeText(this, "Foto Guardada!", Toast.LENGTH_SHORT).show();
            }

            if(streamSalida != null){
                try{
                    streamSalida.flush();
                    streamSalida.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            uri.setText(r);

            if(archivoFoto != null){
                MediaScannerConnection.scanFile(this, new String[]{archivoFoto.toString()}, null, null);
            }


        } else {
            Toast.makeText(this, "Primero debe tomar una foto antes de usar esta opción", Toast.LENGTH_SHORT).show();
        }
    }
}