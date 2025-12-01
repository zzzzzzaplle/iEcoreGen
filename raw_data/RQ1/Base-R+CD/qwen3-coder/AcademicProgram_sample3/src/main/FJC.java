import java.util.*;

/**
 * Represents an academic program containing courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of all classrooms used in courses of this program
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
     * Calculates the average grade for students enrolled in a specific course.
     *
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of all students in the specified course
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
     * Counts the number of courses in this academic program that are taught on a specific day.
     *
     * @param day the day to check for courses (e.g., "Monday", "Tuesday")
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

    // Getters and setters
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
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
     * Default constructor initializes empty collections.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Increases the course quota by the specified amount.
     *
     * @param amount the amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the course quota by the specified amount.
     *
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0 && this.quota >= amount) {
            this.quota -= amount;
        }
    }

    /**
     * Changes the weekly hours for this course.
     *
     * @param newHours the new number of weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Changes the days when this course is taught.
     *
     * @param newDays array of new course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String day : newDays) {
                this.courseDays.add(day);
            }
        }
    }

    // Getters and setters
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
     * Default constructor.
     */
    public Classroom() {
    }

    // Getters and setters
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
     * Default constructor initializes an empty list of courses.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     *
     * @return the total number of enrolled students across all courses
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota (available spots) across all courses taught by this instructor.
     *
     * @return the total remaining quota across all courses
     */
    public int calculateRemainingQuota() {
        int totalRemaining = 0;
        for (Course course : courses) {
            int enrolled = course.getEnrollments().size();
            int remaining = course.getQuota() - enrolled;
            totalRemaining += Math.max(0, remaining); // Ensure non-negative
        }
        return totalRemaining;
    }

    // Getters and setters
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
}

/**
 * Represents a student who can enroll in courses.
 */
class Student {
    /**
     * Default constructor.
     */
    public Student() {
    }

    /**
     * Enrolls this student in a specified course.
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
        
        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // Default grade
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops this student from a specified course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        Enrollment toRemove = null;
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() == this) {
                toRemove = enrollment;
                break;
            }
        }
        
        if (toRemove != null) {
            course.getEnrollments().remove(toRemove);
        }
    }
}

/**
 * Represents an enrollment of a student in a course with a grade.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Default constructor.
     */
    public Enrollment() {
    }

    // Getters and setters
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