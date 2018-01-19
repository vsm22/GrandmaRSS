package com.kotlandry.grandma.rss;

import com.kotlandry.grandma.rss.objects.IRssChannel;

import java.util.List;

/**
 * Created by Sergey on 1/17/2018.
 */

public interface IEditNewsChannels {

    void onAddNewsChannel(IRssChannel chanel);
    void onDeleteNewsChannel(List<IRssChannel> newListOfCahnnels);

}
