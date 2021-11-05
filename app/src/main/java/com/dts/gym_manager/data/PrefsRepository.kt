package com.dts.gym_manager.data

import com.dts.gym_manager.model.Token

interface PrefsRepository {
    var token: Token?
    var isUserLoggedIn: Boolean
}
