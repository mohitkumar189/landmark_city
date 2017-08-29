package in.squareiapp.landmarkcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.models.RewardsPoints;
import in.squareiapp.landmarkcity.utils.CommonUtils;

/**
 * Created by mohit kumar on 8/28/2017.
 */

public class RewardsAdapter extends BaseAdapter{
    private List<RewardsPoints> rewardsPointses;
    private Context context;


    public RewardsAdapter( List<RewardsPoints> rewardsPointses, Context context) {
        this.rewardsPointses = rewardsPointses;
        this.context = context;

    }

    @Override
    public int getCount() {
        return rewardsPointses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (convertView == null) {
            v = inflater.inflate(R.layout.single_word_container_item, null);
        } else {
            v = convertView;
        }

        ((TextView) v.findViewById(R.id.wordName)).setText(CommonUtils.getFormattedDate(rewardsPointses.get(position).getDate()));
        ((TextView) v.findViewById(R.id.pointTV)).setText(rewardsPointses.get(position).getPoint());

        return v;
    }
}
