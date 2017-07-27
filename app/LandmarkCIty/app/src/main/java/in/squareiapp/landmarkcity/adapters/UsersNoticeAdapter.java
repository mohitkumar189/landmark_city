package in.squareiapp.landmarkcity.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import in.squareiapp.landmarkcity.models.UsersPostsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;

import static in.squareiapp.landmarkcity.R.id.ivImageComment;
import static in.squareiapp.landmarkcity.R.id.ivImageLike;

/**
 * Created by mohit kumar on 7/27/2017.
 */

public class UsersNoticeAdapter extends RecyclerView.Adapter<UsersNoticeAdapter.MyViewHolder> {

    private CustomItemClickListener customItemClickListener;
    private List<UsersPostsData> usersPostsData;
    private Context context;
    private int displayeHeight;
    private int displayWidth;
    private JSONArray jsonArray = null;

    public UsersNoticeAdapter(CustomItemClickListener customItemClickListener, List<UsersPostsData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
        DisplayMetrics d = CommonUtils.getDeviceMetrix();
        displayeHeight = d.heightPixels;
        displayWidth = d.widthPixels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_notice, parent, false);
        return new UsersNoticeAdapter.MyViewHolder(itemView);
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

        ImageView ivProfile, ivShare, ivPostImage, ivUpdateType/*, ivImageLike, ivImageComment*/;
        TextView tvUserName, tvPostTime, tvPostDescription/*, tvPostLikes, tvPostComments*/;
        CardView cardPost;
        String u = null;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvPostTime = (TextView) itemView.findViewById(R.id.tvPostTime);
            tvPostDescription = (TextView) itemView.findViewById(R.id.tvPostDescription);
         /*   tvPostLikes = (TextView) itemView.findViewById(R.id.tvPostLikes);
            tvPostComments = (TextView) itemView.findViewById(R.id.tvPostComments);*/
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            ivUpdateType = (ImageView) itemView.findViewById(R.id.ivUpdateType);
          /*  ivImageLike = (ImageView) itemView.findViewById(ivImageLike);
            ivImageComment = (ImageView) itemView.findViewById(ivImageComment);*/
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);

            /////////////////////set listeners/////////////////////
            cardPost.setOnClickListener(this);
           /* ivImageLike.setOnClickListener(this);
            ivImageComment.setOnClickListener(this);*/
            ivShare.setOnClickListener(this);
            ivUpdateType.setOnClickListener(this);
        }

        public void bind(UsersPostsData usersPostsData, int position, CustomItemClickListener customItemClickListener) {

            try {
                jsonArray = new JSONArray(usersPostsData.getPostURL());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                u = ApiURLS.BASE_URL + jsonArray.get(0);
                Logger.info("=============", u);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Logger.info("tag", "device density =================================::" + CommonUtils.getContentType(usersPostsData.getPost_type()));

            if (CommonUtils.isValidString(usersPostsData.getProfilepic()))
                Picasso.with(context).load(usersPostsData.getProfilepic()).into(ivProfile);

            tvUserName.setText(usersPostsData.getPostedBy());
            tvPostDescription.setText(usersPostsData.getDescription());
          /*  tvPostLikes.setText(usersPostsData.getLikes() + " Likes");
            tvPostComments.setText(usersPostsData.getComments() + " Comments");*/
            tvPostTime.setText(usersPostsData.getPosted_on());

            if (CommonUtils.getContentType(usersPostsData.getPost_type()).equals(CommonUtils.DocumentType.VIDEO.name())) {
                Logger.info("tag", "====================type equal");
                ivPostImage.setImageResource(R.drawable.videoback);
                if (ivUpdateType.getVisibility() == View.GONE) {
                    Logger.info("tag", "====================showing image icon");
                    ivUpdateType.setVisibility(View.VISIBLE);
                }
            } else {
                if (CommonUtils.isValidString(u))
                    Picasso.with(context).load(u).fit().into(ivPostImage);
                Logger.info("tag", "====================type not equal");
            }

            ////////////////////////////////////////////////////changing comment icon
         /*   if (usersPostsData.getCommented() == 1) {
                ivImageComment.setImageResource(R.drawable.comment_hover);
            } else {
                ivImageComment.setImageResource(R.drawable.comment);
            }

            ////////////////////////////////////////////////////changing liked icon
            if (usersPostsData.getLiked() == 1) {
                ivImageLike.setImageResource(R.drawable.like_hover);
            } else {
                ivImageLike.setImageResource(R.drawable.like);
            }*/
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
                case R.id.ivShare:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 4);
                case R.id.ivUpdateType:

                    if (CommonUtils.getContentType(usersPostsData.get(getAdapterPosition()).getPost_type()).equals(CommonUtils.DocumentType.VIDEO.name())) {
                        //  String u = ApiURLS.BASE_URL + jsonArray.get(0);
                        Logger.info("tag", "Playing video");
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        Logger.info("tag", "sending url::" + u);
                        intent.putExtra("videourl", u);
                        context.startActivity(intent);

                    }
                    //  customItemClickListener.onItemClickCallback(getAdapterPosition(), 5);
                    break;
            }
        }
    }
}

