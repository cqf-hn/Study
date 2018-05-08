package hn.cqf.com.gank.base;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewWrapper<T> implements View.OnClickListener, View.OnAttachStateChangeListener {

    protected BaseActivity _activity;
    protected BaseRecyclerAdapter adapter;
    protected ArrayList<T> mData;
    protected int viewType;
    protected View root;
    protected int position = -1;
    protected ArrayList<String> apiTags = new ArrayList<>();
    private boolean defaultHandleApiCallBack;
    private boolean hasShowLoadDialog;


    public BaseRecyclerViewWrapper(BaseActivity activity) {
        this(activity, null);
    }

    public BaseRecyclerViewWrapper(BaseActivity activity, ViewGroup parent) {
        this._activity = activity;
        onLayoutBefore();
        root = LayoutInflater.from(activity).inflate(onItemLayoutId(), parent, false);
        ButterKnife.bind(this, root);
        root.setOnClickListener(this);
        root.addOnAttachStateChangeListener(this);
        onLayoutAfter();
    }

    public void init(BaseRecyclerAdapter adapter, ArrayList<T> mData, int viewType) {
        this.adapter = adapter;
        this.mData = mData;
        this.viewType = viewType;
    }

    @Override
    public void onClick(View v) {

    }

    protected T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public BaseRecyclerViewHolder getViewHolder() {
        return new BaseRecyclerViewHolder(root);
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    @CallSuper
    public void onViewDetachedFromWindow(View v) {

    }

    protected void onLayoutBefore() {

    }

    protected void onLayoutAfter() {

    }

    protected void toggleHasShowLoadDialog(boolean hasShowLoadDialog) {
        this.hasShowLoadDialog = hasShowLoadDialog;
    }

    protected void toggleDefaultHandleApiCallBack(boolean defaultHandleApiCallBack) {
        this.defaultHandleApiCallBack = defaultHandleApiCallBack;
    }

    protected abstract int onItemLayoutId();

    public abstract void bindData(int position);

    public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(int position) {
            BaseRecyclerViewWrapper.this.position = position;
            BaseRecyclerViewWrapper.this.bindData(position);
        }
    }
}