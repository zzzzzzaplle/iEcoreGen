import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a classroom with capacity, location, and identification information.
 */
class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    public Classroom() {
    }

    /**
     * Gets the unique identifier for the classroom.
     * @return the classroom ID
     */
    public String getClassroomId() {
        return classroomId;
    }

    /**
     * Sets the unique identifier for the classroom.
     * @param classroomId the classroom ID to set
     */
    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * Gets the maximum number of students the classroom can accommodate.
     * @return the classroom capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the maximum number of students the classroom can accommodate.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor number where the classroom is located.
     * @return the floor number
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor number where the classroom is located.
     * @param floor the floor number to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the block or building where the classroom is located.
     * @return the block information
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block or building where the classroom is located.
     * @param block the block information to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor with personal information, title, specialty, and course management capabilities.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the instructor's first name.
     * @return the instructor's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's first name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's last name.
     * @return the instructor's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's last name.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's professional title.
     * @return the instructor's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's professional title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's area of expertise.
     * @return the instructor's specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's area of expertise.
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor.
     * @return list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor.
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Increases the quota limit for a specific course.
     * @param course the course to modify
     * @param increment the amount to increase the quota by
     * @throws IllegalArgumentException if course is null or increment is negative
     */
    public void increaseCourseQuota(Course course, int increment) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (increment < 0) {
            throw new IllegalArgumentException("Increment cannot be negative");
        }
        course.setQuota(course.getQuota() + increment);
    }

    /**
     * Decreases the quota limit for a specific course.
     * @param course the course to modify
     * @param decrement the amount to decrease the quota by
     * @throws IllegalArgumentException if course is null, decrement is negative, or resulting quota would be negative
     */
    public void decreaseCourseQuota(Course course, int decrement) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (decrement < 0) {
            throw new IllegalArgumentException("Decrement cannot be negative");
        }
        if (course.getQuota() - decrement < 0) {
            throw new IllegalArgumentException("Resulting quota cannot be negative");
        }
        course.setQuota(course.getQuota() - decrement);
    }

    /**
     * Changes the weekly course hours for a specific course.
     * @param course the course to modify
     * @param newWeeklyHours the new weekly hours to set
     * @throws IllegalArgumentException if course is null or newWeeklyHours is negative
     */
    public void changeWeeklyCourseHours(Course course, int newWeeklyHours) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (newWeeklyHours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative");
        }
        course.setWeeklyHours(newWeeklyHours);
    }

    /**
     * Changes the days when a course is taught.
     * @param course the course to modify
     * @param newDays the new course days to set
     * @throws IllegalArgumentException if course is null or newDays is null/empty
     */
    public void changeCourseDays(Course course, List<String> newDays) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (newDays == null || newDays.isEmpty()) {
            throw new IllegalArgumentException("Course days cannot be null or empty");
        }
        course.setDays(newDays);
    }

    /**
     * Adds a course to the instructor's list of courses.
     * @param course the course to add
     * @throws IllegalArgumentException if course is null
     */
    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!courses.contains(course)) {
            courses.add(course);
            course.setInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's list of courses.
     * @param course the course to remove
     * @throws IllegalArgumentException if course is null
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (courses.remove(course)) {
            course.setInstructor(null);
        }
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     * @return the total number of students across all instructor's courses
     */
    public int getTotalStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota across all courses taught by this instructor.
     * @return the total remaining quota across all instructor's courses
     */
    public int getRemainingQuotaInCourses() {
        int remainingQuota = 0;
        for (Course course : courses) {
            remainingQuota += course.getQuota() - course.getEnrolledStudents().size();
        }
        return remainingQuota;
    }
}

/**
 * Represents a student with enrollment, grading, and academic record capabilities.
 */
class Student {
    private String studentId;
    private String name;
    private List<Course> enrolledCourses;
    private Map<Course, Double> grades;

    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    /**
     * Gets the student's unique identifier.
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student's unique identifier.
     * @param studentId the student ID to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the student's name.
     * @return the student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of courses the student is currently enrolled in.
     * @return list of enrolled courses
     */
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * Sets the list of courses the student is currently enrolled in.
     * @param enrolledCourses the list of enrolled courses to set
     */
    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    /**
     * Gets the grades for all courses the student has taken.
     * @return map of courses to grades
     */
    public Map<Course, Double> getGrades() {
        return grades;
    }

    /**
     * Sets the grades for all courses the student has taken.
     * @param grades the map of courses to grades to set
     */
    public void setGrades(Map<Course, Double> grades) {
        this.grades = grades;
    }

    /**
     * Enrolls the student in a course if there is available quota.
     * @param course the course to enroll in
     * @throws IllegalArgumentException if course is null
     * @throws IllegalStateException if course quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getEnrolledStudents().size() >= course.getQuota()) {
            throw new IllegalStateException("Course quota is full");
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.getEnrolledStudents().add(this);
        }
    }

    /**
     * Drops a course the student is enrolled in.
     * @param course the course to drop
     * @throws IllegalArgumentException if course is null
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (enrolledCourses.remove(course)) {
            course.getEnrolledStudents().remove(this);
            grades.remove(course);
        }
    }

    /**
     * Gets the student's transcript showing grades for all completed courses.
     * @return map of courses to grades representing the transcript
     */
    public Map<Course, Double> getTranscript() {
        return new HashMap<>(grades);
    }

