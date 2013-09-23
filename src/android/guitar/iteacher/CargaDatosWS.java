package android.guitar.iteacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.kobjects.base64.Base64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

/**
 * Created by german on 23/08/13.
 */
public class CargaDatosWS {


    public CargaDatosWS(){}
    //Se debe modularizar
    public String ingresar(String user,String password){
        String res=null;
        //Se crea un objeto de tipo SoapObjecto. Permite hacer el llamado al WS
      //  SoapObject rpc;
      //  rpc = new SoapObject("http://192.168.1.4:8080/Teacher/services", "teac:ValidateLogin");
        //De acuerdo a la documentacion del ws, hay 2 parametros que debemos pasar nombre de la ciuda y del pais
        //Para obtener informacion del WS , se puede consultar http://www.webservicex.net/globalweather.asmx?WSDL
       // rpc.addProperty("Nickname", user);
        //rpc.addProperty("Password", password);
       
        XmlPullParserFactory factory=null;
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        factory.setNamespaceAware(true);
        XmlPullParser xpp=null;
		try {
			xpp = factory.newPullParser();
		} catch (XmlPullParserException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        try {
			System.out.println("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
					"xmlns:teac=\"http://teacher/\">"+
  " <soapenv:Header/>"+
	" <soapenv:Body>" +
     " <teac:ValidateLogin>" +
       "  <!--Optional:-->" +
      "   <Nickname>"+user+"</Nickname>" + 
     "    <!--Optional:-->" +
    "     <Password>"+password+"</Password>" +
   "   </teac:ValidateLogin>" +
   "</soapenv:Body>"+
"</soapenv:Envelope>");
        	xpp.setInput( new StringReader ( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
					"xmlns:teac=\"http://teacher/\">"+
  " <soapenv:Header/>"+
	" <soapenv:Body>" +
     " <teac:ValidateLogin>" +
       "  <!--Optional:-->" +
      "   <Nickname>"+user+"</Nickname>" + 
     "    <!--Optional:-->" +
    "     <Password>"+password+"</Password>" +
   "   </teac:ValidateLogin>" +
   "</soapenv:Body>"+
"</soapenv:Envelope>" ) );
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.xsi = SoapSerializationEnvelope.XSI;
        envelope.xsd = SoapSerializationEnvelope.XSD;
        envelope.env = SoapSerializationEnvelope.ENV;
       
        
        try {
		envelope.parse(xpp);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (XmlPullParserException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
        envelope.bodyOut = envelope.bodyIn;
        //Se establece que el servicio web esta hacho en .net
        envelope.dotNet = true;
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;
       
        //Para acceder al WS se crea un objeto de tipo HttpTransportSE , esto es propio de la libreia KSoap
        HttpTransportSE androidHttpTransport= null;
        try {
            String conexion = "http://192.168.1.4:8080/Teacher/services/login";
            androidHttpTransport = new HttpTransportSE(conexion);
            androidHttpTransport.debug = true;
          //  androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            //Llamado al servicio web . Son el nombre del SoapAction, que se encuentra en la documentacion del servicio web y el objeto envelope
      
            androidHttpTransport.call("http://192.168.1.4:8080/Teacher/services/login", envelope);
            //Respuesta del Servicio web 
            Log.d("dump Request: " ,androidHttpTransport.requestDump);
            System.out.println(androidHttpTransport.requestDump+"hola");
            res = envelope.getResponse().toString()+androidHttpTransport.requestDump;
        }catch (Exception e){
         //   System.out.println(e.getMessage());
            res=e.getMessage() + androidHttpTransport.requestDump;
        }

        return res;

    }

}