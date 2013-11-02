package etude.calendar

import etude.region.Country
import etude.calendar.provider.GoogleCalendarHolidays
import java.util.Locale

/**
 *
 */
case class NationalHolidays(country: Country,
                            locale: Locale = Locale.getDefault) extends Holidays {
  lazy val googleCalendar = GoogleCalendarHolidays(locale)

  def holidays(span: CalendarDateSpan): Seq[Holiday] = {
    googleCalendar.nationalHolidays(span, country)
  }
}