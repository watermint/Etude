package etude

import java.time.LocalDate

/**
 *
 */
package object calendar {
  def businessHolidays: BusinessHolidays = BusinessHolidays()

  def businessDays: BusinessDays = BusinessDays()
}
