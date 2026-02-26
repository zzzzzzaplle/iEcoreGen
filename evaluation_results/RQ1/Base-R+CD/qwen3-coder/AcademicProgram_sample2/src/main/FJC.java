import java.util.*;

/**
 * Represents an academic program containing courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Constructs an AcademicProgram with empty courses list.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total capacity of all classrooms used in this Academic Program.
     *
     * @return the sum of capacities of all classrooms
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
     * Calculates the average grade for students enrolled in a specific course within this Academic Program.
     *
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of students in the specified course
     * @throws IllegalArgumentException if no course with the given code exists
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
            return 0.0;
        }

        double sumGrades = 0.0;
        for (Enrollment enrollment : enrollments) {
            sumGrades += enrollment.getGrade();
        }

        return sumGrades / enrollments.size();
    }

    /**
     * Counts the number of courses in this Academic Program that are taught on a specific day.
     *
     * @param day the day to check for courses
     * @return the number of courses taught on the specified day
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

    // Getters and Setters
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}

/**
 * Represents a course in an academic program.
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
     * Constructs a Course with default values.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Increases the quota of this course by the specified amount.
     *
     * @param amount the amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the quota of this course by the specified amount.
     *
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0 && this.quota - amount >= 0) {
            this.quota -= amount;
        }
    }

    /**
     * Changes the weekly hours of this course.
     *
     * @param newHours the new weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Changes the days when this course is taught.
     *
     * @param newDays the new array of course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String day : newDays) {
                this.courseDays.add(day);
            }
        }
    }

    // Getters and Setters
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

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }
}

/**
 * Represents a classroom where courses are held.
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Constructs a Classroom with default values.
     */
    public Classroom() {
    }

    // Getters and Setters
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
 * Represents an instructor who teaches courses.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Constructs an Instructor with empty courses list.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total number of students enrolled in all courses of this instructor.
     *
     * @return the total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this instructor.
     *
     * @return the total remaining quota across all courses
     */
    public int calculateRemainingQuota() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            int enrolledCount = course.getEnrollments().size();
            int remaining = course.getQuota() - enrolledCount;
            totalRemainingQuota += Math.max(0, remaining);
        }
        return totalRemainingQuota;
    }

    // Getters and Setters
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

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}

/**
 * Represents a student who enrolls in courses.
 */
class Student {
    /**
     * Constructs a Student with default values.
     */
    public Student() {
    }

    /**
     * Enrolls this student in the specified course.
     *
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        // Check if student is already enrolled
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() == this) {
                return; // Already enrolled
            }
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0);
        course.addEnrollment(enrollment);
    }

    /**
     * Drops this student from the specified course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() == this) {
                enrollmentToRemove = enrollment;
                break;
            }
        }
        
        if (enrollmentToRemove != null) {
            course.getEnrollments().remove(enrollmentToRemove);
        }
    }
}

/**
 * Represents an enrollment of a student in a course.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Constructs an Enrollment with default values.
     */
    public Enrollment() {
    }

    // Getters and Setters
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