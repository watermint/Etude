package etude.http

import java.net.URI
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.client.methods.HttpGet

/**
 *
 */
object Resource {
  def httpClient: CloseableHttpClient = HttpClients.createDefault()

  def get[T](uri: URI): Either[Exception, Response] = {
    val client = httpClient
    try {
      Right(Response(client.execute(new HttpGet(uri))))
    } catch {
      case e: Exception => Left(e)
    } finally {
      client.close()
    }
  }
}
