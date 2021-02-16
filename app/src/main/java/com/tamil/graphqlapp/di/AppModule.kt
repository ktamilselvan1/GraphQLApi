package com.tamil.graphqlapp.di

import com.apollographql.apollo.ApolloClient
import com.tamil.graphqlapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApoloRetrofit(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.BASE_URL)
            .build()
    }
}
