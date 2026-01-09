package com.kabi.retrofitproject.weather.presentation.util

import com.kabi.retrofitproject.R
import com.kabi.retrofitproject.weather.domain.DataError
import com.kabi.retrofitproject.weather.domain.Result

fun Result.Error<*, DataError>.asUiText(): UiText {
    return error.asUiText()
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> {
            UiText.StringResource(
                R.string.error_network_request_timeout
            )
        }

        DataError.Network.TOO_MANY_REQUESTS -> {
            UiText.StringResource(
                R.string.error_network_too_many_requests
            )
        }

        DataError.Network.NO_INTERNET -> {
            UiText.StringResource(
                R.string.error_network_no_internet
            )
        }

        DataError.Network.PAYLOAD_TOO_LARGE -> {
            UiText.StringResource(
                R.string.error_network_payload_too_large
            )
        }

        DataError.Network.SERVER_ERROR -> {
            UiText.StringResource(
                R.string.error_network_server_error
            )
        }

        DataError.Network.SERIALIZATION -> {
            UiText.StringResource(
                R.string.error_network_serialization
            )
        }

        DataError.Network.UNAUTHORISED -> {
            UiText.StringResource(
                R.string.unauthorised
            )
        }

        DataError.Network.UNKNOWN -> {
            UiText.StringResource(
                R.string.error_network_unknown
            )
        }

        DataError.Local.DISK_FULL -> {
            UiText.StringResource(
                R.string.error_local_disk_full
            )
        }
    }
}
