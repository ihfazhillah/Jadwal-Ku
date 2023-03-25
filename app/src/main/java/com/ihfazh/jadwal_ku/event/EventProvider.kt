package com.ihfazh.jadwal_ku.event

import com.ihfazh.jadwal_ku.event.Event

class EventProvider {
    companion object {
        fun provideEvent() = Event(
            1,
            "https://avatars.githubusercontent.com/u/13466064?s=400&u=7fad815cb2c08a68bffc67d436ffc73b375cc946&v=4",
            "Kajian Anak Jabodetabek",
            "Ramadhanku yang berkesan",
            "Erlan Iskandar S.T",
            "Jumat, 17 Maret 2023",
            "14:00 WIB",
            null,
            null
        )
    }
}