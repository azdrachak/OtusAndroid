package com.github.azdrachak.otusandroid.model.pojo.images

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("images")
    @Expose
    var images: ImagesData? = null,

    @SerializedName("change_keys")
    @Expose
    var changeKeys: List<String>? = null
)
