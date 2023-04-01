package com.ihfazh.jadwal_ku.common

class MediaUrlHelper {
    fun prefixWithDomain(mediaUrl: String): String{
        if (mediaUrl.startsWith("https")) return mediaUrl
        return "${Constants.FULL_DOMAIN}${mediaUrl}"
    }
}