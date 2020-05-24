package com.github.azdrachak.otusandroid.pojo.images

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImagesData(
    @SerializedName("base_url")
    @Expose
    var baseUrl: String? = null,

    @SerializedName("secure_base_url")
    @Expose
    var secureBaseUrl: String? = null,

    @SerializedName("backdrop_sizes")
    @Expose
    var backdropSizes: List<String>? = null,

    @SerializedName("logo_sizes")
    @Expose
    var logoSizes: List<String>? = null,

    @SerializedName("poster_sizes")
    @Expose
    var posterSizes: List<String>? = null,

    @SerializedName("profile_sizes")
    @Expose
    var profileSizes: List<String>? = null,

    @SerializedName("still_sizes")
    @Expose
    var stillSizes: List<String>? = null
)