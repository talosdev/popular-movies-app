package app.we.go.movies.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import static android.os.Build.VERSION_CODES;

/**
 * ViewPager implementation that adapts its height according to the height of the
 * fragments that it contains.
 * 
 *
 * TODO move to personal library (not used in popular-movies-app
 *
 * Take from
 * http://stackoverflow.com/a/32488566/5758378
 * and adapted accordingly
 * http://stackoverflow.com/a/35722487/5758378
 */
public class WrappingViewPager extends ViewPager {


    private Boolean mAnimStarted = false;

    public WrappingViewPager(Context context) {
        super(context);
    }

    public WrappingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mAnimStarted && null != getAdapter()) {
            int height = 0;
            View child = ((FragmentPagerAdapter) getAdapter()).getItem(getCurrentItem()).getView();
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = child.getMeasuredHeight();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && height < getMinimumHeight()) {
                    height = getMinimumHeight();
                }
            }


            // Account for pagerTitleStrip or pagerTabStrip
            View tabStrip = getChildAt(0);
            if (tabStrip instanceof PagerTitleStrip) {
                //tabStrip.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.UNSPECIFIED));
                height += tabStrip.getMeasuredHeight();
            }

            // Not the best place to put this animation, but it works pretty good.
            int newHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            if (getLayoutParams().height != 0 && heightMeasureSpec != newHeight) {
                final int targetHeight = height;
                final int currentHeight = getLayoutParams().height;
                final int heightChange = targetHeight - currentHeight;

                Animation a = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        if (interpolatedTime >= 1) {
                            getLayoutParams().height = targetHeight;
                        } else {
                            int stepHeight = (int) (heightChange * interpolatedTime);
                            getLayoutParams().height = currentHeight + stepHeight;
                        }
                        requestLayout();
                    }

                    @Override
                    public boolean willChangeBounds() {
                        return true;
                    }
                };

                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mAnimStarted = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mAnimStarted = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                a.setDuration(500);
                startAnimation(a);
                mAnimStarted = true;
            } else {
                heightMeasureSpec = newHeight;
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
