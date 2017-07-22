package hn.cqf.com.gank.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {



    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindData(int position);
}