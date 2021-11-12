package com.dts.gym_manager.data

import com.dts.gym_manager.model.Token
import com.dts.gym_manager.model.User

interface PrefsRepository {
    var user: User?
    var token: Token?
    var isUserLoggedIn: Boolean
}
