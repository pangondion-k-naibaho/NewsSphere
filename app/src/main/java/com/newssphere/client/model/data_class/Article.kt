package com.newssphere.client.model.data_class

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    @field:SerializedName("field")
    var source: SourceArticle?= null,

    @field:SerializedName("author")
    var author: String?= "",

    @field:SerializedName("title")
    var title: String?= "",

    @field:SerializedName("description")
    var description: String?= "",

    @field:SerializedName("url")
    var url: String?= null,

    @field:SerializedName("urlToImage")
    var urlToImage: String?= null,

    @field:SerializedName("publishedAt")
    var publishedAt: String?= "",

    @field:SerializedName("content")
    var content: String?= ""
): Parcelable