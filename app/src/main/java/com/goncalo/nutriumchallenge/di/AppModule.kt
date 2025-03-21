package com.goncalo.nutriumchallenge.di

import androidx.room.Room
import com.goncalo.nutriumchallenge.common.BASE_URL
import com.goncalo.nutriumchallenge.common.DATABASE_NAME
import com.goncalo.nutriumchallenge.database.NutriumDatabase
import com.goncalo.nutriumchallenge.professionals.data.datastore.ProfessionalDataStore
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.repository.ProfessionalRepositoryImpl
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetLastFilterUsedUseCase
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetProfessionalByIdUseCase
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetProfessionalListUseCase
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel.ProfessionalDetailViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module {

    //Retrofit Instance
    single {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //DB Instance
    single {
        Room.databaseBuilder(androidContext(), NutriumDatabase::class.java, DATABASE_NAME)
            .build()
    }

    //Retrofit Interface API's
    single {
        (get() as Retrofit).create(ProfessionalApi::class.java)
    }

    //DataStore
    single { ProfessionalDataStore(androidContext()) }

    //DAO's
    single { get<NutriumDatabase>().professionalDao() }

    //Repositories
    single<ProfessionalRepository> { ProfessionalRepositoryImpl(get(), get(), get())}

    //Use Cases
    single<GetProfessionalByIdUseCase> { GetProfessionalByIdUseCase(get())}
    single<GetProfessionalListUseCase> { GetProfessionalListUseCase(get())}
    single<GetLastFilterUsedUseCase> { GetLastFilterUsedUseCase(get()) }

    //ViewModels
    single<ProfessionalListViewModel> { ProfessionalListViewModel(get(), get()) }
    single<ProfessionalDetailViewModel> { ProfessionalDetailViewModel(get())}


}