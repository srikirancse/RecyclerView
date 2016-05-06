package com.example.srikiransistla.homework5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Srikiran Sistla on 2/15/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private List<Map<String, ?>> mDataset;
    private Context mContext;
    OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);

        public void onOverflowMenuClick(View v,final int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    //Constructor
    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataset) {
        mContext = myContext;
        mDataset = myDataset;
    }

    //create new view (invoked by layout manger) first time load
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;


            v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.cardview, parent, false);



        /*v=LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview,parent,false);*/

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //replace the contents of the view (get old remembered view holders)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, ?> movie = mDataset.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public RatingBar vRating;
        public TextView vRatingnum;
        public ImageView vMenu;

        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.rv_icon);
            vTitle = (TextView) v.findViewById(R.id.rv_title);
            vDescription = (TextView) v.findViewById(R.id.rv_description);
            vRating=(RatingBar) v.findViewById(R.id.rv_rating);
            vRatingnum=(TextView) v.findViewById(R.id.rv_rating_num);
            vMenu=(ImageView) v.findViewById(R.id.rv_overflow);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mItemClickListener != null) {
                        Log.d("Inside adapter", "Onitem click");
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }

            });

            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener!=null){
                            mItemClickListener.onOverflowMenuClick(v,getAdapterPosition());
                        }
                    }
                });
            }


        }


        public void bindMovieData(Map<String, ?> movie) {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
            Double movieRating=((Double) movie.get("rating"));
            Double movieRatingHalf=movieRating/2;
            float f = movieRatingHalf.floatValue();
            vRating.setRating(f);
            vRatingnum.setText(movieRating+"");
        }

    }
}
