package org.mesba.globalvariable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    private TextView nameTv;
    private TextView emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Calling Application class (see application tag in AndroidManifest.xml)
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        // Get name and email from global/application context
        final String name  = globalVariable.getName();
        final String email = globalVariable.getEmail();

        nameTv = (TextView) findViewById(R.id.nameTv);
        emailTv = (TextView) findViewById(R.id.emailTv);

        nameTv.setText(name);
        emailTv.setText(email);

    }
}
