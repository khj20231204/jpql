package myjpql;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable //정의 하는 곳의 어노테이션
public class Period {
   
   private LocalDateTime startDate;
   private LocalDateTime endDate;

   public Period() {
   }

   public Period(LocalDateTime endDate, LocalDateTime startDate) {
      this.endDate = endDate;
      this.startDate = startDate;
   }

   public LocalDateTime getStartDate() {
      return startDate;
   }

   public void setStartDate(LocalDateTime startDate) {
      this.startDate = startDate;
   }

   public LocalDateTime getEndDate() {
      return endDate;
   }

   public void setEndDate(LocalDateTime endDate) {
      this.endDate = endDate;
   }
}
