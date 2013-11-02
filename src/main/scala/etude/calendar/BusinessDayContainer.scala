package etude.calendar

import java.time.LocalDate

/**
 *
 */
case class BusinessDayContainer(date: LocalDate, businessDays: BusinessDays) extends CalendarDate {

  def and(end: String): Seq[BusinessDay] = and(LocalDate.parse(end))

  def and(end: LocalDate): Seq[BusinessDay] = {
    val span = CalendarDateSpan(date, end)
    val holidays = BusinessHolidays(businessDays.patterns).holidays(span).map(_.date).distinct

    span.days.flatMap {
      day =>
        if (holidays.contains(day)) {
          None
        } else {
          Some(BusinessDay(day))
        }
    }
  }
}
