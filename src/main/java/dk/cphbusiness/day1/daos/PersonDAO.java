package dk.cphbusiness.day1.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.day1.dtos.PersonDTO;
import dk.cphbusiness.day1.entities.Address;
import dk.cphbusiness.day1.entities.Person;
import dk.cphbusiness.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class PersonDAO implements IDAO<Person>{
    ObjectMapper om = new ObjectMapper();
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    @Override
    public Person findById(long id) {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void create(Person person) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(person);
//            for (Address address : person.getAddresses()) {
//
//            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(Person person) {
        //

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Person person) {
        //

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Set<Person> findAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return query.getResultStream().collect(Collectors.toSet());
        }
    }

    public Set<PersonDTO> findAllAsDTO(){
        try(EntityManager em = emf.createEntityManager()) {
           TypedQuery<PersonDTO> query = em.createQuery("SELECT new dk.cphbusiness.day1.dtos.PersonDTO(p) FROM Person p", PersonDTO.class);
           return query.getResultStream().collect(Collectors.toSet());
        }
    }

    public String dto2Json(PersonDTO person){
        try{
            String json = om.writeValueAsString(person);
            return json;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public PersonDTO json2DTO(String json){
        try{
            PersonDTO person = om.readValue(json, PersonDTO.class);
            return person;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        PersonDAO dao = new PersonDAO();
//        Address a1 = new Address("Testvej", 1, 9999, "Testby");
//        Address a2 = new Address("Testvej", 2, 9800, "Gammelby");
//        Address a3 = new Address("Testvej", 3, 1111, "Nyby");
//        Address a4 = new Address("Testvej", 4, 2222, "Næstved");
        Person p1 = new Person("Holger", "holger@mail.com", "1234");
        Person p2 = new Person("Frederikke", "frederikke@mail.com", "1234");
        Person p3 = new Person("Ulrik", "ulrik@mail.com", "1234");
        Person p4 = new Person("Hassan", "hassan@mail.com", "1234");

//        p1.addAddress(a1);
//        p2.addAddress(a1);
//        p3.addAddress(a1);
//        p1.addAddress(a2);
//        p1.addAddress(a3);
        dao.create(p1);
        dao.create(p2);
        dao.create(p3);
        dao.create(p4);

        Set<PersonDTO> personDTOs = dao.findAll().stream()
                .map(person->{
            return new PersonDTO(person);
        }).collect(Collectors.toSet());
//        String json = dao.dto2Json(personDTOs.iterator().next());
        Set<String> jsonStrings = personDTOs.stream()
                .map(personDTO -> dao.dto2Json(personDTO))
                .collect(Collectors.toSet());
        jsonStrings.forEach(System.out::println);

        System.out.println("------------FROM JSON TO DTO: ------------------");
        String s1 = jsonStrings.iterator().next();
        PersonDTO personDTO = dao.json2DTO(s1);
        System.out.println(personDTO);


        System.out.println("--------------FIND ALL AS DTO---------------------");
        dao.findAllAsDTO().forEach(System.out::println);

    }
}
