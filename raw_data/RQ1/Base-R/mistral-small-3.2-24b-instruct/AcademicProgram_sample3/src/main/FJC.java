import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an Academic Program.
 */
 class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the academic program.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Removes a course from the academic program.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program.
     *
     * @return the total classroom capacity
     */
    public int calculateTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course.
     *
     * @param course the course to calculate the average grade for
     * @return the average grade
     * @throws IllegalArgumentException if the course is not found in the program
     */
    public double calculateAverageGrade(Course course) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Course not found in the academic program");
        }
        return course.calculateAverageGrade();
    }

    /**
     * Finds the total number of courses in the Academic Program that are taught on a given day.
     *
     * @param day the day to check for courses
     * @return the number of courses on the specified day
     */
    public int countCoursesOnDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the list of courses in the academic program.
     *
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program.
     *
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

/**
 * Class representing a Course.
 */
 class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private Map<Student, Integer> studentGrades;
    private int quota;

    /**
     * Default constructor.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.studentGrades = new HashMap<>();
    }

    /**
     * Adds a student to the course with a given grade.
     *
     * @param student the student to add
     * @param grade the grade of the student
     */
    public void addStudent(Student student, int grade) {
        studentGrades.put(student, grade);
    }

    /**
     * Removes a student from the course.
     *
     * @param student the student to remove
     */
    public void removeStudent(Student student) {
        studentGrades.remove(student);
    }

    /**
     * Calculates the average grade for the students enrolled in the course.
     *
     * @return the average grade
     */
    public double calculateAverageGrade() {
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : studentGrades.values()) {
            sum += grade;
        }
        return (double) sum / studentGrades.size();
    }

    /**
     * Increases the course quota limit.
     *
     * @param amount the amount to increase the quota by
     */
    public void increaseQuota(int amount) {
        quota += amount;
    }

    /**
     * Decreases the course quota limit.
     *
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuota(int amount) {
        quota -= amount;
    }

    /**
     * Changes the weekly course hours.
     *
     * @param newHours the new weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        // Implementation to change weekly hours
    }

    /**
     * Changes the course days.
     *
     * @param newDays the new days of the course
     */
    public void changeCourseDays(List<String> newDays) {
        this.days = newDays;
    }

    /**
     * Gets the name of the course.
     *
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     *
     * @return the code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     *
     * @param code the code of the course
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     *
     * @return the start time of the course
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     *
     * @param startTime the start time of the course
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     *
     * @return the end time of the course
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     *
     * @param endTime the end time of the course
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days of the course.
     *
     * @return the days of the course
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Sets the days of the course.
     *
     * @param days the days of the course
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Gets the classroom of the course.
     *
     * @return the classroom of the course
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom of the course.
     *
     * @param classroom the classroom of the course
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the instructor of the course.
     *
     * @return the instructor of the course
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor of the course.
     *
     * @param instructor the instructor of the course
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Gets the student grades in the course.
     *
     * @return the student grades
     */
    public Map<Student, Integer> getStudentGrades() {
        return studentGrades;
    }

    /**
     * Sets the student grades in the course.
     *
     * @param studentGrades the student grades
     */
    public void setStudentGrades(Map<Student, Integer> studentGrades) {
        this.studentGrades = studentGrades;
    }

    /**
     * Gets the quota of the course.
     *
     * @return the quota of the course
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota of the course.
     *
     * @param quota the quota of the course
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }
}

/**
 * Class representing a Classroom.
 */
 class Classroom {
    private String id;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Default constructor.
     */
    public Classroom() {
    }

    /**
     * Gets the id of the classroom.
     *
     * @return the id of the classroom
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the classroom.
     *
     * @param id the id of the classroom
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of the classroom.
     *
     * @return the capacity of the classroom
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     *
     * @param capacity the capacity of the classroom
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor of the classroom.
     *
     * @return the floor of the classroom
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor of the classroom.
     *
     * @param floor the floor of the classroom
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the block of the classroom.
     *
     * @return the block of the classroom
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block of the classroom.
     *
     * @param block the block of the classroom
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Class representing an Instructor.
 */
 class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the instructor's list.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Removes a course from the instructor's list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Determines the total number of students enrolled in all courses of the instructor.
     *
     * @return the total number of students
     */
    public int getTotalNumberOfStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudentGrades().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     *
     * @return the remaining quota
     */
    public int getRemainingQuota() {
        int remainingQuota = 0;
        for (Course course : courses) {
            remainingQuota += course.getQuota() - course.getStudentGrades().size();
        }
        return remainingQuota;
    }

    /**
     * Gets the name of the instructor.
     *
     * @return the name of the instructor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     *
     * @param name the name of the instructor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     *
     * @return the surname of the instructor
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     *
     * @param surname the surname of the instructor
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     *
     * @return the title of the instructor
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     *
     * @param title the title of the instructor
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     *
     * @return the specialty of the instructor
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     *
     * @param specialty the specialty of the instructor
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses of the instructor.
     *
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses of the instructor.
     *
     * @param courses the list of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

/**
 * Class representing a Student.
 */
 class Student {
    private String name;
    private String surname;
    private List<Course> enrolledCourses;

    /**
     * Default constructor.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    /**
     * Drops the student from a course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
    }

    /**
     * Gets the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name the name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the student.
     *
     * @return the surname of the student
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the student.
     *
     * @param surname the surname of the student
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the list of enrolled courses of the student.
     *
     * @return the list of enrolled courses
     */
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * Sets the list of enrolled courses of the student.
     *
     * @param enrolledCourses the list of enrolled courses
     */
    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}