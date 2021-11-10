package com.example.myapplication.bussiness

import com.example.myapplication.data.local.model.Location
import com.example.myapplication.data.local.model.Name
import com.example.myapplication.data.local.model.User
import com.example.myapplication.utilities.TextUtils
import org.hamcrest.core.Is
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


class RandomUserMapperTest {

    private lateinit var randomMapper: Mapper<String, User>

    @get:Rule
    var thrown = ExpectedException.none()

    @Before
    fun setUp() {
        randomMapper = RandomUserMapper()
    }

    @Test
    fun transform_RandomUser_from_Json_string() {
        val expect = User(
            name = Name(
                title = "mr",
                first = "enrique",
                last = "romero"
            ),
            location = Location(
                street = "3451 china ave",
                city = "lancaster",
                state = "california",
                zip = "19040"
            ),
            ssn = "322-31-7447",
            dateOfBirth = 200337769,
            phone = "(818)-127-3982",
            picture = "https://api.randomuser.me/portraits/men/5.jpg"
        )

        val input: String = TextUtils.getTextFromFile("RandomUser.json")
        val result: User = randomMapper.transform(input)

        assertThat(result, Is.`is`(expect))
    }

    @Test
    fun throw_illegal_argument_exception_when_json_string_format_wrong() {
        val expectException = IllegalArgumentException::class.java
        val expectMessage = "Illegal"

        thrown.expect(expectException)
        thrown.expectMessage(expectMessage)

        val input = ""
        randomMapper.transform(input)
    }

    @Test
    fun throw_illegal_argument_exception_when_json_string_not_have_json_key_corresponding() {
        val expectException = IllegalArgumentException::class.java
        val expectMessage = "Illegal"

        thrown.expect(expectException)
        thrown.expectMessage(expectMessage)

        val input: String = TextUtils.getTextFromFile("RandomUser.json")
            .replace("user", "user_not_expect")

        randomMapper.transform(input)
    }
}