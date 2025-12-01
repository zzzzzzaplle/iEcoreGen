import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an Academic Program in the Software Engineering Department.
 * Contains courses and provides various operations related to the program.
 */
 class AcademicProgram {
    private String programName;
    private List<Course> courses;

    /**
     * Default constructor for AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the program name.
     * @return the program name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets the program name.
     * @param programName the program name to set
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Gets the list of courses in the program.
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the program.
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the academic program.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the academic program.
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program.
     * @return the total capacity of all classrooms
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
     * @return the average grade for the specified course, or 0.0 if no students are enrolled
     */
    public double calculateAverageGradeInCourse(String courseCode) {
        Course targetCourse = null;
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                targetCourse = course;
                break;
            }
        }
        
        if (targetCourse == null || targetCourse.getStudents().isEmpty()) {
            return 0.0;
        }
        
        double totalGrade = 0.0;
        int studentCount = 0;
        for (Student student : targetCourse.getStudents()) {
            Double grade = student.getGradeForCourse(courseCode);
            if (grade != null) {
                totalGrade += grade;
                studentCount++;
            }
        }
        
        return studentCount > 0 ? totalGrade / studentCount : 0.0;
    }

    /**
     * Finds the total number of courses in the Academic Program that are taught on a given day.
     * @param day the day to check for courses
     * @return the number of courses on the specified day
     */
    public int getCoursesOnSpecificDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays() != null && course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a course in the Academic Program.
 */
 class Course {
    private String courseName;
    private String courseCode;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private List<Instructor> instructors;
    private List<Student> students;
    private int quotaLimit;
    private int weeklyHours;

    /**
     * Default constructor for Course.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Gets the course name.
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name.
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the course code.
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code.
     * @param courseCode the course code to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the start time.
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days of the course.
     * @return the list of days
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Sets the days of the course.
     * @param days the list of days to set
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Gets the classroom.
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom.
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the instructors.
     * @return the list of instructors
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    /**
     * Sets the instructors.
     * @param instructors the list of instructors to set
     */
    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    /**
     * Gets the students enrolled in the course.
     * @return the list of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students enrolled in the course.
     * @param students the list of students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Gets the quota limit.
     * @return the quota limit
     */
    public int getQuotaLimit() {
        return quotaLimit;
    }

    /**
     * Sets the quota limit.
     * @param quotaLimit the quota limit to set
     */
    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    /**
     * Gets the weekly hours.
     * @return the weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours.
     * @param weeklyHours the weekly hours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Adds a student to the course.
     * @param student the student to add
     */
    public void addStudent(Student student) {
        if (students.size() < quotaLimit) {
            students.add(student);
        }
    }

    /**
     * Removes a student from the course.
     * @param student the student to remove
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Adds an instructor to the course.
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    /**
     * Removes an instructor from the course.
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        instructors.remove(instructor);
    }

    /**
     * Gets the remaining quota for the course.
     * @return the remaining quota
     */
    public int getRemainingQuota() {
        return quotaLimit - students.size();
    }
}

/**
 * Represents a classroom where courses are held.
 */
 class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Default constructor for Classroom.
     */
    public Classroom() {
    }

    /**
     * Gets the classroom ID.
     * @return the classroom ID
     */
    public String getClassroomId() {
        return classroomId;
    }

    /**
     * Sets the classroom ID.
     * @param classroomId the classroom ID to set
     */
    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * Gets the capacity.
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor.
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor.
     * @param floor the floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the block.
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block.
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor who teaches courses.
 */
 class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty.
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty.
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the courses taught by the instructor.
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses taught by the instructor.
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the instructor's list.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Removes a course from the instructor's list.
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Increases the quota limit for a course.
     * @param course the course to increase quota for
     * @param increment the amount to increase by
     */
    public void increaseCourseQuota(Course course, int increment) {
        if (courses.contains(course)) {
            course.setQuotaLimit(course.getQuotaLimit() + increment);
        }
    }

    /**
     * Decreases the quota limit for a course.
     * @param course the course to decrease quota for
     * @param decrement the amount to decrease by
     */
    public void decreaseCourseQuota(Course course, int decrement) {
        if (courses.contains(course)) {
            int newQuota = course.getQuotaLimit() - decrement;
            if (newQuota >= course.getStudents().size()) {
                course.setQuotaLimit(newQuota);
            }
        }
    }

    /**
     * Changes the weekly course hours for a course.
     * @param course the course to change hours for
     * @param newWeeklyHours the new weekly hours
     */
    public void changeWeeklyCourseHours(Course course, int newWeeklyHours) {
        if (courses.contains(course)) {
            course.setWeeklyHours(newWeeklyHours);
        }
    }

    /**
     * Changes the course days for a course.
     * @param course the course to change days for
     * @param newDays the new list of days
     */
    public void changeCourseDays(Course course, List<String> newDays) {
        if (courses.contains(course)) {
            course.setDays(newDays);
        }
    }

    /**
     * Determines the total number of students enrolled in all courses of this instructor.
     * @return the total number of students across all courses
     */
    public int getTotalStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this instructor.
     * @return the total remaining quota across all courses
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
 * Represents a student who can enroll in courses.
 */
 class Student {
    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;
    private Map<String, Double> courseGrades;

    /**
     * Default constructor for Student.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the student ID.
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID.
     * @param studentId the student ID to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the enrolled courses.
     * @return the list of enrolled courses
     */
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * Sets the enrolled courses.
     * @param enrolledCourses the list of enrolled courses to set
     */
    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    /**
     * Gets the course grades.
     * @return the map of course codes to grades
     */
    public Map<String, Double> getCourseGrades() {
        return courseGrades;
    }

    /**
     * Sets the course grades.
     * @param courseGrades the map of course codes to grades to set
     */
    public void setCourseGrades(Map<String, Double> courseGrades) {
        this.courseGrades = courseGrades;
    }

    /**
     * Enrolls the student in a course.
     * @param course the course to enroll in
     */
    public void enrollCourse(Course course) {
        if (!enrolledCourses.contains(course) && course.getRemainingQuota() > 0) {
            enrolledCourses.add(course);
            course.addStudent(this);
        }
    }

    /**
     * Drops a course that the student is enrolled in.
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.removeStudent(this);
            courseGrades.remove(course.getCourseCode());
        }
    }

    /**
     * Gets the transcript of the student.
     * @return the map of course codes to grades
     */
    public Map<String, Double> getTranscript() {
        return new HashMap<>(courseGrades);
    }

    /**
     * Requests documents from the department.
     * @param documentType the type of document requested
     * @return a confirmation message
     */
    public String requestDocuments(String documentType) {
        return "Document request for " + documentType + " has been submitted for student " + studentId;
    }

    /**
     * Conducts a survey for a course.
     * @param course the course to conduct survey for
     * @param rating the rating given (1-5)
     * @param comments additional comments
     * @return a confirmation message
     */
    public String conductSurvey(Course course, int rating, String comments) {
        return "Survey submitted for course " + course.getCourseName() + " with rating " + rating;
    }

    /**
     * Gets the grade for a specific course.
     * @param courseCode the course code
     * @return the grade for the course, or null if not found
     */
    public Double getGradeForCourse(String courseCode) {
        return courseGrades.get(courseCode);
    }

    /**
     * Sets the grade for a specific course.
     * @param courseCode the course code
     * @param grade the grade to set
     */
    public void setGradeForCourse(String courseCode, double grade) {
        courseGrades.put(courseCode, grade);
    }
}