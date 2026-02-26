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
     * Default constructor for Classroom.
     */
    public Classroom() {}

    /**
     * Constructor for Classroom with parameters.
     * @param id Unique identifier for the classroom.
     * @param capacity Maximum number of students the classroom can hold.
     * @param floor Floor where the classroom is located.
     * @param block Block where the classroom is located.
     */
    public Classroom(String id, int capacity, String floor, String block) {
        this.id = id;
        this.capacity = capacity;
        this.floor = floor;
        this.block = block;
    }

    /**
     * Gets the classroom id.
     * @return The id of the classroom.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the classroom id.
     * @param id The id to set for the classroom.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the classroom capacity.
     * @return The capacity of the classroom.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the classroom capacity.
     * @param capacity The capacity to set for the classroom.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where the classroom is located.
     * @return The floor of the classroom.
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the classroom is located.
     * @param floor The floor to set for the classroom.
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where the classroom is located.
     * @return The block of the classroom.
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where the classroom is located.
     * @param block The block to set for the classroom.
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
     * Default constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Constructor for Instructor with parameters.
     * @param name Name of the instructor.
     * @param surname Surname of the instructor.
     * @param title Title of the instructor.
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
     * @return The name of the instructor.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's name.
     * @param name The name to set for the instructor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's surname.
     * @return The surname of the instructor.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's surname.
     * @param surname The surname to set for the instructor.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's title.
     * @return The title of the instructor.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's title.
     * @param title The title to set for the instructor.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's specialty.
     * @return The specialty of the instructor.
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's specialty.
     * @param specialty The specialty to set for the instructor.
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor.
     * @return The list of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor.
     * @param courses The list of courses to set.
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by the instructor.
     * @return The total number of enrolled students.
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
     * @return The total remaining quota.
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
     * Default constructor for Course.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Constructor for Course with parameters.
     * @param name Name of the course.
     * @param code Code of the course.
     * @param startTime Start time of the course.
     * @param endTime End time of the course.
     * @param quota Quota for the course.
     * @param weeklyHours Weekly hours of the course.
     * @param classroom Classroom where the course is held.
     */
    public Course(String name, String code, Date startTime, Date endTime, int quota, int weeklyHours, Classroom classroom) {
        this.name = name;
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quota = quota;
        this.weeklyHours = weeklyHours;
        this.classroom = classroom;
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the course name.
     * @return The name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name.
     * @param name The name to set for the course.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course code.
     * @return The code of the course.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code.
     * @param code The code to set for the course.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     * @return The start time of the course.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * @param startTime The start time to set for the course.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * @return The end time of the course.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * @param endTime The end time to set for the course.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days on which the course is held.
     * @return The list of course days.
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days on which the course is held.
     * @param courseDays The list of days to set for the course.
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota for the course.
     * @return The quota of the course.
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota for the course.
     * @param quota The quota to set for the course.
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     * @return The weekly hours of the course.
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     * @param weeklyHours The weekly hours to set for the course.
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where the course is held.
     * @return The classroom of the course.
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is held.
     * @param classroom The classroom to set for the course.
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments for the course.
     * @return The list of enrollments.
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments for the course.
     * @param enrollments The list of enrollments to set.
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by a specified amount.
     * @param amount The amount to increase the quota by.
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by a specified amount.
     * @param amount The amount to decrease the quota by.
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
        if (this.quota < 0) {
            this.quota = 0;
        }
    }

    /**
     * Changes the weekly hours of the course.
     * @param newHours The new weekly hours.
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days on which the course is held.
     * @param newDays The new list of course days.
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
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
     * Default constructor for Enrollment.
     */
    public Enrollment() {}

    /**
     * Constructor for Enrollment with parameters.
     * @param grade Grade of the student in the course.
     * @param student Student enrolled in the course.
     * @param course Course in which the student is enrolled.
     */
    public Enrollment(double grade, Student student, Course course) {
        this.grade = grade;
        this.student = student;
        this.course = course;
    }

    /**
     * Gets the grade of the student.
     * @return The grade of the student.
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the student.
     * @param grade The grade to set for the student.
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student enrolled.
     * @return The enrolled student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student enrolled.
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course in which the student is enrolled.
     * @return The course of the enrollment.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course in which the student is enrolled.
     * @param course The course to set for the enrollment.
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}

/**
 * Represents a Student with their properties and behaviors.
 */
class Student {
    /**
     * Default constructor for Student.
     */
    public Student() {}

    /**
     * Enrolls the student in a course.
     * @param course The course to enroll in.
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment(0.0, this, course);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course.
     * @param course The course to drop.
     */
    public void dropCourse(Course course) {
        course.getEnrollments().removeIf(enrollment -> enrollment.getStudent() == this);
    }
}

/**
 * Represents an Academic Program with its properties and behaviors.
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
     * Constructor for AcademicProgram with a list of courses.
     * @param courses List of courses in the academic program.
     */
    public AcademicProgram(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets the list of courses in the academic program.
     * @return The list of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program.
     * @param courses The list of courses to set.
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * @return The total capacity.
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course.
     * @param courseCode The code of the course.
     * @return The average grade.
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                double sum = 0;
                for (Enrollment enrollment : course.getEnrollments()) {
                    sum += enrollment.getGrade();
                }
                return course.getEnrollments().isEmpty() ? 0 : sum / course.getEnrollments().size();
            }
        }
        return 0;
    }

    /**
     * Counts the number of courses taught on a specific day.
     * @param day The day to check.
     * @return The number of courses on the specified day.
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