package com.famisanar.catalogofamisanar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Invitado on 18/11/2017.
 */

/****************************************************************************************************************************
 En esta Activity se Listan las Apps que se encuentran en url indicada
 ****************************************************************************************************************************/
public class ListadoApps extends Activity {
    private static final String TAG = "Famisanar";
    private static final String module = "ListadoApps";

    private LinearLayout listaApps;
    private boolean finalizo=false;
    private ArrayList <String> arrayInfoIconImage;
    private ArrayList <String> arrayInfoHeaderTitle;
    private ArrayList <String> arrayInfoPublicDescr;

    private ProgressDialog esperaCargarInfo;

    private Context context;
    private Famisanar infoActualPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, module + ": Entra x onCreate");
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.listado_apps);

        listaApps = (LinearLayout) findViewById(R.id.listapps);
        context= getApplicationContext();
        infoActualPref = ((Famisanar) context);

        esperaCargarInfo = new ProgressDialog(ListadoApps.this);

        esperaCargarInfo.setTitle("CARGANDO INFORMACION");
        esperaCargarInfo.setMessage("ESPERE UN MOMENTO...");
        esperaCargarInfo.setCancelable(true);
        esperaCargarInfo.show();

        Thread thrd = new Thread() {
            public void run() {

                String urlJson = ("https://www.reddit.com/reddits.json");
                String jsonObject = "";

                try {
                    jsonObject = getJSON(urlJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i(TAG, module + " El JSON es este : " + jsonObject);
                Log.i(TAG, module + " El TAMAÑO ES : " + jsonObject.length());

                try {
                    JSONObject obj = new JSONObject(jsonObject);
                    JSONObject objData =obj.getJSONObject("data");
                    Log.i(TAG, module + " El JSONObject es : " + objData.toString());

                    ArrayList<String> infoIconImage = new ArrayList<String>();
                    ArrayList<String> infoHeaderTitle = new ArrayList<String>();
                    ArrayList<String> infoPublicDescr = new ArrayList<String>();

                    JSONArray array = objData.getJSONArray("children");

                    Log.i(TAG, module + " El Array tiene : " + array.length() + "elementos");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj2 = array.getJSONObject(i);

                        infoIconImage.add(obj2.getJSONObject("data").getString("icon_img"));
                        infoHeaderTitle.add(obj2.getJSONObject("data").getString("header_title"));
                        infoPublicDescr.add(obj2.getJSONObject("data").getString("public_description"));

                        Log.i(TAG, module + " El Array IconImage en la posicion " + i + "contiene : " + obj2.getJSONObject("data").getString("icon_img"));
                        Log.i(TAG, module + " El Array HeaderTitle en la posicion " + i + "contiene : " + obj2.getJSONObject("data").getString("header_title"));
                        Log.i(TAG, module + " El Array PublicDescr en la posicion " + i + "contiene : " + obj2.getJSONObject("data").getString("public_description"));
                    }

                    //MostrarInfo(infoIconImage,infoHeaderTitle);
                    arrayInfoIconImage = infoIconImage;
                    arrayInfoHeaderTitle = infoHeaderTitle;
                    arrayInfoPublicDescr = infoPublicDescr;
                    finalizo=true;


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            };
        thrd.start();

        new Handler().postDelayed(new Runnable() {
            public void run() {

                try {
                    esperaCargarInfo.dismiss();
                    MostrarInfo(arrayInfoIconImage,arrayInfoHeaderTitle);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }, 1200);



    }

    //****************************************************************************************

    public static String getJSON(String url) {
        HttpsURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.connect();


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    //****************************************************************************************

    public void MostrarInfo(ArrayList<String> arrayIconImage,ArrayList<String> arrayHeaderTitle) {

        Log.i(TAG, module + "ENTRA A CARGAR LAS IMAGENES....");

        int cont = 0;
        while (arrayIconImage.size() > cont) {

            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.HORIZONTAL);
            TextView text = new TextView(this);
            text.setGravity(Gravity.LEFT);
            text.setText(arrayHeaderTitle.get(cont)+"  ");
            text.setMaxWidth(700);
            text.setTextSize(14);
            text.setGravity(Gravity.CENTER);
            text.setPadding(2, 2, 2, 2);
            text.setTextColor(Color.BLACK);

            ImageView imgView = new ImageView(this);
            imgView.setBackgroundDrawable(null);


            if(!arrayIconImage.get(cont).equals("")){
                new ImageLoadTask(arrayIconImage.get(cont), imgView).execute();

                double num = 0.5;
                imgView.setScaleX((float) num);
                imgView.setScaleY((float) num);
            }else{
                imgView.setImageResource(R.drawable.sinicono);
            }

            info.setGravity(Gravity.CENTER_VERTICAL);
            //info.setMinimumHeight(text.getMaxHeight());
            info.addView(text);
            //imgView.setMaxHeight(10);
            //imgView.setMaxWidth(10);
            info.addView(imgView);
            info.setId(900000000 + cont);

            listaApps.addView(info);

            cont++;

            info.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.i("click", String.valueOf(v.getId()));
                    //Se carga la infomación de la App para mostrarla en ResumendelApp
                    infoActualPref.setTituloApp(arrayInfoHeaderTitle.get(v.getId()-900000000));
                    infoActualPref.setUrlIconoApp(arrayInfoIconImage.get(v.getId()-900000000));
                    infoActualPref.setDescripcionApp(arrayInfoPublicDescr.get(v.getId()-900000000));

                    //Pasamos a Resumen del App
                    Intent i = new Intent(ListadoApps.this, ResumendelApp.class);
                    startActivityForResult(i, 1);
                }
            });

        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    //************************************************************
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i(TAG, module + ": Entra x onDestroy");
        System.gc();
        finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i(TAG, module + ": Entra x onPause");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i(TAG, module + ": Entra x onResume");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, module + ": Entra x onStart");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.i(TAG, module + ": Entra x onStop");
    }

}
