package hn.cqf.com.gank.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import hn.cqf.com.gank.R;
import io.reactivex.Flowable;

/**
 * @author cqf
 * @time 2017/1/21 0021  下午 1:04
 * @desc ${TODD}
 */
public class AnimUtils {
    private static Subscription mSubscription;

    public static void AnimInsert(final View view, long delay, TimeUnit unit) {
        Flowable.timer(delay, unit).subscribe(
                new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                                R.anim.slide_in_right);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                view.setAlpha(1);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        view.startAnimation(animation);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
