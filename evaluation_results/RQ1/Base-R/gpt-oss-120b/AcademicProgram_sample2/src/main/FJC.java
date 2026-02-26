import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an academic program that groups a collection of courses.
 */
 class AcademicProgram {

    private String name;
    private List<Course> courses = new ArrayList<>();

    /** Unparameterized constructor */
    public AcademicProgram() {
    }

    /** Getters and Setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the academic program.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the academic program.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     * The capacity of a classroom is counted once per course that uses it (duplicates are allowed).
     *
     * @return total sum of classroom capacities
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
     * Returns the number of courses that are scheduled on the given day.
     *
     * @param day the day to check (e.g., "Monday")
     * @return number of courses taught on that day
     */
    public int getCoursesCountByDay(String day) {
        int count = 0;
        for (Course c : courses) {
            if (c.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a classroom where courses take place.
 */
class Classroom {

    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /** Unparameterized constructor */
    public Classroom() {
    }

    /** Getters and Setters */
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
 * Represents a university instructor.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses = new ArrayList<>();

    /** Unparameterized constructor */
    public Instructor() {
    }

    /** Getters and Setters */
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
     * Adds a course to the instructor's teaching list.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            if (!course.getInstructors().contains(this)) {
                course.addInstructor(this);
            }
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
        course.getInstructors().remove(this);
    }

    /**
     * Increases the quota limit of a given course.
     *
     * @param course the course whose quota will be increased
     * @param amount the amount to increase
     * @throws IllegalArgumentException if amount is negative or course is null
     */
    public void increaseCourseQuota(Course course, int amount) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be non‑negative.");
        }
        course.setQuotaLimit(course.getQuotaLimit() + amount);
    }

    /**
     * Decreases the quota limit of a given course.
     *
     * @param course the course whose quota will be decreased
     * @param amount the amount to decrease
     * @throws IllegalArgumentException if amount is negative, course is null,
     *                                  or resulting quota would be less than current enrollment
     */
    public void decreaseCourseQuota(Course course, int amount) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be non‑negative.");
        }
        int newQuota = course.getQuotaLimit() - amount;
        if (newQuota < course.getEnrolledStudents().size()) {
            throw new IllegalArgumentException("New quota cannot be less than current enrollment.");
        }
        course.setQuotaLimit(newQuota);
    }

    /**
     * Changes the weekly meeting times of a course.
     *
     * @param course    the course to modify
     * @param newStart  new start time
     * @param newEnd    new end time
     * @throws IllegalArgumentException if any argument is null or start is after end
     */
    public void changeWeeklyCourseHours(Course course, LocalTime newStart, LocalTime newEnd) {
        if (course == null || newStart == null || newEnd == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (newStart.isAfter(newEnd)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        course.setStartTime(newStart);
        course.setEndTime(newEnd);
    }

    /**
     * Changes the days on which a course meets.
     *
     * @param course   the course to modify
     * @param newDays  a set containing the new days (e.g., {"Monday","Wednesday"})
     * @throws IllegalArgumentException if course or newDays is null
     */
    public void changeCourseDays(Course course, Set<String> newDays) {
        if (course == null || newDays == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        course.setDays(new HashSet<>(newDays));
    }

    /**
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return total number of enrolled students
     */
    public int getTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrolledStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) for all courses taught by this instructor.
     *
     * @return sum of remaining seats across all courses
     */
    public int getTotalRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            remaining += c.getQuotaLimit() - c.getEnrolledStudents().size();
        }
        return remaining;
    }
}

