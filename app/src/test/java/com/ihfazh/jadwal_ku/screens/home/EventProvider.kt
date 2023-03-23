package com.ihfazh.jadwal_ku.screens.home

import com.ihfazh.jadwal_ku.event.Event

class EventProvider {
    companion object {
        fun provideEvent() = Event(
            1,
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