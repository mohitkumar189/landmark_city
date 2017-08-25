package in.squareiapp.landmarkcity.adapters;

import android.content.Context;
import android.content.Intent;
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
import in.squareiapp.landmarkcity.activities.featuresactivities.FriendsActivity;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.models.GreetingData;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;

/**
 * Created by mohit kumar on 8/25/2017.
 */

public class GreetingsAdapter extends RecyclerView.Adapter<GreetingsAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private CustomItemClickListener customItemClickListener;
    private List<GreetingData> usersPostsData;
    private Context context;

    public GreetingsAdapter(CustomItemClickListener customItemClickListener, List<GreetingData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
        Logger.info(TAG, "===::setting title");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.info(TAG, "===::onCreateViewHolder()");
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_store, parent, false);
        return new GreetingsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Logger.info(TAG, "===::onBindViewHolder()");
        holder.bind(usersPostsData.get(position), position, customItemClickListener);
    }

    @Override
    public int getItemCount() {
        return usersPostsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivNewsImage;
        TextView tvNews;
        CardView cardPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNews = (TextView) itemView.findViewById(R.id.tvNews);
            ivNewsImage = (ImageView) itemView.findViewById(R.id.ivNewsImage);
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);

            /////////////////////set listeners/////////////////////
           cardPost.setOnClickListener(this);
        }

        public void bind(GreetingData usersPostsData, int position, CustomItemClickListener customItemClickListener) {
            Logger.info(TAG, "===::binding view");
            if (CommonUtils.isValidString(usersPostsData.getStoreIcon()))
                Picasso.with(context).load(usersPostsData.getStoreIcon()).fit().into(ivNewsImage);

            tvNews.setText(usersPostsData.getStoreName());

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardPost:

                    Intent i = new Intent(context, FriendsActivity.class);
                    i.putExtra("greetingImage", usersPostsData.get(getAdapterPosition()).getStoreIcon());
                    i.putExtra("greetingId", usersPostsData.get(getAdapterPosition()).getId());
                    context.startActivity(i);

                    break;
            }
        }
    }
}