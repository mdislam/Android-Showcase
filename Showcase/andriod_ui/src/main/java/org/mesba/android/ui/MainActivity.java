package org.mesba.android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.mesba.android.ui.master_detail.ProfileListActivity;
import org.mesba.android.ui.nav_view.NavigationActivity;
import org.mesba.android.ui.scrolling_view.ScrollingActivity;

public class MainActivity extends AppCompatActivity {

    private Button navViewButton;
    private Button scrollViewButton;
    private Button masterDetailViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // opening the navigation view
        navViewButton = (Button) findViewById(R.id.nav_view_button);
        navViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(navIntent);
            }
        });

        // opening the navigation view
        scrollViewButton = (Button) findViewById(R.id.scrolling_view_button);
        scrollViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scrollViewIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(scrollViewIntent);
            }
        });

        // opening the navigation view
        masterDetailViewButton = (Button) findViewById(R.id.master_detail_button);
        masterDetailViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masterDetailIntent = new Intent(getApplicationContext(), ProfileListActivity.class);
                startActivity(masterDetailIntent);
            }
        });
    }
}
