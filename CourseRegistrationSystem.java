import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private List<String> schedule;
    private List<String> enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, List<String> schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents.size();
    }

    public void enrollStudent(String studentId) {
        if (getAvailableSlots() > 0) {
            enrolledStudents.add(studentId);
            System.out.println("Enrolled student " + studentId + " in " + courseCode);
        } else {
            System.out.println("Course " + courseCode + " is full. Cannot enroll student " + studentId);
        }
    }

    public void removeStudent(String studentId) {
        if (enrolledStudents.remove(studentId)) {
            System.out.println("Removed student " + studentId + " from " + courseCode);
        } else {
            System.out.println("Student " + studentId + " is not enrolled in " + courseCode);
        }
    }

    @Override
    public String toString() {
        return "Course: " + courseCode + "\nTitle: " + title + "\nDescription: " + description +
               "\nCapacity: " + capacity + "\nAvailable Slots: " + getAvailableSlots() +
               "\nSchedule: " + schedule;
    }
}

class Student {
    private String studentId;
    private String name;
    private List<String> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerForCourse(String courseCode, List<Course> courses) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                if (!registeredCourses.contains(courseCode)) {
                    course.enrollStudent(studentId);
                    registeredCourses.add(courseCode);
                    System.out.println(name + " registered for " + courseCode);
                } else {
                    System.out.println(name + " is already registered for " + courseCode);
                }
                return;
            }
        }
        System.out.println("Course " + courseCode + " not found.");
    }

    public void dropCourse(String courseCode, List<Course> courses) {
        if (registeredCourses.contains(courseCode)) {
            for (Course course : courses) {
                if (course.getCourseCode().equals(courseCode)) {
                    course.removeStudent(studentId);
                    registeredCourses.remove(courseCode);
                    System.out.println(name + " dropped " + courseCode);
                    return;
                }
            }
        } else {
            System.out.println(name + " is not registered for " + courseCode);
        }
    }

    @Override
    public String toString() {
        return "Student: " + studentId + "\nName: " + name + "\nRegistered Courses: " + registeredCourses;
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        // Add some sample courses
        Course c1 = new Course("CSE101", "Introduction to Computer Science", "Basic CS concepts", 30, List.of("Mon 9:00 AM"));
        Course c2 = new Course("MAT101", "Calculus", "Mathematics course", 25, List.of("Tue 10:30 AM"));
        courses.add(c1);
        courses.add(c2);

        // Add some sample students
        Student s1 = new Student("S1001", "Alice");
        Student s2 = new Student("S1002", "Bob");
        students.add(s1);
        students.add(s2);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Course Listing");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nCourse Listing:");
                    for (Course course : courses) {
                        System.out.println(course);
                        System.out.println("Enrolled Students: " + course.getEnrolledStudents());
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    for (Student student : students) {
                        if (student.getStudentId().equals(studentId)) {
                            System.out.print("Enter course code to register: ");
                            String courseCode = scanner.nextLine();
                            student.registerForCourse(courseCode, courses);
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    for (Student student : students) {
                        if (student.getStudentId().equals(studentId)) {
                            System.out.print("Enter course code to drop: ");
                            String courseCode = scanner.nextLine();
                            student.dropCourse(courseCode, courses);
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
