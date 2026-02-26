import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Academic Program in the Software Engineering Department.
 */
class AcademicProgram {
    private String name;
    private List<Course> courses;
    private List<Classroom> classrooms;

    /**
     * Default constructor for AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
    }

    /**
     * Getter for name.
     * @return the name of the academic program
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for courses.
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Setter for courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Getter for classrooms.
     * @return the list of classrooms
     */
    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    /**
     * Setter for classrooms.
     * @param classrooms the classrooms to set
     */
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    /**
     * Adds a course to the academic program.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Adds a classroom to the academic program.
     * @param classroom the classroom to add
     */
    public void addClassroom(Classroom classroom) {
        this.classrooms.add(classroom);
    }

    /**
     * Calculate the total capacity of all classrooms used in an Academic Program.
     * @return the total classroom capacity
     */
    public int getTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Classroom classroom : classrooms) {
            totalCapacity += classroom.getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Find the total number of courses in an Academic Program that are taught on a given day.
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
     * Calculate the average grade for the students enrolled in a specific course within an Academic Program.
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of students in the course, or 0 if no students or course not found
     */
    public double getAverageGradeInCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                List<Student> students = course.getStudents();
                if (students.isEmpty()) {
                    return 0;
                }
                
                double totalGrade = 0;
                for (Student student : students) {
                    totalGrade += student.getGrade();
                }
                return totalGrade / students.size();
            }
        }
        return 0; // Course not found
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
     * Default constructor for Course.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Getter for name.
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for code.
     * @return the code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for code.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for startTime.
     * @return the start time of the course
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter for startTime.
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for endTime.
     * @return the end time of the course
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter for endTime.
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter for days.
     * @return the days of the course
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Setter for days.
     * @param days the days to set
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Getter for classroom.
     * @return the classroom of the course
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Setter for classroom.
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Getter for instructor.
     * @return the instructor of the course
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Setter for instructor.
     * @param instructor the instructor to set
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Getter for students.
     * @return the students enrolled in the course
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Setter for students.
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Getter for quotaLimit.
     * @return the quota limit of the course
     */
    public int getQuotaLimit() {
        return quotaLimit;
    }

    /**
     * Setter for quotaLimit.
     * @param quotaLimit the quota limit to set
     */
    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    /**
     * Enrolls a student in the course.
     * @param student the student to enroll
     */
    public void enrollStudent(Student student) {
        if (students.size() < quotaLimit) {
            this.students.add(student);
        }
    }

    /**
     * Drops a student from the course.
     * @param student the student to drop
     */
    public void dropStudent(Student student) {
        this.students.remove(student);
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
     * Default constructor for Classroom.
     */
    public Classroom() {
    }

    /**
     * Getter for id.
     * @return the id of the classroom
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for capacity.
     * @return the capacity of the classroom
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setter for capacity.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter for floor.
     * @return the floor of the classroom
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Setter for floor.
     * @param floor the floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Getter for block.
     * @return the block of the classroom
     */
    public String getBlock() {
        return block;
    }

    /**
     * Setter for block.
     * @param block the block to set
     */
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
     * Default constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Getter for name.
     * @return the name of the instructor
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for surname.
     * @return the surname of the instructor
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter for surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter for title.
     * @return the title of the instructor
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for specialty.
     * @return the specialty of the instructor
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Setter for specialty.
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Getter for courses.
     * @return the courses taught by the instructor
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Setter for courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the instructor's course list.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the instructor's course list.
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Increases the quota limit of a course.
     * @param course the course to modify
     * @param increment the amount to increase the quota by
     */
    public void increaseQuota(Course course, int increment) {
        course.setQuotaLimit(course.getQuotaLimit() + increment);
    }

    /**
     * Decreases the quota limit of a course.
     * @param course the course to modify
     * @param decrement the amount to decrease the quota by
     */
    public void decreaseQuota(Course course, int decrement) {
        int newQuota = course.getQuotaLimit() - decrement;
        if (newQuota >= 0) {
            course.setQuotaLimit(newQuota);
        }
    }

    /**
     * Changes the weekly hours of a course.
     * @param course the course to modify
     * @param startTime the new start time
     * @param endTime the new end time
     */
    public void changeCourseHours(Course course, String startTime, String endTime) {
        course.setStartTime(startTime);
        course.setEndTime(endTime);
    }

    /**
     * Changes the days of a course.
     * @param course the course to modify
     * @param days the new days for the course
     */
    public void changeCourseDays(Course course, List<String> days) {
        course.setDays(days);
    }

    /**
     * Determine the total number of students enrolled in all courses of this Instructor.
     * @return the total number of students in the instructor's courses
     */
    public int getNumberOfStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculate the remaining quota for all courses of this Instructor.
     * @return the total remaining quota across all courses
     */
    public int getRemainingQuotaInCourses() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            int enrolled = course.getStudents().size();
            int quota = course.getQuotaLimit();
            totalRemainingQuota += (quota - enrolled);
        }
        return totalRemainingQuota;
    }
}

/**
 * Represents a Student enrolled in courses.
 */
class Student {
    private String name;
    private String surname;
    private String studentId;
    private List<Course> courses;
    private double grade;
    private List<String> documents;

    /**
     * Default constructor for Student.
     */
    public Student() {
        this.courses = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    /**
     * Getter for name.
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for surname.
     * @return the surname of the student
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter for surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter for studentId.
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Setter for studentId.
     * @param studentId the student ID to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Getter for courses.
     * @return the courses the student is enrolled in
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Setter for courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Getter for grade.
     * @return the grade of the student
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Setter for grade.
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Getter for documents.
     * @return the documents requested by the student
     */
    public List<String> getDocuments() {
        return documents;
    }

    /**
     * Setter for documents.
     * @param documents the documents to set
     */
    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    /**
     * Enrolls the student in a course.
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        if (!courses.contains(course) && course.getStudents().size() < course.getQuotaLimit()) {
            this.courses.add(course);
            course.enrollStudent(this);
        }
    }

    /**
     * Drops a course the student is enrolled in.
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            this.courses.remove(course);
            course.dropStudent(this);
        }
    }

    /**
     * Receives a transcript.
     * @return a string representation of the transcript
     */
    public String receiveTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(name).append(" ").append(surname).append(":\n");
        for (Course course : courses) {
            transcript.append("- ").append(course.getCode()).append(": ").append(grade).append("\n");
        }
        return transcript.toString();
    }

    /**
     * Requests a document.
     * @param document the document to request
     */
    public void requestDocument(String document) {
        this.documents.add(document);
    }

    /**
     * Conducts a survey.
     * @param survey the survey to conduct
     */
    public void conductSurvey(String survey) {
        // Implementation would depend on survey requirements
    }
}