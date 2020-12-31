package com.kent.android.slim.sample.letv.desktopmanager

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.anim.RecyclerAnimator
import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import com.kent.android.slim.sample.letv.desktopmanager.interfaces.Badge
import kotlinx.android.synthetic.main.activity_desktop_manager.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by songzhukai on 2020/12/28.
 */
class DesktopManagerActivity : AppCompatActivity() {

    val tag = DesktopManagerActivity::class.simpleName

    lateinit var mScreenInfos: ArrayList<ScreenInfo>
    lateinit var mBadgeMap: HashMap<String, Badge>
    private var mEditMode = false

    val spanNum = 7
    lateinit var mInUseRv: RecyclerView
    lateinit var mInUseAdapter: ScreenAdapter
    val amInUse = RecyclerAnimator()

    lateinit var mToAddRv: RecyclerView
    lateinit var mToAddAdapter: ScreenAdapter
    val amToAdd = RecyclerAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop_manager)

        initData()
        initBadge()
    }

    fun initBadge() {
        val badgeHelper = BadgeHelper()
        mBadgeMap = badgeHelper.initBadgeMap(this)
    }

    fun initData() {
        mScreenInfos = XmlLoader.loadScreenInfoFromXML(this, R.xml.default_screens_cibn)
        Log.d(tag, "mScreenInfos size = ${mScreenInfos.size}")

        val linkedList = ArrayList(mScreenInfos)

        mScreenInfos[10].locked = true
        mScreenInfos[9].locked = true

        //init InUseRv
        var inUseAdapter = ScreenAdapter(linkedList, this)
        inUseAdapter.setKeyListener(InUseKeyListener())
        inUseAdapter.setFocusChangeListener(InUseOnFocusChangeListener())
        InUseRv.layoutManager = GridLayoutManager(this, spanNum)
        InUseRv.adapter = inUseAdapter
        InUseRv.itemAnimator = amInUse
        mInUseRv = InUseRv
        mInUseAdapter = inUseAdapter

        //init ToAddRv
        var toAddAdapter = ScreenAdapter(ArrayList(), this)
        toAddAdapter.setKeyListener(ToAddKeyListener())
        toAddAdapter.setFocusChangeListener(ToAddOnFocusChangeListener())
        ToAddRv.layoutManager = GridLayoutManager(this, spanNum)
        ToAddRv.adapter = toAddAdapter
        ToAddRv.itemAnimator = amToAdd
        mToAddRv = ToAddRv
        mToAddAdapter = toAddAdapter
    }


    /**
     * 开关编辑模式，模式关闭时保存数据
     * @param view the view who trigger this toggle
     */
    private fun toggleEditMode(view: View?) {
        if (view == null) {
            return
        }
        mEditMode = !mEditMode
        // TODO once enter edit mode.should not show edit any more
//        forceExitGuide()
        if (mEditMode) {
            // turn on
            Log.d(tag, "edit mode is on")
//            mBtnMain.setVisibility(View.INVISIBLE)
        } else {
//            brushDatabase(false)
            // turn off;
            Log.d(tag, "edit mode is off")
//            if (rlvInUse.getAdapter().getItemCount() !== 0) {
//                mBtnMain.setVisibility(View.VISIBLE)
//            }
        }
        if (view.onFocusChangeListener is ScreenItemOnFocusChangeListener) {
            val listener: ScreenItemOnFocusChangeListener = view.onFocusChangeListener as ScreenItemOnFocusChangeListener
            listener.updateBadges(view, mEditMode)
        }
    }

    private abstract class ScreenItemOnFocusChangeListener : View.OnFocusChangeListener {
        abstract fun updateBadges(v: View?, shouldShowArrow: Boolean)

        override fun onFocusChange(v: View, hasFocus: Boolean) {
            if (hasFocus) {
                v.isSelected = false
            }
        }
    }

    private inner class InUseOnFocusChangeListener : ScreenItemOnFocusChangeListener() {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            super.onFocusChange(v, hasFocus)
            updateBadges(v, hasFocus)
        }

        /**
         * update the arrows attached to this view
         * @param v is a View Group so we don't judge here. but we can add judgement to make it more
         * readable.
         * @param shouldShowArrow
         */
        override fun updateBadges(v: View?, shouldShowArrow: Boolean) {
            // LetvLog.i(TAG, "Focus " + shouldShowArrow + " view has gain focus :" + v);
//            stopArrowAnimation()
            if (v == null) {
                return
            }


            if (mEditMode) {
                if (shouldShowArrow) {
//                    mHandler.sendEmptyMessageDelayed(DesktopManagerActivity.START_ARROW_ANIMATION,
//                            DesktopManagerActivity.DEFAULT_ARROW_ANIMATION_INTERVAL.toLong())
                    val useChildren: List<ScreenInfo> = mInUseAdapter.getDataList()
                    val useChildPos: Int = mInUseRv.getChildPosition(v)
                    mBadgeMap.get(BadgeKey.EDGE)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.RIGHT)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.DOWN)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.LEFT)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.UP)?.setTargetViewGroup(v as ViewGroup)
                    // 根据左右item内容的状态，判断如何绘制UI