    /**
     * Records a grade for the student in a specific course.
     * @param course the course to record grade for
     * @param grade the grade to record
     * @throws IllegalArgumentException if course is null or grade is negative
     */
    public void recordGrade(Course course, double grade) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative");
        }
        grades.put(course, grade);
    }

    /**
     * Requests academic documents (placeholder method).
     */
    public void requestDocuments() {
        // Implementation for requesting academic documents
        System.out.println("Requesting academic documents for student: " + name);
    }

    /**
     * Conducts course surveys (placeholder method).
     */
    public void conductSurvey() {
        // Implementation for conducting course surveys
        System.out.println("Conducting survey for student: " + name);
    }
}

/**
 * Represents a course with scheduling, enrollment, and instructional information.
 */
class Course {
    private String courseName;
    private String courseCode;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private int quota;
    private int weeklyHours;
    private List<Student> enrolledStudents;

    public Course() {
        this.days = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    /**
     * Gets the name of the course.
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course.
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the unique code identifying the course.
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the unique code identifying the course.
     * @param courseCode the course code to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the start time of the course.
     * @return the course start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * @return the course end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days of the week when the course is taught.
     * @return list of course days
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Sets the days of the week when the course is taught.
     * @param days the list of course days to set
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Gets the classroom where the course is taught.
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is taught.
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the instructor teaching the course.
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor teaching the course.
     * @param instructor the instructor to set
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Gets the maximum number of students that can enroll in the course.
     * @return the course quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the maximum number of students that can enroll in the course.
     * @param quota the quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours allocated for the course.
     * @return the weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours allocated for the course.
     * @param weeklyHours the weekly hours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the list of students currently enrolled in the course.
     * @return list of enrolled students
     */
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Sets the list of students currently enrolled in the course.
     * @param enrolledStudents the list of enrolled students to set
     */
    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    /**
     * Checks if the course is taught on a specific day.
     * @param day the day to check
     * @return true if the course is taught on the specified day, false otherwise
     */
    public boolean isTaughtOnDay(String day) {
        return days.contains(day);
    }

    /**
     * Calculates the average grade for students enrolled in this course.
     * @return the average grade, or 0.0 if no students have grades
     */
    public double calculateAverageGrade() {
        if (enrolledStudents.isEmpty()) {
            return 0.0;
        }
        
        double totalGrade = 0.0;
        int studentsWithGrades = 0;
        
        for (Student student : enrolledStudents) {
            Double grade = student.getGrades().get(this);
            if (grade != null) {
                totalGrade += grade;
                studentsWithGrades++;
            }
        }
        
        return studentsWithGrades > 0 ? totalGrade / studentsWithGrades : 0.0;
    }
}

/**
 * Represents an academic program containing multiple courses and managing academic operations.
 */
class AcademicProgram {
    private String programName;
    private List<Course> courses;

    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name of the academic program.
     * @return the program name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets the name of the academic program.
     * @param programName the program name to set
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Gets the list of courses in the academic program.
     * @return list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program.
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the academic program.
     * @param course the course to add
     * @throws IllegalArgumentException if course is null
     */
    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the academic program.
     * @param course the course to remove
     * @throws IllegalArgumentException if course is null
     */
    public void removeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * @return the total classroom capacity across all courses
     */
    public int calculateTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            if (course.getClassroom() != null) {
                totalCapacity += course.getClassroom().getCapacity();
            }
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course.
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade for the specified course
     * @throws IllegalArgumentException if courseCode is null or empty, or if course is not found
     */
    public double calculateAverageGradeInCourse(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        
        Course targetCourse = null;
        for (Course course : courses) {
            if (courseCode.equals(course.getCourseCode())) {
                targetCourse = course;
                break;
            }
        }
        
        if (targetCourse == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found in the program");
        }
        
        return targetCourse.calculateAverageGrade();
    }

    /**
     * Finds the total number of courses taught on a specific day in the academic program.
     * @param day the day to check (e.g., "Monday", "Tuesday", etc.)
     * @return the number of courses taught on the specified day
     * @throws IllegalArgumentException if day is null or empty
     */
    public int getCoursesOnSpecificDay(String day) {
        if (day == null || day.trim().isEmpty()) {
            throw new IllegalArgumentException("Day cannot be null or empty");
        }
        
        int count = 0;
        for (Course course : courses) {
            if (course.isTaughtOnDay(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the remaining quota for all courses taught by a specific instructor.
     * @param instructor the instructor to check
     * @return the total remaining quota across all courses taught by the instructor
     * @throws IllegalArgumentException if instructor is null
     */
    public int getRemainingQuotaForInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        
        return instructor.getRemainingQuotaInCourses();
    }

    /**
     * Gets the total number of students enrolled in all courses taught by a specific instructor.
     * @param instructor the instructor to check
     * @return the total number of students across all instructor's courses
     * @throws IllegalArgumentException if instructor is null
     */
    public int getTotalStudentsForInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        
        return instructor.getTotalStudentsInCourses();
    }
}