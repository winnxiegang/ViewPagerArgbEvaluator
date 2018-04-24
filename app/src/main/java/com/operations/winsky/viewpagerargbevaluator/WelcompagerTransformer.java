package com.operations.winsky.viewpagerargbevaluator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup;


public class WelcompagerTransformer implements PageTransformer, OnPageChangeListener {
    private static final float ROT_MOD = -15f;
    private int pageIndex;
    private boolean pageChanged = true;
    private static final float MIN_SCALE = 0.75f;

    /**
     * 此方法是滑动的时候每一个页面View都会调用该方法
     * view:当前的页面
     * position:当前滑动的位置
     * 视差效果：在View正常滑动的情况下，给当前View或者当前view里面的每一个子view来一个加速度
     * 而且每一个加速度大小不一样。
     */
    @Override
    public void transformPage(View view, float position) {

        ViewGroup v = (ViewGroup) view.findViewById(R.id.rl);
        View bg1 = v.findViewById(R.id.rl);
        View bg2 = v.findViewById(R.id.vp_text);

        int bg1_green = view.getContext().getResources().getColor(R.color.nt_tls_ui_black);
        int bg2_blue = view.getContext().getResources().getColor(R.color.tencent_tls_ui_titleBackground);

        Integer tag = (Integer) view.getTag();
        View parent = (View) view.getParent();
        //颜色估值器
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int color = bg1_green;
        if (tag.intValue() == pageIndex) {
            switch (pageIndex) {
                case 0:
                    color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
                    bg2.setBackgroundColor(color);
                    break;
                case 1:
                    color = (int) evaluator.evaluate(Math.abs(position), bg2_blue, bg1_green);
                    bg2.setBackgroundColor(color);
                    break;
                case 2:
                    color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
                    bg2.setBackgroundColor(color);
                    break;
                default:
                    break;
            }
            //设置整个viewpager的背景颜色
            if (color == bg1_green) {
                parent.setBackgroundColor(bg2_blue);
            } else if (color == bg2_blue) {
                parent.setBackgroundColor(bg1_green);
            }

        }
        int pageWidth = view.getWidth();
        /**
         * 设置viewpage的 动画
         */
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        System.out.println("position:" + position + ", positionOffset:" + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        pageIndex = position;
        pageChanged = true;
        System.out.println("position_selected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
