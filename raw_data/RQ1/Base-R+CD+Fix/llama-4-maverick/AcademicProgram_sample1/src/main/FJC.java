import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an Academic Program with a list of courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Unparameterized constructor to initialize an AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in the Academic Program.
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the Academic Program.
     * @param courses List of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program.
     * @return Total capacity of all classrooms
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course within the Academic Program.
     * @param courseCode Code of the course
     * @return Average grade of the students in the course
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course.calculateAverageGrade();
            }
        }
        return 0.0;
    }

    /**
     * Counts the total number of courses in the Academic Program that are taught on a given day.
     * @param day Day of the week
     * @return Number of courses on the specified day
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
 * Represents a Course with its details and enrollments.
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
     * Unparameterized constructor to initialize a Course.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of the course.
     * @return Name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     * @param name Name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     * @return Code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     * @param code Code of the course
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     * @return Start time of the course
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * @param startTime Start time of the course
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * @return End time of the course
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * @param endTime End time of the course
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days of the course.
     * @return List of course days
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days of the course.
     * @param courseDays List of course days
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota of the course.
     * @return Quota of the course
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota of the course.
     * @param quota Quota of the course
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     * @return Weekly hours of the course
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     * @param weeklyHours Weekly hours of the course
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom of the course.
     * @return Classroom of the course
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom of the course.
     * @param classroom Classroom of the course
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the enrollments in the course.
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the enrollments in the course.
     * @param enrollments List of enrollments
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the quota of the course by a specified amount.
     * @param amount Amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the quota of the course by a specified amount.
     * @param amount Amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours of the course.
     * @param newHours New weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days of the course.
     * @param newDays New days of the course
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }

    /**
     * Calculates the average grade of the students enrolled in the course.
     * @return Average grade of the students
     */
    public double calculateAverageGrade() {
        double sum = 0;
        for (Enrollment enrollment : enrollments) {
            sum += enrollment.getGrade();
        }
        return enrollments.isEmpty() ? 0.0 : sum / enrollments.size();
    }
}

/**
 * Represents a Classroom with its details.
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Unparameterized constructor to initialize a Classroom.
     */
    public Classroom() {}

    /**
     * Gets the ID of the classroom.
     * @return ID of the classroom
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the classroom.
     * @param id ID of the classroom
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of the classroom.
     * @return Capacity of the classroom
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     * @param capacity Capacity of the classroom
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor of the classroom.
     * @return Floor of the classroom
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor of the classroom.
     * @param floor Floor of the classroom
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block of the classroom.
     * @return Block of the classroom
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block of the classroom.
     * @param block Block of the classroom
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an Instructor with their details and courses.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Unparameterized constructor to initialize an Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name of the instructor.
     * @return Name of the instructor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     * @param name Name of the instructor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     * @return Surname of the instructor
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     * @param surname Surname of the instructor
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     * @return Title of the instructor
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     * @param title Title of the instructor
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     * @return Specialty of the instructor
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     * @param specialty Specialty of the instructor
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the courses taught by the instructor.
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses taught by the instructor.
     * @param courses List of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
     * @return Total number of enrolled students
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
     * @return Remaining quota
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        int totalEnrolled = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota();
            totalEnrolled += course.getEnrollments().size();
        }
        return totalQuota - totalEnrolled;
    }
}

/**
 * Represents a Student with their enrollments.
 */
class Student {
    private List<Enrollment> enrollments;

    /**
     * Unparameterized constructor to initialize a Student.
     */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the enrollments of the student.
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the enrollments of the student.
     * @param enrollments List of enrollments
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a course.
     * @param course Course to enroll in
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(this);
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course.
     * @param course Course to drop
     */
    public void dropCourse(Course course) {
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                enrollmentToRemove = enrollment;
                break;
            }
        }
        if (enrollmentToRemove != null) {
            this.enrollments.remove(enrollmentToRemove);
            course.getEnrollments().remove(enrollmentToRemove);
        }
    }
}

/**
 * Represents an Enrollment with its details.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Unparameterized constructor to initialize an Enrollment.
     */
    public Enrollment() {}

    /**
     * Gets the grade of the enrollment.
     * @return Grade of the enrollment
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the enrollment.
     * @param grade Grade of the enrollment
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student of the enrollment.
     * @return Student of the enrollment
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student of the enrollment.
     * @param student Student of the enrollment
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course of the enrollment.
     * @return Course of the enrollment
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course of the enrollment.
     * @param course Course of the enrollment
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}