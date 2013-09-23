package android.guitar.iteacher;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by german on 22/08/13.
 */
public class ServiceConnection {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    /** onJava es el método encargado de realizar la conexión principal con el json que va a contener la direccion de los XML
     *
     * @throws org.json.JSONException
     */

    public void ServiceConnection() throws JSONException {

        URL url;
        URLConnection connection = null;

        try {
            url = new URL("Direccion");
            connection = url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        int responseCode = 0;
        try {
            responseCode = httpConnection.getResponseCode();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try {
                InputStream in = httpConnection.getInputStream();

                JSONArray JArray = this.getJSONFromUrl("Direccion");
                for (int i = 0; i < JArray.length(); i++) {
                    JSONObject json = JArray.getJSONObject(i);

                    try {

                        this.getXML(json.getString(json.names().getString(0)),
                                json.names().getString(0));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }
    /** getXML es el método encargado de obtener el contenido de un archivoXML
     *
     * @param StringURL
     * @param pantalla
     * @return
     */
    public String getXML(String StringURL, String pantalla) {

        URL url;
        URLConnection connection = null;

        try {
            url = new URL(StringURL);
            connection = url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        int responseCode = 0;
        try {
            responseCode = httpConnection.getResponseCode();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try {
                InputStream in = httpConnection.getInputStream();
                String xmlResult = this.convertStreamToString(in);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return "";
    }
    /** convertStreamToString es el método que se encarga de convertir un Stream en un String
     *
     * @param is
     * @return
     * @throws IOException
     */
    public String convertStreamToString(InputStream is) throws IOException {
        //
        // To convert the InputStream to String we use the
        // Reader.read(char[] buffer) method. We iterate until the
        // Reader return -1 which means there's no more data to
        // read. We use the StringWriter class to produce the string.
        //
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is,
                        "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    /** getJSONFromUrl es el método que se encarga de obtener una estructura JSON de un url
     *
     * @param url
     * @return
     */
    public JSONArray getJSONFromUrl(String url) {
        JSONArray jArray = null;
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {

            jArray = new JSONArray(json);

            /*for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                this.listaVentanas.add(jObj.names().getString(0));

            }*/

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jArray;

    }



}
