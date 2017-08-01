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
import in.squareiapp.landmarkcity.models.FriendsData;
import in.squareiapp.landmarkcity.utils.CommonUtils;

/**
 * Created by mohit kumar on 7/31/2017.
 */

public class FriendSuggestionAdapter extends RecyclerView.Adapter<FriendSuggestionAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private CustomItemClickListener customItemClickListener;
    private Context context;
    private List<FriendsData> friendsData;

    public FriendSuggestionAdapter(CustomItemClickListener customItemClickListener, List<FriendsData> usersPostsData, Context context) {
        this.customItemClickListener = customItemClickListener;
        this.friendsData = usersPostsData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_friend_suggestion, parent, false);
        return new FriendSuggestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(friendsData.get(position), position, customItemClickListener);
    }

    @Override
    public int getItemCount() {
        return friendsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivImage;
        ImageView ivReject;
        TextView tvName;
        TextView tvStatus;
        TextView tvAddFriend;
        CardView friendCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvAddFriend = (TextView) itemView.findViewById(R.id.tvAddFriend);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            ivReject = (ImageView) itemView.findViewById(R.id.ivReject);
            friendCard = (CardView) itemView.findViewById(R.id.friendCard);
        }

        public void bind(FriendsData usersPostsData, int position, CustomItemClickListener customItemClickListener) {
            tvName.setText(usersPostsData.getName());
            if (CommonUtils.isValidString(usersPostsData.getProf_status()))
                tvStatus.setText(usersPostsData.getProf_status());
            String imageUrl = usersPostsData.getProfilePic();
            if (CommonUtils.isValidString(imageUrl))
                Picasso.with(context).load(usersPostsData.getProfilePic()).placeholder(R.drawable.user_image).error(R.drawable.user_image).into(ivImage);
            friendCard.setOnClickListener(this);
            tvAddFriend.setOnClickListener(this);
            ivReject.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.friendCard:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 10);
                    break;
                case R.id.tvAddFriend:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 20);
                    break;
                case R.id.ivReject:
                    customItemClickListener.onItemClickCallback(getAdapterPosition(), 30);
                    break;
            }
        }
    }
}
