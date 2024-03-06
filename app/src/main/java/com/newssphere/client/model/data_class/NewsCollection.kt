package com.newssphere.client.model.data_class

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Field


@Parcelize
class NewsCollection(
    @field:SerializedName("status")
    var status: String?= "",

    @field:SerializedName("totalResult")
    var totalResult: Int = 0,

    @field:SerializedName("articles")
    var articles: List<Article>?= null
): Parcelable