//                    if (useChildPos < mInUseAdapter.getItemCount() - 1) {
//                        badgeList.get(KEY_RIGHT)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_RIGHT_DISABLE)?.remove()
//                    } else {
                    // reach the right boundary
//                        badgeList.get(KEY_RIGHT)?.remove()
//                        badgeList.get(KEY_RIGHT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
//                    if (useChildPos > mSortOffsetPosition) {
//                        badgeList.get(KEY_LEFT)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_LEFT_DISABLE)?.remove()
//                    } else {
//                        // reach the left boundary
//                        badgeList.get(KEY_LEFT)?.remove()
//                        badgeList.get(KEY_LEFT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
                    // 根据是否能够移除，判断是否添加下箭头
//                    if (useChildren[useChildPos].removable) {
//                        badgeList.get(KEY_DOWN)?.setTargetViewGroup(v as ViewGroup) // v as _root_ide_package_.android.view.ViewGroup is ViewGroup
//                    } else {
//                        // can't be removed
//                        badgeList.get(KEY_DOWN)?.remove()
//                    }
//                    // 根据是否能够排序，判断是否添加左右肩头
//                    // this judge must be done at last
//                    if (!useChildren[useChildPos].sortable) {
//                        badgeList.get(KEY_LEFT)?.remove()
//                        badgeList.get(KEY_LEFT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_RIGHT)?.remove()
//                        badgeList.get(KEY_RIGHT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
                    return
                }
            }
            mBadgeMap.get(BadgeKey.DOWN)?.remove()
            mBadgeMap.get(BadgeKey.UP)?.remove()
            mBadgeMap.get(BadgeKey.RIGHT)?.remove()
            mBadgeMap.get(BadgeKey.LEFT)?.remove()
            mBadgeMap.get(BadgeKey.EDGE)?.remove()
            mBadgeMap.get(BadgeKey.RIGHT_DISABLE)?.remove()
            mBadgeMap.get(BadgeKey.LEFT_DISABLE)?.remove()
        }
    }

    /**
     * Items in InUserRecyclerView should register this Listener
     * Handle the keyEvents in InUserRecyclerView
     */
    private inner class InUseKeyListener : View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            val inUseChildPos: Int = mInUseRv.getChildPosition(v)
            if (event.action == KeyEvent.ACTION_DOWN) {
                // not in Edit Mode

                // Edit Mode
                if (!mEditMode) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                            // toggle edit mode
                            toggleEditMode(v)
                            return true
                        }
                        else -> {
                        }
                    }
