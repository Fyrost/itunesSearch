package com.example.myapplication.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.OvershootInterpolator
import com.example.myapplication.R
import com.github.clans.fab.FloatingActionMenu

fun fabFilterAnimation(fab: FloatingActionMenu) {
    val set = AnimatorSet()

    val scaleOutX: ObjectAnimator = ObjectAnimator.ofFloat(fab.menuIconView,"ScaleX", 1.0f, 0.2f)
    val scaleOutY: ObjectAnimator = ObjectAnimator.ofFloat(fab.menuIconView,"ScaleY", 1.0f, 0.2f)

    val scaleInX: ObjectAnimator = ObjectAnimator.ofFloat(fab.menuIconView,"ScaleX", 0.2f, 1.0f)
    val scaleInY: ObjectAnimator = ObjectAnimator.ofFloat(fab.menuIconView,"ScaleY", 0.2f, 1.0f)

    scaleOutX.duration = 50
    scaleOutY.duration = 50

    scaleInX.duration = 150
    scaleInY.duration = 150

    scaleInX.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            fab.menuIconView.setImageResource(if (!fab.isOpened) R.drawable.ic_fab_filter_close else R.drawable.ic_fab_filter_list)
        }
    })

    set.play(scaleOutX).with(scaleOutY)
    set.play(scaleInX).with(scaleInY).after(scaleOutX)
    set.interpolator = OvershootInterpolator(2F)

    fab.iconToggleAnimatorSet = set
    fab.setClosedOnTouchOutside(true)
}