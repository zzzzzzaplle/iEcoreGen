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
     * Constructs an AcademicProgram with empty courses and classrooms lists.
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
        int courseCount = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                courseCount++;
            }
        }
        return courseCount;
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
            if (course.getCourseCode().equals(courseCode)) {
                targetCourse = course;
                break;
            }
        }

        if (targetCourse == null || targetCourse.getEnrolledStudents().isEmpty()) {
            return 0.0;
        }

        double totalGrade = 0.0;
        for (Student student : targetCourse.getEnrolledStudents()) {
            totalGrade += student.getGrade();
        }

        return totalGrade / targetCourse.getEnrolledStudents().size();
    }
}

/**
 * Represents a Course in the Academic Program.
 */
class Course {
    private String courseName;
    private String courseCode;
    private String startTime;
    private String endTime;
    private String days;
    private Classroom classroom;
    private Instructor instructor;
    private int quotaLimit;
    private List<Student> enrolledStudents;

    /**
     * Constructs a Course with empty enrolled students list.
     */
    public Course() {
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

    /**
     * Enrolls a student in this course if there is space available.
     *
     * @param student the student to enroll
     * @return true if the student was successfully enrolled, false otherwise
     */
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() < quotaLimit) {
            return enrolledStudents.add(student);
        }
        return false;
    }

    /**
     * Drops a student from this course.
     *
     * @param student the student to drop
     * @return true if the student was successfully dropped, false otherwise
     */
    public boolean dropStudent(Student student) {
        return enrolledStudents.remove(student);
    }
}

/**
 * Represents a Classroom used for courses.
 */
class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Constructs a Classroom with default values.
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
 * Represents an Instructor who teaches courses.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Constructs an Instructor with an empty courses list.
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
     * Increases the quota limit of a course by a specified amount.
     *
     * @param course the course to modify
     * @param amount the amount to increase the quota by
     */
    public void increaseQuota(Course course, int amount) {
        course.setQuotaLimit(course.getQuotaLimit() + amount);
    }

    /**
     * Decreases the quota limit of a course by a specified amount.
     *
     * @param course the course to modify
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuota(Course course, int amount) {
        int newQuota = course.getQuotaLimit() - amount;
        if (newQuota >= 0) {
            course.setQuotaLimit(newQuota);
        }
    }

    /**
     * Changes the weekly hours of a course.
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
     * Changes the days a course is taught.
     *
     * @param course the course to modify
     * @param days   the new days the course is taught
     */
    public void changeCourseDays(Course course, String days) {
        course.setDays(days);
    }

    /**
     * Adds a course to this instructor's course list.
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
     * Drops a course from this instructor's course list.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Calculates the total number of students enrolled in all courses of this Instructor.
     *
     * @return the total number of students enrolled in this instructor's courses
     */
    public int getTotalStudentsInCourses() {
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
            remainingQuota += (course.getQuotaLimit() - course.getEnrolledStudents().size());
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
    private List<Document> requestedDocuments;

    /**
     * Constructs a Student with empty enrolled courses and requested documents lists.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.requestedDocuments = new ArrayList<>();
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

    public List<Document> getRequestedDocuments() {
        return requestedDocuments;
    }

    public void setRequestedDocuments(List<Document> requestedDocuments) {
        this.requestedDocuments = requestedDocuments;
    }

    /**
     * Enrolls this student in a course.
     *
     * @param course the course to enroll in
     * @return true if successfully enrolled, false otherwise
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
     * @param course the course to drop from
     * @return true if successfully dropped, false otherwise
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
    public void requestDocument(Document document) {
        requestedDocuments.add(document);
    }

    /**
     * Receives a transcript.
     *
     * @return a new Transcript object
     */
    public Transcript receiveTranscript() {
        return new Transcript(this);
    }

    /**
     * Conducts a survey.
     *
     * @param survey the survey to conduct
     */
    public void conductSurvey(Survey survey) {
        survey.addParticipant(this);
    }
}

/**
 * Represents a Document that can be requested by students.
 */
class Document {
    private String documentType;
    private String documentId;

    /**
     * Constructs a Document with default values.
     */
    public Document() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

/**
 * Represents a Transcript for a student.
 */
class Transcript {
    private Student student;
    private List<Course> completedCourses;

    /**
     * Constructs a Transcript for a specific student.
     *
     * @param student the student this transcript belongs to
     */
    public Transcript(Student student) {
        this.student = student;
        this.completedCourses = new ArrayList<>(student.getEnrolledCourses());
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Course> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(List<Course> completedCourses) {
        this.completedCourses = completedCourses;
    }
}

/**
 * Represents a Survey that students can participate in.
 */
class Survey {
    private String surveyTopic;
    private List<Student> participants;

    /**
     * Constructs a Survey with an empty participants list.
     */
    public Survey() {
        this.participants = new ArrayList<>();
    }

    public String getSurveyTopic() {
        return surveyTopic;
    }

    public void setSurveyTopic(String surveyTopic) {
        this.surveyTopic = surveyTopic;
    }

    public List<Student> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Student> participants) {
        this.participants = participants;
    }

    /**
     * Adds a participant to this survey.
     *
     * @param student the student to add as a participant
     */
    public void addParticipant(Student student) {
        if (!participants.contains(student)) {
            participants.add(student);
        }
    }
}