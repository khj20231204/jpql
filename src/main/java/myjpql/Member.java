package myjpql;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Member {
   
   @Id @GeneratedValue
   private Long id;
   private String username;
   private int age;

   @ManyToOne
   @JoinColumn(name="TEAM_ID")
   private Team team;

   @Embedded
   private Period period;

   @Embedded
   private Address address;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public Team getTeam() {
      return team;
   }

   public void setTeam(Team team) {
      this.team = team;
   }

   public Period getPeriod() {
      return period;
   }

   public void setPeriod(Period period) {
      this.period = period;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   
}
