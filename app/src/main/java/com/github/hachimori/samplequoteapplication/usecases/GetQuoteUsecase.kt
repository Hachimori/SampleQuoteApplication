package com.github.hachimori.samplequoteapplication.usecases

import com.github.hachimori.samplequoteapplication.di.IoDispatcher
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.github.hachimori.samplequoteapplication.io.network.getBodyOrThrow
import com.github.hachimori.samplequoteapplication.repositories.SevenMindRepository
import com.github.hachimori.samplequoteapplication.utils.UID_CHARSET
import com.github.hachimori.samplequoteapplication.utils.UID_LENGTH
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetQuoteUsecase @Inject constructor(
    private val sevenMindRepository: SevenMindRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getQuote(lang: String): Flow<Quote> = flow {
        // Generate random string which consists of alpha-numerical characters
        val uid = (0 until UID_LENGTH)
            .map { UID_CHARSET.random() }
            .joinToString("")

        val quoteResponse = sevenMindRepository.getQuote(lang, uid).getBodyOrThrow()
        emit(quoteResponse.mapToQuote())
    }.flowOn(ioDispatcher)
}
