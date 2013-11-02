package etude.calendar.provider

import scala.xml.{Node, XML}
import java.util.Locale
import java.net.URL
import java.time.LocalDate
import etude.religion.Religion
import etude.region.Country
import etude.calendar.{CalendarDateSpan, ReligiousHoliday, NationalHoliday, Holiday}
import scala.collection.immutable.HashMap

case class GoogleCalendarHolidays(locale: Locale = Locale.getDefault) {

  def nationalHolidays(span: CalendarDateSpan, country: Country): Seq[Holiday] = {
    if (supportedRegions.contains(country)) {
      holidays(url(span, country)).map {
        p =>
          NationalHoliday(p._2, Some(p._1))
      }
    } else {
      Seq()
    }
  }

  def religiousHolidays(span: CalendarDateSpan, religion: Religion): Seq[Holiday] = {
    if (supportedReligions.contains(religion)) {
      holidays(url(span, religion)).map {
        p =>
          ReligiousHoliday(p._2, Some(p._1))
      }
    } else {
      Seq()
    }
  }

  lazy val defaultLanguage = "en"

  lazy val supportedLanguages: Seq[String] = Seq(
    "da", // Danish
    "de", // German
    "en", // English
    "es", // Spanish
    "fi", // Finnish
    "fr", // French
    "it", // Italian
    "ja", // Japanese
    "ko", // Korean
    "nl", // Dutch
    "no", // Norwegian
    "pl", // Polish
    "ru", // Russian
    "sv" // Swedish
  )

  lazy val supportedReligions: Map[Religion, String] = Map(
    Religion.christian -> "christian",
    Religion.islamic -> "islamic",
    Religion.jewish -> "jewish"
  )

  lazy val supportedRegions: Map[Country, String] = Map(
    Country.AUSTRALIA -> "australian",
    Country.AUSTRIA -> "austrian",
    Country.BRAZIL -> "brazilian",
    Country.CANADA -> "canadian",
    Country.CHINA -> "china",
    Country.DENMARK -> "danish",
    Country.FINLAND -> "finnish",
    Country.FRANCE -> "french",
    Country.GERMANY -> "german",
    Country.GREECE -> "greek",
    Country.HONGKONG -> "hong_kong",
    Country.INDIA -> "indian",
    Country.INDONESIA -> "indonesian",
    Country.ITALY -> "italian",
    Country.JAPAN -> "japanese",
    Country.KOREA -> "south_korea",
    Country.MALAYSIA -> "malaysia",
    Country.MEXICO -> "mexican",
    Country.NEW_ZEALAND -> "new_zealand",
    Country.NORWAY -> "norwegian",
    Country.PHILIPPINES -> "philippines",
    Country.POLAND -> "polish",
    Country.PORTUGAL -> "portuguese",
    Country.RUSSIAN -> "russian",
    Country.SINGAPORE -> "singapore",
    Country.SOUTH_AFRICA -> "sa",
    Country.SPAIN -> "spain",
    Country.SWEDEN -> "swedish",
    Country.TAIWAN -> "taiwan",
    Country.THAILAND -> "thai",
    Country.UNITED_KINGDOM -> "uk",
    Country.UNITED_STATES -> "usa",
    Country.VIET_NAM -> "vietnamese"
  )

  def calendarLanguage(locale: Locale): String = {
    val lang = locale.getLanguage match {
      case "" => Locale.getDefault.getLanguage
      case _ => locale.getLanguage
    }
    if (supportedLanguages.contains(lang)) {
      lang
    } else {
      defaultLanguage
    }
  }

  private def calendarName(country: Country): String = {
    supportedRegions.get(country).get
  }

  private def url(span: CalendarDateSpan, name: String): URL = {
    new URL(
      "http://www.google.com/calendar/feeds/" +
        calendarLanguage(locale) + "." + name +
        "%23holiday%40group.v.calendar.google.com/public/full?" +
        "start-min=" + span.start +
        "&start-max=" + span.end
    )
  }

  private def url(span: CalendarDateSpan, country: Country): URL = {
    url(span, calendarName(country))
  }

  private def url(span: CalendarDateSpan, religion: Religion): URL = {
    url(span, religion.id)
  }

  private def holiday(entry: Node): Pair[String, LocalDate] = {
    (entry \ "title").text -> LocalDate.parse(((entry \ "when").last \ "@startTime").text)
  }

  private def holidays(url: URL): Seq[Pair[String, LocalDate]] = {
    GoogleCalendarHolidays.cache.get(Pair(locale, url)) match {
      case Some(cached) => cached
      case _ =>
        val result = (XML.load(url) \ "entry").map(holiday)
        GoogleCalendarHolidays.cache.update(Pair(locale, url), result)
        result
    }
  }

}

object GoogleCalendarHolidays {
  private val cache: scala.collection.mutable.Map[Pair[Locale, URL], Seq[Pair[String, LocalDate]]] = scala.collection.mutable.Map()
}