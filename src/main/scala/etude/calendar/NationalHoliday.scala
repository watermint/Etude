package etude.calendar

import java.time.LocalDate

case class NationalHoliday(date: LocalDate, title: Option[String]) extends Holiday
