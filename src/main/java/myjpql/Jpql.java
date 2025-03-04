/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package myjpql;

import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 *
 * @author natis
 */
public class Jpql {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member2 m2 = new Member2();
            m2.setUsername("member1");
            m2.setHomeAddress(new Address("city", "street", "zipcode"));
            
            //collection에 입력
            m2.getFavoriteFoods().add("치킨");
            m2.getFavoriteFoods().add("피자");
            m2.getFavoriteFoods().add("족발");

            //m2.getAddressHistory().add(new Address("old1", "street1", "zipcode1"));
            //m2.getAddressHistory().add(new Address("old2", "street2", "zipcode2"));
            m2.getAddressHistory().add(new AddressEntity(new Address("old1", "street1", "zipcode1")));
            m2.getAddressHistory().add(new AddressEntity(new Address("old2", "street2", "zipcode2")));

            em.persist(m2);

            em.flush();
            em.clear();

            System.out.println("=========== 값 조회 ============");
            Member2 findMember = em.find(Member2.class, m2.getId());

            /* List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for(AddressEntity a : addressHistory){
                System.out.println(a.getCity());
            } */

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String s : favoriteFoods){
                System.out.println(s);
            }

            System.out.println("=========== 값 수정 ============");

            //homeAddress의 값을 수정할 때 이렇게 하면 될 것 같지만 이렇게 하면 안 된다.
            //findMember.getHomeAddress().setCity("home city");
            //값 타입의 수정은 완전히 새로운 객체를 생성해서 넣어줘야 한다.
            //왜? 값 타입과 객체 타입-
            findMember.setHomeAddress(new Address("home city", "home street", "home zipcode"));
            //city를 하나만 바꿀려고 해도 이렇게 전체적으로 객체를 생성해서 바꿔줘야한다.
            /* update쿼리문이 생성된다
            결과 값:
            Hibernate: 
                update Member2 
                set
                city=?,
                street=?,
                zipcode=?,
                username=? 
            where
                id=?
             */

            //기본타입 컬렉션의 변경 : 치킨 -> 짜장면으로 바꿔라
            findMember.getFavoriteFoods().remove("치킨"); //삭제 후
            findMember.getFavoriteFoods().add("짜장면"); //새로 입력
            //String인 값 타입은 update가 없다. 완전 삭제 후 다시 넣어야한다.

            //임베디드타입 컬렉션의 변경 : city에서 old1 -> new1로 변경하라
            //findMember.getAddressHistory().remove(new Address("old1", "street1", "zipcode1"));
            //findMember.getAddressHistory().add(new Address("new1", "street1", "zipcode1"));
            
            Member2 member3 = new Member2();
            member3.setUsername("member3");
            member3.setType(MemberType.ADMIN);
            
            em.persist(member3);

            em.flush();
            em.clear();

            System.out.println("==================== enum type =======================");

            /* String query4 = "select m.username, 'HELLO', true from Member2 m " +
            "where m.type = myjpql.MemberType.ADMIN";
                        
            List<Object[]> result8 = em.createQuery(query4).getResultList();*/

            String query4 = "select m.username, 'HELLO', true from Member2 m " +
            "where m.type = :userType";
                        
            List<Object[]> result8 = em.createQuery(query4)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for(Object[] o  : result8){
                System.out.println("Object[0]:"+ o[0]);
                System.out.println("Object[0]:"+ o[1]);
                System.out.println("Object[0]:"+ o[2]);
            }

            Member member4 = new Member();
            member4.setUsername("member4");
            member4.setAge(23);

            em.persist(member4);

            String query5 = "select "
            + " case when m.age <= 10 then '학생요금'"
            + "      when m.age >=60 then '경로요금'"
            + "      else '일반요금'"
            + " end"
            + " from Member m";

            List<String> resultList2 = em.createQuery(query5, String.class).getResultList();

            for(String s : resultList2){
                System.out.println(s);
            }

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
        
    }

    public static void jpqlQuery(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city", "street", "zipcode"));
            member.setPeriod(new Period());
            member.setAge(10);

            em.persist(member);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            //위에 쿼리 select m from Member m 로 가져온 m의 결과는 모두 영속성 컨텍스트에 저장이 되어 관리가 된다.   

            Member findMember = result.get(0);
            //List의 0번째 인텍스 값을 가져와서 
            
            findMember.setAge(30);
            //다시 변경하면 영속성 컨텍스트에 의해 관리가 되므로 변경이 가능

            List<Team> result2 = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
            for(Team t : result2){
                System.out.println(t);
            }

            //List<Address> result3 = em.createQuery("select m.address from Member m", Address.class).getResultList();
            List<Address> result3 = em.createQuery("select o.address from Order o", Address.class).getResultList();
            for(Address a : result3){
                System.out.println(a.getZipcode());
            }

            List result4 = em.createQuery("select m.username , m.age from Member m").getResultList();

            Object obj = result4.get(0);
            Object[] obj2 = (Object[])obj;
            System.out.println("obj2[0]-username : " + obj2[0]);
            System.out.println("obj2[1]-age : " + obj2[1]);
            
            List<Object[]> result5 = em.createQuery("select m.username, m.age from Member m").getResultList();

            for(Object[] o : result5){
                String username = (String)o[0];
                int age = (int)o[1];
                System.out.println("username:"+username+" ,age:"+age);
            }

            List<MemberDTO> result6 = em.createQuery("select new myjpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            for(MemberDTO md : result6){
                System.out.println("username:"+md.getUsername()+" ,age:"+md.getAge());
            }
            //TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            //List<Member> resultList = query.getResultList(); //결과 값이 여러개, getResultList

            //TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.id=10", Member.class);
            //Member resultOne = query2.getSingleResult();  
            //결과 값이 반드시 한개, getSingleResult
            //없거나 한개 이상이면 exception 발생
            //System.out.println(resultOne);

            Member query3 = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

            /* query3.setParameter("username", "member1"); //where username = 'member1' 과 같은 의미
            Member resultQuery3 = query3.getSingleResult();
            System.out.println(resultQuery3); */


            for(int i=2 ; i<50 ; i++){
                Member member2 = new Member();
                if(i<10){
                    member2.setUsername("username 0" + i);
                }else{
                    member2.setUsername("username " + i);
                }
                member2.setAge(i+10);
                em.persist(member2);
            }
            
            em.flush();
            em.clear();

            String pagingQuery = "select m from Member m order by m.username desc";
            List<Member> pagingList = em.createQuery(pagingQuery, Member.class)
                                .setFirstResult(1)
                                .setMaxResults(20)
                                .getResultList();

            for(Member m : pagingList){
                System.out.println(m.getUsername());
                System.out.println(m.getAge());
            }


            List<Member> result7 = em.createQuery("select m from Member m left join Team t on m.username = t.name").getResultList();

            System.out.println(result7.size());          
            
            tx.commit();

        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}

