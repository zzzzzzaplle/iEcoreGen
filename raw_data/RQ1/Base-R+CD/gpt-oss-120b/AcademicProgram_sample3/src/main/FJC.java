import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents an academic program that groups a collection of courses.
 */
 class AcademicProgram {

    /** List of courses that belong to this academic program. */
    private List<Course> courses;

    /** No‑argument constructor initializing the courses list. */
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

    /* -------------------- Business Methods -------------------- */

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of the classrooms assigned to each course.
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
     * identified by its course code.
     *
     * @param courseCode the unique code of the course whose average grade is required
     * @return the average grade; returns 0.0 if the course does not exist or has no enrollments
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
     * @param day the day to check (e.g., "Monday", "Tue", etc.)
     * @return the number of courses that have the specified day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null) {
                for (String d : days) {
                    if (d.equalsIgnoreCase(day)) {
                        count++;
                        break; // count each course only once
                    }
                }
            }
        }
        return count;
    }

    /**
     * Adds a course to this academic program.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from this academic program.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a classroom where a course takes place.
 */
class Classroom {

    private String id;
    private int capacity;
    private String floor;
    private String block;

    /** No‑argument constructor. */
    public Classroom() {
        // default values can be set later via setters
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
 * Represents an instructor who teaches and mentors students.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** No‑argument constructor initializing the courses list. */
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

    /* -------------------- Business Methods -------------------- */

    /**
     * Determines the total number of students enrolled in all courses taught by this instructor.
     *
     * @return the sum of enrollments across all courses belonging to the instructor
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            List<Enrollment> enrollments = c.getEnrollments();
            if (enrollments != null) {
                total += enrollments.size();
            }
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) for all courses taught by this instructor.
     *
     * @return the sum of remaining seats across the instructor's courses
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            int quota = c.getQuota();
            List<Enrollment> enrollments = c.getEnrollments();
            int enrolled = (enrollments == null) ? 0 : enrollments.size();
            remaining += Math.max(0, quota - enrolled);
        }
        return remaining;
    }

    /**
     * Adds a course to the instructor's teaching list.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a university course.
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

    /** No‑argument constructor initializing collections. */
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

    /* -------------------- Business Methods -------------------- */

    /**
     * Increases the quota (maximum number of students) for this course by the specified amount.
     *
     * @param amount the number of additional seats to add; must be non‑negative
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase must be non‑negative.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota for this course by the specified amount.
     * The quota will never be set below the current number of enrolled students.
     *
     * @param amount the number of seats to remove; must be non‑negative
     * @throws IllegalArgumentException if {@code amount} is negative or would reduce quota below current enrollment
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to decrease must be non‑negative.");
        }
        int enrolled = (enrollments == null) ? 0 : enrollments.size();
        int newQuota = this.quota - amount;
        if (newQuota < enrolled) {
            throw new IllegalArgumentException("Cannot decrease quota below current enrollment (" + enrolled + ").");
        }
        this.quota = newQuota;
    }

    /**
     * Changes the weekly teaching hours for this course.
     *
     * @param newHours the new number of weekly hours; must be positive
     * @throws IllegalArgumentException if {@code newHours} is not positive
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive.");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the list of days on which the course is taught.
     *
     * @param newDays an array of day strings (e.g., "Monday", "Wednesday")
     * @throws IllegalArgumentException if {@code newDays} is {@code null}
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null) {
            throw new IllegalArgumentException("newDays cannot be null.");
        }
        this.courseDays.clear();
        for (String d : newDays) {
            this.courseDays.add(d);
        }
    }

    /**
     * Adds an enrollment to this course.
     *
     * @param enrollment the enrollment to be added
     */
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    /**
     * Removes an enrollment from this course.
     *
     * @param enrollment the enrollment to be removed
     */
    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }
}

/**
 * Represents a student.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Enrollment> enrollments;

    /** No‑argument constructor initializing the enrollments list. */
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /* -------------------- Business Methods -------------------- */

    /**
     * Enrolls this student in the specified course.
     *
     * @param course the course to enroll in
     * @throws IllegalArgumentException if the course is {@code null} or the quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        int currentEnrolled = (course.getEnrollments() == null) ? 0 : course.getEnrollments().size();
        if (currentEnrolled >= course.getQuota()) {
            throw new IllegalArgumentException("Cannot enroll: quota for the course is full.");
        }

        // Create the enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // default grade

        // Add to both sides
        this.enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    /**
     * Drops this student from the specified course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (course == null) {
            return;
        }
        Enrollment target = null;
        for (Enrollment e : enrollments) {
            if (e.getCourse() != null && e.getCourse().equals(course)) {
                target = e;
                break;
            }
        }
        if (target != null) {
            enrollments.remove(target);
            course.removeEnrollment(target);
        }
    }
}

/**
 * Represents the enrollment of a student in a course, together with the achieved grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** No‑argument constructor. */
    public Enrollment() {
        // default values can be set later via setters
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