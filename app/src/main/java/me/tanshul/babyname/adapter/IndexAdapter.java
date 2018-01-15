package me.tanshul.babyname.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tanshul.babyname.R;
import me.tanshul.babyname.uitl.ValueCallback;

/**
 * Created by tansdeva on 15/01/18.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {
    private Context mContext;
    private ValueCallback mCallback;
    private ArrayList<String> mItemList;

    public IndexAdapter(Context context, ArrayList<String> itemList, ValueCallback callback) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mCallback = callback;
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_index_item, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, final int position) {
        final String item = mItemList.get(position);
        holder.tvIndexText.setText(item);
        holder.llIndexItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.call(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class IndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_index_item)
        LinearLayout llIndexItem;
        @BindView(R.id.tv_index_text)
        TextView tvIndexText;

        IndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
