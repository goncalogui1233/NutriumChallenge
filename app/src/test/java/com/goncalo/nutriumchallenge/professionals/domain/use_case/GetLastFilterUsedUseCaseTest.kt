package com.goncalo.nutriumchallenge.professionals.domain.use_case

import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.repository.FakeProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSortType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetLastFilterUsedUseCaseTest {

    private lateinit var fakeProfessionalRepository: ProfessionalRepository

    @Before
    fun setUp() {
        fakeProfessionalRepository = FakeProfessionalRepository()
    }

    @Test
    fun IsSortTypeBestMatch() = runTest {
        val sortType = fakeProfessionalRepository.getLastFilterUsed()
        assertEquals(ProfessionalSortType.BEST_MATCH.typeName, sortType)
    }

}