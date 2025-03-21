package com.goncalo.nutriumchallenge.di

import androidx.room.Room
import com.goncalo.nutriumchallenge.professionals.data.database.NutriumDatabase
import com.goncalo.nutriumchallenge.professionals.data.datastore.ProfessionalDataStore
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.repository.ProfessionalRepositoryImpl
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetProfessionalByIdUseCase
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel.ProfessionalDetailViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module {

    single {
        Retrofit.Builder().baseUrl("https://nutrisearch.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Room.databaseBuilder(androidContext(), NutriumDatabase::class.java, "NutriumDatabase")
            .build()
    }

    single {
        (get() as Retrofit).create(ProfessionalApi::class.java)
    }

    single { ProfessionalDataStore(androidContext()) }

    single { get<NutriumDatabase>().professionalDao() }

    single<ProfessionalRepository> { ProfessionalRepositoryImpl(get(), get(), get())}

    single<GetProfessionalByIdUseCase> { GetProfessionalByIdUseCase(get())}

    single<ProfessionalListViewModel> { ProfessionalListViewModel(get()) }

    single<ProfessionalDetailViewModel> { ProfessionalDetailViewModel(get())}


}