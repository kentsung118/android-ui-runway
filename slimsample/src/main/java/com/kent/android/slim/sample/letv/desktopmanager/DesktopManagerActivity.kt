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
    lateinit var mTestItemToAdd: TestItem

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
        mTestItemToAdd = TestItem(spanNum, ToAddMoveItemListener(), false)
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
                            val pos = mTestItem.searchPosition(mInUseAdapter.data, from, Direction.DOWN)
                            if (pos == -2) {
                                updateAdapterDelete(from, 0)
                            } else {
                                updateAdapterMove(from, v, Direction.DOWN)
                            }
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

        /**
         * transfer data between to Adapters, and update UI at the same time
         * @param position position in InUseAdapter which to be deleted
         * @param destPosition position in ToAddAdapter which to be added
         * @param viewGroupPosition position in ViewGroup at which the view should get focus;
         */
        private fun updateAdapterDelete(position: Int, destPosition: Int) {
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

    private inner class MoveItemListener : TestItem.MoveItemListener {
        override fun onMoveItem(form: Int, to: Int) {
            mInUseAdapter.moveItem(form, to)
        }
    }

    private inner class ToAddMoveItemListener : TestItem.MoveItemListener {
        override fun onMoveItem(form: Int, to: Int) {
            mToAddAdapter.moveItem(form, to)
        }
    }

    private inner class ToAddKeyListener : View.OnKeyListener {
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
                    val from = mToAddRv.getChildPosition(v)
                    val to: Int
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            updateAdapterMove(from, v, Direction.LEFT);
                            return true
                        }
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            updateAdapterMove(from, v, Direction.RIGHT);
                            return true
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            updateAdapterMove(from, v, Direction.DOWN);
                            return true
                        }
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            val pos = mTestItemToAdd.searchPosition(mInUseAdapter.data, from, Direction.UP)
                            if (pos == -2) {
                                updateAdapterDelete(from, mInUseAdapter.itemCount)
                            } else {
                                updateAdapterMove(from, v, Direction.UP)
                            }
                            return true
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

        fun updateAdapterMove(from: Int, v: View, direction: Direction) {

            val pos = mTestItemToAdd.searchPosition(mToAddAdapter.data, from, direction)
            if (pos == -1) {
                return
            }

            when (direction) {
                Direction.LEFT,
                Direction.UP -> {
                    mTestItemToAdd.editUpSort(mToAddAdapter.data, from, pos)
                }
                Direction.RIGHT,
                Direction.DOWN -> {
                    mTestItemToAdd.editDownSort(mToAddAdapter.data, from, pos)
                }
            }
            amToAdd.addAnimationsFinishedListener {
                // 动画结束后，检查view的位置，更新箭头的状态
                (v.onFocusChangeListener as ScreenItemOnFocusChangeListener)
                        .updateBadges(v, editMode)
            }

        }

        private fun updateAdapterDelete(position: Int, destPosition: Int) {
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
            if (v == null) {
                return
            }

            if (editMode) {
                if (shouldShowArrow) {
                    val currPos: Int = mInUseRv.getChildPosition(v)
                    badgeMap.get(BadgeKey.EDGE)?.setTargetViewGroup(v as ViewGroup)
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
            if (mTestItemToAdd.searchPosition(mToAddAdapter.data, pos, direction) != -1) {
                badgeMap[badgeKey]?.setTargetViewGroup(view as ViewGroup)
            } else {
                badgeMap[badgeKey]?.remove()
            }
        }
    }


}

