package com.example.lwilcox.lab2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class StreamFragment extends Fragment {

    public StreamFragment() {
    }

    public Button viewSearchButton;
    public Button nextButton;
    public Button previousButton;
    private View myFragmentView;
    public ImageView imageView;
    public ArrayList<String> imageLinks;
    public Integer photoIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_stream, container, false);
        final Context context = getActivity().getApplicationContext();
        imageView = (ImageView) myFragmentView.findViewById(R.id.imageView);
        viewSearchButton = (Button) myFragmentView.findViewById(R.id.viewSearch);
        nextButton = (Button) myFragmentView.findViewById(R.id.next);
        previousButton = (Button) myFragmentView.findViewById(R.id.previous);
        final FeedReaderDbHelper FeedReaderDBH = new FeedReaderDbHelper(context);
        imageLinks = FeedReaderDBH.readList();

        setImage();

        viewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragments();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoIndex < (imageLinks.size() - 1)) {
                    photoIndex ++;
                }
                else {
                    photoIndex = 0;
                }
                setImage();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoIndex > 0) {
                    photoIndex--;
                } else {
                    photoIndex = (imageLinks.size() - 1);
                }
                setImage();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(context.getResources().getString(R.string.delete));
                alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FeedReaderDBH.removeItem(imageLinks.get(photoIndex));
                        imageLinks = FeedReaderDBH.readList();
                        setImage();
                    }
                });
                alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.nope),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return myFragmentView;
    }
    public void setImage(){
        if (imageLinks.size() > 0) { //prevents the app from crashing if the stream page is hit without any saved images
            if (photoIndex < imageLinks.size()) { //if photoIndex is still a valid number okay
                String thumbnailLink = imageLinks.get(photoIndex);
                new ImageLoadTask(thumbnailLink, imageView).execute();
            }
            else{ //otherwise bring the index down one to account for the deleted entry
                String thumbnailLink = imageLinks.get(photoIndex - 1);
                new ImageLoadTask(thumbnailLink, imageView).execute();
            }
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage(getContext().getResources().getString(R.string.null_to_search));
            alertDialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.okay),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //this is blank because if I put switchFragments here people can finagle
                            //their way out of clicking the okay button and if they don't leave the
                            //page it will crash
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            switchFragments(); //so no matter what they get kicked off the stream page
        }
    }
    public void switchFragments(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        ft.replace(R.id.container, searchFragment);
        ft.commit();
    }
}


