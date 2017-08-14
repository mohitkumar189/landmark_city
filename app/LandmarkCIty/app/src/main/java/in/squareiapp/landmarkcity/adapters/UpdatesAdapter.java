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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.featuresactivities.VideoPlayerActivity;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.models.UpdatesData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;

import static in.squareiapp.landmarkcity.R.id.ivImageComment;
import static in.squareiapp.landmarkcity.R.id.ivImageLike;
import static in.squareiapp.landmarkcity.R.id.ivShare;

/**
 * Created by mohit kumar on 7/28/2017.
 */

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private CustomItemClickListener customItemClickListener;
    private List<UpdatesData> usersPostsData;
    private Context context;
    private JSONArray jsonArray = null;

    public UpdatesAdapter(CustomItemClickListener customItemClickListener, List<UpdatesData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
<<<<<<< HEAD
        Logger.info(TAG, "calling constructor for::" + TAG);
=======
        // Logger.info(TAG, "calling constructor for::" + TAG);
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_updates, parent, false);
<<<<<<< HEAD
        Logger.info(TAG, "returning view");
=======
        //     Logger.info(TAG, "returning view");
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
        return new UpdatesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
<<<<<<< HEAD
        Logger.info(TAG, "binding view");
=======
        //    Logger.info(TAG, "binding view");
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
        holder.bind(usersPostsData.get(position), position, customItemClickListener);
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        Logger.info(TAG, "item size::" + usersPostsData.size());
=======
        //   Logger.info(TAG, "item size::" + usersPostsData.size());
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
        return usersPostsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivUpdateImage, ivUpdateType;/*, ivPostImage, ivUpdateType, ivImageLike, ivImageComment*/
        TextView tvUpdateTitle;/*,,tvUserName, tvPostTime, tvPostDescription tvPostLikes, tvPostComments*/
        CardView cardPost;
        String u = null;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvUpdateTitle = (TextView) itemView.findViewById(R.id.tvUpdateTitle);
            ivUpdateImage = (ImageView) itemView.findViewById(R.id.ivUpdateImage);
            ivUpdateType = (ImageView) itemView.findViewById(R.id.ivUpdateType);
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);

            /////////////////////set listeners/////////////////////
            cardPost.setOnClickListener(this);
            ivUpdateImage.setOnClickListener(this);
            ivUpdateType.setOnClickListener(this);
        }

        public void bind(UpdatesData usersPostsData, int position, CustomItemClickListener customItemClickListener) {
            Logger.info(TAG, "binding view");
            try {
                jsonArray = new JSONArray(usersPostsData.getPostURL());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                u = ApiURLS.BASE_URL + jsonArray.get(0);
<<<<<<< HEAD
            //    Logger.info("=============", u);
=======
                Picasso.with(context).load(u).into(ivUpdateImage);
                Logger.info("=============", u);
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
            } catch (JSONException e) {
                e.printStackTrace();
            }

<<<<<<< HEAD
            if (CommonUtils.isValidString(u))
                Picasso.with(context).load(u).into(ivUpdateImage);

            tvUpdateTitle.setText(usersPostsData.getTitle());

            if (CommonUtils.getContentType(usersPostsData.getPost_type()).equals(CommonUtils.DocumentType.VIDEO.name())) {
                // Logger.info("tag", "====================type equal");
                if (ivUpdateType.getVisibility() == View.GONE) {
                    //    Logger.info("tag", "====================showing image icon");
                    ivUpdateType.setVisibility(View.VISIBLE);
=======

            tvUpdateTitle.setText(usersPostsData.getTitle());
            Logger.info("=============Conent type", usersPostsData.getPost_type()+" "+usersPostsData.getId());
            if (CommonUtils.getContentType(usersPostsData.getPost_type()).equals(CommonUtils.DocumentType.VIDEO.name())) {
                 Logger.info("tag", "====================type equal");
                if (ivUpdateType.getVisibility() == View.GONE) {
                        Logger.info("tag", "====================showing image icon");
                    ivUpdateType.setVisibility(View.VISIBLE);
                    ivUpdateImage.setImageResource(R.drawable.videoback);
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardPost:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 1);
                    break;
                case ivImageLike:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 2);
                    break;
                case ivImageComment:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 3);
                    break;
                case ivShare:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 4);
                case R.id.ivUpdateType:

                    if (CommonUtils.getContentType(usersPostsData.get(getAdapterPosition()).getPost_type()).equals(CommonUtils.DocumentType.VIDEO.name())) {
                        //  String u = ApiURLS.BASE_URL + jsonArray.get(0);
<<<<<<< HEAD
                        Logger.info("tag", "Playing video");
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        Logger.info("tag", "sending url::" + u);
=======
                        //   Logger.info("tag", "Playing video");
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        //   Logger.info("tag", "sending url::" + u);
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
                        intent.putExtra("videourl", u);
                        context.startActivity(intent);
                    }
                    //  customItemClickListener.onItemClickCallback(getAdapterPosition(), 5);
                    break;
            }
        }
    }
}


