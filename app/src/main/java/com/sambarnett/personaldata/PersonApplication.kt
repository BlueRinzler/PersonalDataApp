package com.sambarnett.personaldata

import android.app.Application
import com.sambarnett.personaldata.data.PersonRoomDataBase


class PersonApplication: Application() {
    val database: PersonRoomDataBase by lazy { PersonRoomDataBase.getDatabase(this) }
}