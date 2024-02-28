package com.ruhuna.mappingsdemo.DAO;

import com.ruhuna.mappingsdemo.entity.Course;
import com.ruhuna.mappingsdemo.entity.Instructor;
import com.ruhuna.mappingsdemo.entity.InstructorDetail;
import com.ruhuna.mappingsdemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDao{

    private static final Logger logger = LoggerFactory.getLogger(AppDAOImpl.class);

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {

        Instructor tobeDeleted = entityManager.find(Instructor.class,id);

        List<Course> courseList = tobeDeleted.getCourses();
        for (Course course : courseList)
        {
            //break the association
            course.setInstructor(null);
        }

        entityManager.remove(tobeDeleted);

    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {

        return entityManager.find(InstructorDetail.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class,id);

        //remove bi-directional relationship
        instructorDetail.getInstructor().setInstructorDetail(null);
        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {

        TypedQuery<Course> courseTypedQuery = entityManager.createQuery("from Course where instructor.id =:data",Course.class);
        courseTypedQuery.setParameter("data",id);

        List<Course> courseList = courseTypedQuery.getResultList();
        return courseList;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {

        //Join fetch is similar to eager loading
        TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor " +
                "i JOIN FETCH i.courses JOIN FETCH i.instructorDetail where i.id = :data ",Instructor.class);
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {

        entityManager.merge(instructor);

    }

    @Override
    public Course findCourseById(int id) {

        Course course = entityManager.find(Course.class,id);
        return course;
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {

        entityManager.merge(course);

    }

    @Override
    @Transactional
    public void saveStudent(Student student)
    {
        entityManager.merge(student);
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {

        TypedQuery<Course> courseTypedQuery = entityManager.createQuery("select c from Course c " +
                "JOIN FETCH c.students where c.id = :theData",Course.class);
        courseTypedQuery.setParameter("theData",id);
        Course course = courseTypedQuery.getSingleResult();
        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int id) {

        Student student;

        try{
            TypedQuery<Student> studentTypedQuery = entityManager.createQuery("select s from Student s " +
                    "JOIN FETCH s.courses c where s.id = :theData",Student.class);
            studentTypedQuery.setParameter("theData",id);
            student = studentTypedQuery.getSingleResult();

        }
        catch (IllegalStateException e) {
            // Log the exception for debugging purposes
            logger.error("LazyInitializationException occurred: {}", e.getMessage(), e);

            // Rethrow the exception with a more informative message
            throw new IllegalStateException("Error fetching courses with reviews: " + e.getMessage(), e);
        }
        return student;


    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsById(int id) {

        TypedQuery<Course> courseTypedQuery = entityManager.createQuery("select c from Course c " +
                "JOIN FETCH c.reviews where c.id = :theData",Course.class);
        courseTypedQuery.setParameter("theData",id);
        Course course = courseTypedQuery.getSingleResult();
        return course;
    }
}
