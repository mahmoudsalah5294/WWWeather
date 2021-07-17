package com.mahmoudsalah.wwweather.ui.alerts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudsalah.wwweather.databinding.AlertsCardLayoutBinding
import com.mahmoudsalah.wwweather.databinding.FavoriteCardLayoutBinding
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.model.Favorite
import com.mahmoudsalah.wwweather.ui.favorite.ClickFavorite
import com.mahmoudsalah.wwweather.ui.favorite.FavoriteAdapter

class AlertsAdapter(var data: List<Alert>,val clickAlert: ClickAlert): RecyclerView.Adapter<AlertsAdapter.VH>() {
    class VH(val myView: AlertsCardLayoutBinding): RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsAdapter.VH {
        val alertsCardLayoutBinding = AlertsCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(alertsCardLayoutBinding)
    }



    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.myView.alertTime.text = "${data[position].fromTime} - ${data[position].toTime}"
        holder.myView.alertDate.text = data[position].toDate

        holder.myView.deleteImageButton.setOnClickListener { clickAlert.deleteItem(data[position]) }
        holder.myView.editImageButton.setOnClickListener { clickAlert.editItem(data[position]) }

    }


    @JvmName("setData1")
    fun setData(newData: List<Alert>){
        data = newData
    }
}