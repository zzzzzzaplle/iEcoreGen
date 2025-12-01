import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 * Represents an academic program that aggregates multiple courses.
 */
 class AcademicProgram {

    /** List of courses belonging to this academic program. */
    private List<Course> courses;

    /** Default constructor. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** @return the list of courses */
    public List<Course> getCourses() {
        return courses;
    }

    /** @param courses the list of courses to set */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of the classrooms assigned to the program's courses
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
     * Calculates the average grade of all students enrolled in a specific course.
     *
     * @param courseCode the unique code of the course whose average grade is required
     * @return the average grade, or {@code 0.0} if the course is not found or has no enrollments
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().equals(courseCode)) {
                List<Enrollment> enrollments = c.getEnrollments();
                if (enrollments.isEmpty()) {
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
     * Counts how many courses in the program are taught on a given day.
     *
     * @param day the day to search for (e.g., "Monday")
     * @return the number of courses whose courseDays list contains the specified day
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null) {
                for (String d : days) {
                    if (d.equalsIgnoreCase(day)) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
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
     * Removes a course from the academic program.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
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

    /** Default constructor. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** Getters and setters for all fields */
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
     * Increases the enrollment quota by a given amount.
     *
     * @param amount the amount to increase the quota; must be non‑negative
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be non‑negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the enrollment quota by a given amount.
     *
     * @param amount the amount to decrease the quota; must be non‑negative and not less than current enrollment count
     * @throws IllegalArgumentException if {@code amount} is negative or would make the quota lower than current enrollments
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be non‑negative");
        }
        int newQuota = this.quota - amount;
        if (newQuota < this.enrollments.size()) {
            throw new IllegalArgumentException("Quota cannot be less than number of already enrolled students");
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
            throw new IllegalArgumentException("Weekly hours must be positive");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the days on which the course is taught.
     *
     * @param newDays an array of day names (e.g., {"Monday","Wednesday"}); cannot be {@code null}
     * @throws IllegalArgumentException if {@code newDays} is {@code null}
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null) {
            throw new IllegalArgumentException("Course days cannot be null");
        }
        this.courseDays = new ArrayList<>(Arrays.asList(newDays));
    }

    /**
     * Adds an enrollment to this course.
     *
     * @param enrollment the enrollment to add
     */
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            if (enrollments.size() >= quota) {
                throw new IllegalStateException("Course quota reached, cannot add more enrollments");
            }
            enrollments.add(enrollment);
        }
    }

    /**
     * Removes an enrollment from this course.
     *
     * @param enrollment the enrollment to remove
     */
    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
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

    /** Default constructor. */
    public Classroom() {
    }

    /** Getters and setters */
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
 * Represents an instructor who can teach multiple courses.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** Default constructor. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /** Getters and setters */
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
     * @return total enrolled student count across all courses
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return sum of remaining seats for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            int courseRemaining = c.getQuota() - c.getEnrollments().size();
            remaining += Math.max(courseRemaining, 0);
        }
        return remaining;
    }

    /**
     * Assigns a course to this instructor.
     *
     * @param course the course to assign
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from this instructor's list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a student who can enroll in and drop courses.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Enrollment> enrollments;

    /** Default constructor. */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /** Getters and setters */
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

    /**
     * Enrolls this student in a given course if quota allows.
     *
     * @param course the course to enroll in
     * @throws IllegalStateException if the course quota is full
     * @throws IllegalArgumentException if the student is already enrolled in the course
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        // check if already enrolled
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                throw new IllegalArgumentException("Student already enrolled in this course");
            }
        }
        if (course.getEnrollments().size() >= course.getQuota()) {
            throw new IllegalStateException("Course quota reached, cannot enroll");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        // default grade 0.0
        enrollment.setGrade(0.0);
        // add to both sides
        this.enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    /**
     * Drops this student from a given course.
     *
     * @param course the course to drop
     * @throws IllegalArgumentException if the student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        Enrollment toRemove = null;
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in the specified course");
        }
        enrollments.remove(toRemove);
        course.removeEnrollment(toRemove);
    }
}

/**
 * Represents an enrollment tying a student to a course together with a grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** Default constructor. */
    public Enrollment() {
    }

    /** Getters and setters */
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