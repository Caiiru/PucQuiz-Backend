package com.graspfy.graspit.security

import com.graspfy.graspit.User.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Component
class JWT {

    fun createToken(user:User):String=
        UserToken(user).let {
            Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET.toByteArray()))
                .json(JacksonSerializer())
                .issuedAt(utcNow().toDate())//DATA DE CRiAÇÃO
                .expiration(utcNow().plusHours(if
                        (it.isAdmin) ADMIN_EXPIRE_HOURS else EXPIRE_HOURS).toDate())//TEMPO PRA EXPIRAR
                .issuer(ISSUER) // SERVIDOR DO TOKEN
                .subject(user.id.toString()) // DONO DO TOKEN
                .claim(USER_FIELD, it)
                .compact()
        }

    fun extract(req:HttpServletRequest):Authentication?{
        try{
            val header = req.getHeader(AUTHORIZATION)

            if(header==null || !header.startsWith("Bearer")) return null

            val token = header.replace("Bearer","").trim()

            val claims = Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.toByteArray()))
                .json(JacksonDeserializer(
                    mapOf(USER_FIELD to UserToken::class.java)

                ))
                .build()
                .parseSignedClaims(token)
                .payload

            if(claims.issuer != ISSUER) return null
            return claims.get("user",UserToken::class.java).toAuthentication()

        }catch(e:Throwable){
            log.debug("Token Rejected",e)
            return null
        }
    }

     companion object{
         const val SECRET = "036ebbb5c45d7a48c45735a00e1db49b7f0be5c6"
         const val EXPIRE_HOURS = 4L
         const val ADMIN_EXPIRE_HOURS = 1L
         const val ISSUER = "GraspAuthServer"
         const val USER_FIELD = "user"

         private fun utcNow() = ZonedDateTime.now(ZoneOffset.UTC)
         private fun ZonedDateTime.toDate(): Date =
             Date.from(this.toInstant())

         val log = LoggerFactory.getLogger(JWT::class.java)

         private fun UserToken.toAuthentication():Authentication{
             val authorities = roles.map { SimpleGrantedAuthority("ROLE_$it") }
             return UsernamePasswordAuthenticationToken.authenticated(this, id, authorities)
         }
     }
}