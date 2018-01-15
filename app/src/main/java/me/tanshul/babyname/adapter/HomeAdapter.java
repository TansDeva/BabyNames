package me.tanshul.babyname.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tanshul.babyname.R;
import me.tanshul.babyname.activity.HomeActivity;
import me.tanshul.babyname.model.NameItem;
import me.tanshul.babyname.uitl.NameDialog;

/**
 * Created by tansdeva on 15/01/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements SectionIndexer {
    private Context mContext;
    private HomeActivity mActivity;
    private ArrayList<NameItem> mItemList;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public HomeAdapter(Context context, ArrayList<NameItem> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mActivity = (HomeActivity) mContext;
        updateSections();
    }

    private void updateSections() {
        String sections = "";
        for (int i = 0; i < mSections.length(); i++) {
            String value = String.valueOf(mSections.charAt(i));
            boolean exist = false;
            for (int j = 0; j < getItemCount(); j++) {
                if (mItemList.get(j).getName().startsWith(value)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                sections += value;
            }
        }
        mSections = sections;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_name_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        final NameItem item = mItemList.get(position);
        holder.tvItemName.setText(item.getName());
        holder.tvItemMeaning.setText(item.getMeaning());
        //Alternate item background
        if (position % 2 == 0) {
            holder.llRoot.setBackgroundResource(R.color.white);
        } else {
            holder.llRoot.setBackgroundResource(R.color.list_back);
        }
        boolean isHeader = true;
        char value = item.getName().charAt(0);
        if (position != 0) {
            isHeader = value != mItemList.get(position - 1).getName().charAt(0);
        }
        holder.tvSectionName.setVisibility(isHeader ? View.VISIBLE : View.GONE);
        if (isHeader) {
            holder.tvSectionName.setText(String.valueOf(value).toUpperCase());
        }
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showNameDetails(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_root)
        LinearLayout llRoot;
        @BindView(R.id.tv_section_name)
        TextView tvSectionName;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_item_meaning)
        TextView tvItemMeaning;

        HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getItemCount(); j++) {
                if (mItemList.get(j).getName().charAt(0) == mSections.charAt(i)) {
                    return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++) {
            sections[i] = String.valueOf(mSections.charAt(i));
        }
        return sections;
    }
}
