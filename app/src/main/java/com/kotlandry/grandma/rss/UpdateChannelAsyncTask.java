package com.kotlandry.grandma.rss;

import android.os.AsyncTask;
import android.util.Log;

import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.IRssItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param updateChannelResult The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(UpdateChannelResult updateChannelResult) {
        super.onPostExecute(updateChannelResult);
        downloadCallback.updateFromDownload(updateChannelResult);
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
        if(iRssChannels != null && iRssChannels.length > 0){
            return dowloadRssFeed(iRssChannels[0]);
        }else{
            return new UpdateChannelResult(new Exception("List of Channels is empty"));
        }
    }

    private UpdateChannelResult dowloadRssFeed(IRssChannel channel){

        String link = channel.getLink();

        if(link != null && !link.isEmpty()){
            try {

                URL url =  new URL(link);
                InputStream stream = url.openStream();
                List<IRssItem> items = parseStream(stream);
                return new UpdateChannelResult(items);

            } catch ( Exception e) {
                Log.e("dowloadRssFeed",link + ": " + e.getMessage(), e);
                return new UpdateChannelResult(e);
            }
        }
        return new UpdateChannelResult(new Exception("Channel URL is null or Empty"));

    }

    private List<IRssItem> parseStream(InputStream stream) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document feedDoc = builder.parse(stream);

         List<IRssItem> listItem = new ArrayList<>();
         NodeList itemNodeList = feedDoc.getElementsByTagName("item");
         for(int i=0; i<itemNodeList.getLength(); i++){

             try {

                 Element item = (Element) itemNodeList.item(i);

                 String title = item.getElementsByTagName("title").item(0).getTextContent();
                 String link = item.getElementsByTagName("link").item(0).getTextContent();
                 String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();

                 RssItem rssItem = new RssItem(title, link, pubDate);
                 listItem.add(rssItem);

             }catch (Exception e){
                 Log.e("parseStream","item is malformed", e);
             }

         }

         return listItem;
    }


    public static class RssItem implements IRssItem{

        private String title;
        private String link;
        private String pubDate;

        @Override  public String getTitle() { return title; }
        @Override  public String getLink() { return link; }
        @Override  public String getPubDate() { return pubDate; }

        public RssItem(String title, String link, String pubDate) {

            this.title = title;
            this.link = link;
            this.pubDate = pubDate;

        }

        /**
         * Returns a string representation of the object. In general, the
         * {@code toString} method returns a string that
         * "textually represents" this object. The result should
         * be a concise but informative representation that is easy for a
         * person to read.
         * It is recommended that all subclasses override this method.
         * <p>
         * The {@code toString} method for class {@code Object}
         * returns a string consisting of the name of the class of which the
         * object is an instance, the at-sign character `{@code @}', and
         * the unsigned hexadecimal representation of the hash code of the
         * object. In other words, this method returns a string equal to the
         * value of:
         * <blockquote>
         * <pre>
         * getClass().getName() + '@' + Integer.toHexString(hashCode())
         * </pre></blockquote>
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            return title;
        }
    }


}
