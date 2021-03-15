package com.basim.bitcoinstats

import com.basim.bitcoinstats.data.model.BaseResponse
import com.basim.bitcoinstats.utils.Resource

/**
 * Utility class which contains all utility methods
 */
fun getTestResponse(): Resource<BaseResponse> {
    return Resource.success(BaseResponse("","","","","", listOf()))
}