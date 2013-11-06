package etude.csv

/**
 *
 */
case class Style(separator: String = ",",
                 quote: String = "\"")

object Style {
  lazy val DEFAULT = Style()
}
