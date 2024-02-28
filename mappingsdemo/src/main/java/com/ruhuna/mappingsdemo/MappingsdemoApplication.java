package com.ruhuna.mappingsdemo;

import com.ruhuna.mappingsdemo.DAO.AppDao;
import com.ruhuna.mappingsdemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MappingsdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MappingsdemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppDao appDao)
	{
		return runner->
		{
			//createInstructor(appDao);
			//findInstructor(appDao);
			//deleteById(appDao);
			//findInstructorDetail(appDao);
			//deleteInstructorDetailById(appDao);
			//createInstructorWithCourses(appDao);
			//findInstructorWithCourses(appDao);
			//findInstructorWithCoursesJoinFetch(appDao);
			//updateInstructor(appDao);
			//updateCourse(appDao);
			//saveCourseWithReviews(appDao);
			//findCoursesAndReviewsByCourseId(appDao);
			//createCourseAndStudents(appDao);
			//findCourseAndStudentsByCourseId(appDao);
			findStudentAndCoursesByStudentId(appDao);

		};

	}

	private void findStudentAndCoursesByStudentId(AppDao appDao) {

		Student student = appDao.findStudentAndCoursesByStudentId(18);

		System.out.println(student.getCourses());
	}

	private void findCourseAndStudentsByCourseId(AppDao appDao) {

		int id = 39;

		Course course = appDao.findCourseAndStudentsByCourseId(id);
		System.out.println(course.getStudents());
	}

	private void createCourseAndStudents(AppDao appDao) {

		Course course1 = new Course("Solar8");
		Course course2 = new Course("DAA10");
		Course course3 = new Course("55");

		course1.addReviews(new Review("Supiri"));

		Student malshi = new Student("Malshi","Gim","malshi@gmail.com");
		Student ravishan = new Student("Ravi","Shan","ravi@gmail.com");

		malshi.addCourses(course3);
		malshi.addCourses(course2);
		ravishan.addCourses(course3);
		ravishan.addCourses(course1);

		appDao.saveStudent(malshi);
		appDao.saveStudent(ravishan);

	}

	private void findCoursesAndReviewsByCourseId(AppDao appDao) {

		Course course = appDao.findCourseAndReviewsById(21);
		System.out.println(course.getReviews());

	}

	private void saveCourseWithReviews(AppDao appDao) {

		Course course = new Course("EAD8");
		Review review = new Review("Good Course");

		course.addReviews(review);
		appDao.saveCourse(course);


	}

	private void updateCourse(AppDao appDao) {

		Course course = appDao.findCourseById(10);
		Instructor instructor = course.getInstructor();
		instructor.setFirstName("Harsha");
		course.setInstructor(instructor);
		course.setTitle("Web App Dev");

		appDao.updateCourse(course);

	}

	private void updateInstructor(AppDao appDao) {

		Instructor instructor = appDao.findInstructorById(2);

		instructor.setFirstName("Sunimal");

		appDao.updateInstructor(instructor);

	}

	private void findInstructorWithCoursesJoinFetch(AppDao appDao) {

		Instructor instructor = appDao.findInstructorByIdJoinFetch(2);
		System.out.println(instructor.getCourses());
	}

	private void findInstructorWithCourses(AppDao appDao) {
		int id = 2;

		Instructor tempInstructor = appDao.findInstructorById(id);

		List<Course> courseList = appDao.findCoursesByInstructorId(id);

		// Added the associations
		tempInstructor.setCourses(courseList);

		System.out.println("Associated Courses : "+tempInstructor.getCourses());
	}

	private void createInstructorWithCourses(AppDao appDao) {

		Instructor instructor = new Instructor("Nimal","Silva","Nimal@gmail.com");

		InstructorDetail instructorDetail = new InstructorDetail("youtube.com","Test Hobby");
		instructor.setInstructorDetail(instructorDetail);

		Course powerElectronics = new Course("Power Electronics");
		Course energy = new Course("Energy");

		instructor.add(powerElectronics);
		instructor.add(energy);

		appDao.save(instructor);

		System.out.println("Courses Saved : " + instructor.getCourses());





	}

	private void deleteInstructorDetailById(AppDao appDao) {

		appDao.deleteInstructorDetailById(7);

	}

	private void findInstructorDetail(AppDao appDao) {
		InstructorDetail instructorDetail = appDao.findInstructorDetailById(2);
		Instructor instructor = appDao.findInstructorById(2);
	}

	private void deleteById(AppDao appDao) {
		appDao.deleteInstructorById(1);
	}

	private void findInstructor(AppDao appDao) {
		Instructor instructor = appDao.findInstructorById(1);
		InstructorDetail instructorDetail = instructor.getInstructorDetail();

		System.out.println("Finding instructor of id 1");
		System.out.println("Instructor : " + instructor);
		System.out.println("Associated instructor detail : "+instructorDetail);
	}

	private void createInstructor(AppDao appDao) {

		Instructor instructor = new Instructor("Hiruni","Silva","Hiruni@gmail.com");

		InstructorDetail instructorDetail = new InstructorDetail("youtube.com","Test Hobby");

		instructor.setInstructorDetail(instructorDetail);

		appDao.save(instructor);


	}

}
