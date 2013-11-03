package etude.calendar

import java.time.LocalDate


/**
 *
 */
trait Holidays {
  def holidays(span: CalendarDateSpan): Either[Exception, Seq[Holiday]]

  def between(start: String): HolidayDateContainer = HolidayDateContainer(LocalDate.parse(start), this)

  def between(start: LocalDate): HolidayDateContainer = HolidayDateContainer(start, this)
}
