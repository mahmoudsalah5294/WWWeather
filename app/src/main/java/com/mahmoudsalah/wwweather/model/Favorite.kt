package com.mahmoudsalah.wwweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "favorite_table")
data class Favorite(val name:String,
                    val latitude:Double,
                    val longitude:Double,
                    @PrimaryKey(autoGenerate = true)
                    var id:Int  = 0){
     override fun toString(): String {
        return "$name:$latitude:$longitude"
    }
}
