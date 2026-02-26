import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an academic program that contains a collection of courses.
 */
 class AcademicProgram {

    /** List of courses that belong to this academic program. */
    private List<Course> courses;

    /** Unparameterized constructor required for tests. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** Getter for the courses list. */
    public List<Course> getCourses() {
        return courses;
    }

    /** Setter for the courses list. */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     *
     * @return the sum of capacities of each classroom assigned to the program's courses.
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
     * within the academic program.
     *
     * @param courseCode the unique code of the course whose average grade is required.
     * @return the average grade; returns 0.0 if the course is not found or has no grades.
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (Objects.equals(c.getCode(), courseCode)) {
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
     * Counts how many courses in the academic program are taught on a given day.
     *
     * @param day the day of the week (e.g., "Monday") to query.
     * @return the number of courses that have the specified day in their schedule.
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            if (c.getCourseDays().contains(day)) {
                count++;
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

    /** Unparameterized constructor required for tests. */
    public Classroom() {
    }

    /** Getter and setter methods. */
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

    /** Unparameterized constructor required for tests. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** Getter and setter methods. */
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
     * @param amount the amount to increase the quota by; must be non‑negative.
     * @throws IllegalArgumentException if {@code amount} is negative.
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount cannot be negative.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota for this course. The quota will never become negative.
     *
     * @param amount the amount to decrease the quota by; must be non‑negative.
     * @throws IllegalArgumentException if {@code amount} is negative.
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount cannot be negative.");
        }
        this.quota = Math.max(0, this.quota - amount);
    }

    /**
     * Changes the number of weekly teaching hours for the course.
     *
     * @param newHours the new number of weekly hours; must be positive.
     * @throws IllegalArgumentException if {@code newHours} is not positive.
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
     * @param newDays an array of day names (e.g., "Monday", "Wednesday").
     * @throws IllegalArgumentException if {@code newDays} is null.
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null) {
            throw new IllegalArgumentException("Course days cannot be null.");
        }
        this.courseDays.clear();
        for (String d : newDays) {
            this.courseDays.add(d);
        }
    }
}

/**
 * Represents an instructor who teaches and mentors courses.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** Unparameterized constructor required for tests. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /** Getter and setter methods. */
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
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return the sum of enrolled students for each course.
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) for all courses taught by this instructor.
     *
     * @return the sum of remaining seats across the instructor's courses.
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            int enrolled = c.getEnrollments().size();
            remaining += Math.max(0, c.getQuota() - enrolled);
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
    private List<Enrollment> enrollments;

    /** Unparameterized constructor required for tests. */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /** Getter and setter methods. */
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
     * Enrolls the student in a given course if there is remaining quota.
     *
     * @param course the course to enroll in.
     * @throws IllegalStateException if the course has no remaining quota.
     */
    public void enrollInCourse(Course course) {
        if (course.getEnrollments().size() >= course.getQuota()) {
            throw new IllegalStateException("Cannot enroll: course quota reached.");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // default grade
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a given course if the student is currently enrolled.
     *
     * @param course the course to drop.
     */
    public void dropCourse(Course course) {
        Enrollment toRemove = null;
        for (Enrollment e : this.enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove != null) {
            this.enrollments.remove(toRemove);
            course.getEnrollments().remove(toRemove);
        }
    }
}

/**
 * Represents the enrollment of a student in a specific course, including the grade earned.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** Unparameterized constructor required for tests. */
    public Enrollment() {
    }

    /** Getter and setter methods. */
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