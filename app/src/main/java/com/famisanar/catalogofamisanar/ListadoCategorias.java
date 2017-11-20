package com.famisanar.catalogofamisanar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Invitado on 18/11/2017.
 */
/****************************************************************************************************************************
En esta Activity se deja un boton de acceso para ir a la URL donde estan los icono e informacin Solicitada
   Tambien se indica el estado de la conexion si esta o no en linea
 *****************************************************************************************************************************/
public class ListadoCategorias extends Activity {

    private static final String TAG = "Famisanar";
    private static final String module = "ListadoCategorias";
    private boolean banderaMostrarApps = false;
    private TextView estadoConexion;
    private Context context;
    private Famisanar infoActualPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, module + ": Entra x onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_categorias);
        estadoConexion = (TextView)findViewById(R.id.estadoconexion);
        context= getApplicationContext();
        infoActualPref = ((Famisanar) context);

        //Por default el estado de la conexión es en línea....

        if (!verificaConexion(ListadoCategorias.this)) {    //Se verifica la Conexion de Datos del Usuario...
            estadoConexion.setText("Fuera de Línea");
            estadoConexion.setTextColor(Color.RED);  //Es el mismo #FF0000 en Hexa
        }

    }

    //*********************************************************************************************************************

    public static boolean verificaConexion(Context ctx) {

        Log.i(TAG, module + "Comprueba Conexion a Internet");
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < 2; i++) {

            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }

        Log.i(TAG, module+ "La Conexion es: " + bConectado);
        return bConectado;
    }

    //Aca llega porque se presiono el Botón Listado de Apps
    public void MostrarApps(View view) {
        if(!banderaMostrarApps){
            banderaMostrarApps=true;    //No hay que volver a entrar por presión del boton pronta
            Log.i(TAG, module+ "Presionaron el Botón");
            if (!verificaConexion(ListadoCategorias.this) && infoActualPref.getInfoJsonApps().equals("NOHAYJSON") ) {    //Se verifica la Conexion de Datos del Usuario...
                Toast.makeText(getApplicationContext(), "SR USUARIO PORFAVOR VERIFIQUE SU CONEXION A INTERNET", Toast.LENGTH_LONG).show();
                banderaMostrarApps=false;
            }
            else {
                //Pasamos a la siguiente Activity que es listar las apps
                Intent login = new Intent();
                login.setClass(getApplicationContext(), ListadoApps.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                finish();
            }

        }

    }
}
