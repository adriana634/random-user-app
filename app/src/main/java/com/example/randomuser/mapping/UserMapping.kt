package com.example.randomuser.mapping

import com.example.randomuser.model.Location
import com.example.randomuser.model.User
import com.example.randomuser.service.RandomUser

/**
 * Maps a [RandomUser] to a User.
 *
 * @return The mapped [User].
 */
fun RandomUser.mapToUser(): User {
    val name = "${this.name.title} ${this.name.first} ${this.name.last}"
    val location = Location(this.location.coordinates.latitude, this.location.coordinates.longitude)

    return User(
        name,
        this.email,
        this.gender,
        this.picture.thumbnail,
        this.picture.medium,
        this.registered.date,
        this.cell,
        location
    )
}
