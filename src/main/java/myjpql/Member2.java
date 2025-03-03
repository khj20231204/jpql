package myjpql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Member2 {
   
   @Id @GeneratedValue
   private Long id;
   private String username;

   @Embedded
   private Address homeAddress; 

   @ElementCollection  //맵핑
   @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))  //테이블명 지정
   @Column(name = "FOOD_NAME")
   private Set<String> favoriteFoods = new HashSet<>();

   /*
   @ElementCollection
   @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID")) 
   private List<Address> addressHistory = new ArrayList<>();
   */

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "MEMBER_ID")
   private List<AddressEntity> addressHistory = new ArrayList<>();
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Set<String> getFavoriteFoods() {
      return favoriteFoods;
   }

   public void setFavoriteFoods(Set<String> favoriteFoods) {
      this.favoriteFoods = favoriteFoods;
   }

   public List<AddressEntity> getAddressHistory() {
      return addressHistory;
   }

   public void setAddressHistory(List<AddressEntity> addressHistory) {
      this.addressHistory = addressHistory;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public Address getHomeAddress() {
      return homeAddress;
   }

   public void setHomeAddress(Address homeAddress) {
      this.homeAddress = homeAddress;
   }

   
}
