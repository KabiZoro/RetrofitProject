package com.kabi.retrofitproject.presentation.util

import com.kabi.retrofitproject.R
import com.kabi.retrofitproject.domain.DataError
import com.kabi.retrofitproject.domain.Result
import com.kabi.retrofitproject.presentation.util.UiText.*

fun Result.Error<*, DataError>.asUiText(): UiText {
    return error.asUiText()
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> {
            StringResource(
                R.string.error_network_request_timeout
            )
        }

        DataError.Network.TOO_MANY_REQUESTS -> {
            StringResource(
                R.string.error_network_too_many_requests
            )
        }

        DataError.Network.NO_INTERNET -> {
            StringResource(
                R.string.error_network_no_internet
            )
        }

        DataError.Network.PAYLOAD_TOO_LARGE -> {
            StringResource(
                R.string.error_network_payload_too_large
            )
        }

        DataError.Network.SERVER_ERROR -> {
            StringResource(
                R.string.error_network_server_error
            )
        }

        DataError.Network.SERIALIZATION -> {
            StringResource(
                R.string.error_network_serialization
            )
        }

        DataError.Network.UNAUTHORISED -> {
            StringResource(
                R.string.unauthorised
            )
        }

        DataError.Network.UNKNOWN -> {
            StringResource(
                R.string.error_network_unknown
            )
        }

        DataError.Local.DISK_FULL -> {
            StringResource(
                R.string.error_local_disk_full
            )
        }
    }
}
