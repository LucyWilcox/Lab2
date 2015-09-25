package com.example.lwilcox.lab2;

import android.content.Context;
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
        imageView = (ImageView) myFragmentView.findViewById(R.id.imageView);
        viewSearchButton = (Button) myFragmentView.findViewById(R.id.viewSearch);
        nextButton = (Button) myFragmentView.findViewById(R.id.next);
        previousButton = (Button) myFragmentView.findViewById(R.id.previous);
        final FeedReaderDbHelper FeedReaderDBH = new FeedReaderDbHelper(getActivity().getApplicationContext());

        imageLinks = FeedReaderDBH.readList();

//        if (imageLinks.size() == 0) { TO DO
//
//        }

        setImage();

        viewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SearchFragment searchFragment = new SearchFragment();
                ft.replace(R.id.container, searchFragment);
                ft.commit();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoIndex < (imageLinks.size() - 1)) {
                    photoIndex += 1;
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
                    photoIndex -= 1;
                }
                else {
                    photoIndex = (imageLinks.size() - 1);
                }
                setImage();
            }
        });
        return myFragmentView;
    }

    public void setImage(){
        String thumbnailLink = imageLinks.get(photoIndex);
        new ImageLoadTask(thumbnailLink, imageView).execute();
    }
}


