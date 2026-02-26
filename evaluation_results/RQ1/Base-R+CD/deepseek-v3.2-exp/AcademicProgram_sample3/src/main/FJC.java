import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program containing multiple courses
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in the academic program
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses for the academic program
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
     * @return Total capacity of all classrooms
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            if (course.getClassroom() != null) {
                totalCapacity += course.getClassroom().getCapacity();
            }
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course
     * @param courseCode The code of the course to calculate average grade for
     * @return Average grade of students in the specified course
     * @throws IllegalArgumentException if course with given code is not found
     * @throws ArithmeticException if no students are enrolled in the course
     */
    public double calculateAverageGrade(String courseCode) {
        Course targetCourse = null;
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                targetCourse = course;
                break;
            }
        }
        
        if (targetCourse == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found");
        }
        
        List<Enrollment> enrollments = targetCourse.getEnrollments();
        if (enrollments.isEmpty()) {
            throw new ArithmeticException("No students enrolled in course " + courseCode);
        }
        
        double totalGrade = 0;
        for (Enrollment enrollment : enrollments) {
            totalGrade += enrollment.getGrade();
        }
        
        return totalGrade / enrollments.size();
    }

    /**
     * Counts the number of courses taught on a specific day
     * @param day The day to search for courses (e.g., "Monday", "Tuesday")
     * @return Number of courses taught on the specified day
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getCourseDays() != null && course.getCourseDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a course in the academic program
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
     * Default constructor initializes empty lists for course days and enrollments
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by the specified amount
     * @param amount The amount to increase the quota by
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount
     * @param amount The amount to decrease the quota by
     * @throws IllegalArgumentException if amount is negative or if new quota would be less than current enrollments
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (this.quota - amount < enrollments.size()) {
            throw new IllegalArgumentException("New quota cannot be less than current enrollments");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours for the course
     * @param newHours The new weekly hours value
     * @throws IllegalArgumentException if new hours is negative
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days to the new set of days
     * @param newDays Array of new course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String day : newDays) {
                this.courseDays.add(day);
            }
        }
    }
}

/**
 * Represents a classroom with capacity and location information
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Default constructor
     */
    public Classroom() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor who teaches courses
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor initializes empty list of courses
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor
     * @return Total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses taught by this instructor
     * @return Total remaining quota across all courses
     */
    public int calculateRemainingQuota() {
        int remainingQuota = 0;
        for (Course course : courses) {
            int enrolledStudents = course.getEnrollments().size();
            remainingQuota += (course.getQuota() - enrolledStudents);
        }
        return remainingQuota;
    }
}

/**
 * Represents a student who can enroll in courses
 */
class Student {
    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * Enrolls the student in a course
     * @param course The course to enroll in
     */
    public void enrollInCourse(Course course) {
        // Implementation would typically involve creating an enrollment record
    }

    /**
     * Drops the student from a course
     * @param course The course to drop from
     */
    public void dropCourse(Course course) {
        // Implementation would typically involve removing an enrollment record
    }
}

/**
 * Represents an enrollment record linking a student to a course with a grade
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Default constructor
     */
    public Enrollment() {
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}