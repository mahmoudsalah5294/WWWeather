package com.mahmoudsalah.wwweather

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudsalah.wwweather.databinding.HourlyTempBinding
import com.mahmoudsalah.wwweather.model.Hourly
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class HourlyAdapter(var data: List<Hourly>):RecyclerView.Adapter<HourlyAdapter.VH>() {


    class VH(val myView: HourlyTempBinding):RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding = HourlyTempBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return VH(viewBinding);
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val convertUnix = convertUnix(data[position].dt.toLong())
        holder.myView.timeTxt.text = convertUnix
        val icon:String = data[position].weather[0].icon
        val iconUrl = "http://openweathermap.org/img/w/$icon.png"
        Picasso.get().load(iconUrl).into(holder.myView.statusHourlyImage)
        holder.myView.tempTxt.text = data[position].temp.toInt().toString()

    }

    override fun getItemCount() = data.size

}


fun convertUnix(unix: Long): String {
    val date = Date(unix * 1000)
    val format = SimpleDateFormat("hh a")
    format.setTimeZone(TimeZone.getTimeZone("GMT+02:00"))
    return format.format(date)
}
