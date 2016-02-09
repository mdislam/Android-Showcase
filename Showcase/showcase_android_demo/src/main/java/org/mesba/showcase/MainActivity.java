package org.mesba.showcase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mesba.customfont.CustomFontHelper;
import org.mesba.showcase.interfaces.IViewInitializer;

public class MainActivity extends AppCompatActivity implements IViewInitializer, View.OnClickListener {

    private Button btnNavDrawer;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
        addUIListeners();
        prepareScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initializeUIComponents() {
        btnNavDrawer = (Button) findViewById(R.id.btn_nav_drawer);
        tvTitle = (TextView) findViewById(R.id.textView);

        // using the custom font library
        btnNavDrawer.setTypeface(CustomFontHelper.applyAvenirRoman(getApplicationContext()));
        tvTitle.setTypeface(CustomFontHelper.applyAvenirBlack(getApplicationContext()));
    }

    @Override
    public void addUIListeners() {
        btnNavDrawer.setOnClickListener(this);
    }

    @Override
    public void prepareScreen() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nav_drawer:
                Intent navIntent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                startActivity(navIntent);
                break;
        }
    }
}
