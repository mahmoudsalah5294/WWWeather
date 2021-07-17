package com.mahmoudsalah.wwweather.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudsalah.wwweather.databinding.DailyTempBinding
import com.mahmoudsalah.wwweather.databinding.FavoriteCardLayoutBinding
import com.mahmoudsalah.wwweather.databinding.FragmentFavoriteBinding
import com.mahmoudsalah.wwweather.model.Favorite

class FavoriteAdapter(var data: List<Favorite> , var clickFavorite: ClickFavorite): RecyclerView.Adapter<FavoriteAdapter.VH>() {
    class VH(val myView: FavoriteCardLayoutBinding):RecyclerView.ViewHolder(myView.root)
//    lateinit var clickListener:ClickFavorite
//    fun onClick(clickFavorite: ClickFavorite){
//        clickListener = clickFavorite
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val favoriteCardBinding = FavoriteCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(favoriteCardBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.myView.cityName.text = data[position].name
        holder.myView.imageButton.setOnClickListener(View.OnClickListener { clickFavorite.deleteItem(data.get(position))})
        holder.itemView.setOnClickListener(View.OnClickListener { clickFavorite.clickItem(data.get(position)) })
    }

    override fun getItemCount() = data.size

}