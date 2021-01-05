package com.kent.android.slim.sample.letv.desktopmanager

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.interfaces.Badge
import com.kent.android.slim.sample.letv.desktopmanager.view.BadgeImageView

/**
 * Created by songzhukai on 2020/12/29.
 */

object BadgeKey {
    const val LEFT = "left"
    const val LEFT_DISABLE = "left_disable"
    const val RIGHT_DISABLE = "right_disable"
    const val RIGHT = "right"
    const val UP = "up"
    const val DOWN = "down"
    const val HOME = "home"
    const val EDGE = "edge"
}

class BadgeHelper {

    /**
     * init all badges e.g. upArrow,DownArrow,HomeIcon,etc.
     * we set the LayoutParams here as well so that he badge can be shown at the right position
     */
    fun initBadgeMap(context: Context): HashMap<String, Badge> {

        val badgeMap = HashMap<String, Badge>()

        val resources = context.resources
        val arrowSize = resources.getDimensionPixelSize(R.dimen.desktop_manager_arrow_size)
        val arrowPadding = resources
                .getDimensionPixelSize(R.dimen.desktop_manager_arrow_padding)
        val iconPadding = resources
                .getDimensionPixelSize(R.dimen.desktop_manager_edit_mode_width)

        var frameParams: FrameLayout.LayoutParams

        // 指示箭头：向上
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        val arrowUp = BadgeImageView(context)
        arrowUp.setImageResource(R.drawable.up)
        arrowUp.setViewOutOfParent(true)
        arrowUp.setPadding(0, -arrowPadding, 0, 0)
        arrowUp.setCustomizedLayoutParams(frameParams)
        arrowUp.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 指示箭头：向左
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        val arrowLeft = BadgeImageView(context)
        arrowLeft.setImageResource(R.drawable.left)
        arrowLeft.setViewOutOfParent(true)
        arrowLeft.setPadding(-arrowPadding, 0, 0, 0)
        arrowLeft.setCustomizedLayoutParams(frameParams)
        arrowLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 指示箭头：禁止向左
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        val arrowLeftDisable = BadgeImageView(context)
        arrowLeftDisable.setImageResource(R.drawable.left_disabled)
        arrowLeftDisable.setViewOutOfParent(true)
        arrowLeftDisable.setPadding(-arrowPadding, 0, 0, 0)
        arrowLeftDisable.setCustomizedLayoutParams(frameParams)
        arrowLeftDisable.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 指示箭头：向右
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        val arrowRight = BadgeImageView(context)
        arrowRight.setImageResource(R.drawable.right)
        arrowRight.setViewOutOfParent(true)
        arrowRight.setPadding(0, 0, -arrowPadding, 0)
        arrowRight.setCustomizedLayoutParams(frameParams)
        arrowRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 指示箭头：禁止向右
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        val arrowRightDisable = BadgeImageView(context)
        arrowRightDisable.setImageResource(R.drawable.right_disabled)
        arrowRightDisable.setViewOutOfParent(true)
        arrowRightDisable.setPadding(0, 0, -arrowPadding, 0)
        arrowRightDisable.setCustomizedLayoutParams(frameParams)
        arrowRightDisable.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 指示箭头：向下
        frameParams = FrameLayout.LayoutParams(arrowSize, arrowSize)
        frameParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        val arrowDown = BadgeImageView(context)
        arrowDown.setImageResource(R.drawable.down)
        arrowDown.setViewOutOfParent(true)
        arrowDown.setPadding(0, 0, 0, -arrowPadding)
        arrowDown.setCustomizedLayoutParams(frameParams)
        arrowDown.setScaleType(ImageView.ScaleType.CENTER_INSIDE)

        // 左上角：锁
        frameParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        frameParams.gravity = Gravity.TOP or Gravity.START
        val lockIcon = BadgeImageView(context)
        lockIcon.setImageResource(R.drawable.ic_lock_screen_tag)
        lockIcon.setPadding(iconPadding, iconPadding, 0, 0)
        lockIcon.setCustomizedLayoutParams(frameParams)

        // 右上角：home
        frameParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        frameParams.gravity = Gravity.TOP or Gravity.END
        val homeIcon = BadgeImageView(context)
        homeIcon.setImageResource(R.drawable.ic_top_right_home)
        homeIcon.setPadding(0, iconPadding, iconPadding, 0)
        homeIcon.setCustomizedLayoutParams(frameParams)
        homeIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE)


        // 边缘：just a little bigger then item
        val whiteEdgeWidth = resources.getDimensionPixelSize(R.dimen.item_width) + 4
        val whiteEdgeHeight = resources.getDimensionPixelSize(R.dimen.item_height) + 4
        frameParams = FrameLayout.LayoutParams(whiteEdgeWidth, whiteEdgeHeight)
        frameParams.gravity = Gravity.CENTER
        val whiteEdge = BadgeImageView(context)
        whiteEdge.setBackgroundDrawable(
                resources.getDrawable(R.drawable.desktop_manager_edit_mode))
        whiteEdge.setCustomizedLayoutParams(frameParams)
        badgeMap.put(BadgeKey.LEFT, arrowLeft)
        badgeMap.put(BadgeKey.LEFT_DISABLE, arrowLeftDisable)
        badgeMap.put(BadgeKey.RIGHT, arrowRight)
        badgeMap.put(BadgeKey.RIGHT_DISABLE, arrowRightDisable)
        badgeMap.put(BadgeKey.UP, arrowUp)
        badgeMap.put(BadgeKey.DOWN, arrowDown)
        badgeMap.put(BadgeKey.HOME, homeIcon)
        badgeMap.put(BadgeKey.EDGE, whiteEdge)

        return badgeMap
    }

}