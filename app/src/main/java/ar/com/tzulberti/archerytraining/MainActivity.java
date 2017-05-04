package ar.com.tzulberti.archerytraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.tzulberti.archerytraining.dao.SerieDataDAO;
import ar.com.tzulberti.archerytraining.fragments.BaseClickableFragment;
import ar.com.tzulberti.archerytraining.fragments.practice.PracticeTestingFragment;
import ar.com.tzulberti.archerytraining.fragments.retentions.ConfigureRetention;
import ar.com.tzulberti.archerytraining.helper.DatabaseHelper;
import ar.com.tzulberti.archerytraining.fragments.series.AddSerieFragment;
import ar.com.tzulberti.archerytraining.fragments.series.BaseFragment;
import ar.com.tzulberti.archerytraining.fragments.series.TotayTotalsFragment;
import ar.com.tzulberti.archerytraining.fragments.series.ViewRawDataFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper databaseHelper;
    private SerieDataDAO serieDataDAO;
    private BaseClickableFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        this.databaseHelper = new DatabaseHelper(this);
        this.serieDataDAO = new SerieDataDAO(this.databaseHelper);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today_add_serie) {
            this.currentFragment = new AddSerieFragment();
        } else if (id == R.id.nav_today_total_data) {
            this.currentFragment = new TotayTotalsFragment();
        } else if (id == R.id.nav_today_raw_data) {
            this.currentFragment = new ViewRawDataFragment();
        } else if (id == R.id.nav_retentions) {
            this.currentFragment = new ConfigureRetention();
        } else if (id == R.id.nav_practice) {
            this.currentFragment = new PracticeTestingFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, this.currentFragment)
                .commit();
        return true;
    }


    public SerieDataDAO getSerieDAO() {
        return this.serieDataDAO;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment == null) {
            this.currentFragment = null;
        } else {
            this.currentFragment = (BaseClickableFragment) fragment;
        }
    }

    public void onClick(View v) {
        this.currentFragment.handleClick(v);
    }

}
