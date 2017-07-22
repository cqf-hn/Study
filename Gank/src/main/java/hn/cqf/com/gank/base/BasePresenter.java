package hn.cqf.com.gank.base;


public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detach();
    void start();
}
