package com.newssphere.client.model.data_class

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourceArticle(
    @field:SerializedName("id")
    var id: String?= "",

    @field:SerializedName("name")
    var name: String?= ""
): Parcelable