package etude.calendar

import java.time.{Duration, DayOfWeek, LocalDate}

/**
 * Date span (start/end dates are inclusive).
 */
case class CalendarDateSpan(start: LocalDate, end: LocalDate, holidayProvider: Option[Holidays] = None) {
  def isBetween(other: LocalDate): Boolean = {
    start.isAfter(other) && end.isBefore(other)
  }

  lazy val years: Seq[Int] = start.getYear to end.getYear

  lazy val days: Seq[LocalDate] = {
    val duration = Duration.between(start.atStartOfDay(), end.atStartOfDay())
    (0.toLong to duration.toDays).map(start.plusDays)
  }

  def weeks(dayOfWeek: DayOfWeek): Seq[LocalDate] = {
    val s = start.`with`(dayOfWeek)
    val duration = Duration.between(s.atStartOfDay(), end.atStartOfDay())
    val weekCount = (duration.toDays / 7).toInt

    (0 to weekCount).flatMap {
      w =>
        val d = s.plusDays(w * 7)
        if (d.isBefore(end)) {
          Some(d)
        } else {
          None
        }
    }
  }

  def holidays: Seq[Holiday] = {
    holidayProvider match {
      case Some(h) => h.holidays(this)
      case _ => Seq()
    }
  }
}

object CalendarDateSpan {
  def apply(start: String, end: String): CalendarDateSpan = {
    CalendarDateSpan(LocalDate.parse(start), LocalDate.parse(end))
  }
}
