package com.kotlandry.grandma.rss;

import android.os.AsyncTask;
import android.util.Log;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Sergey on 1/13/2018.
 */

public class UpdateChannelAsyncTask extends AsyncTask<IRssChannel, Integer, UpdateChannelResult> {

    DownloadCallback<UpdateChannelResult> downloadCallback;

    public UpdateChannelAsyncTask(){ super(); }

    public UpdateChannelAsyncTask(DownloadCallback<UpdateChannelResult> downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param iRssChannels The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected UpdateChannelResult doInBackground(IRssChannel... iRssChannels) {
         return null;
    }

    private UpdateChannelResult dowloadRssFeed(IRssChannel channel){

        String link = channel.getLink();

        if(link != null && !link.isEmpty()){
            try {
                URL url =  new URL(link);
                InputStream stream = url.openStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                while(buffer.ready()){
                   buffer.readLine();
                }
                return new UpdateChannelResult("");

            } catch (java.io.IOException e) {
                Log.e("dowloadRssFeed","MalformedUrl is not correct: " + link);
                return new UpdateChannelResult(e);
            }
        }
        return new UpdateChannelResult(new Exception("Channel URL is null or Empty"));

    }


}
