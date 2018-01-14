package com.kotlandry.grandma.rss.objects;

import java.net.URL;
import java.util.Date;

/**
 * Created by Sergey on 1/12/2018.
 */

public interface IRssChannel {

    /**
     *
     * @return name of the News Channel
     */
     String getName();

    /**
     *
     * @return link to the RSS feed
     */
     String getLink();

    /**
     *
     * @return date of publishing
     */
     Date getPubDate();

}
