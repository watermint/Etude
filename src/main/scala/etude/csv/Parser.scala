package etude.csv

import util.parsing.combinator._
import scala.util.matching.Regex

/**
 * @see http://seratch.hatenablog.jp/entry/20111010/1318254084
 */
case class Parser(style: Style = Style.DEFAULT) extends RegexParsers {
  def eol = opt("\r") <~ "\n"

  def plainCell: Regex = ("[^\r\n" + style.quote + "]*").r

  def quotedCell = style.quote ~> ("[^" + style.quote + "]*").r <~ style.quote

  def row = repsep(plainCell | quotedCell, style.separator)

  def rows = repsep(row, eol)
}
