package com.mahmoudsalah.wwweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudsalah.wwweather.databinding.DailyTempBinding
import com.mahmoudsalah.wwweather.model.Daily
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(var data: List<Daily>):RecyclerView.Adapter<DailyAdapter.VH>() {
    class VH(val myView: DailyTempBinding):RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding = DailyTempBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(viewBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val date = convertUnixday(data[position].dt.toLong())
        holder.myView.dayTxt.text = date
        holder.myView.maxTempTxt.text = data[position].temp.max.toInt().toString()+":"
        holder.myView.minTempTxt.text = data[position].temp.min.toInt().toString()

        val icon:String = data[position].weather[0].icon
        val iconUrl = "http://openweathermap.org/img/w/$icon.png"
        Picasso.get().load(iconUrl).into(holder.myView.statusDailyImage)

    }

    override fun getItemCount() = data.size
}

fun convertUnixday(unix: Long): String {
    val date = Date(unix * 1000)
    val format = SimpleDateFormat("EEEE")
    format.setTimeZone(TimeZone.getTimeZone("GMT"))
    return format.format(date)
}