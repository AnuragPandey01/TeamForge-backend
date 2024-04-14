package com.example.teamforge.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JWTProvider(
    @Value("\${jwt.secret}")
    private val secret: String
) {

    private val algo = Algorithm.HMAC256(secret)

    fun generate(vararg claims: Pair<String, String>) = JWT.create().apply {
        for (claim in claims) {
            withClaim(claim.first, claim.second)
        }
    }.sign(algo)


    fun verify(token: String) = try {
        val verifier = JWT.require(algo).build()
        verifier.verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun decode(token: String) = JWT.decode(token.removeRange(0,7)).claims
        .map { it.key to it.value.asString() }
        .toMap()
}