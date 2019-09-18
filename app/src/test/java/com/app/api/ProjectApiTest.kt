package com.app.api

import com.app.model.project.Owner
import com.app.model.project.Project
import com.app.model.project.ProjectApiResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.math.BigDecimal

@RunWith(JUnit4::class)
class ProjectApiTest {

    @Mock private var projectApi : ProjectApi? = null

    private lateinit var projectApiSuccess : ProjectApiResponse

    private lateinit var projectApiError : ProjectApiResponse

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        projectApiSuccess = ProjectApiResponse(
            214,
            mutableListOf(
                Project(5152285,"okhttp",false,
                    "An HTTP client for Android, Kotlin, and Java.","2019-08-31T14:26:06Z",
                    "Java",34107,"https://github.com/square/okhttp",
                    Owner("https://avatars0.githubusercontent.com/u/82592?v=4")),
                Project(892275,"retrofit",false,
                    "Type-safe HTTP client for Android and Java by Square, Inc.","2019-08-31T18:50:53Z",
                    "Java",33530,"https://github.com/square/retrofit",
                    Owner("https://avatars0.githubusercontent.com/u/82592?v=4")),
                Project(34824499,"leakcanary",false,
                    "LeakCanary is a memory leak detection library for Android.","2019-08-31T09:32:04Z",
                    "Kotlin",23494,"https://github.com/square/leakcanary",
                    Owner("https://avatars0.githubusercontent.com/u/82592?v=4"))
            )
        )

        projectApiError = ProjectApiResponse(0,mutableListOf(
            Project(0,"",false,"","","",0,"", Owner(""))
        ))
    }

    @Test
    fun `get Github Repo of Square and succeed`() {

        val response = Response.success(projectApiSuccess)

        runBlocking {
            Mockito.`when`(projectApi?.getProjectsAsync(anyString(), anyInt(), anyInt()))
                .thenReturn(async { response })
        }

        assertEquals(214, response.body()?.totalCount)
        assertEquals("An HTTP client for Android, Kotlin, and Java.", response.body()?.projects?.get(0)?.description)
        assertEquals("retrofit", response.body()?.projects?.get(1)?.name)
        assertEquals(BigDecimal(34824499), response.body()?.projects?.get(2)?.projectId?.let { BigDecimal(it) })
        assertTrue(response.body()?.projects?.get(0)?.language == "Java")
        assertTrue(response.body()?.projects?.get(2)?.private == false)
        assertTrue(response.body()?.projects?.get(0)?.updatedAt == "2019-08-31T14:26:06Z")
        assertFalse(response.body()?.projects?.get(1)?.owner?.avatar == "")
        assertFalse(response.body()?.projects?.size == 0)
    }

    @Test
    fun `get Github Repo of Square and fail`() {

        val response = Response.success(projectApiError)

        runBlocking {
            Mockito.`when`(projectApi?.getProjectsAsync(anyString(), anyInt(), anyInt()))
                .thenReturn(async { response })
        }

        assertEquals(0, response.body()?.totalCount)
        assertEquals("", response.body()?.projects?.get(0)?.owner?.avatar)
        assertTrue(response.body()?.projects?.get(0)?.url == "")
        assertTrue(response.body()?.projects?.get(0)?.name == "")
        assertFalse(response.body()?.projects?.get(0)?.description == "Type-safe HTTP client for Android and Java by Square, Inc.")
        assertFalse(response.body()?.projects?.get(0)?.language == "Java")
    }
}