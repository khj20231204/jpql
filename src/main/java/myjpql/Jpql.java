/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package myjpql;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

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

        try{

            Member member = new Member();
            member.setUsername("member1");
            member.setAddress(new Address("city", "street", "zipcode"));
            member.setPeriod(new Period());

            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query.getResultList(); //결과 값이 여러개, getResultList

            TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.id=10", Member.class);
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
