package com.kotlandry.grandma.rss;

import android.os.AsyncTask;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import java.util.List;

/**
 * Created by Sergey on 1/13/2018.
 */

public class UpdateChannelAsyncTask extends AsyncTask<IRssChannel, Integer, List<IRssItem>> {

    DownloadCallback<List<IRssItem>> downloadCallback;

    public UpdateChannelAsyncTask(){ super(); }

    public UpdateChannelAsyncTask(DownloadCallback<List<IRssItem>> downloadCallback) {
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
    protected List<IRssItem> doInBackground(IRssChannel... iRssChannels) {
        return null;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param iRssItems The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(List<IRssItem> iRssItems) {
        downloadCallback.updateFromDownload(iRssItems);
    }

}
