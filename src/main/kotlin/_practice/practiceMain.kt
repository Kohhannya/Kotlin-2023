package _practice

//import io.ktor.http.*
//import io.ktor.serialization.kotlinx.json.*
//import io.ktor.server.application.*
//import io.ktor.server.engine.*
//import io.ktor.server.netty.*
//import io.ktor.server.plugins.contentnegotiation.*
//import io.ktor.server.plugins.statuspages.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*
//import kotlinx.serialization.json.Json
//import _practice.api.commentsApi
//import _practice.commentsModule
//import org.koin.ktor.plugin.Koin
//
////TIP Press <shortcut raw="SHIFT"/> twice to open the Search Everywhere dialog and type <b>show whitespaces</b>,
//// then press <shortcut raw="ENTER"/>. You can now see whitespace characters in your code.
//fun main() {
//
//    embeddedServer(Netty, port = 8000) {
//        routing {
//            get ("/") {
//                call.respondText("Hello, world!")
//            }
//        }
//        configureServer()
//        commentsApi()
//    }.start(wait = true)
//
//}
//
//fun Application.configureServer() {
//    install(StatusPages) {
//        exception<Throwable> { call, cause ->
//            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
//        }
//        status(HttpStatusCode.NotFound) { call, status ->
//            call.respondText(text = "404: Page Not Found", status = status)
//        }
//    }
//    install(Koin) {
////        slf4jLogger()
//        modules(commentsModule)
//    }
//    install(ContentNegotiation) {
//        json(Json {
//            prettyPrint = true
//            isLenient = true
//        })
//    }
//}
