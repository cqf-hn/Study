package hn.cqf.com.gank.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import hn.cqf.com.gank.utils.AnimUtils;

public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<BaseRecyclerViewHolder> {


    /*数据集合*/
    protected List<T> mData;

    /*单击事件*/
    private OnItemClickListener mListener;
    private Subscription mSubscription;

    /*事件监听接口*/
    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    private static final int DELAY = 138;
    private int mLastPosition = -1;

    /**
     * 返回item的布局资源id
     *
     * @param viewType
     */
    protected abstract int getItemLayoutResId(int viewType);

    /**
     * 返回BaseViewHolder的子类
     */
    protected abstract BaseRecyclerViewHolder createViewHolder(View itemView, int type);

    /**
     * 设置事件单击监听器
     */
    public void setItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(getItemLayoutResId(viewType), parent, false);
        final BaseRecyclerViewHolder holder = createViewHolder(itemView, viewType);
        if (mListener != null && isSetClickListener(viewType)) {
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onClick(itemView, holder.getLayoutPosition());
                        }
                    });
        }
        return holder;
    }

    protected boolean isSetClickListener(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setDataAndRefresh(List<T> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        } else {
            this.mData = data;
        }
        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount());
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void showItemAnim(View view, int position) {
        if (position > mLastPosition) {
            view.setAlpha(0);
            AnimUtils.AnimInsert(view, DELAY * position, TimeUnit.MILLISECONDS);
            mLastPosition = position;
        }
    }
}