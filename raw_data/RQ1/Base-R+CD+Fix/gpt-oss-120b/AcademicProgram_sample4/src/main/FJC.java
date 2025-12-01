import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an academic program which contains a collection of courses.
 */
 class AcademicProgram {

    /** List of courses belonging to the program. */
    private List<Course> courses = new ArrayList<>();

    /** No‑argument constructor. */
    public AcademicProgram() {
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
     * @return the sum of capacities of the classrooms of each course.
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
     * Calculates the average grade for the students enrolled in a specific course.
     *
     * @param courseCode the unique code identifying the course.
     * @return the average grade, or {@code 0.0} if the course has no enrollments
     *         or does not exist in the program.
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
     * Counts how many courses in this program are taught on a given day.
     *
     * @param day the day to search for (e.g., "Monday").
     * @return the number of courses that have the supplied day in their
     *         {@code courseDays} list.
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
 * Represents a classroom where a course takes place.
 */
class Classroom {

    private String id;
    private int capacity;
    private String floor;
    private String block;

    /** No‑argument constructor. */
    public Classroom() {
    }

    /** @return classroom identifier */
    public String getId() {
        return id;
    }

    /** @param id the classroom identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return maximum number of occupants */
    public int getCapacity() {
        return capacity;
    }

    /** @param capacity the capacity to set */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** @return floor description */
    public String getFloor() {
        return floor;
    }

    /** @param floor the floor to set */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /** @return block information */
    public String getBlock() {
        return block;
    }

    /** @param block the block to set */
    public void setBlock(String block) {
        this.block = block;
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
    private List<String> courseDays = new ArrayList<>();
    private int quota;
    private int weeklyHours;
    private Classroom classroom;
    private List<Enrollment> enrollments = new ArrayList<>();

    /** No‑argument constructor. */
    public Course() {
    }

    /** @return course name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return unique course code */
    public String getCode() {
        return code;
    }

    /** @param code the code to set */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return start time of the course */
    public Date getStartTime() {
        return startTime;
    }

    /** @param startTime the start time to set */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /** @return end time of the course */
    public Date getEndTime() {
        return endTime;
    }

    /** @param endTime the end time to set */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /** @return immutable list of days when the course meets */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /** @param courseDays the list of days to set */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /** @return maximum number of students allowed */
    public int getQuota() {
        return quota;
    }

    /** @param quota the quota to set */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /** @return weekly teaching hours */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /** @param weeklyHours the weekly hours to set */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /** @return classroom where the course is held */
    public Classroom getClassroom() {
        return classroom;
    }

    /** @param classroom the classroom to set */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /** @return list of enrollments for this course */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /** @param enrollments the enrollments to set */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the quota of the course by a given amount.
     *
     * @param amount the number of additional seats to add; must be non‑negative.
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount cannot be negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota of the course by a given amount.
     *
     * @param amount the number of seats to remove; must be non‑negative and
     *               not reduce the quota below the current enrollment count.
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount cannot be negative");
        }
        int newQuota = this.quota - amount;
        if (newQuota < enrollments.size()) {
            throw new IllegalArgumentException(
                    "Cannot set quota lower than current number of enrolled students");
        }
        this.quota = newQuota;
    }

    /**
     * Changes the weekly teaching hours of the course.
     *
     * @param newHours the new number of weekly hours; must be positive.
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the days on which the course meets.
     *
     * @param newDays an array of day names (e.g., {"Monday","Wednesday"}).
     *                The array must not be {@code null} or empty.
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null || newDays.length == 0) {
            throw new IllegalArgumentException("Course days cannot be null or empty");
        }
        this.courseDays.clear();
        for (String d : newDays) {
            this.courseDays.add(d);
        }
    }

    /**
     * Adds an enrollment to this course.
     *
     * @param enrollment the enrollment to add; must not be {@code null}.
     * @throws IllegalStateException if the course quota is already full.
     */
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null");
        }
        if (enrollments.size() >= quota) {
            throw new IllegalStateException("Course quota exceeded");
        }
        enrollments.add(enrollment);
    }

    /**
     * Removes an enrollment belonging to the supplied student.
     *
     * @param student the student whose enrollment should be removed.
     * @return {@code true} if an enrollment was removed, {@code false} otherwise.
     */
    public boolean removeEnrollmentByStudent(Student student) {
        return enrollments.removeIf(e -> e.getStudent().equals(student));
    }
}

/**
 * Represents an instructor who can teach several courses.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses = new ArrayList<>();

    /** No‑argument constructor. */
    public Instructor() {
    }

    /** @return instructor's first name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return instructor's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return academic title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return area of specialty */
    public String getSpecialty() {
        return specialty;
    }

    /** @param specialty the specialty to set */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /** @return list of courses taught by the instructor */
    public List<Course> getCourses() {
        return courses;
    }

    /** @param courses the list of courses to set */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Determines the total number of students enrolled in all courses taught by this instructor.
     *
     * @return the sum of enrollment counts across all associated courses.
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota across all courses taught by this instructor.
     *
     * @return the sum of (quota - current enrollment) for each course.
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            remaining += (c.getQuota() - c.getEnrollments().size());
        }
        return remaining;
    }

    /**
     * Adds a course to the instructor's teaching list.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a student that can enroll in and drop courses.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;

    /** No‑argument constructor. */
    public Student() {
    }

    /** @return student's first name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return student's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return unique student identifier */
    public String getStudentId() {
        return studentId;
    }

    /** @param studentId the identifier to set */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Enrolls the student in a given course if there is remaining quota.
     *
     * @param course the course to enroll in; must not be {@code null}.
     * @throws IllegalStateException if the course quota is already full.
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        // prevent duplicate enrollment
        for (Enrollment e : course.getEnrollments()) {
            if (e.getStudent().equals(this)) {
                return; // already enrolled
            }
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // default grade
        course.addEnrollment(enrollment);
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop; must not be {@code null}.
     * @return {@code true} if the student was enrolled and removed, {@code false} otherwise.
     */
    public boolean dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        return course.removeEnrollmentByStudent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}

/**
 * Represents a specific enrollment of a student in a course,
 * together with the student's grade for that course.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** No‑argument constructor. */
    public Enrollment() {
    }

    /** @return the student's grade */
    public double getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /** @return the student linked to this enrollment */
    public Student getStudent() {
        return student;
    }

    /** @param student the student to set */
    public void setStudent(Student student) {
        this.student = student;
    }

    /** @return the course linked to this enrollment */
    public Course getCourse() {
        return course;
    }

    /** @param course the course to set */
    public void setCourse(Course course) {
        this.course = course;
    }
}