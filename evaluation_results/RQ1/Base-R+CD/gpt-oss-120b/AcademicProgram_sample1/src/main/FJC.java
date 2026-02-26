import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an academic program that contains many courses.
 */
 class AcademicProgram {

    /** List of courses belonging to this program. */
    private List<Course> courses;

    /** No‑argument constructor. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /* -------------------- Getters & Setters -------------------- */

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the academic program.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of each classroom assigned to the program's courses
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
     * identified by its code.
     *
     * @param courseCode the unique code of the course
     * @return the average grade, or 0.0 if the course has no enrollments or does not exist
     */
    public double calculateAverageGrade(String courseCode) {
        if (courseCode == null) {
            return 0.0;
        }
        for (Course c : courses) {
            if (courseCode.equals(c.getCode())) {
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
        return 0.0; // course not found
    }

    /**
     * Counts how many courses in this academic program are taught on a given day.
     *
     * @param day the day to search for (e.g., "Monday")
     * @return the number of courses that have the specified day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        if (day == null) {
            return 0;
        }
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null && days.contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a single course within an academic program.
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
    private Instructor instructor; // optional back‑reference

    /** No‑argument constructor. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /* -------------------- Getters & Setters -------------------- */

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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Increases the quota of this course by a given amount.
     *
     * @param amount the amount to increase; must be non‑negative
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be non‑negative.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota of this course by a given amount.
     *
     * @param amount the amount to decrease; must be non‑negative and not exceed current quota
     * @throws IllegalArgumentException if amount is negative or exceeds current quota
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be non‑negative.");
        }
        if (amount > this.quota) {
            throw new IllegalArgumentException("Cannot decrease quota below zero.");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly teaching hours for the course.
     *
     * @param newHours the new number of weekly hours; must be positive
     * @throws IllegalArgumentException if newHours is not positive
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive.");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the current list of course days with a new set of days.
     *
     * @param newDays an array of day names (e.g., "Monday", "Wednesday")
     * @throws IllegalArgumentException if newDays is null
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null) {
            throw new IllegalArgumentException("New days array cannot be null.");
        }
        this.courseDays.clear();
        for (String d : newDays) {
            if (d != null) {
                this.courseDays.add(d);
            }
        }
    }

    /**
     * Adds an enrollment to this course if there is remaining quota.
     *
     * @param enrollment the enrollment to add
     * @throws IllegalStateException if the quota is already full
     */
    public void addEnrollment(Enrollment enrollment) {
        Objects.requireNonNull(enrollment, "Enrollment cannot be null.");
        if (enrollments.size() >= quota) {
            throw new IllegalStateException("Cannot enroll: quota is full.");
        }
        enrollments.add(enrollment);
    }

    /**
     * Removes an enrollment from this course.
     *
     * @param enrollment the enrollment to remove
     */
    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    /**
     * Returns the number of students currently enrolled in this course.
     *
     * @return the size of the enrollments list
     */
    public int getCurrentEnrollmentCount() {
        return enrollments.size();
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

    /* -------------------- Getters & Setters -------------------- */

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
 * Represents an instructor who can teach and mentor students.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** No‑argument constructor. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /* -------------------- Getters & Setters -------------------- */

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
     * Adds a course to the instructor's list of courses.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            course.setInstructor(this);
        }
    }

    /**
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return the sum of enrolled students in each of the instructor's courses
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getCurrentEnrollmentCount();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return sum of (quota - current enrollment) for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            remaining += (c.getQuota() - c.getCurrentEnrollmentCount());
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in and drop courses.
 */
class Student {

    private String name;
    private String surname;
    private List<Enrollment> enrollments;

    /** No‑argument constructor. */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /* -------------------- Getters & Setters -------------------- */

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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a given course if the course has available quota.
     *
     * @param course the course to enroll in
     * @throws IllegalStateException if the course quota is full
     */
    public void enrollInCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null.");
        if (course.getCurrentEnrollmentCount() >= course.getQuota()) {
            throw new IllegalStateException("Cannot enroll: course quota is full.");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        // grade defaults to 0.0; can be set later
        this.enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null.");
        Enrollment toRemove = null;
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove != null) {
            enrollments.remove(toRemove);
            course.removeEnrollment(toRemove);
        }
    }
}

/**
 * Represents an enrollment of a student in a course, with an associated grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** No‑argument constructor. */
    public Enrollment() {
        this.grade = 0.0;
    }

    /* -------------------- Getters & Setters -------------------- */

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