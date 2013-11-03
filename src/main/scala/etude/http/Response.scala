package etude.http

import java.io.InputStream
import org.apache.http.{Header, HttpResponse}

/**
 *
 */
case class Response(statusCode: Int,
                    contentType: Option[String],
                    contentEncoding: Option[String],
                    content: InputStream)

object Response {
  def apply(response: HttpResponse): Response = {
    Response(
      statusCode = response.getStatusLine.getStatusCode,
      contentType = response.getEntity.getContentEncoding match {
        case h: Header => Some(h.getValue)
        case _ => None
      },
      contentEncoding = response.getEntity.getContentEncoding match {
        case h: Header => Some(h.getValue)
        case _ => None
      },
      content = response.getEntity.getContent
    )
  }
}