package org.mesba.showcase;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.mesba.showcase.fragments.BlankFragment;

public class NavigationDrawerActivity extends AppCompatActivity {

    private String NAME = "Jessica Alba";
    private String EMAIL = "alba.jessica@gmail.com";

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    private DrawerLayout drawer;
    private NavigationView mNavigationView;               // Declaring DrawerLayout

    private ActionBarDrawerToggle mDrawerToggle;

    private TextView tvName;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        tvName = (TextView) findViewById(R.id.name);
        tvEmail = (TextView) findViewById(R.id.email);

        tvName.setText(NAME);
        tvEmail.setText(EMAIL);

        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawer.closeDrawers();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        // TODO - Do something
                        break;
                    // TODO - Handle other items
                }
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // drawer Toggle Object Made
        drawer.setDrawerListener(mDrawerToggle); // drawer Listener set to the drawer toggle

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        mDrawerToggle.syncState();

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
    }
}
