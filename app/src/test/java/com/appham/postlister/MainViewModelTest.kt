package com.appham.postlister

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appham.postlister.model.Repository
import com.appham.postlister.viewmodel.MainViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainViewModelTest {

    private val viewModelSpy: MainViewModel = spy()

    private val repositorySpy: Repository = spy()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    init {
        viewModelSpy.repository = repositorySpy
    }

    /**
    * Test that getPosts() invokes the repository's posts() ONCE
    */
    @Test
    fun testGetPostsCallsRepositoryOnce() {
        viewModelSpy.getPosts()
        verify(repositorySpy, times(1)).posts(any(), any())
    }

    /**
     * Test that onCleared() invokes the repository's dispose() ONCE
     */
    @Test
    fun testOnClearedCallsRepositoryDisposeOnce() {
        invokeProtectedMethod(viewModelSpy, "onCleared")
        verify(repositorySpy, times(1)).dispose()
    }

}
