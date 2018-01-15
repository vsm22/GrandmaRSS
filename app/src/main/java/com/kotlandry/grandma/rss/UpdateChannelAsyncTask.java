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
    }


}
