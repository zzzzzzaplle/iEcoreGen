import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Academic Program in the Software Engineering Department.
 */
class AcademicProgram {
    private String programName;
    private List<Course> courses;
    private List<Classroom> classrooms;

    /**
     * Constructs a new AcademicProgram with empty courses and classrooms lists.
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
     * @param day the day to check for courses
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
    private String days;
    private Classroom classroom;
    private Instructor instructor;
    private List<Student> students;
    private int quotaLimit;

    /**
     * Constructs a new Course with an empty students list.
     */
    public Course() {
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
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
     * Calculates the remaining quota for this course.
     *
     * @return the remaining quota (quota limit minus enrolled students)
     */
    public int getRemainingQuota() {
        return quotaLimit - students.size();
    }

    /**
     * Calculates the average grade for students enrolled in this course.
     *
     * @return the average grade of all students in this course
     */
    public double getAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double totalGrade = 0.0;
        for (Student student : students) {
            // Assuming Student has a method getGradeForCourse(Course)
            // For this implementation, we'll use a placeholder value
            totalGrade += 85.0; // Placeholder - in real implementation, get actual grade
        }
        return totalGrade / students.size();
    }
}

/**
 * Represents a Classroom used for courses.
 */
class Classroom {
    private String id;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Constructs a new Classroom.
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
     * Constructs a new Instructor with an empty courses list.
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
     * Increases the quota limit for a specific course.
     *
     * @param course the course for which to increase the quota
     * @param amount the amount by which to increase the quota
     */
    public void increaseQuota(Course course, int amount) {
        if (courses.contains(course)) {
            course.setQuotaLimit(course.getQuotaLimit() + amount);
        }
    }

    /**
     * Decreases the quota limit for a specific course.
     *
     * @param course the course for which to decrease the quota
     * @param amount the amount by which to decrease the quota
     */
    public void decreaseQuota(Course course, int amount) {
        if (courses.contains(course)) {
            int newQuota = course.getQuotaLimit() - amount;
            if (newQuota >= 0) {
                course.setQuotaLimit(newQuota);
            }
        }
    }

    /**
     * Adds a course to this instructor's course list.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Drops a course from this instructor's course list.
     *
     * @param course the course to drop
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
    private String studentId;
    private List<Course> enrolledCourses;
    private List<String> documents;

    /**
     * Constructs a new Student with empty enrolled courses and documents lists.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.documents = new ArrayList<>();
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

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.getStudents().add(this);
        }
    }

    /**
     * Drops the student from a course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
        course.getStudents().remove(this);
    }

    /**
     * Requests a document.
     *
     * @param document the document to request
     */
    public void requestDocument(String document) {
        documents.add(document);
    }

    /**
     * Conducts a survey.
     *
     * @param survey the survey to conduct
     */
    public void conductSurvey(String survey) {
        // Implementation would depend on survey requirements
    }

    /**
     * Receives a transcript.
     *
     * @return a string representation of the transcript
     */
    public String receiveTranscript() {
        // Implementation would generate and return a transcript
        return "Transcript for " + name + " " + surname;
    }
}