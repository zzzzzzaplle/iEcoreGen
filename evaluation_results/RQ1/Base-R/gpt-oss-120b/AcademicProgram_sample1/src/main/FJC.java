import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a classroom where courses are held.
 */
 class Classroom {

    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /** Unparameterized constructor */
    public Classroom() {
    }

    /* ---------- Getters & Setters ---------- */

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
 * Represents an instructor that can teach and mentor students.
 */
 class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;                 // courses the instructor teaches

    /** Unparameterized constructor */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

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
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    /* ---------- Instructor Operations ---------- */

    /**
     * Adds a course to the instructor's teaching list.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.addInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        if (courses.remove(course)) {
            course.removeInstructor(this);
        }
    }

    /**
     * Increases the quota (maximum number of students) of a given course.
     *
     * @param course the course whose quota will be increased
     * @param amount the amount to increase
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseCourseQuota(Course course, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be non‑negative.");
        }
        if (courses.contains(course)) {
            course.setQuotaLimit(course.getQuotaLimit() + amount);
        }
    }

    /**
     * Decreases the quota (maximum number of students) of a given course.
     *
     * @param course the course whose quota will be decreased
     * @param amount the amount to decrease
     * @throws IllegalArgumentException if amount is negative or would make quota lower than current enrolments
     */
    public void decreaseCourseQuota(Course course, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be non‑negative.");
        }
        if (courses.contains(course)) {
            int newQuota = course.getQuotaLimit() - amount;
            if (newQuota < course.getEnrolledStudents().size()) {
                throw new IllegalArgumentException("New quota cannot be less than the number of already enrolled students.");
            }
            course.setQuotaLimit(newQuota);
        }
    }

    /**
     * Changes the weekly hours (start/end times) of a course.
     *
     * @param course the course to be updated
     * @param start  new start time
     * @param end    new end time
     * @throws IllegalArgumentException if start is after end
     */
    public void changeCourseHours(Course course, LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        if (courses.contains(course)) {
            course.setStartTime(start);
            course.setEndTime(end);
        }
    }

    /**
     * Changes the days a course is taught.
     *
     * @param course the course to be updated
     * @param days   new set of days
     */
    public void changeCourseDays(Course course, Set<DayOfWeek> days) {
        if (courses.contains(course)) {
            course.setDays(days);
        }
    }

    /**
     * Determines the total number of students enrolled in all courses taught by this instructor.
     *
     * @return total enrolment across the instructor's courses
     */
    public int getTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrolledStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) summed across all courses taught by this instructor.
     *
     * @return total remaining quota
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
 * Represents a student that can enrol in courses.
 */
 class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;                     // courses the student is currently enrolled in
    private Map<Course, Double> grades;                       // grades per course (0‑100)

    /** Unparameterized constructor */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    /* ---------- Getters & Setters ---------- */

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

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }

    public void setEnrolledCourses(List<Course> courses) {
        this.enrolledCourses = new ArrayList<>(courses);
    }

    public Map<Course, Double> getGrades() {
        return Collections.unmodifiableMap(grades);
    }

    public void setGrades(Map<Course, Double> grades) {
        this.grades = new HashMap<>(grades);
    }

    /* ---------- Student Operations ---------- */

    /**
     * Enrolls the student in a given course.
     *
     * @param course the course to enrol in
     * @throws IllegalArgumentException if the course quota is already full
     */
    public void enrolInCourse(Course course) {
        if (course.getEnrolledStudents().size() >= course.getQuotaLimit()) {
            throw new IllegalArgumentException("Course quota reached; cannot enrol.");
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.enrollStudent(this);
        }
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (enrolledCourses.remove(course)) {
            course.dropStudent(this);
            grades.remove(course);
        }
    }

    /**
     * Receives a simple transcript containing the list of courses and grades.
     *
     * @return a formatted transcript string
     */
    public String receiveTranscript() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(name).append(' ').append(surname).append(" (ID: ").append(studentId).append(")\n");
        for (Course c : enrolledCourses) {
            sb.append("- ").append(c.getCourseName()).append(" (").append(c.getCourseCode()).append("): ");
            Double grade = grades.get(c);
            sb.append(grade != null ? grade : "N/A").append('\n');
        }
        return sb.toString();
    }

    /**
     * Requests a document (e.g., enrollment verification) from the academic program.
     *
     * @param documentType type of document requested
     * @return a simple confirmation string
     */
    public String requestDocument(String documentType) {
        // In a real system this would involve more logic; here we return a placeholder.
        return "Document '" + documentType + "' requested for student " + studentId + ".";
    }

    /**
     * Conducts a survey by submitting the provided response.
     *
     * @param surveyResponse the student's response
     * @return confirmation string
     */
    public String conductSurvey(String surveyResponse) {
        // Placeholder for storing the response.
        return "Survey response recorded: \"" + surveyResponse + "\"";
    }

    /**
     * Assigns a grade for a particular course.
     *
     * @param course the course
     * @param grade  grade value (0‑100)
     * @throws IllegalArgumentException if grade is out of range
     */
    public void setGrade(Course course, double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        if (enrolledCourses.contains(course)) {
            grades.put(course, grade);
        }
    }
}

