package com.kotlandry.grandma.rss;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DownloadCallback<UpdateChannelResult>{

    IRssChannel       currentChannel = null;
    List<IRssChannel> listOfChannels = null;
    List<IRssItem>    listOfItems = null;

    private DrawerLayout drawer;
    private ListView mDrawerList;

    private void testInitializer(){

        currentChannel = new IRssChannel() {
            @Override public String getName() { return "РБК - Все материалы"; }
            @Override public String getLink() {
                return "http://static.feed.rbc.ru/rbc/logical/footer/news.rss";
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


    /** Update Navigation Drawer with a new list of Rss Items
     *
     * @param listOfItems
     */
    private void updateNavigationDrawer(List<IRssItem> listOfItems){

        if(listOfItems != null){
            mDrawerList.setAdapter(new ArrayAdapter<IRssItem>(this, R.layout.drawer_list_item, listOfItems ));
            //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }

    }

    /** Start asynchronous process of updating items of selected news channel.
     * @param newsChannel
     */
    private void updateNewsChannel(IRssChannel newsChannel){
        UpdateChannelAsyncTask task = new UpdateChannelAsyncTask(this);
        task.execute(newsChannel);
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



    // ========== Implement DownloadCallback<UpdateChannelResult> =========

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     *
     * @param result
     */
    @Override
    public void updateFromDownload(UpdateChannelResult result) {
        Exception e = result.getException();
        if(e == null){
            updateNavigationDrawer(result.getResult());
        }else{
            Log.e("Update News Channel", e.getMessage(), e);
        }
    }

    /**
     * Get the device's active network status in the form of a NetworkInfo object.
     */
    @Override
    public NetworkInfo getActiveNetworkInfo() {
        return null;
    }

    /**
     * Indicate to callback handler any progress update.
     *
     * @param progressCode    must be one of the constants defined in DownloadCallback.Progress.
     * @param percentComplete must be 0-100.
     */
    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {

    }

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    @Override
    public void finishDownloading() {

    }
}
