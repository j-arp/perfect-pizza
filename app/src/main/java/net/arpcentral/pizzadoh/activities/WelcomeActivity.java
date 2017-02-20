package net.arpcentral.pizzadoh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders;


import net.arpcentral.pizzadoh.PizzaDohApp;
import net.arpcentral.pizzadoh.R;

public class WelcomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        PizzaDohApp application = (PizzaDohApp) getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("GA", "Analyticing Welcome screen");
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("View")
                .setAction("View Welcome Screen")
                .build());

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Perfect Pizza Forever");
        }

        FloatingActionButton start_button = (FloatingActionButton) findViewById(R.id.start);
        if (start_button != null){
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(view.getContext(), SelectOptionsActivity.class));
                }
            });
        }

        String[]nav_items = getResources().getStringArray(R.array.nav_items);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ArrayAdapter nav_adapter;
        nav_adapter = new ArrayAdapter(this, R.layout.drawer_list_item, R.id.drawer_list_item_text, nav_items);
        // Set the adapter for the list view
        if (mDrawerList != null) {
            mDrawerList.setAdapter(nav_adapter);
        }

        // Set the list's click listener
        CharSequence mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerList != null) {
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //mDrawerList.bringToFront();
                //mDrawerLayout.requestLayout();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        if (mDrawerLayout != null) {
            mDrawerLayout.addDrawerListener(mDrawerToggle);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.d("WELCOME ACT", "Nav bar clicked " + id + " : pos " + position);

            // if the menu item clicked is "About", fire off that activity
            if (id == 0) {
                Intent intent = new Intent(WelcomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }

            if (id == 1) {
                Intent intent = new Intent(WelcomeActivity.this, HistoryActivity.class);
                startActivity(intent);
            }

            if (id == 2) {
                Intent intent = new Intent(WelcomeActivity.this, ResourcesActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is pres ent.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("WELCOME ACT", "Clicked Item menu " + item);
        int id = item.getItemId();

//        // if the menu item clicked is "About", fire off that activity
//        if (id == R.id.action_about) {
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//
//        // if the menu item clicked is "About", fire off that activity
//        if (id == R.id.action_history) {
//            Intent intent = new Intent(this, HistoryActivity.class);
//            startActivity(intent);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
