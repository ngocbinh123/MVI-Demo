package com.nnbinh.mvidemo.data.tables

import com.nnbinh.mvidemo.data.Gender

class UserProfile(
    var id: String = "",
    var email: String = "",
    var password: String = "",
    var fullName: String = "",
    var birthday: String = "",
    var phoneNumber: String = "",
    var gender: String = Gender.Male.name,
    var address: String = "",
    var createdAt: String = "",
    var updatedAt: String = ""
) {
}