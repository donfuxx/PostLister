package com.appham.postlister

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appham.postlister.model.Repository
import com.appham.postlister.viewmodel.DetailsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class DetailsViewModelTest {

    private val viewModelSpy: DetailsViewModel = spy()

    private val repositorySpy: Repository = spy()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    init {
        viewModelSpy.repository = repositorySpy
    }

    /**
    * Test that loadPostDetails() invokes the repository's user() and commentsCount() ONCE
    */
    @Test
    fun testLoadPostDetailsCallsRepositoryUserOnce() {
        viewModelSpy.loadPostDetails()
        verify(repositorySpy, times(1)).user(any(), any(), any())
    }

    /**
     * Test that loadPostDetails() invokes the repository's user() and commentsCount() ONCE
     */
    @Test
    fun testLoadPostDetailsCallsRepositoryCommentsCountOnce() {
        viewModelSpy.loadPostDetails()
        verify(repositorySpy, times(1)).commentsCount(any(), any(), any())
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
