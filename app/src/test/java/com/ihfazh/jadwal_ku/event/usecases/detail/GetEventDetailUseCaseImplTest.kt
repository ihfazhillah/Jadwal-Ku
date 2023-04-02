package com.ihfazh.jadwal_ku.event.usecases.detail

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.SingleEventSchema
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import net.bytebuddy.asm.Advice.Argument
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetEventDetailUseCaseImplTest{
    lateinit var SUT: GetEventDetailUseCaseImpl

    @Mock lateinit var serviceMock: KsatriaMuslimService
    @Captor lateinit var idCaptor: ArgumentCaptor<String>

    @Before
    fun setUp(){
        SUT = GetEventDetailUseCaseImpl(
            serviceMock,
            MediaUrlHelper()
        )
    }

    // test network error
    @Test
    fun `getDetail got network error returns network error`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenAnswer {
            throw IOException()
        }

        val resp = SUT.getDetail("1")
        assertEquals(resp, GetEventDetailUseCase.Result.NetworkError)
    }

    // test general error
    @Test
    fun `getDetail got any http errors returns general error`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenAnswer {
            throw HttpException(mock())
        }

        val resp = SUT.getDetail("1")
        assertEquals(resp, GetEventDetailUseCase.Result.GeneralError)
    }

    // empty event mark as general error
    @Test
    fun `getDetail got empty events returns general error`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenReturn(SingleEventSchema(null))

        val resp = SUT.getDetail("1")
        assertEquals(resp, GetEventDetailUseCase.Result.GeneralError)
    }

    // test success empty link
    @Test
    fun `getDetail got event with empty link`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenReturn(SingleEventSchema(EventProvider.provideEventSchemaWithoutLink()))

        val resp = SUT.getDetail("1") as GetEventDetailUseCase.Result.Success
        assertEquals(resp.event, EventProvider.provideEventWithoutLink())
    }

    // test success zoom link
    @Test
    fun `getDetail got event with zoom link`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenReturn(SingleEventSchema(EventProvider.provideEventSchemaWithZoomLink()))

        val resp = SUT.getDetail("1") as GetEventDetailUseCase.Result.Success
        assertEquals(resp.event, EventProvider.provideEventWithZoomLink())
    }
    // test success youtube link
    @Test
    fun `getDetail got event with youtube link`() = runTest {
        `when`(serviceMock.getDetail(anyString())).thenReturn(SingleEventSchema(EventProvider.provideEventSchemaWithYoutubeLink()))

        val resp = SUT.getDetail("1") as GetEventDetailUseCase.Result.Success
        assertEquals(resp.event, EventProvider.provideEvent())
    }

    @Test
    fun `getDetail should send parameter to remote service`() = runTest{
        `when`(serviceMock.getDetail("1")).thenReturn(SingleEventSchema(EventProvider.provideEventSchemaWithYoutubeLink()))

        val resp = SUT.getDetail("1") as GetEventDetailUseCase.Result.Success
        assertEquals(resp.event, EventProvider.provideEvent())
    }
}