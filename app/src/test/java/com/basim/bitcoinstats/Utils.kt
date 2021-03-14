package com.basim.bitcoinstats

import com.basim.bitcoinstats.data.model.BaseResponse
import com.basim.bitcoinstats.utils.Resource

fun getTestResponse(): Resource<BaseResponse> {
    return Resource.success(BaseResponse("","","","","", listOf()))
}