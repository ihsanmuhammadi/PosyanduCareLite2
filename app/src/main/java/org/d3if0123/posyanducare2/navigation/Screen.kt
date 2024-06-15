package org.d3if0123.posyanducare2.navigation

import org.d3if0123.posyanducare2.ui.screen.KEY_ID_ANAK

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Child: Screen("childScreen")
    data object Calculate: Screen("calculateScreen")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_ANAK}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}