/**
 * Represents a course offered in an academic program.
 */
 class Course {

    private String courseName;
    private String courseCode;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<DayOfWeek> days;                     // days of the week the course meets
    private Classroom classroom;
    private List<Instructor> instructors;
    private int quotaLimit;                          // maximum number of students allowed
    private List<Student> enrolledStudents;          // students currently enrolled

    /** Unparameterized constructor */
    public Course() {
        this.instructors = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<DayOfWeek> getDays() {
        return Collections.unmodifiableSet(days);
    }

    public void setDays(Set<DayOfWeek> days) {
        this.days = days;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Instructor> getInstructors() {
        return Collections.unmodifiableList(instructors);
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = new ArrayList<>(instructors);
    }

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    public List<Student> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public void setEnrolledStudents(List<Student> students) {
        this.enrolledStudents = new ArrayList<>(students);
    }

    /* ---------- Course Operations ---------- */

    /**
     * Enrolls a student into the course if quota permits.
     *
     * @param student the student to enroll
     * @throws IllegalArgumentException if quota is full
     */
    public void enrollStudent(Student student) {
        if (enrolledStudents.size() >= quotaLimit) {
            throw new IllegalArgumentException("Course quota reached; cannot enrol student.");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    /**
     * Removes a student from the course.
     *
     * @param student the student to remove
     */
    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
    }

    /**
     * Adds an instructor to the teaching list.
     *
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            if (!instructor.getCourses().contains(this)) {
                instructor.addCourse(this);
            }
        }
    }

    /**
     * Removes an instructor from the teaching list.
     *
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        if (instructors.remove(instructor)) {
            instructor.removeCourse(this);
        }
    }

    /**
     * Calculates the average grade of all students enrolled in this course.
     *
     * @return average grade (0‑100), or 0 if no grades are recorded
     */
    public double getAverageGrade() {
        double total = 0.0;
        int count = 0;
        for (Student s : enrolledStudents) {
            Double grade = s.getGrades().get(this);
            if (grade != null) {
                total += grade;
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }
}

/**
 * Represents an academic program (e.g., Software Engineering) that aggregates multiple courses.
 */
 class AcademicProgram {

    private String programName;
    private List<Course> courses;

    /** Unparameterized constructor */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    /* ---------- Program Operations ---------- */

    /**
     * Adds a course to the academic program.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
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

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return sum of capacities of distinct classrooms referenced by the program's courses
     */
    public int getTotalClassroomCapacity() {
        int total = 0;
        // Use a Set to avoid counting the same classroom multiple times.
        java.util.Set<Classroom> distinct = new java.util.HashSet<>();
        for (Course c : courses) {
            Classroom cr = c.getClassroom();
            if (cr != null && distinct.add(cr)) {
                total += cr.getCapacity();
            }
        }
        return total;
    }

    /**
     * Counts how many courses in the program are taught on a specified day.
     *
     * @param day the day of week to query
     * @return number of courses meeting on that day
     */
    public int getCourseCountByDay(DayOfWeek day) {
        int count = 0;
        for (Course c : courses) {
            if (c.getDays() != null && c.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves a course by its unique code.
     *
     * @param code the course code
     * @return the matching Course, or null if not found
     */
    public Course getCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
}