//                    val v = FocusFinder.getInstance().findNextFocus(mInUseRv, v , View.FOCUS_DOWN)
//                    if(v == null){
//                        return true
//                    }
                } else {
                    val from = inUseChildPos
                    val to: Int
                    when (keyCode) {

                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            if(CalculateUtil.isOnRowFirstPos(inUseChildPos, spanNum)){
                                return true
                            }
                            to = inUseChildPos - 1;
                            updateAdapterMove(from, to, v);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            if(CalculateUtil.isOnRowLastPos(inUseChildPos, spanNum)){
                               return true
                            }
                            to = inUseChildPos + 1;
                            updateAdapterMove(from, to, v);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            to = inUseChildPos - spanNum
                            updateAdapterMove(from, to, v);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            if (!CalculateUtil.isInLastRow(mInUseAdapter.itemCount, inUseChildPos, spanNum)) {
                                to = inUseChildPos + spanNum
                                updateAdapterMove(from, to, v);
                                return true
                            }

                            // there a footer view at the end of the list
//                            if (focusView === footer) {
                            updateAdapterDelete(inUseChildPos, 0, 0)
                            return true
//                            }

                        }
//                      KeyEvent.KEYCODE_BACK,
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                            // toggle edit mode
                            toggleEditMode(v)
                            return true
                        }
                        else -> {
                        }
                    }
                }

            }
            return false
        }

        fun updateAdapterMove(from: Int, to: Int, v: View) {
            if (to < 0 || to >= mInUseAdapter.itemCount) {
                return
            }
            mInUseAdapter.moveItem(from, to)
            amInUse.addAnimationsFinishedListener(
                    RecyclerView.ItemAnimator.ItemAnimatorFinishedListener { // 动画结束后，检查view的位置，更新箭头的状态
                        (v.onFocusChangeListener as ScreenItemOnFocusChangeListener)
                                .updateBadges(v, mEditMode)
                    })
        }




        /**
         * transfer data between to Adapters, and update UI at the same time
         * @param position position in InUseAdapter which to be deleted
         * @param destPosition position in ToAddAdapter which to be added
         * @param viewGroupPosition position in ViewGroup at which the view should get focus;
         */
        private fun updateAdapterDelete(position: Int, destPosition: Int,
                                        viewGroupPosition: Int) {
            mToAddAdapter.addItem(destPosition, mInUseAdapter.deleteItem(position))
            Log.d(tag, "position :$position--destPosition :$destPosition")
            amToAdd.addAnimationsStartedListener(
                    object : RecyclerAnimator.ItemAnimatorStartedListener {
                        override fun onAnimationsStarted(holder: RecyclerView.ViewHolder) {
                            holder.itemView.requestFocus()
                        }
                    })
        }
    }


    private inner class ToAddKeyListener : View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            val inUseChildPos: Int = mToAddRv.getChildPosition(v)
            if (event.action == KeyEvent.ACTION_DOWN) {
                // not in Edit Mode

                // Edit Mode
                if (!mEditMode) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                            // toggle edit mode
                            toggleEditMode(v)
                            return true
                        }
                        else -> {
                        }
                    }

                } else {
                    val from = inUseChildPos
                    val to: Int
                    when (keyCode) {

                        KeyEvent.KEYCODE_DPAD_LEFT ,
                        KeyEvent.KEYCODE_DPAD_RIGHT,
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            updateAdapterDelete(inUseChildPos, mInUseAdapter.itemCount, 0)
                            return true;
                        }


//                    KeyEvent.KEYCODE_BACK,
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                            // toggle edit mode
                            toggleEditMode(v)
                            return true
                        }
                    }
                }

            }
            return false
        }

        private fun updateAdapterDelete(position: Int, destPosition: Int,
                                        viewGroupPosition: Int) {
            mInUseAdapter.addItem(destPosition, mToAddAdapter.deleteItem(position))
            Log.d(tag, "position :$position--destPosition :$destPosition")
            amInUse.addAnimationsStartedListener(
                    object : RecyclerAnimator.ItemAnimatorStartedListener {
                        override fun onAnimationsStarted(holder: RecyclerView.ViewHolder) {
                            holder.itemView.requestFocus()
                        }
                    })
        }





    }


    private inner class ToAddOnFocusChangeListener : ScreenItemOnFocusChangeListener() {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            super.onFocusChange(v, hasFocus)
            updateBadges(v, hasFocus)
        }

        /**
         * update the arrows attached to this view
         * @param v is a View Group so we don't judge here. but we can add judgement to make it more
         * readable.
         * @param shouldShowArrow
         */
        override fun updateBadges(v: View?, shouldShowArrow: Boolean) {
            // LetvLog.i(TAG, "Focus " + shouldShowArrow + " view has gain focus :" + v);
//            stopArrowAnimation()
            if (v == null) {
                return
            }


            if (mEditMode) {
                if (shouldShowArrow) {
//                    mHandler.sendEmptyMessageDelayed(DesktopManagerActivity.START_ARROW_ANIMATION,
//                            DesktopManagerActivity.DEFAULT_ARROW_ANIMATION_INTERVAL.toLong())
                    val useChildren: List<ScreenInfo> = mInUseAdapter.getDataList()
                    val useChildPos: Int = mInUseRv.getChildPosition(v)
                    mBadgeMap.get(BadgeKey.EDGE)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.RIGHT)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.DOWN)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.LEFT)?.setTargetViewGroup(v as ViewGroup)
                    mBadgeMap.get(BadgeKey.UP)?.setTargetViewGroup(v as ViewGroup)
                    // 根据左右item内容的状态，判断如何绘制UI
//                    if (useChildPos < mInUseAdapter.getItemCount() - 1) {
//                        badgeList.get(KEY_RIGHT)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_RIGHT_DISABLE)?.remove()
//                    } else {
                    // reach the right boundary
//                        badgeList.get(KEY_RIGHT)?.remove()
//                        badgeList.get(KEY_RIGHT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
//                    if (useChildPos > mSortOffsetPosition) {
//                        badgeList.get(KEY_LEFT)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_LEFT_DISABLE)?.remove()
//                    } else {
//                        // reach the left boundary
//                        badgeList.get(KEY_LEFT)?.remove()
//                        badgeList.get(KEY_LEFT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
                    // 根据是否能够移除，判断是否添加下箭头
//                    if (useChildren[useChildPos].removable) {
//                        badgeList.get(KEY_DOWN)?.setTargetViewGroup(v as ViewGroup) // v as _root_ide_package_.android.view.ViewGroup is ViewGroup
//                    } else {
//                        // can't be removed
//                        badgeList.get(KEY_DOWN)?.remove()
//                    }
//                    // 根据是否能够排序，判断是否添加左右肩头
//                    // this judge must be done at last
//                    if (!useChildren[useChildPos].sortable) {
//                        badgeList.get(KEY_LEFT)?.remove()
//                        badgeList.get(KEY_LEFT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                        badgeList.get(KEY_RIGHT)?.remove()
//                        badgeList.get(KEY_RIGHT_DISABLE)?.setTargetViewGroup(v as ViewGroup)
//                    }
                    return
                }
            }
            mBadgeMap.get(BadgeKey.DOWN)?.remove()
            mBadgeMap.get(BadgeKey.UP)?.remove()
            mBadgeMap.get(BadgeKey.RIGHT)?.remove()
            mBadgeMap.get(BadgeKey.LEFT)?.remove()
            mBadgeMap.get(BadgeKey.EDGE)?.remove()
            mBadgeMap.get(BadgeKey.RIGHT_DISABLE)?.remove()
            mBadgeMap.get(BadgeKey.LEFT_DISABLE)?.remove()
        }
    }


}