import java.util.*;

/**
 * Represents an Academic Program in the Software Engineering Department.
 */
class AcademicProgram {
    private String programName;
    private List<Course> courses;
    private List<Classroom> classrooms;

    /**
     * Creates a new AcademicProgram with empty courses and classrooms lists.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
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

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    /**
     * Calculates the total capacity of all classrooms used in this Academic Program.
     *
     * @return the total capacity of all classrooms
     */
    public int getTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Classroom classroom : classrooms) {
            totalCapacity += classroom.getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Finds the total number of courses in this Academic Program that are taught on a given day.
     *
     * @param day the day to check for courses (e.g., "Monday", "Tuesday")
     * @return the number of courses on the specified day
     */
    public int getCoursesOnDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a Course in the Academic Program.
 */
class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private List<Student> students;
    private int quotaLimit;

    /**
     * Creates a new Course with empty days and students lists.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
    }

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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    /**
     * Calculates the average grade for the students enrolled in this course.
     *
     * @return the average grade of all students in this course, or 0 if no students are enrolled
     */
    public double getAverageGrade() {
        if (students.isEmpty()) {
            return 0;
        }
        
        double totalGrade = 0;
        for (Student student : students) {
            totalGrade += student.getGrade();
        }
        return totalGrade / students.size();
    }

    /**
     * Calculates the remaining quota for this course.
     *
     * @return the difference between the quota limit and the current number of enrolled students
     */
    public int getRemainingQuota() {
        return quotaLimit - students.size();
    }
}

/**
 * Represents a Classroom where courses are held.
 */
class Classroom {
    private String id;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Creates a new Classroom.
     */
    public Classroom() {
    }

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
 * Represents an Instructor who teaches courses.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Creates a new Instructor with an empty courses list.
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
     * Determines the total number of students enrolled in all courses of this Instructor.
     *
     * @return the total number of students across all courses taught by this instructor
     */
    public int getTotalStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this Instructor.
     *
     * @return the total remaining quota across all courses taught by this instructor
     */
    public int getRemainingQuota() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }

    /**
     * Increases the quota limit of a course by a specified amount.
     *
     * @param course the course whose quota limit is to be increased
     * @param amount the amount by which to increase the quota limit
     */
    public void increaseQuota(Course course, int amount) {
        course.setQuotaLimit(course.getQuotaLimit() + amount);
    }

    /**
     * Decreases the quota limit of a course by a specified amount.
     *
     * @param course the course whose quota limit is to be decreased
     * @param amount the amount by which to decrease the quota limit
     */
    public void decreaseQuota(Course course, int amount) {
        course.setQuotaLimit(Math.max(0, course.getQuotaLimit() - amount));
    }

    /**
     * Changes the weekly course hours for a course.
     *
     * @param course the course whose hours are to be changed
     * @param startTime the new start time for the course
     * @param endTime the new end time for the course
     */
    public void changeCourseHours(Course course, String startTime, String endTime) {
        course.setStartTime(startTime);
        course.setEndTime(endTime);
    }

    /**
     * Changes the days when a course is held.
     *
     * @param course the course whose days are to be changed
     * @param days the new list of days when the course is held
     */
    public void changeCourseDays(Course course, List<String> days) {
        course.setDays(days);
    }

    /**
     * Adds a course to this instructor's list of courses.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Drops a course from this instructor's list of courses based on attendance.
     *
     * @param course the course to be dropped
     */
    public void dropCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a Student enrolled in courses.
 */
class Student {
    private String name;
    private String surname;
    private int studentId;
    private double grade;
    private List<Course> courses;

    /**
     * Creates a new Student with an empty courses list.
     */
    public Student() {
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course the course to enroll in
     */
    public void enrollCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.getStudents().add(this);
        }
    }

    /**
     * Drops a course from the student's enrollment.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }

    /**
     * Receives the student's transcript.
     *
     * @return a string representation of the student's transcript
     */
    public String receiveTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(name).append(" ").append(surname).append(" (ID: ").append(studentId).append(")\n");
        for (Course course : courses) {
            transcript.append("Course: ").append(course.getName()).append(" (").append(course.getCode()).append(")\n");
        }
        return transcript.toString();
    }

    /**
     * Requests documents from the academic department.
     *
     * @param documentType the type of document to request
     * @return a string confirming the document request
     */
    public String requestDocument(String documentType) {
        return "Document request for " + documentType + " has been submitted for student " + name + " " + surname;
    }

    /**
     * Conducts a survey.
     *
     * @param surveyTopic the topic of the survey
     * @return a string confirming survey completion
     */
    public String conductSurvey(String surveyTopic) {
        return "Survey on " + surveyTopic + " has been conducted by student " + name + " " + surname;
    }
}