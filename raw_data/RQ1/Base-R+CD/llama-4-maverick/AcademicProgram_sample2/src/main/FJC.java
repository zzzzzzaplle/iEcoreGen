import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a classroom with its properties.
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Default constructor for Classroom.
     */
    public Classroom() {}

    /**
     * Parameterized constructor for Classroom.
     * 
     * @param id       Unique identifier for the classroom.
     * @param capacity Maximum capacity of the classroom.
     * @param floor    Floor where the classroom is located.
     * @param block    Block where the classroom is located.
     */
    public Classroom(String id, int capacity, String floor, String block) {
        this.id = id;
        this.capacity = capacity;
        this.floor = floor;
        this.block = block;
    }

    /**
     * Gets the classroom id.
     * 
     * @return Classroom id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the classroom id.
     * 
     * @param id New classroom id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the classroom capacity.
     * 
     * @return Classroom capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the classroom capacity.
     * 
     * @param capacity New classroom capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where the classroom is located.
     * 
     * @return Floor of the classroom.
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the classroom is located.
     * 
     * @param floor New floor of the classroom.
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where the classroom is located.
     * 
     * @return Block of the classroom.
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where the classroom is located.
     * 
     * @param block New block of the classroom.
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor with their properties and behaviors.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Parameterized constructor for Instructor.
     * 
     * @param name      Name of the instructor.
     * @param surname   Surname of the instructor.
     * @param title     Title of the instructor.
     * @param specialty Specialty of the instructor.
     */
    public Instructor(String name, String surname, String title, String specialty) {
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.specialty = specialty;
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the instructor's name.
     * 
     * @return Instructor's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's name.
     * 
     * @param name New name for the instructor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's surname.
     * 
     * @return Instructor's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's surname.
     * 
     * @param surname New surname for the instructor.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's title.
     * 
     * @return Instructor's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's title.
     * 
     * @param title New title for the instructor.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's specialty.
     * 
     * @return Instructor's specialty.
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's specialty.
     * 
     * @param specialty New specialty for the instructor.
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor.
     * 
     * @return List of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor.
     * 
     * @param courses New list of courses.
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by the instructor.
     * 
     * @return Total number of enrolled students.
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course course : courses) {
            total += course.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota for all courses taught by the instructor.
     * 
     * @return Total remaining quota.
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota();
        }
        return totalQuota - calculateTotalEnrolledStudents();
    }
}

/**
 * Represents a course with its properties and behaviors.
 */
class Course {
    private String name;
    private String code;
    private Date startTime;
    private Date endTime;
    private List<String> courseDays;
    private int quota;
    private int weeklyHours;
    private Classroom classroom;
    private List<Enrollment> enrollments;

    /**
     * Default constructor for Course.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Parameterized constructor for Course.
     * 
     * @param name       Name of the course.
     * @param code       Code of the course.
     * @param startTime  Start time of the course.
     * @param endTime    End time of the course.
     * @param courseDays Days when the course is held.
     * @param quota      Quota for the course.
     * @param weeklyHours Weekly hours of the course.
     * @param classroom  Classroom where the course is held.
     */
    public Course(String name, String code, Date startTime, Date endTime, List<String> courseDays, int quota,
            int weeklyHours, Classroom classroom) {
        this.name = name;
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDays = courseDays;
        this.quota = quota;
        this.weeklyHours = weeklyHours;
        this.classroom = classroom;
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the course name.
     * 
     * @return Course name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name.
     * 
     * @param name New course name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course code.
     * 
     * @return Course code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code.
     * 
     * @param code New course code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     * 
     * @return Start time.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * 
     * @param startTime New start time.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * 
     * @return End time.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * 
     * @param endTime New end time.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days when the course is held.
     * 
     * @return List of course days.
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days when the course is held.
     * 
     * @param courseDays New list of course days.
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota for the course.
     * 
     * @return Course quota.
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota for the course.
     * 
     * @param quota New quota.
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     * 
     * @return Weekly hours.
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     * 
     * @param weeklyHours New weekly hours.
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where the course is held.
     * 
     * @return Classroom.
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is held.
     * 
     * @param classroom New classroom.
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments for the course.
     * 
     * @return List of enrollments.
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments for the course.
     * 
     * @param enrollments New list of enrollments.
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by a specified amount.
     * 
     * @param amount Amount to increase the quota by.
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by a specified amount.
     * 
     * @param amount Amount to decrease the quota by.
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours of the course.
     * 
     * @param newHours New weekly hours.
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days when the course is held.
     * 
     * @param newDays New list of course days.
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }
}

/**
 * Represents a student with their properties and behaviors.
 */
class Student {
    /**
     * Default constructor for Student.
     */
    public Student() {}

    /**
     * Enrolls the student in a course.
     * 
     * @param course Course to enroll in.
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment(this, course);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course.
     * 
     * @param course Course to drop.
     */
    public void dropCourse(Course course) {
        course.getEnrollments().removeIf(enrollment -> enrollment.getStudent() == this);
    }
}

/**
 * Represents an enrollment with its properties.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Parameterized constructor for Enrollment.
     * 
     * @param student Student enrolled in the course.
     * @param course  Course in which the student is enrolled.
     */
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    /**
     * Gets the grade of the enrollment.
     * 
     * @return Grade.
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the enrollment.
     * 
     * @param grade New grade.
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student enrolled.
     * 
     * @return Student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student enrolled.
     * 
     * @param student New student.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course of the enrollment.
     * 
     * @return Course.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course of the enrollment.
     * 
     * @param course New course.
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}

/**
 * Represents an academic program with its properties and behaviors.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor for AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Parameterized constructor for AcademicProgram.
     * 
     * @param courses List of courses in the academic program.
     */
    public AcademicProgram(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets the list of courses in the academic program.
     * 
     * @return List of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program.
     * 
     * @param courses New list of courses.
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * 
     * @return Total classroom capacity.
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course.
     * 
     * @param courseCode Code of the course.
     * @return Average grade.
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                double sum = 0;
                for (Enrollment enrollment : course.getEnrollments()) {
                    sum += enrollment.getGrade();
                }
                return sum / course.getEnrollments().size();
            }
        }
        return 0;
    }

    /**
     * Counts the number of courses taught on a specific day in the academic program.
     * 
     * @param day Day to check.
     * @return Number of courses on the specified day.
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getCourseDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}