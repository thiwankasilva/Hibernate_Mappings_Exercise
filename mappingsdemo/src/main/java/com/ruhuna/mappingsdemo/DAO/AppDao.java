package com.ruhuna.mappingsdemo.DAO;

import com.ruhuna.mappingsdemo.entity.Course;
import com.ruhuna.mappingsdemo.entity.Instructor;
import com.ruhuna.mappingsdemo.entity.InstructorDetail;
import com.ruhuna.mappingsdemo.entity.Student;

import java.util.List;

public interface AppDao {

    void save(Instructor instructor);
    Instructor findInstructorById(int id);
    void deleteInstructorById(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorDetailById(int id);
    List<Course> findCoursesByInstructorId(int id);
    Instructor findInstructorByIdJoinFetch(int id);
    void updateInstructor(Instructor instructor);
    Course findCourseById(int id);
    void updateCourse(Course course);
    void saveCourse(Course course);
    Course findCourseAndReviewsById(int id);
    void saveStudent(Student student);
    Course findCourseAndStudentsByCourseId(int id);
    Student findStudentAndCoursesByStudentId(int id);

}
