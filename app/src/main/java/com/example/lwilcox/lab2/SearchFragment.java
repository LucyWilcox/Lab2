package com.example.lwilcox.lab2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    public Button searchButton;
    public  Button nextButton;
    public Button previousButton;
    public Button saveButton;
    public Button viewStreamButton;
    private View myFragmentView;
    public EditText editText;
    public ImageView imageView;
    public Integer photoIndex;
    public ArrayList<String> imageLinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        searchButton = (Button) myFragmentView.findViewById(R.id.search);
        nextButton = (Button) myFragmentView.findViewById(R.id.next);
        previousButton = (Button) myFragmentView.findViewById(R.id.previous);
        saveButton = (Button) myFragmentView.findViewById(R.id.save);
        viewStreamButton = (Button) myFragmentView.findViewById(R.id.viewStream);
        editText = (EditText) myFragmentView.findViewById(R.id.editText);
        imageView = (ImageView) myFragmentView.findViewById(R.id.imageView);
        final FeedReaderDbHelper FeedReaderDBH = new FeedReaderDbHelper(getActivity().getApplicationContext());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                HttpHandler handler = new HttpHandler(getActivity().getApplicationContext());
                photoIndex = 0;
                handler.imageSearch(searchText, new Callback() {
                    @Override
                    public void callback(ArrayList<String> displayList) {
                        imageLinks = displayList;
                        setImage();
                    }
                });
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (photoIndex < (imageLinks.size() - 1)) {
                        photoIndex += 1;
                    }
                    else {
                        photoIndex = 0;
                    }
                    setImage();
                } catch (Exception e) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "@string/search_first";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (photoIndex > 0) {
                        photoIndex -= 1;
                    }
                    else {
                        photoIndex = (imageLinks.size() - 1);
                    }
                    setImage();
                } catch (Exception e) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "@string/search_first";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FeedReaderDBH.addItem(imageLinks.get(photoIndex));
                } catch (Exception e) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "@string/search_first";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        viewStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                StreamFragment streamFragment = new StreamFragment();
                ft.replace(R.id.container, streamFragment);
                ft.commit();
            }
        });
        return myFragmentView;
    }

    public void setImage(){
        String thumbnailLink = imageLinks.get(photoIndex);
        new ImageLoadTask(thumbnailLink, imageView).execute();
    }
}

