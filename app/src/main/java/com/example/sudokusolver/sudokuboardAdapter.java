package com.example.sudokusolver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class sudokuboardAdapter extends RecyclerView.Adapter<sudokuboardAdapter.MyViewHolder> {
    private char[] mDataset;
    public static int selected=-1;
    private static View selectedView;
    public static ArrayList<Integer> constants=new ArrayList<>();


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.numberText);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("view id", String.valueOf(getLayoutPosition()));
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.grey));
            if(selected!=-1)
            {
                selectedView.setBackground(ContextCompat.getDrawable(view.getContext(),R.drawable.back));
            }
            if(selected==getLayoutPosition())
            {
                selected=-1;
            }
            selected=getLayoutPosition();
            selectedView=view;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    sudokuboardAdapter(char[] myDataset) {
        mDataset = myDataset;
        initialize();
    }
    private void initialize()
    {
        for (int i=0;i<mDataset.length;i++)
        {
            if(mDataset[i]!=' ')
            {
                constants.add(i);
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public sudokuboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType){
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sudokuposition, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(String.valueOf(mDataset[position]));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
