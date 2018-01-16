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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements  AdapterView.OnItemClickListener,
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
        listOfChannels.add(new IRssChannel() {
                               @Override public String getName() { return "Интерфакс"; }
                               @Override public String getLink() {
                                   return "http://www.interfax.ru/rss.asp";
                               }
                             @Override
                               public Date getPubDate() { return new Date(); }
                          }

        );

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
        updateNewsChannel(currentChannel);

    }


    /** Update Navigation Drawer with a new list of Rss Items
     *
     * @param listOfItems
     */
    private void updateNavigationDrawer(List<IRssItem> listOfItems){

        if(listOfItems != null){
            mDrawerList.setAdapter(new ArrayAdapter<IRssItem>(this, R.layout.drawer_list_item, listOfItems ));
            drawer.openDrawer(GravityCompat.START);
            mDrawerList.setOnItemClickListener(this);
        }

    }

    /** Start asynchronous process of updating items of selected news channel.
     * @param newsChannel
     */
    private void updateNewsChannel(IRssChannel newsChannel){
        if(newsChannel == null) return;
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

    /**
     * Callback method. Inflating ActionBar from the main menu in resource
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     * <p>
     * <p>The default implementation updates the system menu items based on the
     * activity's state.  Deriving classes should always call through to the
     * base class implementation.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean result = super.onPrepareOptionsMenu(menu);
        if( result && listOfChannels != null && !listOfChannels.isEmpty()){
          MenuItem newsItem = menu.findItem(R.id.action_news_channel);
          if(newsItem.hasSubMenu()){
              SubMenu submenu = newsItem.getSubMenu();
              for(int i=0; i < listOfChannels.size(); i++){
                  submenu.add( Menu.NONE , i, i, listOfChannels.get(i).getName());
              }
          }
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Settings has unique Id
        if (id == R.id.action_settings) {
            return true;
        }
        // Other is the position of news channel in the list:
        if( id >=0 && id < listOfChannels.size() ){
            currentChannel = listOfChannels.get(id);
            getSupportActionBar().setTitle(currentChannel.getName());
            updateNewsChannel(currentChannel);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(listOfItems != null){

            IRssItem item = listOfItems.get(position);
            if(item != null) {
                WebView articleView =  findViewById(R.id.article_view);
                articleView.loadUrl(item.getLink());
                drawer.closeDrawer(GravityCompat.START);
            }

        }

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
            listOfItems = result.getResult();
            updateNavigationDrawer(listOfItems);
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
