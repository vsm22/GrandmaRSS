package com.kotlandry.grandma.rss;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.kotlandry.grandma.rss.objects.IRssChannel;
import com.kotlandry.grandma.rss.objects.RssChannel;

import java.io.Serializable;

/**
 * Created by Sergey on 1/17/2018.
 */

public class AddChannelDialogFragment extends DialogFragment {

    private IEditNewsChannels mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder .setTitle(R.string.title_add_channel)
                .setView(inflater.inflate(R.layout.add_channel_dlg, null))
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog dlg = (AlertDialog)dialog;
                        TextView nameView = dlg.findViewById(R.id.addChannelName);
                        TextView urlView  = dlg.findViewById(R.id.addChannelURL);
                        RssChannel channel = new RssChannel(
                                nameView.getText().toString(),
                                urlView.getText().toString());
                        mListener.onAddNewsChannel(channel);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddChannelDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (IEditNewsChannels)activity;
    }

}
