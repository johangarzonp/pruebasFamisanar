package com.famisanar.catalogofamisanar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Invitado on 19/11/2017.
 */

/****************************************************************************************************************************
En esta Activity se muestra el resumen de la App que se escogio en la Lista de Apps
 ****************************************************************************************************************************/

public class ResumendelApp extends Activity {

    private static final String TAG = "Famisanar";
    private static final String module = "ResumendelApp";

    private TextView tituloApp;
    private TextView descripcionApp;
    private ImageView iconoApp;
    private Context context;
    private Famisanar infoActualPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, module + ": Entra x onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen_app);

        tituloApp = (TextView) findViewById(R.id.infotitulo);
        descripcionApp = (TextView) findViewById(R.id.infodescripcion);
        iconoApp = (ImageView) findViewById(R.id.imageicono);

        context= getApplicationContext();
        infoActualPref = ((Famisanar) context);

        //Se trae la informacion de los Preference para mostarla en el resumen

        tituloApp.setText(infoActualPref.getTituloApp());
        descripcionApp.setText(infoActualPref.getDescripcionApp());

        if(!infoActualPref.getUrlIconoApp().equals("")) {
            new ImageLoadTask(infoActualPref.getUrlIconoApp(), iconoApp).execute();
        }else{
            iconoApp.setImageResource(R.drawable.sinicono);
        }
    }
}
