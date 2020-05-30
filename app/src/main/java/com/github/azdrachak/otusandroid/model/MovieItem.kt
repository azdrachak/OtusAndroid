package com.github.azdrachak.otusandroid.model

import android.os.Parcel
import android.os.Parcelable
import com.github.azdrachak.otusandroid.R

data class MovieItem(
    val id: String?,
    val title: String?,
    val description: String?,
    val poster: Int = R.drawable.ic_image_black_24dp,
    val posterPath: String?,
    var isFavorite: Boolean,
    var isVisited: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(poster)
        parcel.writeString(posterPath)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeByte(if (isVisited) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieItem> {
        override fun createFromParcel(parcel: Parcel): MovieItem {
            return MovieItem(parcel)
        }

        override fun newArray(size: Int): Array<MovieItem?> {
            return arrayOfNulls(size)
        }
    }

}
