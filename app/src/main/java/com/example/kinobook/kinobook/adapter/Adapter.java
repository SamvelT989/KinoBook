package com.example.kinobook.kinobook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kinobook.R;
import com.example.kinobook.kinobook.model.Docs;
import com.example.kinobook.kinobook.model.ModelDataCinema;

import java.util.List;

public class Adapter extends BaseAdapter {

    private  List<ModelDataCinema> docs;

    public Adapter(List<ModelDataCinema> modelDataCinema){

        this.docs = modelDataCinema;

    }

    @Override
    public int getCount() {
        return docs.size();
    }

    @Override
    public Object getItem(int i) {
        return docs.get(i);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if(docs.get(i) != null){
            Log.d("-----------------------" ,String.valueOf( docs.get(i).getType()));
            Log.d("-----------------------" ,String.valueOf( docs.get(i).getName()));
        }


        if (view == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_for_listview_month_film, viewGroup, false);
        }

        TextView textViewName = view.findViewById(R.id.textview_name_film);
        TextView textViewRating = view.findViewById(R.id.textview_rating_film);

        textViewName.setText(docs.get(i).getName());
        textViewRating.setText(String.format("%.1f",docs.get(i).getRating().getKp()));

        return view;
    }
}
