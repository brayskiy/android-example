package com.brayskiy.example.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by brayskiy on 03/08/19.
 */

data class Video(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("iso_639_1") @Expose val iso6391: String,
    @SerializedName("iso_3166_1") @Expose val iso31661: String,
    @SerializedName("key") @Expose val key: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("site") @Expose val site: String,
    @SerializedName("size") @Expose val size: Int,
    @SerializedName("type") @Expose val type: String
)

data class Videos(
    @SerializedName("results") @Expose val results: List<Video>
)



