package com.ihfazh.jadwal_ku.common

import javax.inject.Inject

class MediaUrlHelper @Inject constructor() {
    fun prefixWithDomain(mediaUrl: String): String{
        if (mediaUrl.startsWith("https")) return mediaUrl
        return "${Constants.FULL_DOMAIN}${mediaUrl}"
    }
}