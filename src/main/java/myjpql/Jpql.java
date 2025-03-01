/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package myjpql;

import java.util.List;

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

            Address address = new Address("city", "street", "1000");
                    
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAddress(address);
            
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAddress(copyAddress);
                    
            member2.getAddress().setCity("no city");

            System.out.println("-------------------"+member1.getAddress().getCity());

            em.persist(member1);
            em.persist(member2);

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
            member.setAddress(new Address("city", "street", "zipcode"));
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
