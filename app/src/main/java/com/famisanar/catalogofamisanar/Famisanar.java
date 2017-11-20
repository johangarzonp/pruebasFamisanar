package com.famisanar.catalogofamisanar;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Invitado on 18/11/2017.
 */

/****************************************************************************************************************************
 Clase que extiende de Application y se usa para almacenar los Preferences
 *****************************************************************************************************************************/

public class Famisanar extends Application {

    private static final String TAG = "Famisanar";
    private static final String module = "FamisanarSave";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private String hayConexion="0";
    private String infoJsonApps="0";

    private ArrayList<Bitmap> InfoIconImage;
    private ArrayList <String> InfoHeaderTitle;
    private ArrayList <String> InfoPublicDescr;

    private String tituloApp="";
    private String urlIconoApp="";
    private String descripcionApp="";

    public Famisanar() {
        super();
    }

    //********************************************************************
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Context context = getBaseContext();
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
            editor = prefs.edit();
        } catch (Exception e) {
            Log.e(TAG, module + e.getMessage());
            e.printStackTrace();
        }
    }

//***********************************************************************************************

    public String getHayConexion() {
        hayConexion = prefs.getString("hayConexion", "NO");
        return hayConexion;
    }

    public void setHayConexion(String _hayConexion) {
        editor.putString("hayConexion", _hayConexion);
        editor.commit();
        hayConexion = _hayConexion;
    }

    public String getInfoJsonApps() {
        infoJsonApps = prefs.getString("infoJsonApps", "NOHAYJSON");
        return infoJsonApps;
    }

    public void setInfoJsonApps(String _infoJsonApps) {
        editor.putString("infoJsonApps", _infoJsonApps);
        editor.commit();
        infoJsonApps = _infoJsonApps;
    }

//***********************************************************************************************

    public ArrayList<Bitmap> getInfoIconImage() {
        return InfoIconImage;
    }

    public void setInfoIconImage(ArrayList<Bitmap> infoIconImage) {
        InfoIconImage = infoIconImage;
    }

    public ArrayList<String> getInfoHeaderTitle() {
        return InfoHeaderTitle;
    }

    public void setInfoHeaderTitle(ArrayList<String> infoHeaderTitle) {
        InfoHeaderTitle = infoHeaderTitle;
    }

    public ArrayList<String> getInfoPublicDescr() {
        return InfoPublicDescr;
    }

    public void setInfoPublicDescr(ArrayList<String> infoPublicDescr) {
        InfoPublicDescr = infoPublicDescr;
    }
//***********************************************************************************************
    public String getTituloApp() {
        return tituloApp;
    }

    public void setTituloApp(String tituloApp) {
        this.tituloApp = tituloApp;
    }

    public String getUrlIconoApp() {
        return urlIconoApp;
    }

    public void setUrlIconoApp(String urlIconoApp) {
        this.urlIconoApp = urlIconoApp;
    }

    public String getDescripcionApp() {
        return descripcionApp;
    }

    public void setDescripcionApp(String descripcionApp) {
        this.descripcionApp = descripcionApp;
    }
}
