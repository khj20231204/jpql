package myjpql;

public class MemberDTO {

   private String username;
   private int age;
   
   //쿼리문에서 넣을 변수를 포함하는 생성자를 만들어야 한다
   public MemberDTO(String username, int age) {
      this.username = username;
      this.age = age;
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
}
