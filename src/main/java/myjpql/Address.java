package myjpql;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
   private String city;
   private String street;
   private String zipcode;

   public Address() {
   }
   
   public Address(String city, String street, String zipcode) {
      this.city = city;
      this.street = street;
      this.zipcode = zipcode;
   }

   public String getCity() {
      return city;
   }
   public void setCity(String city) {
      this.city = city;
   }
   public String getStreet() {
      return street;
   }
   public void setStreet(String street) {
      this.street = street;
   }
   public String getZipcode() {
      return zipcode;
   }
   public void setZipcode(String zipcode) {
      this.zipcode = zipcode;
   }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.city);
        hash = 53 * hash + Objects.hashCode(this.street);
        hash = 53 * hash + Objects.hashCode(this.zipcode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        return Objects.equals(this.zipcode, other.zipcode);
    }

 
   
}
