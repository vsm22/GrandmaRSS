package com.kotlandry.grandma.rss.objects;

/**
 * Created by Sergey on 1/12/2018.
 */

public interface IRssItem {

     String getTitle();
     String getLink();
     String getPubDate();
     @Override
     String toString();

}
