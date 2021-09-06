package com.dice.shared.lifecycle


/**
 * Describes state of the view at any
 * point of time.
 */
sealed class ResultState<out ResultType> {

    /**
     * Describes success state of the UI with
     * [data] shown
     */
    data class Success<out ResultType>(
        val data: ResultType
    ) : ResultState<ResultType>()

    /**
     * Describes loading state of the UI
     */
    class Loading : ResultState<Nothing>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    /**
     *  Describes error state of the UI
     */
    data class Error(
        val exception: Exception
    ) : ResultState<Nothing>()

    companion object {
        /**
         * Creates [ResultState] object with [Success] state and [data].
         */
        fun <ResultType> success(data: ResultType): ResultState<ResultType> = Success(data)

        /**
         * Creates [ResultState] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun loading(): ResultState<Nothing> = Loading()

        /**
         * Creates [ResultState] object with [Error] state and [message].
         */
        fun error(message: Exception): ResultState<Nothing> = Error(message)


        val <T> ResultState<T>.data: T?
            get() = (this as? Success)?.data
    }
}
