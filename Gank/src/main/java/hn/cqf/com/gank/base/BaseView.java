package hn.cqf.com.gank.base;


public interface BaseView {
    /**
     * 当P层发生错误时调用
     */
    void showError(String msg, Throwable e);
}
