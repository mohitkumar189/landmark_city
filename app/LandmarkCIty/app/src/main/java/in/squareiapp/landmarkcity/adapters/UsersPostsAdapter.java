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

import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.models.UsersPostsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.Logger;

/**
 * Created by mohit kumar on 7/26/2017.
 */

public class UsersPostsAdapter extends RecyclerView.Adapter<UsersPostsAdapter.MyViewHolder> {

    private CustomItemClickListener customItemClickListener;
    private List<UsersPostsData> usersPostsData;
    private Context context;

    public UsersPostsAdapter(CustomItemClickListener customItemClickListener, List<UsersPostsData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.usersPostsData = usersPostsData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_user_feeds, parent, false);
        return new UsersPostsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(usersPostsData.get(position), position, customItemClickListener);
    }

    @Override
    public int getItemCount() {
        return usersPostsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile, ivShare, ivPostImage, ivImageLike, ivImageComment;
        TextView tvUserName, tvPostTime, tvPostDescription, tvPostLikes, tvPostComments;
        CardView cardPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvPostTime = (TextView) itemView.findViewById(R.id.tvPostTime);
            tvPostDescription = (TextView) itemView.findViewById(R.id.tvPostDescription);
            tvPostLikes = (TextView) itemView.findViewById(R.id.tvPostLikes);
            tvPostComments = (TextView) itemView.findViewById(R.id.tvPostComments);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            ivImageLike = (ImageView) itemView.findViewById(R.id.ivImageLike);
            ivImageComment = (ImageView) itemView.findViewById(R.id.ivImageComment);
            cardPost = (CardView) itemView.findViewById(R.id.cardPost);
        }

        public void bind(UsersPostsData usersPostsData, int position, CustomItemClickListener customItemClickListener) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(usersPostsData.getPostURL());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String u = null;
            try {
                u = ApiURLS.BASE_URL + jsonArray.get(0);
                Logger.info("=============",u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Picasso.with(context).load(u).into(ivPostImage);
            Picasso.with(context).load(usersPostsData.getProfilepic()).into(ivProfile);

            tvUserName.setText(usersPostsData.getPostedBy());
            tvPostDescription.setText(usersPostsData.getDescription());
            tvPostLikes.setText(usersPostsData.getLikes() + " Likes");
            tvPostComments.setText(usersPostsData.getComments() + " Comments");
        }
    }
}
