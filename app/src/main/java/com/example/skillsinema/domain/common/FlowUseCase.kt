package com.example.skillsinema.domain.common

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in P, R> {
    abstract operator fun invoke(parameters: P): Flow<R>
}