import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
 * Represents an instructor who can teach several courses.
 */
 class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> coursesTeaching;

    /** Unparameterized constructor */
    public Instructor() {
        this.coursesTeaching = new ArrayList<>();
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

    public List<Course> getCoursesTeaching() {
        return coursesTeaching;
    }

    public void setCoursesTeaching(List<Course> coursesTeaching) {
        this.coursesTeaching = coursesTeaching;
    }

    /**
     * Adds a course to the instructor's teaching list.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (!coursesTeaching.contains(course)) {
            coursesTeaching.add(course);
            course.addInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's teaching list.
     *
     * @param course the course to be removed
     */
    public void dropCourse(Course course) {
        if (coursesTeaching.remove(course)) {
            course.removeInstructor(this);
        }
    }

    /**
     * Determines the total number of students enrolled in all courses taught by this instructor.
     *
     * @return total number of enrolled students across all courses
     */
    public int getTotalEnrolledStudents() {
        int total = 0;
        for (Course c : coursesTeaching) {
            total += c.getEnrolledStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return sum of remaining seats for all courses
     */
    public int getRemainingQuota() {
        int remaining = 0;
        for (Course c : coursesTeaching) {
            remaining += (c.getQuota() - c.getEnrolledStudents().size());
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in courses.
 */
 class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;
    private Map<Course, Double> transcript; // grade per course

    /** Unparameterized constructor */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.transcript = new HashMap<>();
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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Map<Course, Double> getTranscript() {
        return transcript;
    }

    public void setTranscript(Map<Course, Double> transcript) {
        this.transcript = transcript;
    }

    /**
     * Enrolls the student in a course if there is available quota.
     *
     * @param course the course to enroll in
     * @throws IllegalStateException if the course quota is full
     */
    public void enrollInCourse(Course course) {
        if (course.getQuota() <= course.getEnrolledStudents().size()) {
            throw new IllegalStateException("Course quota is full.");
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.addStudent(this);
        }
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to be dropped
     */
    public void dropCourse(Course course) {
        if (enrolledCourses.remove(course)) {
            course.dropStudent(this);
        }
    }

    /**
     * Returns a copy of the student's transcript (course‑grade map).
     *
     * @return transcript map
     */
    public Map<Course, Double> receiveTranscript() {
        return new HashMap<>(transcript);
    }

    /**
     * Placeholder for a document request.
     *
     * @param documentName name of the document being requested
     */
    public void requestDocument(String documentName) {
        // In a real system this would trigger a workflow.
        System.out.println("Document request submitted: " + documentName);
    }

    /**
     * Placeholder for conducting a survey.
     *
     * @param surveyTitle title of the survey
     */
    public void conductSurvey(String surveyTitle) {
        // In a real system this would record survey participation.
        System.out.println("Survey participated: " + surveyTitle);
    }
}

/**
 * Represents a course offered in an academic program.
 */
 class Course {

    private String name;
    private String code;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<DayOfWeek> days;
    private Classroom classroom;
    private List<Instructor> instructors;
    private int quota;                     // maximum number of students
    private List<Student> enrolledStudents;
    private int weeklyHours;
    private Map<Student, Double> studentGrades; // grade per student

    /** Unparameterized constructor */
    public Course() {
        this.days = new HashSet<>();
        this.instructors = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.studentGrades = new HashMap<>();
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

    public Set<DayOfWeek> getDays() {
        return days;
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
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Map<Student, Double> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Map<Student, Double> studentGrades) {
        this.studentGrades = studentGrades;
    }

    /**
     * Adds an instructor to the course.
     *
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            instructor.addCourse(this);
        }
    }

    /**
     * Removes an instructor from the course.
     *
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        if (instructors.remove(instructor)) {
            instructor.dropCourse(this);
        }
    }

    /**
     * Increases the quota (maximum number of students) for this course.
     *
     * @param amount the amount to increase the quota by; must be positive
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuota(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be positive.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota (maximum number of students) for this course.
     *
     * @param amount the amount to decrease the quota by; must be positive and not cause quota to drop below current enrollment
     * @throws IllegalArgumentException if amount is negative or would make quota less than enrolled students
     */
    public void decreaseQuota(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount must be positive.");
        }
        if (this.quota - amount < enrolledStudents.size()) {
            throw new IllegalArgumentException("Cannot set quota below current enrollment.");
        }
        this.quota -= amount;
    }

    /**
     * Sets the weekly number of teaching hours for this course.
     *
     * @param hours number of hours per week; must be non‑negative
     * @throws IllegalArgumentException if hours is negative
     */
    public void setWeeklyHours(int hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative.");
        }
        this.weeklyHours = hours;
    }

    /**
     * Adds a day to the set of days on which the course meets.
     *
     * @param day the day to be added
     */
    public void addDay(DayOfWeek day) {
        this.days.add(day);
    }

    /**
     * Removes a day from the set of days on which the course meets.
     *
     * @param day the day to be removed
     */
    public void removeDay(DayOfWeek day) {
        this.days.remove(day);
    }

    /**
     * Enrolls a student in this course if the quota permits.
     *
     * @param student the student to enroll
     * @throws IllegalStateException if the course quota is already full
     */
    public void addStudent(Student student) {
        if (enrolledStudents.size() >= quota) {
            throw new IllegalStateException("Course quota reached.");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    /**
     * Removes a student from this course.
     *
     * @param student the student to drop
     */
    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
        studentGrades.remove(student);
    }

    /**
     * Records a grade for a specific student in this course.
     *
     * @param student the student whose grade is being recorded
     * @param grade   the grade value (0‑100)
     */
    public void recordGrade(Student student, double grade) {
        if (!enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }
        studentGrades.put(student, grade);
    }

    /**
     * Calculates the average grade for all students enrolled in this course.
     *
     * @return average grade, or 0.0 if no grades have been recorded
     */
    public double getAverageGrade() {
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double g : studentGrades.values()) {
            sum += g;
        }
        return sum / studentGrades.size();
    }
}

/**
 * Represents an academic program containing multiple courses.
 */
 class AcademicProgram {

    private String programName;
    private List<Course> courses;

    /** Unparameterized constructor */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** Getters and Setters */
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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
     * Calculates the total capacity of all classrooms used by the courses in this program.
     *
     * @return sum of capacities of all associated classrooms
     */
    public int getTotalClassroomCapacity() {
        int total = 0;
        Set<Classroom> uniqueClassrooms = new HashSet<>();
        for (Course c : courses) {
            Classroom cr = c.getClassroom();
            if (cr != null && uniqueClassrooms.add(cr)) {
                total += cr.getCapacity();
            }
        }
        return total;
    }

    /**
     * Counts how many courses in the program are taught on a given day.
     *
     * @param day the day of the week to check
     * @return number of courses occurring on the specified day
     */
    public int getCoursesCountByDay(DayOfWeek day) {
        int count = 0;
        for (Course c : courses) {
            if (c.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}