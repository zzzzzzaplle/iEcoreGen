import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an academic program that groups several courses.
 */
 class AcademicProgram {

    /** List of courses that belong to this program. */
    private List<Course> courses;

    /** No‑argument constructor required by the task. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /* -------------------- getters & setters -------------------- */

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /** Adds a course to the program. */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /** Removes a course from the program. */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    // -----------------------------------------------------------------
    // Functional requirement implementations
    // -----------------------------------------------------------------

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of each classroom assigned to a course in the program.
     */
    public int getTotalClassroomCapacity() {
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
     * Counts how many courses in this program are taught on a given day.
     *
     * @param day the day to check (e.g., "Monday", "Tuesday").
     * @return the number of courses that have the supplied day in their schedule.
     */
    public int getCoursesCountOnDay(String day) {
        int count = 0;
        for (Course c : courses) {
            if (c.getDays() != null && c.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a university classroom.
 */
 class Classroom {

    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /** No‑argument constructor required by the task. */
    public Classroom() {
        // default values can be set later via setters
    }

    /* -------------------- getters & setters -------------------- */

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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
 * Represents an instructor who can teach several courses.
 */
 class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses; // courses the instructor teaches

    /** No‑argument constructor required by the task. */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /* -------------------- getters & setters -------------------- */

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

    /** Adds a course to the instructor's teaching list. */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.addInstructor(this);
        }
    }

    /** Removes a course from the instructor's teaching list. */
    public void removeCourse(Course course) {
        if (courses.remove(course)) {
            course.removeInstructor(this);
        }
    }

    /**
     * Increases the quota limit of a specific course taught by this instructor.
     *
     * @param course the course whose quota will be increased.
     * @param amount the amount to increase (must be positive).
     * @throws IllegalArgumentException if amount is non‑positive or course not taught by this instructor.
     */
    public void increaseCourseQuota(Course course, int amount) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Instructor does not teach the given course.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive.");
        }
        course.setQuotaLimit(course.getQuotaLimit() + amount);
    }

    /**
     * Decreases the quota limit of a specific course taught by this instructor.
     *
     * @param course the course whose quota will be decreased.
     * @param amount the amount to decrease (must be positive and not lower than current enrollment).
     * @throws IllegalArgumentException if constraints are violated.
     */
    public void decreaseCourseQuota(Course course, int amount) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Instructor does not teach the given course.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive.");
        }
        int newQuota = course.getQuotaLimit() - amount;
        if (newQuota < course.getEnrolledStudents().size()) {
            throw new IllegalArgumentException("New quota cannot be less than current enrollment.");
        }
        course.setQuotaLimit(newQuota);
    }

    /**
     * Changes the weekly hours of a specific course taught by this instructor.
     *
     * @param course      the course to modify.
     * @param weeklyHours the new weekly hour count (must be positive).
     * @throws IllegalArgumentException if weeklyHours is non‑positive or course not taught.
     */
    public void changeCourseWeeklyHours(Course course, int weeklyHours) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Instructor does not teach the given course.");
        }
        if (weeklyHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive.");
        }
        course.setWeeklyHours(weeklyHours);
    }

    /**
     * Changes the days on which a specific course is taught.
     *
     * @param course the course to modify.
     * @param days   the new list of days (must not be null or empty).
     * @throws IllegalArgumentException if days is null/empty or course not taught.
     */
    public void changeCourseDays(Course course, List<String> days) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Instructor does not teach the given course.");
        }
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days list cannot be null or empty.");
        }
        course.setDays(new ArrayList<>(days));
    }

    /**
     * Determines the total number of students enrolled in all courses taught by this instructor.
     *
     * @return total enrollment across the instructor's courses.
     */
    public int getTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrolledStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return sum of remaining seats for each course.
     */
    public int getRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            remaining += (c.getQuotaLimit() - c.getEnrolledStudents().size());
        }
        return remaining;
    }
}

/**
 * Represents a university course.
 */
 class Course {

    private String name;
    private String code;
    private String startTime; // e.g., "09:00"
    private String endTime;   // e.g., "10:30"
    private List<String> days; // e.g., ["Monday", "Wednesday"]
    private Classroom classroom;
    private List<Instructor> instructors;
    private List<Student> enrolledStudents;
    private int quotaLimit;   // maximum number of students allowed
    private int weeklyHours;  // total hours per week

    /** No‑argument constructor required by the task. */
    public Course() {
        this.days = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    /* -------------------- getters & setters -------------------- */

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /** Adds an instructor to the course (bidirectional). */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            if (!instructor.getCourses().contains(this)) {
                instructor.getCourses().add(this);
            }
        }
    }

    /** Removes an instructor from the course (bidirectional). */
    public void removeInstructor(Instructor instructor) {
        if (instructors.remove(instructor)) {
            instructor.getCourses().remove(this);
        }
    }

    /** Enrolls a student if quota permits. */
    public void enrollStudent(Student student) {
        if (enrolledStudents.size() >= quotaLimit) {
            throw new IllegalStateException("Course quota reached.");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            student.addCourse(this);
        }
    }

    /** Drops a student from the course. */
    public void dropStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            student.removeCourse(this);
        }
    }

    // -----------------------------------------------------------------
    // Functional requirement implementations
    // -----------------------------------------------------------------

    /**
     * Calculates the average grade of all students enrolled in this course.
     *
     * @return the average grade, or 0.0 if no grades are recorded.
     */
    public double getAverageGrade() {
        double sum = 0.0;
        int count = 0;
        for (Student s : enrolledStudents) {
            Double grade = s.getGradeForCourse(this);
            if (grade != null) {
                sum += grade;
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }
}

/**
 * Represents a student.
 */
 class Student {

    private String name;
    private String surname;
    private List<Course> courses;                 // courses the student is enrolled in
    private Map<String, Double> gradesByCourseCode; // key = course code, value = grade

    /** No‑argument constructor required by the task. */
    public Student() {
        this.courses = new ArrayList<>();
        this.gradesByCourseCode = new HashMap<>();
    }

    /* -------------------- getters & setters -------------------- */

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Map<String, Double> getGradesByCourseCode() {
        return gradesByCourseCode;
    }

    public void setGradesByCourseCode(Map<String, Double> gradesByCourseCode) {
        this.gradesByCourseCode = gradesByCourseCode;
    }

    /** Enrolls the student into a course (bidirectional). */
    public void enrollInCourse(Course course) {
        if (!courses.contains(course)) {
            course.enrollStudent(this);
            // course.enrollStudent already adds this student to its list and calls addCourse(this)
        }
    }

    /** Drops the student from a course (bidirectional). */
    public void dropCourse(Course course) {
        if (courses.remove(course)) {
            course.dropStudent(this);
        }
    }

    /** Internal helper used by Course when enrollment occurs. */
    protected void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /** Internal helper used by Course when dropping occurs. */
    protected void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Assigns a grade for a specific course.
     *
     * @param course the course for which the grade is set.
     * @param grade  the grade value (0.0 – 100.0).
     * @throws IllegalArgumentException if grade is out of range.
     */
    public void setGradeForCourse(Course course, double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        gradesByCourseCode.put(course.getCode(), grade);
    }

    /**
     * Retrieves the grade for a given course.
     *
     * @param course the course whose grade is requested.
     * @return the grade, or {@code null} if not recorded.
     */
    public Double getGradeForCourse(Course course) {
        return gradesByCourseCode.get(course.getCode());
    }

    // Additional student‑centric functionalities (document request, survey, transcript) can be added here.
}