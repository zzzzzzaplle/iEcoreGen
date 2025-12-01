import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Academic Program containing courses, classrooms, instructors, and students.
 */
class AcademicProgram {
    private List<Course> courses;
    private List<Classroom> classrooms;
    private List<Student> students;
    private List<Instructor> instructors;

    /**
     * Constructs an empty AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
        this.students = new ArrayList<>();
        this.instructors = new ArrayList<>();
    }

    // Getters and Setters
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
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

    /**
     * Calculates the average grade for students enrolled in a specific course within this Academic Program.
     *
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of students in the specified course
     */
    public double getAverageGradeInCourse(String courseCode) {
        Course targetCourse = null;
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                targetCourse = course;
                break;
            }
        }

        if (targetCourse == null || targetCourse.getEnrolledStudents().isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Student student : targetCourse.getEnrolledStudents()) {
            sum += student.getGrade();
        }

        return sum / targetCourse.getEnrolledStudents().size();
    }
}

/**
 * Represents a Course in an Academic Program.
 */
class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private String days;
    private Classroom classroom;
    private Instructor instructor;
    private int quota;
    private List<Student> enrolledStudents;

    /**
     * Constructs an empty Course.
     */
    public Course() {
        this.enrolledStudents = new ArrayList<>();
    }

    // Getters and Setters
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

    /**
     * Enrolls a student in this course if there is space available.
     *
     * @param student the student to enroll
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() < quota) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    /**
     * Drops a student from this course.
     *
     * @param student the student to drop
     * @return true if dropping was successful, false if student wasn't enrolled
     */
    public boolean dropStudent(Student student) {
        return enrolledStudents.remove(student);
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
     * Constructs an empty Classroom.
     */
    public Classroom() {
    }

    // Getters and Setters
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
     * Constructs an empty Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    // Getters and Setters
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
     * @param amount the amount to increase the quota by
     */
    public void increaseCourseQuota(Course course, int amount) {
        course.setQuota(course.getQuota() + amount);
    }

    /**
     * Decreases the quota limit for a specific course.
     *
     * @param course the course to modify
     * @param amount the amount to decrease the quota by
     */
    public void decreaseCourseQuota(Course course, int amount) {
        int newQuota = course.getQuota() - amount;
        if (newQuota >= 0) {
            course.setQuota(newQuota);
        }
    }

    /**
     * Changes the weekly hours for a specific course.
     *
     * @param course    the course to modify
     * @param startTime the new start time
     * @param endTime   the new end time
     */
    public void changeCourseHours(Course course, String startTime, String endTime) {
        course.setStartTime(startTime);
        course.setEndTime(endTime);
    }

    /**
     * Changes the days when a specific course is taught.
     *
     * @param course the course to modify
     * @param days   the new days string
     */
    public void changeCourseDays(Course course, String days) {
        course.setDays(days);
    }

    /**
     * Adds a course to this instructor's list.
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
     * Drops a course from this instructor's list.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        courses.remove(course);
        course.setInstructor(null);
    }

    /**
     * Calculates the total number of students enrolled in all courses of this Instructor.
     *
     * @return the total number of students in this instructor's courses
     */
    public int getNumberOfStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this Instructor.
     *
     * @return the total remaining quota across all courses taught by this instructor
     */
    public int getRemainingQuotaInCourses() {
        int remainingQuota = 0;
        for (Course course : courses) {
            remainingQuota += (course.getQuota() - course.getEnrolledStudents().size());
        }
        return remainingQuota;
    }
}

/**
 * Represents a Student enrolled in courses.
 */
class Student {
    private String name;
    private String surname;
    private double grade;
    private List<Course> enrolledCourses;
    private List<String> requestedDocuments;

    /**
     * Constructs an empty Student.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.requestedDocuments = new ArrayList<>();
    }

    // Getters and Setters
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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<String> getRequestedDocuments() {
        return requestedDocuments;
    }

    public void setRequestedDocuments(List<String> requestedDocuments) {
        this.requestedDocuments = requestedDocuments;
    }

    /**
     * Enrolls this student in a course.
     *
     * @param course the course to enroll in
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollInCourse(Course course) {
        if (course.enrollStudent(this)) {
            enrolledCourses.add(course);
            return true;
        }
        return false;
    }

    /**
     * Drops this student from a course.
     *
     * @param course the course to drop
     * @return true if dropping was successful, false if not enrolled
     */
    public boolean dropCourse(Course course) {
        if (course.dropStudent(this)) {
            enrolledCourses.remove(course);
            return true;
        }
        return false;
    }

    /**
     * Requests a document.
     *
     * @param document the document to request
     */
    public void requestDocument(String document) {
        requestedDocuments.add(document);
    }

    /**
     * Receives a transcript.
     *
     * @return a string representation of the transcript
     */
    public String receiveTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(name).append(" ").append(surname).append(":\n");
        for (Course course : enrolledCourses) {
            transcript.append("- ").append(course.getName()).append(": ").append(grade).append("\n");
        }
        return transcript.toString();
    }

    /**
     * Conducts a survey.
     *
     * @param survey the survey content
     */
    public void conductSurvey(String survey) {
        // Implementation would depend on how surveys are stored/processed
        System.out.println(name + " " + surname + " conducted survey: " + survey);
    }
}