package com.goncalo.nutriumchallenge.professionals.domain.use_case

import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.repository.FakeProfessionalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetProfessionalByIdUseCaseTest {
    private lateinit var fakeRepository: ProfessionalRepository

    @Before
    fun setUp() {
        fakeRepository = FakeProfessionalRepository()
    }

    @Test
    fun checkItemUniqueIdMatches() = runTest {
        val professional = fakeRepository.getProfessionalDetail("2")

        assertTrue(professional.isSuccess)
        assertNotNull(professional.content)
        assertEquals(2, professional.content?.id)
        assertEquals("John Brown", professional.content?.name)
    }

    @Test
    fun checkItemUniqueIdNotMatches() = runTest {
        val professional = fakeRepository.getProfessionalDetail("3")

        assertFalse(professional.isSuccess)
        assertNull(professional.content)
        assertEquals("No Professional", professional.errorMessage)
    }

}