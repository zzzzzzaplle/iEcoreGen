import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

/**
 * Represents an academic program that contains a collection of courses.
 */
 class AcademicProgram {

    /** List of courses belonging to this academic program. */
    private List<Course> courses;

    /** No‑argument constructor initializing the courses list. */
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
     * @return the sum of capacities of each classroom associated with the program's courses
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
     * @param day the day to search for (e.g., "Monday")
     * @return the number of courses that have the specified day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null && days.contains(day)) {
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
    private Instructor instructor; // optional back‑reference for convenience

    /** No‑argument constructor initializing collection fields. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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

    // ---------- Business methods ----------
    /**
     * Increases the quota of this course by a given amount.
     *
     * @param amount the number of seats to add (must be non‑negative)
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the quota of this course by a given amount.
     *
     * @param amount the number of seats to remove (must be non‑negative and not reduce quota below current enrollment)
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0) {
            int minQuota = this.enrollments.size(); // cannot go below already enrolled students
            this.quota = Math.max(minQuota, this.quota - amount);
        }
    }

    /**
     * Changes the weekly teaching hours for this course.
     *
     * @param newHours the new number of weekly hours (must be non‑negative)
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Replaces the list of days on which the course is taught.
     *
     * @param newDays an array of day names (e.g., {"Monday","Wednesday"})
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String d : newDays) {
                this.courseDays.add(d);
            }
        }
    }

    /**
     * Registers an enrollment for a student if quota permits.
     *
     * @param enrollment the enrollment to add
     * @return true if enrollment succeeded, false otherwise (e.g., quota full)
     */
    public boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            return false;
        }
        if (this.enrollments.size() < this.quota) {
            this.enrollments.add(enrollment);
            return true;
        }
        return false;
    }

    /**
     * Removes an enrollment belonging to a specific student.
     *
     * @param student the student whose enrollment should be removed
     * @return true if an enrollment was removed, false otherwise
     */
    public boolean removeEnrollment(Student student) {
        if (student == null) {
            return false;
        }
        Iterator<Enrollment> it = this.enrollments.iterator();
        while (it.hasNext()) {
            Enrollment e = it.next();
            if (e.getStudent() == student) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a physical classroom.
 */
class Classroom {

    private String id;
    private int capacity;
    private String floor;
    private String block;

    /** No‑argument constructor. */
    public Classroom() {
    }

    // ---------- Getters and Setters ----------
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

    /** No‑argument constructor initializing the courses list. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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

    // ---------- Business methods ----------
    /**
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return the sum of enrollment counts for each course
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            if (c != null && c.getEnrollments() != null) {
                total += c.getEnrollments().size();
            }
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return the sum of (quota - current enrollment) for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            if (c != null) {
                int enrolled = (c.getEnrollments() != null) ? c.getEnrollments().size() : 0;
                remaining += Math.max(0, c.getQuota() - enrolled);
            }
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
            course.setInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        if (courses.remove(course)) {
            course.setInstructor(null);
        }
    }
}

/**
 * Represents a student who can enroll in or drop courses.
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

    // ---------- Getters and Setters ----------
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

    // ---------- Business methods ----------
    /**
     * Enrolls the student in a given course if quota permits.
     *
     * @param course the course to enroll in
     * @return true if enrollment succeeded, false otherwise
     */
    public boolean enrollInCourse(Course course) {
        if (course == null) {
            return false;
        }
        // Check if already enrolled
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                return false; // already enrolled
            }
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        // default grade could be 0.0 until set later
        enrollment.setGrade(0.0);
        boolean added = course.addEnrollment(enrollment);
        if (added) {
            this.enrollments.add(enrollment);
        }
        return added;
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop
     * @return true if the student was successfully dropped, false otherwise
     */
    public boolean dropCourse(Course course) {
        if (course == null) {
            return false;
        }
        Iterator<Enrollment> it = this.enrollments.iterator();
        while (it.hasNext()) {
            Enrollment e = it.next();
            if (e.getCourse() == course) {
                // Remove from course's enrollment list as well
                course.removeEnrollment(this);
                it.remove();
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents the enrollment of a student in a specific course, together with the obtained grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** No‑argument constructor. */
    public Enrollment() {
    }

    // ---------- Getters and Setters ----------
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