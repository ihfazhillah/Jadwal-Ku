package com.ihfazh.jadwal_ku.event

import com.ihfazh.jadwal_ku.common.Constants
import com.ihfazh.jadwal_ku.networking.schemas.EventSchema

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
            EventLink.YoutubeLink("https://www.youtube.com/watch?v=0SryhULjcjI")
        )

        fun provideEventList() = arrayListOf(
            EventListItem("1", "https://scontent-cgk1-2.cdninstagram.com/v/t51.2885-15/337336357_893285668618864_8083752984114041139_n.webp?stp=dst-jpg_e35_p1080x1080&_nc_ht=scontent-cgk1-2.cdninstagram.com&_nc_cat=104&_nc_ohc=ZRsHKWmqfkAAX-YynK2&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MzA2MzMwOTAyNDg5NzA0ODczOQ%3D%3D.2-ccb7-5&oh=00_AfDe72oQTCBXFiR0iLrXZGBiM5spIOYXgvN-dViwON3f5w&oe=64239F7D&_nc_sid=30a2ef", "Ramadhan yang Menyenangkan", "Rabu, 22 Maret 2023", "16:00 WIB"),
            EventListItem("2", "https://scontent-cgk1-2.cdninstagram.com/v/t51.2885-15/337336357_893285668618864_8083752984114041139_n.webp?stp=dst-jpg_e35_p1080x1080&_nc_ht=scontent-cgk1-2.cdninstagram.com&_nc_cat=104&_nc_ohc=ZRsHKWmqfkAAX-YynK2&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MzA2MzMwOTAyNDg5NzA0ODczOQ%3D%3D.2-ccb7-5&oh=00_AfDe72oQTCBXFiR0iLrXZGBiM5spIOYXgvN-dViwON3f5w&oe=64239F7D&_nc_sid=30a2ef", "Ramadhan yang Menyenangkan", "Rabu, 22 Maret 2023", "16:00 WIB"),
        )

        fun provideEventSchemaWithoutLink(): EventSchema {
            return EventSchema(
                "1",
                "Ramadhanku yang berkesan",
                "/media/hello-world.png",
                "Kajian Anak Jabodetabek",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
            )
        }

        fun provideEventWithoutLink(): Event {
            return Event(
                1,
                "${Constants.FULL_DOMAIN}/media/hello-world.png",
                "Kajian Anak Jabodetabek",
                "Ramadhanku yang berkesan",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
                EventLink.EmptyLink
            )
        }

        fun provideEventSchemaWithYoutubeLink(): EventSchema {
            return EventSchema(
                "1",
                "Ramadhanku yang berkesan",
                "https://avatars.githubusercontent.com/u/13466064?s=400&u=7fad815cb2c08a68bffc67d436ffc73b375cc946&v=4",
                "Kajian Anak Jabodetabek",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
                "https://www.youtube.com/watch?v=0SryhULjcjI"
            )
        }

        fun provideEventWithZoomLink(): Event {

             return Event(
                1,
                "https://avatars.githubusercontent.com/u/13466064?s=400&u=7fad815cb2c08a68bffc67d436ffc73b375cc946&v=4",
                "Kajian Anak Jabodetabek",
                "Ramadhanku yang berkesan",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
                EventLink.ZoomLink("https://www.youtube.com/watch?v=0SryhULjcjI")
            )
        }

        fun provideEventSchemaWithZoomLink(): EventSchema {
            return EventSchema(
                "1",
                "Ramadhanku yang berkesan",
                "https://avatars.githubusercontent.com/u/13466064?s=400&u=7fad815cb2c08a68bffc67d436ffc73b375cc946&v=4",
                "Kajian Anak Jabodetabek",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
                null,
                "https://www.youtube.com/watch?v=0SryhULjcjI"
            )

        }

        fun provideEventSchemaWithEmptyLinks(): EventSchema {
            return EventSchema(
                "1",
                "Ramadhanku yang berkesan",
                "/media/hello-world.png",
                "Kajian Anak Jabodetabek",
                "Erlan Iskandar S.T",
                "Jumat, 17 Maret 2023",
                "14:00 WIB",
                "",
                ""
            )
        }
    }
}