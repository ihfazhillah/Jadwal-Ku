package com.ihfazh.jadwal_ku.event.usecases.upcoming

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.EventListItem
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.*
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.EventListItemSchema
import com.ihfazh.jadwal_ku.networking.schemas.UpcomingEventsSchema
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetUpcomingEventsUseCaseImplTest {
    lateinit var SUT : GetUpcomingEventsUseCaseImpl

    @Mock lateinit var remoteServiceMock: KsatriaMuslimService
    val mediaUrlHelper = MediaUrlHelper()


    @Before
    fun setUp() {
        SUT = GetUpcomingEventsUseCaseImpl(
            remoteServiceMock,
            mediaUrlHelper
        )
    }

    @Test
    fun `getUpcomingEvents http error returns general error`() = runTest {
        `when`(remoteServiceMock.getUpcomingEvents()).thenAnswer {
            throw HttpException(Mockito.mock())
        }

        val resp = SUT.getUpcomingEvents()
        assertEquals(resp, UpcomingEventsResponse.GeneralError)
    }

    @Test
    fun `getUpcomingEvents network error returns network error`() = runTest {
        `when`(remoteServiceMock.getUpcomingEvents()).thenAnswer {
            throw IOException()
        }

        val resp = SUT.getUpcomingEvents()
        assertEquals(resp, UpcomingEventsResponse.NetworkError)
    }

    @Test
    fun `getUpcomingEvents returns empty list event, return empty event`() = runTest {
        `when`(remoteServiceMock.getUpcomingEvents()).thenReturn(UpcomingEventsSchema(emptyList()))
        val resp = SUT.getUpcomingEvents()
        assertEquals(resp, UpcomingEventsResponse.EmptyEvent)
    }

    @Test
    fun `getUpcomingEvents returns list event, return success`() = runTest {
        `when`(remoteServiceMock.getUpcomingEvents()).thenReturn(UpcomingEventsSchema(
            listOf(EventListItemSchema("1", "title", "/media/png.png", "100", "100"))
        ))
        val resp = SUT.getUpcomingEvents()
        assertEquals(resp, UpcomingEventsResponse.Success(
            arrayListOf(
                EventListItem("1",  "https://cms.ksatriamuslim.com/media/png.png","title", "100", "100")
            )
        ))
    }

    @Test
    fun `getUpcomingEvents should pass correct argument`() = runTest {
        `when`(remoteServiceMock.getUpcomingEvents(1, 10)).thenReturn(UpcomingEventsSchema(
            listOf(EventListItemSchema("1", "title", "/media/png.png", "100", "100"))
        ))
        SUT.getUpcomingEvents(1, 10)
        verify(remoteServiceMock).getUpcomingEvents(1, 10)
    }
}