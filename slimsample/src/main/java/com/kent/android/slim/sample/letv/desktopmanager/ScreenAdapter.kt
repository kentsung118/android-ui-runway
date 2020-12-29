package com.kent.android.slim.sample.letv.desktopmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import kotlinx.android.synthetic.main.item_desktop_screen_item.view.*
import java.util.*

/**
 * Created by songzhukai on 2020/12/29.
 */
class ScreenAdapter(val data: ArrayList<ScreenInfo>, val context: Context) : RecyclerView.Adapter<ScreenAdapter.ScreenInfoHolder>() {


    class ScreenInfoHolder(private val container: View) : RecyclerView.ViewHolder(container) {

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
        holder.bindData(data[position])
    }

}