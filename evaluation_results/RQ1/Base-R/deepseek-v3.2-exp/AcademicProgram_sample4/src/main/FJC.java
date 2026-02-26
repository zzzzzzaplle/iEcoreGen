import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an academic program in the Software Engineering Department.
 * Contains a collection of courses and provides various operations on them.
 */
 class AcademicProgram {
    private String programName;
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

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
     * Calculates the total capacity of all classrooms used in the Academic Program.
     * 
     * @return the sum of capacities of all classrooms used in the program's courses
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
     * 
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of students in the specified course, or 0.0 if no students or course not found
     */
    public double calculateAverageGradeInCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                List<Student> enrolledStudents = course.getEnrolledStudents();
                if (enrolledStudents.isEmpty()) {
                    return 0.0;
                }
                double totalGrade = 0.0;
                for (Student student : enrolledStudents) {
                    // Assuming each student has a grade for the course
                    Double grade = student.getGradeForCourse(courseCode);
                    if (grade != null) {
                        totalGrade += grade;
                    }
                }
                return totalGrade / enrolledStudents.size();
            }
        }
        return 0.0;
    }

    /**
     * Finds the total number of courses in the Academic Program that are taught on a given day.
     * 
     * @param day the day to check for courses (e.g., "Monday", "Tuesday")
     * @return the number of courses taught on the specified day
     */
    public int countCoursesOnDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDaysOfCourse() != null && course.getDaysOfCourse().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a course in the academic program.
 */
 class Course {
    private String courseName;
    private String courseCode;
    private String startTime;
    private String endTime;
    private List<String> daysOfCourse;
    private Classroom classroom;
    private Instructor instructor;
    private List<Student> enrolledStudents;
    private int quotaLimit;

    /**
     * Default constructor initializes empty lists for days and enrolled students.
     */
    public Course() {
        this.daysOfCourse = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

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

    public List<String> getDaysOfCourse() {
        return daysOfCourse;
    }

    public void setDaysOfCourse(List<String> daysOfCourse) {
        this.daysOfCourse = daysOfCourse;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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

    /**
     * Calculates the remaining quota for the course.
     * 
     * @return the number of available spots remaining in the course
     */
    public int getRemainingQuota() {
        return quotaLimit - enrolledStudents.size();
    }
}

/**
 * Represents a classroom with location and capacity information.
 */
 class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Default constructor.
     */
    public Classroom() {
    }

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
 * Represents an instructor who teaches courses and mentors students.
 */
 class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

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
     * Increases the quota limit for a specific course.
     * 
     * @param course the course to modify
     * @param increaseAmount the amount to increase the quota by
     */
    public void increaseCourseQuota(Course course, int increaseAmount) {
        if (courses.contains(course)) {
            course.setQuotaLimit(course.getQuotaLimit() + increaseAmount);
        }
    }

    /**
     * Decreases the quota limit for a specific course.
     * 
     * @param course the course to modify
     * @param decreaseAmount the amount to decrease the quota by
     * @throws IllegalArgumentException if the decrease would result in a negative quota
     *                                 or if the new quota is less than current enrollment
     */
    public void decreaseCourseQuota(Course course, int decreaseAmount) {
        if (courses.contains(course)) {
            int newQuota = course.getQuotaLimit() - decreaseAmount;
            if (newQuota < 0) {
                throw new IllegalArgumentException("Quota cannot be negative");
            }
            if (newQuota < course.getEnrolledStudents().size()) {
                throw new IllegalArgumentException("New quota cannot be less than current enrollment");
            }
            course.setQuotaLimit(newQuota);
        }
    }

    /**
     * Changes the weekly course hours for a specific course.
     * 
     * @param course the course to modify
     * @param newStartTime the new start time
     * @param newEndTime the new end time
     */
    public void changeCourseHours(Course course, String newStartTime, String newEndTime) {
        if (courses.contains(course)) {
            course.setStartTime(newStartTime);
            course.setEndTime(newEndTime);
        }
    }

    /**
     * Changes the days when a course is taught.
     * 
     * @param course the course to modify
     * @param newDays the new list of days
     */
    public void changeCourseDays(Course course, List<String> newDays) {
        if (courses.contains(course)) {
            course.setDaysOfCourse(newDays);
        }
    }

    /**
     * Adds a course to the instructor's list of courses.
     * 
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.setInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's list of courses.
     * 
     * @param course the course to remove
     */
    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.setInstructor(null);
        }
    }

    /**
     * Determines the total number of students enrolled in all courses of this instructor.
     * 
     * @return the total number of students across all courses taught by this instructor
     */
    public int getTotalStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this instructor.
     * 
     * @return the total remaining quota across all courses taught by this instructor
     */
    public int getRemainingQuotaInCourses() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }
}

/**
 * Represents a student who can enroll in courses and receive grades.
 */
 class Student {
    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;
    private Map<String, Double> courseGrades; // Maps course code to grade

    /**
     * Default constructor initializes empty lists and maps.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
    }

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

    public Map<String, Double> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(Map<String, Double> courseGrades) {
        this.courseGrades = courseGrades;
    }

    /**
     * Enrolls the student in a course if there is available quota.
     * 
     * @param course the course to enroll in
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollInCourse(Course course) {
        if (course.getRemainingQuota() > 0 && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.getEnrolledStudents().add(this);
            return true;
        }
        return false;
    }

    /**
     * Drops a course that the student is enrolled in.
     * 
     * @param course the course to drop
     * @return true if the course was successfully dropped, false if student wasn't enrolled
     */
    public boolean dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.getEnrolledStudents().remove(this);
            courseGrades.remove(course.getCourseCode());
            return true;
        }
        return false;
    }

    /**
     * Gets the grade for a specific course.
     * 
     * @param courseCode the code of the course
     * @return the grade for the course, or null if no grade exists
     */
    public Double getGradeForCourse(String courseCode) {
        return courseGrades.get(courseCode);
    }

    /**
     * Sets the grade for a specific course.
     * 
     * @param courseCode the code of the course
     * @param grade the grade to set
     */
    public void setGradeForCourse(String courseCode, double grade) {
        courseGrades.put(courseCode, grade);
    }

    /**
     * Generates a transcript showing all courses and grades.
     * 
     * @return a string representation of the student's transcript
     */
    public String getTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(name).append(" ").append(surname).append(" (").append(studentId).append(")\n");
        for (Course course : enrolledCourses) {
            Double grade = courseGrades.get(course.getCourseCode());
            transcript.append(course.getCourseCode()).append(" - ").append(course.getCourseName());
            if (grade != null) {
                transcript.append(": ").append(grade);
            } else {
                transcript.append(": No grade assigned");
            }
            transcript.append("\n");
        }
        return transcript.toString();
    }

    /**
     * Requests a document (simplified implementation).
     * 
     * @param documentType the type of document requested
     * @return a confirmation message
     */
    public String requestDocument(String documentType) {
        return "Document request submitted for: " + documentType;
    }

    /**
     * Conducts a survey for a course (simplified implementation).
     * 
     * @param course the course being surveyed
     * @param rating the rating given by the student
     * @param comments any additional comments
     * @return a confirmation message
     */
    public String conductSurvey(Course course, int rating, String comments) {
        return "Survey submitted for " + course.getCourseName() + " with rating: " + rating;
    }
}