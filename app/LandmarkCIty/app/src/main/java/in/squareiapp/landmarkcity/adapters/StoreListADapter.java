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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.models.StoreData;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;

/**
 * Created by mohit kumar on 8/1/2017.
 */

public class StoreListADapter extends RecyclerView.Adapter<StoreListADapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private CustomItemClickListener customItemClickListener;
    private List<StoreData> usersPostsData;
    private Context context;
    private String[] images;

    public StoreListADapter(CustomItemClickListener customItemClickListener, List<StoreData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
        Logger.info(TAG, "===::setting title");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.info(TAG, "===::onCreateViewHolder()");
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_shop, parent, false);
        return new StoreListADapter.MyViewHolder(itemView);
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
        TextView tvShopName;
        TextView tvShopAddress;
        TextView tvLocation;
        TextView tvCall;
        CardView cardPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvShopName = (TextView) itemView.findViewById(R.id.tvShopName);
            tvShopAddress = (TextView) itemView.findViewById(R.id.tvShopAddress);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvCall = (TextView) itemView.findViewById(R.id.tvCall);
            ivNewsImage = (ImageView) itemView.findViewById(R.id.ivNewsImage);
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);

            /////////////////////set listeners/////////////////////
            cardPost.setOnClickListener(this);
        }

        public void bind(StoreData usersPostsData, int position, CustomItemClickListener customItemClickListener) {
            Logger.info(TAG, "===::binding view");
            // images = usersPostsData.getImages().split(",");
            String i = usersPostsData.getImages();
            try {
                JSONObject jsonObject = new JSONObject(usersPostsData.getImages());
                JSONArray jsonArray = jsonObject.getJSONArray("imgs");
                if (CommonUtils.isValidString(String.valueOf(jsonArray.get(0))))
                    Picasso.with(context).load("" + jsonArray.get(0)).fit().into(ivNewsImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (CommonUtils.isValidString(usersPostsData.getStoreName()))
                tvShopName.setText(usersPostsData.getStoreName());

            if (CommonUtils.isValidString(usersPostsData.getAddress()))
                tvShopAddress.setText(usersPostsData.getAddress());

            tvLocation.setOnClickListener(this);
            tvCall.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardPost:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 1);
                    break;
                case R.id.tvCall:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 2);
                    break;
                case R.id.tvLocation:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 3);
                    break;
            }
        }
    }
}