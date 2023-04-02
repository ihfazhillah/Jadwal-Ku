package com.ihfazh.jadwal_ku.event.usecases.current

import com.ihfazh.jadwal_ku.common.Constants
import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase.CurrentEventResponse.*
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.SingleEventSchema
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetCurrentEventUseCaseImplTest {

    lateinit var SUT: GetCurrentEventUseCaseImpl
    @Mock lateinit var ksmServiceMock: KsatriaMuslimService

    @Before
    fun setUp() {
        SUT = GetCurrentEventUseCaseImpl(
            ksmServiceMock,
            MediaUrlHelper()
        )
    }

    @Test
    fun `getCurrent remote returns null event should return empty event`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(event=null))
        val resp = SUT.getCurrent()
        assertEquals(resp, EmptyEvent)
    }

    @Test
    fun `getCurrent remote returns not null event with no link`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(event= EventProvider.provideEventSchemaWithoutLink()))
        val resp = SUT.getCurrent()
        assertEquals(resp, Success(EventProvider.provideEventWithoutLink()))
    }

    @Test
    fun `getCurrent remote returns not null event with no link not null but empty`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(event= EventProvider.provideEventSchemaWithEmptyLinks()))
        val resp = SUT.getCurrent()
        assertEquals(resp, Success(EventProvider.provideEventWithoutLink()))
    }

    @Test
    fun `getCurrent remote returns not null event with youtube link`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(event= EventProvider.provideEventSchemaWithYoutubeLink()))
        val resp = SUT.getCurrent()
        assertEquals(resp, Success(EventProvider.provideEvent()))
    }

    @Test
    fun `getCurrent remote returns not null event with zoom link`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(event= EventProvider.provideEventSchemaWithZoomLink()))
        val resp = SUT.getCurrent()
        assertEquals(resp, Success(EventProvider.provideEventWithZoomLink()))
    }

    @Test
    fun `getCurrent throw io exception returns network error response`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenAnswer{
            throw IOException()
        }
        val resp = SUT.getCurrent()
        assertEquals(resp, NetworkError)
    }

    @Test
    fun `getCurrent throw http exception returns generic error response`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenAnswer{
            throw HttpException(Mockito.mock())
        }
        val resp = SUT.getCurrent()
        assertEquals(resp, GeneralError)
    }

    @Test
    fun `getCurrent thumbnailUrl should has https and domain`() = runTest{
        `when`(ksmServiceMock.getCurrentEvent()).thenReturn(SingleEventSchema(EventProvider.provideEventSchemaWithoutLink()))
        val resp: Success = SUT.getCurrent() as Success
        assertEquals(resp.event.thumbnailUrl, "${Constants.FULL_DOMAIN}/media/hello-world.png")
    }
}