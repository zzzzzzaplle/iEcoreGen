import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program containing multiple courses.
 */
 class AcademicProgram {

    /** List of courses that belong to this academic program. */
    private List<Course> courses;

    /** No‑argument constructor. Initializes the courses list. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** Getter for courses. */
    public List<Course> getCourses() {
        return courses;
    }

    /** Setter for courses. */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of the classrooms of all courses
     */
    public int sumClassroomCapacity() {
        int total = 0;
        for (Course c : courses) {
            Classroom room = c.getClassroom();
            if (room != null) {
                total += room.getCapacity();
            }
        }
        return total;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course
     * within this academic program.
     *
     * @param courseCode the unique code of the course whose average grade is required
     * @return the average grade, or 0.0 if the course does not exist or has no grades
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().equals(courseCode)) {
                List<Enrollment> enrollments = c.getEnrollments();
                if (enrollments == null || enrollments.isEmpty()) {
                    return 0.0;
                }
                double sum = 0.0;
                for (Enrollment e : enrollments) {
                    sum += e.getGrade();
                }
                return sum / enrollments.size();
            }
        }
        return 0.0;
    }

    /**
     * Counts how many courses in this academic program are taught on a given day.
     *
     * @param day the day to be checked (e.g., "Monday")
     * @return the number of courses that have the specified day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null) {
                for (String d : days) {
                    if (d != null && d.equalsIgnoreCase(day)) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
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

    /** No‑argument constructor. */
    public Classroom() {
    }

    /** Getters and setters. */
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
 * Represents a course within an academic program.
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

    /** No‑argument constructor. Initializes collections. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** Getters and setters. */
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
     * Increases the quota (maximum number of students) for this course.
     *
     * @param amount the number of additional seats to add; must be positive
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be non‑negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota for this course.
     *
     * @param amount the number of seats to remove; must be positive and not reduce the quota below the current enrollment count
     * @throws IllegalArgumentException if amount is negative or would make quota smaller than current enrollments
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be non‑negative");
        }
        int newQuota = this.quota - amount;
        if (newQuota < this.enrollments.size()) {
            throw new IllegalArgumentException("Cannot set quota below current enrollment count");
        }
        this.quota = newQuota;
    }

    /**
     * Changes the weekly teaching hours for this course.
     *
     * @param newHours the new number of weekly hours; must be non‑negative
     * @throws IllegalArgumentException if newHours is negative
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours < 0) {
            throw new IllegalArgumentException("Weekly hours must be non‑negative");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days on which the course is taught.
     *
     * @param newDays an array of day names (e.g., {"Monday","Wednesday"}); cannot be null
     * @throws IllegalArgumentException if newDays is null
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null) {
            throw new IllegalArgumentException("New days array cannot be null");
        }
        this.courseDays.clear();
        for (String d : newDays) {
            this.courseDays.add(d);
        }
    }
}

/**
 * Represents an instructor who can teach multiple courses.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** No‑argument constructor. Initializes the courses list. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /** Getters and setters. */
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
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     *
     * @return the sum of enrolled students across all courses
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            if (c.getEnrollments() != null) {
                total += c.getEnrollments().size();
            }
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return the sum of remaining seats for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            int courseRemaining = c.getQuota() - (c.getEnrollments() != null ? c.getEnrollments().size() : 0);
            remaining += Math.max(courseRemaining, 0);
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in or drop courses.
 */
class Student {

    private String name;
    private String surname;
    private List<Course> enrolledCourses;
    private List<Enrollment> enrollments;

    /** No‑argument constructor. Initializes collections. */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** Getters and setters. */
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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a given course if quota permits.
     *
     * @param course the course to enroll in; must not be null
     * @throws IllegalArgumentException if the course is null or quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getEnrollments().size() >= course.getQuota()) {
            throw new IllegalArgumentException("Course quota is full");
        }
        // Create enrollment link
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // default grade

        // Update collections
        course.getEnrollments().add(enrollment);
        this.enrollments.add(enrollment);
        this.enrolledCourses.add(course);
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop; must not be null
     * @throws IllegalArgumentException if the course is null or the student is not enrolled in it
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        Enrollment toRemove = null;
        for (Enrollment e : this.enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in the specified course");
        }
        // Remove from both sides
        this.enrollments.remove(toRemove);
        this.enrolledCourses.remove(course);
        course.getEnrollments().remove(toRemove);
    }
}

/**
 * Represents the enrollment of a student in a specific course, including the grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** No‑argument constructor. */
    public Enrollment() {
    }

    /** Getters and setters. */
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