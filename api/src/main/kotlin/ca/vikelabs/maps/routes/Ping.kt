package ca.vikelabs.maps.routes

import org.http4k.contract.ContractRoute
import org.http4k.contract.meta
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

data class Success(val success: Boolean = true)

object Ping {
    val response = Body.auto<Success>().toLens()
}

fun ping(): ContractRoute {

    val spec = "/ping" meta {
        summary = "Returns 200 OK and a small json object describing the server status."
        description = "A handy endpoint for checking to see weather the server is alive and replying."
        produces += ContentType.APPLICATION_JSON
        returning(Status.OK, Ping.response to Success(), "a successful ping!")
    } bindContract Method.GET

    val ping: HttpHandler = { _ ->
        Response(Status.OK).with(Ping.response of Success())
    }

    return spec to ping
}
