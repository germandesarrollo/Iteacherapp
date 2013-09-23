package android.guitar.iteacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;



/**
 * Created by german on 22/08/13.
 */
public class loginActivity extends Activity {
    private EditText user;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.clave);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void login(View v){

        Intent i = new Intent(this, MainActivity.class);
        CargaDatosWS carga =new CargaDatosWS();
        String respuesta = carga.ingresar(user.getText().toString(),password.getText().toString());

        i.putExtra("user", user.getText().toString());
        i.putExtra("password", respuesta);
        startActivity(i);

        System.out.println("Holaaaa me llamaorns");
    }
}
