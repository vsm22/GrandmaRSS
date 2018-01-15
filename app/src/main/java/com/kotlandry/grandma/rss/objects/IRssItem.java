package com.kotlandry.grandma.rss.objects;

/**
 * Created by Sergey on 1/12/2018.
 */

public interface IRssItem {

    /**
     *
     * @return article title
     */
     String getTitle();

    /**
     *
     * @return URL link to an article
     */
     String getLink();

    /**
     *
     * @return date when article was creqated
     */
     String getPubDate();

}
