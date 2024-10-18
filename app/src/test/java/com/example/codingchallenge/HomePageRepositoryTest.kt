package com.example.codingchallenge

import com.example.codingchallenge.data.ApiService
import com.example.codingchallenge.data.local.LocalDataSource
import com.example.codingchallenge.data.model.HomePageModel
import com.example.codingchallenge.data.repository.HomePageRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomePageRepositoryTest {

    // TODO - add test case for both local data source fail and API fail
    // TODO - add UI tests and/or view model tests

    private lateinit var localDataSource: LocalDataSource
    private lateinit var apiService: ApiService
    private lateinit var homePageRepository: HomePageRepository
    private lateinit var jsonResponse : String

    private fun loadJson(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        return inputStream?.bufferedReader().use { it?.readText() } ?: ""
    }

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        apiService = mockk(relaxed = true)
        homePageRepository = HomePageRepository(localDataSource)
        jsonResponse = loadJson("response.json")
    }

    @Test
    fun `getData returns data from API and saves to local data source`() = runBlocking {
        val expectedData = jsonResponse
        val response = mockk<Response<HomePageModel>>(relaxed = true)
        every { response.isSuccessful } returns true
        every { response.body().toString() } returns expectedData
        coEvery { apiService.getHomeData() } returns response

        val result = homePageRepository.getData()
        assertEquals(Result.success(expectedData), result)
    }

    @Test
    fun `getData returns data from local data source when API call fails`() = runBlocking {
        val localData = jsonResponse
        coEvery { localDataSource.getHomeData().toString() } returns localData
        val response = mockk<Response<HomePageModel>>(relaxed = true)
        every { response.isSuccessful } returns false
        coEvery { apiService.getHomeData() } returns response

        val result = homePageRepository.getData()
        assertEquals(Result.success(localData), result)
    }
}