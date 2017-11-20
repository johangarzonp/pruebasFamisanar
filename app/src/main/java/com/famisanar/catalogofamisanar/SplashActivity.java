package com.famisanar.catalogofamisanar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/****************************************************************************************************************************
En SplashActivity se coloca el fondo que se desee para presentacion de la App, este se puede cambiar en activity_splash.xml
reemplazando el .png que se llama splash.

Se puede cambiar la duración del Splash simplemente cambiando el valor de DURACION_SPLASH que se encuentra en milisegundos
De esta acitivity se pasa a ListadoCategorias
****************************************************************************************************************************/

public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "Famisanar";
    private static final String module = "SplashActivity";
    private int DURACION_SPLASH = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, module + ": Entra x onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){

                Log.i(TAG, module + ": INGRESA AL HANDLER");
                //Pasamos a la siguiente Activity que es listar las categorias
                Intent intent = new Intent(SplashActivity.this, ListadoCategorias.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);

    }


}
