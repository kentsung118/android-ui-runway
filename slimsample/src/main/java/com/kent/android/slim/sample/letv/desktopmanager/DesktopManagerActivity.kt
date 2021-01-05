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
    lateinit var badgeMap: HashMap<String, Badge>
    private var editMode = false

    val spanNum = 7
    lateinit var mInUseRv: RecyclerView
    lateinit var mInUseAdapter: ScreenAdapter
    val amInUse = RecyclerAnimator()

    lateinit var mToAddRv: RecyclerView
    lateinit var mToAddAdapter: ScreenAdapter
    val amToAdd = RecyclerAnimator()
    lateinit var mTestItem: TestItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop_manager)

        initData()
        initBadge()
    }

    fun initBadge() {
        val badgeHelper = BadgeHelper()
        badgeMap = badgeHelper.initBadgeMap(this)
    }

    override fun onResume() {
        super.onResume()
        //设定首页
        inUseRv.postDelayed(Runnable {
            val view = inUseRv.getChildAt(0)
            view?.let {
                badgeMap[BadgeKey.HOME]?.setTargetViewGroup(view as ViewGroup)
            }
        }, 300)
    }

    fun initData() {
        mScreenInfos = XmlLoader.loadScreenInfoFromXML(this, R.xml.default_screens_cibn)
        Log.d(tag, "mScreenInfos size = ${mScreenInfos.size}")

        val linkedList = ArrayList(mScreenInfos)

        mScreenInfos[10].locked = true
        mScreenInfos[9].locked = true
        mScreenInfos[2].locked = true

        //init InUseRv
        val inUseAdapter = ScreenAdapter(linkedList, this)
        inUseAdapter.setKeyListener(InUseKeyListener())
        inUseAdapter.setFocusChangeListener(InUseOnFocusChangeListener())
        inUseRv.layoutManager = GridLayoutManager(this, spanNum)
        inUseRv.adapter = inUseAdapter
        inUseRv.itemAnimator = amInUse
        mInUseRv = inUseRv
        mInUseAdapter = inUseAdapter

        //init ToAddRv
        val toAddAdapter = ScreenAdapter(ArrayList(), this)
        toAddAdapter.setKeyListener(ToAddKeyListener())
        toAddAdapter.setFocusChangeListener(ToAddOnFocusChangeListener())
        toAddRv.layoutManager = GridLayoutManager(this, spanNum)
        toAddRv.adapter = toAddAdapter
        toAddRv.itemAnimator = amToAdd
        mToAddRv = toAddRv
        mToAddAdapter = toAddAdapter

        mTestItem = TestItem(spanNum, MoveItemListener())
    }


    /**
     * 开关编辑模式，模式关闭时保存数据
     * @param view the view who trigger this toggle
     */
    private fun toggleEditMode(view: View?) {
        if (view == null) {
            return
        }

        editMode = !editMode
        Log.d(tag, "editMode turn $editMode")
        if (view.onFocusChangeListener is ScreenItemOnFocusChangeListener) {
            val listener: ScreenItemOnFocusChangeListener = view.onFocusChangeListener as ScreenItemOnFocusChangeListener
            listener.updateBadges(view, editMode)
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
            if (v == null) {
                return
            }

            if (editMode) {
                if (shouldShowArrow) {
                    val currPos: Int = mInUseRv.getChildPosition(v)
                    //框线圆角
                    badgeMap.get(BadgeKey.EDGE)?.setTargetViewGroup(v as ViewGroup)
                    //箭头
                    validShowArrow(currPos, v, Direction.RIGHT, BadgeKey.RIGHT)
                    validShowArrow(currPos, v, Direction.LEFT, BadgeKey.LEFT)
                    validShowArrow(currPos, v, Direction.UP, BadgeKey.UP)
                    validShowArrow(currPos, v, Direction.DOWN, BadgeKey.DOWN)
                    return
                }
            }
            badgeMap.get(BadgeKey.DOWN)?.remove()
            badgeMap.get(BadgeKey.UP)?.remove()
            badgeMap.get(BadgeKey.RIGHT)?.remove()
            badgeMap.get(BadgeKey.LEFT)?.remove()
            badgeMap.get(BadgeKey.EDGE)?.remove()
        }

        private fun validShowArrow(pos: Int, view: View, direction: Direction, badgeKey: String) {
            if (mTestItem.searchPosition(mInUseAdapter.data, pos, direction) != -1) {
                badgeMap[badgeKey]?.setTargetViewGroup(view as ViewGroup)
            } else {
                badgeMap[badgeKey]?.remove()
            }
        }
    }

    /**
     * Items in InUserRecyclerView should register this Listener
     * Handle the keyEvents in InUserRecyclerView
     */
    private inner class InUseKeyListener : View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            if (event.action == KeyEvent.ACTION_DOWN) {
                // not in Edit Mode

                // Edit Mode
                if (!editMode) {
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
                    val from = mInUseRv.getChildLayoutPosition(v)
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            updateAdapterMove(from, v, Direction.LEFT);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            updateAdapterMove(from, v, Direction.RIGHT);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            updateAdapterMove(from, v, Direction.UP);
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            updateAdapterMove(from, v, Direction.DOWN)
                            return true
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

        fun updateAdapterMove(from: Int, v: View, direction: Direction) {

            val pos = mTestItem.searchPosition(mInUseAdapter.data, from, direction)
            if (pos == -1) {
                return
            }

            when (direction) {
                Direction.LEFT,
                Direction.UP -> {
                    mTestItem.editUpSort(mInUseAdapter.data, from, pos)
                }
                Direction.RIGHT,
                Direction.DOWN -> {
                    mTestItem.editDownSort(mInUseAdapter.data, from, pos)
                }
            }
            amInUse.addAnimationsFinishedListener {
                // 动画结束后，检查view的位置，更新箭头的状态
                (v.onFocusChangeListener as ScreenItemOnFocusChangeListener)
                        .updateBadges(v, editMode)
            }

        }
    }

    private inner class MoveItemListener : TestItem.MoveItemListener {
        override fun onMoveItem(form: Int, to: Int) {
            mInUseAdapter.moveItem(form, to)
        }
    }

    private inner class ToAddKeyListener : View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            val inUseChildPos: Int = mToAddRv.getChildPosition(v)
            if (event.action == KeyEvent.ACTION_DOWN) {
                // not in Edit Mode

                // Edit Mode
                if (!editMode) {
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

                        KeyEvent.KEYCODE_DPAD_LEFT,
                        KeyEvent.KEYCODE_DPAD_RIGHT,
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            return true;
                        }
                        KeyEvent.KEYCODE_DPAD_UP -> {
//                            updateAdapterDelete(inUseChildPos, mInUseAdapter.itemCount, 0)
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


            if (editMode) {
                if (shouldShowArrow) {
//                    mHandler.sendEmptyMessageDelayed(DesktopManagerActivity.START_ARROW_ANIMATION,
//                            DesktopManagerActivity.DEFAULT_ARROW_ANIMATION_INTERVAL.toLong())
                    val useChildren: List<ScreenInfo> = mInUseAdapter.getDataList()
                    val useChildPos: Int = mInUseRv.getChildPosition(v)
                    badgeMap.get(BadgeKey.EDGE)?.setTargetViewGroup(v as ViewGroup)
                    badgeMap.get(BadgeKey.RIGHT)?.setTargetViewGroup(v as ViewGroup)
                    badgeMap.get(BadgeKey.DOWN)?.setTargetViewGroup(v as ViewGroup)
                    badgeMap.get(BadgeKey.LEFT)?.setTargetViewGroup(v as ViewGroup)
                    badgeMap.get(BadgeKey.UP)?.setTargetViewGroup(v as ViewGroup)
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
            badgeMap.get(BadgeKey.DOWN)?.remove()
            badgeMap.get(BadgeKey.UP)?.remove()
            badgeMap.get(BadgeKey.RIGHT)?.remove()
            badgeMap.get(BadgeKey.LEFT)?.remove()
            badgeMap.get(BadgeKey.EDGE)?.remove()
            badgeMap.get(BadgeKey.RIGHT_DISABLE)?.remove()
            badgeMap.get(BadgeKey.LEFT_DISABLE)?.remove()
        }
    }


}

