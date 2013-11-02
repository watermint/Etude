package etude.calendar

import java.time.LocalDate

/**
 *
 */
case class HolidayDateContainer(date: LocalDate, holidays: Option[Holidays]) extends CalendarDate {
  def and(other: LocalDate): Seq[Holiday] = CalendarDateSpan(date, other, holidays).holidays

  def and(other: String): Seq[Holiday] = CalendarDateSpan(date, LocalDate.parse(other), holidays).holidays

}

object HolidayDateContainer {
  def apply(date: String): HolidayDateContainer = HolidayDateContainer(LocalDate.parse(date), None)

  def apply(date: String, holidays: Holidays): HolidayDateContainer = HolidayDateContainer(LocalDate.parse(date), Some(holidays))

}
