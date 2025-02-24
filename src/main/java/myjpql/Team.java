package myjpql;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Team {
   
   @Id @GeneratedValue
   private Long id;

   private String name;

   @OneToMany(mappedBy= "team")
   private List<Member> members = new ArrayList<>();
}
