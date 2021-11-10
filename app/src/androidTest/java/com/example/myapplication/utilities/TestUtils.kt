package com.example.myapplication.utilities

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.myapplication.data.local.model.Location
import com.example.myapplication.data.local.model.Name
import com.example.myapplication.data.local.model.User
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.security.AccessController.getContext
import java.util.stream.Collectors

object TestUtils {
    val userOriginal = User(
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

    /**
     * [User] objects used for tests.
     */
    val testUsers = arrayListOf(
        userOriginal,
        userOriginal.copy(
            ssn = "322-31-1111", name = Name(
                title = "mr",
                first = "ruger",
                last = "hardock"
            )
        ),
        userOriginal.copy(
            ssn = "322-31-2222", name = Name(
                title = "ms",
                first = "best",
                last = "beautiful"
            )
        )
    )

    val randomUserJsonString = "{\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"user\": {\n" +
            "        \"gender\": \"male\",\n" +
            "        \"name\": {\n" +
            "          \"title\": \"mr\",\n" +
            "          \"first\": \"enrique\",\n" +
            "          \"last\": \"romero\"\n" +
            "        },\n" +
            "        \"location\": {\n" +
            "          \"street\": \"3451 china ave\",\n" +
            "          \"city\": \"lancaster\",\n" +
            "          \"state\": \"california\",\n" +
            "          \"zip\": \"19040\"\n" +
            "        },\n" +
            "        \"email\": \"enrique.romero82@example.com\",\n" +
            "        \"username\": \"bigelephant132\",\n" +
            "        \"password\": \"hottest\",\n" +
            "        \"salt\": \"16VwMQ97\",\n" +
            "        \"md5\": \"e2ac4a325d8a91fd8c9c325d83368c6d\",\n" +
            "        \"sha1\": \"f3d8d0dcf6b534198d68e006f5bc9badb479648c\",\n" +
            "        \"sha256\": \"8ce681b80ed950a0cb121201aa64f5d25a43ad3c74890be3456325eb5f228d91\",\n" +
            "        \"registered\": \"1371063853\",\n" +
            "        \"dob\": \"200337769\",\n" +
            "        \"phone\": \"(818)-127-3982\",\n" +
            "        \"cell\": \"(586)-419-7464\",\n" +
            "        \"SSN\": \"322-31-7447\",\n" +
            "        \"picture\": \"http://api.randomuser.me/portraits/men/5.jpg\"\n" +
            "      },\n" +
            "      \"seed\": \"7066ca26932a8bba\",\n" +
            "      \"version\": \"0.4\"\n" +
            "    }\n" +
            "  ]\n" +
            "}"
}