package com.kotlandry.grandma.rss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    IRssChannel       currentChannel = null;
    List<IRssChannel> listOfChannels = null;
    List<IRssItem>    listOfItems = null;

    private DrawerLayout drawer;
    private ListView mDrawerList;

    private void testInitializer(){

        currentChannel = new IRssChannel() {
            @Override public String getName() { return "РБК - Все материалы"; }
            @Override public URL getLink() {
                try { return new URL("http://static.feed.rbc.ru/rbc/logical/footer/news.rss"); } catch (MalformedURLException e) {  e.printStackTrace();  }
                return null;
            }

            @Override
            public Date getPubDate() { return new Date(); }

        };

        listOfChannels = new ArrayList<IRssChannel>();
        listOfChannels.add(currentChannel);
        listOfChannels.add(currentChannel);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        testInitializer();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mDrawerList   = (ListView)findViewById(R.id.nav_view);

        updateNavigationDrawer(listOfItems);

    }


    /** update Navigation Drawer with a new list of Rss Items
     *
     * @param listOfItems
     */
    private void updateNavigationDrawer(List<IRssItem> listOfItems){

        if(listOfItems != null){
            mDrawerList.setAdapter(new ArrayAdapter<IRssItem>(this, R.layout.drawer_list_item, listOfItems ));
            //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }

    }

    private void updateNewsChannel(IRssChannel newsChannel){

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
