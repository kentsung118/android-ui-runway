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

/**
 * Created by songzhukai on 2020/12/29.
 */
class ScreenAdapter(val data: LinkedList<ScreenInfo>, val context: Context) : RecyclerView.Adapter<ScreenAdapter.ScreenInfoHolder>() {

    private var mKeyListener: View.OnKeyListener? = null
    private var mFocusChangeListener: View.OnFocusChangeListener? = null

    class ScreenInfoHolder(val container: View) : RecyclerView.ViewHolder(container) {
        private val data: ScreenInfo? = null

        fun bindData(screen: ScreenInfo) {
            container.title.text = screen.packageName
            if (screen.locked) {
                container.ic_lock.visibility = View.VISIBLE
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
//        if (mDataSetChangedListener != null) {
//            mDataSetChangedListener.onItemMoved(from, to)
//        }
    }

    fun deleteItem(position: Int): ScreenInfo {
        notifyItemRemoved(position)
        val result: ScreenInfo = data.removeAt(position)
//        if (mDataSetChangedListener != null) {
//            mDataSetChangedListener.onItemDelete(position)
//        }
        return result
    }

    fun addItem(position: Int, s: ScreenInfo) {
        data.add(position, s)
        notifyItemInserted(position)
//        if (mDataSetChangedListener != null) {
//            mDataSetChangedListener.onItemInserted(position)
//        }
    }

}