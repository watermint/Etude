package etude.calendar


/**
 *
 */
trait Holidays {
  def holidays(span: CalendarDateSpan): Seq[Holiday]

  def between(start: String): HolidayDateContainer = HolidayDateContainer(start, this)
}
