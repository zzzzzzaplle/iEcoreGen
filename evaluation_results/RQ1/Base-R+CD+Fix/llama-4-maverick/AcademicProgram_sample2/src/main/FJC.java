import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Classroom with its properties and behaviors.
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Unparameterized constructor for Classroom.
     */
    public Classroom() {}

    /**
     * Constructor for Classroom.
     * 
     * @param id       the id of the classroom
     * @param capacity the capacity of the classroom
     * @param floor    the floor where the classroom is located
     * @param block    the block where the classroom is located
     */
    public Classroom(String id, int capacity, String floor, String block) {
        this.id = id;
        this.capacity = capacity;
        this.floor = floor;
        this.block = block;
    }

    /**
     * Gets the id of the classroom.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the classroom.
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of the classroom.
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     * 
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where the classroom is located.
     * 
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the classroom is located.
     * 
     * @param floor the floor to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where the classroom is located.
     * 
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where the classroom is located.
     * 
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an Instructor with their properties and behaviors.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Unparameterized constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Constructor for Instructor.
     * 
     * @param name      the name of the instructor
     * @param surname   the surname of the instructor
     * @param title     the title of the instructor
     * @param specialty the specialty of the instructor
     */
    public Instructor(String name, String surname, String title, String specialty) {
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.specialty = specialty;
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name of the instructor.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     * 
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     * 
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     * 
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the courses taught by the instructor.
     * 
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses taught by the instructor.
     * 
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
     * 
     * @return the total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course course : courses) {
            total += course.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     * 
     * @return the remaining quota
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota() - course.getEnrollments().size();
        }
        return totalQuota;
    }
}

/**
 * Represents a Course with its properties and behaviors.
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
     * Unparameterized constructor for Course.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Constructor for Course.
     * 
     * @param name       the name of the course
     * @param code       the code of the course
     * @param startTime  the start time of the course
     * @param endTime    the end time of the course
     * @param courseDays the days when the course is held
     * @param quota      the quota of the course
     * @param weeklyHours the weekly hours of the course
     * @param classroom  the classroom where the course is held
     */
    public Course(String name, String code, Date startTime, Date endTime, List<String> courseDays, int quota, int weeklyHours, Classroom classroom) {
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
     * Gets the name of the course.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     * 
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     * 
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * 
     * @param startTime the start time to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * 
     * @return the end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * 
     * @param endTime the end time to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days when the course is held.
     * 
     * @return the course days
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days when the course is held.
     * 
     * @param courseDays the course days to set
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota of the course.
     * 
     * @return the quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota of the course.
     * 
     * @param quota the quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     * 
     * @return the weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     * 
     * @param weeklyHours the weekly hours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where the course is held.
     * 
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is held.
     * 
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the enrollments in the course.
     * 
     * @return the enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the enrollments in the course.
     * 
     * @param enrollments the enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the quota of the course by the given amount.
     * 
     * @param amount the amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the quota of the course by the given amount.
     * 
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours of the course to the given new hours.
     * 
     * @param newHours the new weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days to the given new days.
     * 
     * @param newDays the new course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }
}

/**
 * Represents an Academic Program with its properties and behaviors.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Unparameterized constructor for AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Constructor for AcademicProgram.
     * 
     * @param courses the courses in the academic program
     */
    public AcademicProgram(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets the courses in the academic program.
     * 
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses in the academic program.
     * 
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * 
     * @return the total capacity
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for the students enrolled in the course with the given code.
     * 
     * @param courseCode the code of the course
     * @return the average grade
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
     * Counts the number of courses in the academic program that are taught on the given day.
     * 
     * @param day the day to count courses for
     * @return the number of courses on the given day
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

/**
 * Represents a Student with their properties and behaviors.
 */
class Student {
    /**
     * Unparameterized constructor for Student.
     */
    public Student() {}

    /**
     * Enrolls the student in the given course.
     * 
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment(this, course);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from the given course.
     * 
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        course.getEnrollments().removeIf(enrollment -> enrollment.getStudent().equals(this));
    }
}

/**
 * Represents an Enrollment with its properties.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Constructor for Enrollment.
     * 
     * @param student the student enrolled
     * @param course  the course enrolled in
     */
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    /**
     * Gets the grade of the enrollment.
     * 
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the enrollment.
     * 
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student enrolled.
     * 
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student enrolled.
     * 
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course enrolled in.
     * 
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course enrolled in.
     * 
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}