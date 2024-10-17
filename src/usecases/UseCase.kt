package usecases

import kotlinx.coroutines.runBlocking

interface UseCase<T> {
    suspend fun invoke(): T
    fun invoke(blocking: Any): T = runBlocking {
        invoke()
    }
}