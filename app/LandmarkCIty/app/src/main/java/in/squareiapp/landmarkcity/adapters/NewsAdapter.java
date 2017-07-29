package in.squareiapp.landmarkcity.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.models.NewsData;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;

/**
 * Created by mohit kumar on 7/27/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private CustomItemClickListener customItemClickListener;
    private List<NewsData> usersPostsData;
    private Context context;


    public NewsAdapter(CustomItemClickListener customItemClickListener, List<NewsData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_news, parent, false);
        return new NewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(usersPostsData.get(position), position, customItemClickListener);
    }

    @Override
    public int getItemCount() {
        return usersPostsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivNewsImage;
        TextView tvNews, tvNewsDate, tvNewsTime, tvNewsTitle;
        CardView cardPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNews = (TextView) itemView.findViewById(R.id.tvNews);
            tvNewsDate = (TextView) itemView.findViewById(R.id.tvNewsDate);
            tvNewsTime = (TextView) itemView.findViewById(R.id.tvNewsTime);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.tvNewsTitle);
            ivNewsImage = (ImageView) itemView.findViewById(R.id.ivNewsImage);
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);

            /////////////////////set listeners/////////////////////
            cardPost.setOnClickListener(this);

        }

        public void bind(NewsData usersPostsData, int position, CustomItemClickListener customItemClickListener) {

            Logger.error("tag","=========news url::"+usersPostsData.getImage());
            if (CommonUtils.isValidString(usersPostsData.getImage()))
                Picasso.with(context).load("http:"+usersPostsData.getImage()).fit().into(ivNewsImage);

            tvNews.setText(usersPostsData.getStory());
            tvNewsTitle.setText(usersPostsData.getSite_title());
            //  tvNewsDate.setText(usersPostsData.get());
            //    tvNewsTime.setText(usersPostsData.getPostedBy());


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardPost:
                    //  customItemClickListener.onItemClickCallback(getAdapterPosition(), 1);
                    break;
            }
        }
    }
}

