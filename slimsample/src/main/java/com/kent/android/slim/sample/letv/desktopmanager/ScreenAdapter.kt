package com.kent.android.slim.sample.letv.desktopmanager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import kotlinx.android.synthetic.main.item_desktop_screen_item.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by songzhukai on 2020/12/29.
 */
class ScreenAdapter(val data: ArrayList<ScreenInfo>, val context: Context) : RecyclerView.Adapter<ScreenAdapter.ScreenInfoHolder>() {

    private var mKeyListener: View.OnKeyListener? = null
    private var mFocusChangeListener: View.OnFocusChangeListener? = null

    class ScreenInfoHolder(val container: View) : RecyclerView.ViewHolder(container) {
        private val data: ScreenInfo? = null

        fun bindData(screen: ScreenInfo) {
            container.title.text = screen.packageName.split(".")[3]
            if (screen.locked) {
                container.ic_lock.visibility = View.VISIBLE
                container.isFocusable = false
            } else {
                container.ic_lock.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenInfoHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_desktop_screen_item, parent, false)
        return ScreenInfoHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ScreenInfoHolder, position: Int) {
        holder.container.setOnKeyListener(mKeyListener)
        holder.container.setOnFocusChangeListener(mFocusChangeListener)
        holder.bindData(data[position])
    }

    fun getDataList(): List<ScreenInfo> {
        return data
    }

    fun setKeyListener(listener: View.OnKeyListener) {
        mKeyListener = listener
    }

    fun setFocusChangeListener(listener: View.OnFocusChangeListener) {
        mFocusChangeListener = listener
    }

    fun moveItem(from: Int, to: Int) {
        val s: ScreenInfo = data.removeAt(from)
        data.add(to, s)
        notifyItemMoved(from, to)

//        Collections.swap(data, from, to + 1);
//          editUpSort(data, 12, 5)
//        notifyItemMoved(from, to + 1)
//        notifyItemRangeRemoved(from , 2)
//        notifyItemRangeInserted(from ,2)
//        notifyItemMoved(from, to + 3);
//        notifyItemMoved(from + 2, to + 2);
//        notifyItemMoved(from + 1, to+ 1);
//        notifyItemMoved(from, to);


    }

    fun deleteItem(position: Int): ScreenInfo {
        notifyItemRemoved(position)
        val result: ScreenInfo = data.removeAt(position)
        return result
    }

    fun addItem(position: Int, s: ScreenInfo) {
        data.add(position, s)
        notifyItemInserted(position)
    }


    fun editUpSort(itemList: ArrayList<ScreenInfo>, from: Int, to: Int) {

        println("排序前：$itemList")
        val removedItem = itemList.removeAt(from)
        itemList.add(to, removedItem)
        println("排序(1)：$itemList")
        notifyItemMoved(from, to)


        var beginPoint = to
        var endPoint = from
        var moveNum = 0

        while (beginPoint < endPoint) {

            var to: Int? = null
            for ((index, item) in itemList.withIndex()) {
                //最后一笔，边界
                if (index == endPoint)
                    beginPoint = index

                //若慢指针已检查过，忽略
                if (index < beginPoint)
                    continue

                //仅有lock需处理，其他忽略
                if (item.locked && to != null) {
                    val lockItem = itemList.removeAt(index)
                    itemList.add(to, lockItem)
                    //处理move动画 move(to, index)
                    notifyItemMoved(index, to)

                    println("moveItem from:$index ,to:$to")

                    moveNum ++
                    beginPoint = index
                    break
                } else {
                    to = index
                    continue
                }
            }
        }

        println("排序(2)：$itemList")
        println("moveItem 次数：$moveNum")
    }

}