/**
 * Represents a student.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Course> courses = new ArrayList<>();
    private Map<Course, Double> grades = new HashMap<>();

    /** Unparameterized constructor */
    public Student() {
    }

    /** Getters and Setters */
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Map<Course, Double> getGrades() {
        return grades;
    }

    public void setGrades(Map<Course, Double> grades) {
        this.grades = grades;
    }

    /**
     * Enrolls the student in a course if there is quota.
     *
     * @param course the course to enroll in
     * @throws IllegalArgumentException if the course is null or quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (course.getEnrolledStudents().size() >= course.getQuotaLimit()) {
            throw new IllegalArgumentException("Course quota is full.");
        }
        if (!courses.contains(course)) {
            courses.add(course);
            course.enrollStudent(this);
        }
    }

    /**
     * Drops the student from a course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (course != null && courses.remove(course)) {
            course.dropStudent(this);
            grades.remove(course);
        }
    }

    /**
     * Records a grade for a specific course.
     *
     * @param course the course
     * @param grade  the numeric grade (0‑100)
     */
    public void setGrade(Course course, double grade) {
        if (course != null) {
            grades.put(course, grade);
            course.recordGrade(this, grade);
        }
    }

    /**
     * Retrieves a transcript string (simplified placeholder).
     *
     * @return a textual representation of the student's courses and grades
     */
    public String receiveTranscript() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(name).append(' ').append(surname).append(" (ID: ").append(studentId).append(")\n");
        for (Course c : courses) {
            sb.append("- ").append(c.getName()).append(" (").append(c.getCode()).append("): ");
            Double g = grades.get(c);
            sb.append(g != null ? g : "N/A").append('\n');
        }
        return sb.toString();
    }

    /**
     * Requests a document (e.g., enrollment verification). This method only returns a placeholder string.
     *
     * @param documentType the type of document requested
     * @return a confirmation message
     */
    public String requestDocument(String documentType) {
        return "Document of type '" + documentType + "' has been requested for student " + studentId + ".";
    }

    /**
     * Submits a survey feedback.
     *
     * @param feedback the textual feedback
     * @return acknowledgment string
     */
    public String conductSurvey(String feedback) {
        return "Survey received: \"" + feedback + "\". Thank you, " + name + "!";
    }
}

/**
 * Represents a university course.
 */
class Course {

    private String name;
    private String code;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<String> days = new HashSet<>();
    private Classroom classroom;
    private List<Instructor> instructors = new ArrayList<>();
    private int quotaLimit;
    private List<Student> enrolledStudents = new ArrayList<>();
    private Map<Student, Double> grades = new HashMap<>();

    /** Unparameterized constructor */
    public Course() {
    }

    /** Getters and Setters */
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

    public Set<String> getDays() {
        return days;
    }

    public void setDays(Set<String> days) {
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

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Map<Student, Double> getGrades() {
        return grades;
    }

    public void setGrades(Map<Student, Double> grades) {
        this.grades = grades;
    }

    /**
     * Adds an instructor to the course.
     *
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        if (instructor != null && !instructors.contains(instructor)) {
            instructors.add(instructor);
            if (!instructor.getCourses().contains(this)) {
                instructor.addCourse(this);
            }
        }
    }

    /**
     * Removes an instructor from the course.
     *
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        if (instructor != null) {
            instructors.remove(instructor);
            instructor.getCourses().remove(this);
        }
    }

    /**
     * Enrolls a student in the course if quota permits.
     *
     * @param student the student to enroll
     * @throws IllegalArgumentException if student is null or quota is full
     */
    public void enrollStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (enrolledStudents.size() >= quotaLimit) {
            throw new IllegalArgumentException("Course quota is full.");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    /**
     * Drops a student from the course.
     *
     * @param student the student to drop
     */
    public void dropStudent(Student student) {
        if (student != null) {
            enrolledStudents.remove(student);
            grades.remove(student);
        }
    }

    /**
     * Records a grade for a particular student.
     *
     * @param student the student
     * @param grade   the numeric grade (0‑100)
     */
    public void recordGrade(Student student, double grade) {
        if (student != null) {
            grades.put(student, grade);
        }
    }

    /**
     * Calculates the average grade of all students enrolled in this course.
     *
     * @return the average grade, or 0.0 if no grades are recorded
     */
    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double g : grades.values()) {
            sum += g;
        }
        return sum / grades.size();
    }

    /**
     * Returns the number of remaining seats (quota) in this course.
     *
     * @return remaining quota (capacity - current enrollment)
     */
    public int getRemainingQuota() {
        return quotaLimit - enrolledStudents.size();
    }
}