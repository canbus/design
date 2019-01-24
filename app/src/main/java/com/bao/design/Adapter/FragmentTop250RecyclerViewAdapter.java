package com.bao.design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bao.design.Entry.Top250Entry;
import com.bao.design.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class FragmentTop250RecyclerViewAdapter extends RecyclerView.Adapter {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;
    private Context mContext;
    private int mLayoutID;
    private List<Top250Entry.Subject> mMovieSubjects;
    private View mHeaderView = null;
    public FragmentTop250RecyclerViewAdapter(Context context, int layout_fragment_top250_item, List<Top250Entry.Subject> movieSubjects) {
        this.mContext = context;
        mLayoutID = layout_fragment_top250_item;
        this.mMovieSubjects = movieSubjects;
    }
    public void addHeader(View v)
    {
        mHeaderView = v;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null)
            return TYPE_NORMAL;
        if(position == 0)
            return TYPE_HEADER;
        else
            return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(getItemViewType(i) == TYPE_HEADER)
            return;

        ImageLoader.getInstance().displayImage(mMovieSubjects.get(i).getImages().getSmall(),((MyViewHolder)viewHolder).imageview);
        ((MyViewHolder)viewHolder).titleTv.setText(mMovieSubjects.get(i).getTitle());
        ((MyViewHolder)viewHolder).ratingTv.setText(mMovieSubjects.get(i).getRating().getAverage()+"");
        String directors="导演:";
        for(Top250Entry.Director director:mMovieSubjects.get(i).getDirectors()){
            if(directors.length() > 3)
                directors += "/";
            directors += director.getName();
        }
        ((MyViewHolder)viewHolder).directorTv.setText(directors);
        String casts="主演:";
        for(Top250Entry.Cast cast:mMovieSubjects.get(i).getCasts()){
            if(casts.length() > 3)
                casts += "/";
            casts += cast.getName();
        }
        ((MyViewHolder)viewHolder).castsTv.setText(casts);
        String genres="风格:";
        for(String str:mMovieSubjects.get(i).getGenres()){
            if(genres.length() > 3)
                genres += "/";
            genres += str;
        }
        ((MyViewHolder)viewHolder).genresTv.setText(genres);
        ((MyViewHolder)viewHolder).yearTv.setText("年代:"+mMovieSubjects.get(i).getYear());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER ){
            return new MyViewHolder(mHeaderView);
        }
        MyViewHolder viewHolder = new MyViewHolder(View.inflate(mContext,R.layout.layout_fragment_top250_item,null));
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mMovieSubjects == null ? 0: mMovieSubjects.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView titleTv,ratingTv,directorTv,castsTv,genresTv,yearTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView)
                return;
            imageview = itemView.findViewById(R.id.id_imageview);
            titleTv = itemView.findViewById(R.id.id_title);
            ratingTv = itemView.findViewById(R.id.id_rating);
            directorTv = itemView.findViewById(R.id.id_director);
            castsTv = itemView.findViewById(R.id.id_casts);
            genresTv = itemView.findViewById(R.id.id_genres);
            yearTv = itemView.findViewById(R.id.id_year);
        }
    }
}
