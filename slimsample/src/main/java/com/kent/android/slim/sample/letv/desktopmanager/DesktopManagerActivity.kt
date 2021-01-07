package com.kent.android.slim.sample.letv.desktopmanager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.SortHandler.Companion.cross
import com.kent.android.slim.sample.letv.desktopmanager.SortHandler.Companion.notFound
import com.kent.android.slim.sample.letv.desktopmanager.anim.RecyclerAnimator
import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import com.kent.android.slim.sample.letv.desktopmanager.interfaces.Badge
import kotlinx.android.synthetic.main.activity_desktop_manager.*
import java.lang.ref.WeakReference
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
    lateinit var mInUseHandler: SortHandler
    lateinit var mToAddHandler: SortHandler
    lateinit var mGuide: Guide

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

//        startGuide()
    }



    private fun startGuide(){
        val resources = arrayListOf<Int>(R.drawable.desktop_manager_guide_1, R.drawable.desktop_manager_guide_2)
        mGuide = Guide(guideView, LinkedList<Int>(resources))
        mGuide.start()
    }

    fun initData() {
        mScreenInfos = XmlLoader.loadScreenInfoFromXML(this, R.xml.default_screens_cibn)
        Log.d(tag, "mScreenInfos size = ${mScreenInfos.size}")

        val linkedList = ArrayList(mScreenInfos)

        mScreenInfos[10].locked = true
        mScreenInfos[9].locked = true
        mScreenInfos[2].locked = true

        //init InUseRv
        val inUse = InUse()
        val inUseAdapter = ScreenAdapter(linkedList, this)
        inUseAdapter.setKeyListener(inUse.InUseKeyListener())
        inUseAdapter.setFocusChangeListener(inUse.InUseOnFocusChangeListener())
        inUseRv.layoutManager = GridLayoutManager(this, spanNum)
        inUseRv.adapter = inUseAdapter
        inUseRv.itemAnimator = amInUse
        mInUseRv = inUseRv
        mInUseAdapter = inUseAdapter

        //init ToAddRv
        val toAdd = ToAdd()
        val toAddAdapter = ScreenAdapter(ArrayList(), this)
        toAddAdapter.setKeyListener(toAdd.ToAddKeyListener())
        toAddAdapter.setFocusChangeListener(toAdd.ToAddOnFocusChangeListener())
        toAddRv.layoutManager = GridLayoutManager(this, spanNum)
        toAddRv.adapter = toAddAdapter
        toAddRv.itemAnimator = amToAdd
        mToAddRv = toAddRv
        mToAddAdapter = toAddAdapter

        mInUseHandler = SortHandler(spanNum, inUse.MoveItemListener())
        mToAddHandler = SortHandler(spanNum, toAdd.ToAddMoveItemListener(), false)
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

    abstract class ScreenItemOnFocusChangeListener : View.OnFocusChangeListener {
        abstract fun updateBadges(v: View?, shouldShowArrow: Boolean)

        override fun onFocusChange(v: View, hasFocus: Boolean) {
            if (hasFocus) {
                v.isSelected = false
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when(event.keyCode){
                KeyEvent.KEYCODE_DPAD_RIGHT ->{
                    if(guideView.visibility == View.VISIBLE){
                        mGuide.stop()
                        return true
                    }
                }
            }
        }

        return super.dispatchKeyEvent(event)
    }

    inner class InUse {
        /**
         * Items in InUserRecyclerView should register this Listener
         * Handle the keyEvents in InUserRecyclerView
         */
        inner class InUseKeyListener : View.OnKeyListener {
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
                                val pos = mInUseHandler.searchPosition(mInUseAdapter.data, from, Direction.DOWN)
                                if (pos == cross) {
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

                val pos = mInUseHandler.searchPosition(mInUseAdapter.data, from, direction)
                if (pos == notFound) {
                    return
                }

                when (direction) {
                    Direction.LEFT,
                    Direction.UP -> {
                        mInUseHandler.editUpSort(mInUseAdapter.data, from, pos)
                    }
                    Direction.RIGHT,
                    Direction.DOWN -> {
                        mInUseHandler.editDownSort(mInUseAdapter.data, from, pos)
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

        inner class MoveItemListener : SortHandler.MoveItemListener {
            override fun onMoveItem(form: Int, to: Int) {
                mInUseAdapter.moveItem(form, to)
            }
        }

        inner class InUseOnFocusChangeListener : ScreenItemOnFocusChangeListener() {
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
                        val currPos: Int = mInUseRv.getChildLayoutPosition(v)
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
                if (mInUseHandler.searchPosition(mInUseAdapter.data, pos, direction) != notFound) {
                    badgeMap[badgeKey]?.setTargetViewGroup(view as ViewGroup)
                } else {
                    badgeMap[badgeKey]?.remove()
                }
            }
        }
    }

    inner class ToAdd {
        inner class ToAddMoveItemListener : SortHandler.MoveItemListener {
            override fun onMoveItem(form: Int, to: Int) {
                mToAddAdapter.moveItem(form, to)
            }
        }

        inner class ToAddKeyListener : View.OnKeyListener {
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
                                val pos = mToAddHandler.searchPosition(mInUseAdapter.data, from, Direction.UP)
                                if (pos == cross) {
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

                val pos = mToAddHandler.searchPosition(mToAddAdapter.data, from, direction)
                if (pos == notFound) {
                    return
                }

                when (direction) {
                    Direction.LEFT,
                    Direction.UP -> {
                        mToAddHandler.editUpSort(mToAddAdapter.data, from, pos)
                    }
                    Direction.RIGHT,
                    Direction.DOWN -> {
                        mToAddHandler.editDownSort(mToAddAdapter.data, from, pos)
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

        inner class ToAddOnFocusChangeListener : ScreenItemOnFocusChangeListener() {
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
                if (mToAddHandler.searchPosition(mToAddAdapter.data, pos, direction) != notFound) {
                    badgeMap[badgeKey]?.setTargetViewGroup(view as ViewGroup)
                } else {
                    badgeMap[badgeKey]?.remove()
                }
            }
        }
    }

    class Guide(val v: View, val drawables: LinkedList<Int>) {

        private val mHandler = MsgHandler(this)

        fun start() {
            //外部檢查是否有使用過教學
            //啟動 sendMsg 10s 後換照片
            v.visibility = View.VISIBLE
            next()
        }

        private fun next() {
            if (drawables.size > 0) {
                val drawableRes = drawables.removeFirst()
                v.background = v.resources.getDrawable(drawableRes)
                mHandler.sendEmptyMessageDelayed(0, 1000 * 10)
            } else {
                stop()
            }
        }

        fun stop() {
            mHandler.removeCallbacksAndMessages(null)
            v.visibility = View.GONE
            //有人為操作，中斷停止 msgHandler
        }

        //避免内存泄漏使用 companion object & WeakReference 持有 BgHandler
        companion object {
            class MsgHandler(guide: Guide) : Handler(Looper.getMainLooper()) {
                private val mReference: WeakReference<Guide> = WeakReference(guide)
                override fun handleMessage(msg: Message) {

                    mReference.get()?.let {
                        it.next()
                    }
                }
            }
        }

    }


}

