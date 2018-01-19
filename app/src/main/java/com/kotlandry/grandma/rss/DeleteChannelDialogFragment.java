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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 1/18/2018.
 */

public class DeleteChannelDialogFragment extends DialogFragment {

    IEditNewsChannels mListener;
    List<IRssChannel> listOfChannels;
    String[] arrayChannelName;
    boolean[] checkedChannels;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        listOfChannels = ((MainActivity)getActivity()).getListOfChannels();
        arrayChannelName = new String[listOfChannels.size()];
        checkedChannels   = new boolean[listOfChannels.size()];
        for(int i=0; i<listOfChannels.size(); i++){
            arrayChannelName[i] = listOfChannels.get(i).getName();
            checkedChannels[i]   = false;
        }

        builder .setTitle(R.string.title_delete_channel)
                .setMultiChoiceItems(arrayChannelName, null, new DialogInterface.OnMultiChoiceClickListener() {
                    /**
                     * This method will be invoked when an item in the dialog is clicked.
                     *
                     * @param dialog    the dialog where the selection was made
                     * @param which     the position of the item in the list that was clicked
                     * @param isChecked {@code true} if the click checked the item, else
                     *                  {@code false}
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                         checkedChannels[which] = isChecked;
                     }
                })
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        List<IRssChannel> newListOfChannels = new ArrayList<>();
                        for(int i=0; i<checkedChannels.length; i++){
                            if( !checkedChannels[i] ) newListOfChannels.add(listOfChannels.get(i));
                        }
                        mListener.onDeleteNewsChannel(newListOfChannels);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteChannelDialogFragment.this.getDialog().cancel();
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
