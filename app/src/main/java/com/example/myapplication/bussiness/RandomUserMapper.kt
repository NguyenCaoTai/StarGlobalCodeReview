package com.example.myapplication.bussiness

import com.example.myapplication.data.local.model.Location
import com.example.myapplication.data.local.model.Name
import com.example.myapplication.data.local.model.User
import org.json.JSONObject
import java.lang.Exception
import java.lang.IllegalArgumentException

class RandomUserMapper : Mapper<String, User> {
    override fun transform(input: String): User = try {
        JSONObject(input)
            .takeIf { it.has("results") }?.getJSONArray("results")
            ?.takeIf { it.length() > 0 }?.getJSONObject(0)
            ?.takeIf { it.has("user") }?.getJSONObject("user")
            ?.let {
                User(
                    ssn = it.getString("SSN"),
                    name = it.getJSONObject("name")
                        .let { name ->
                            Name(
                                title = name.getString("title"),
                                first = name.getString("first"),
                                last = name.getString("last")
                            )
                        },
                    location = it.getJSONObject("location")
                        .let { location ->
                            Location(
                                street = location.getString("street"),
                                city = location.getString("city"),
                                state = location.getString("state"),
                                zip = location.getString("zip")
                            )
                        },
                    dateOfBirth = it.getString("dob").toLong(),
                    phone = it.getString("phone"),
                    picture = it.getString("picture").replace("http://", "https://")
                )
            } ?: throw IllegalArgumentException("Illegal")
    } catch (ex: Exception) {
        throw IllegalArgumentException("Illegal")
    }
}