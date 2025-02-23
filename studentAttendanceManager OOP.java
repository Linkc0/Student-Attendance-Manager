import java.util.Scanner;

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }//END Person

    public void printInfo() {
        System.out.println("Name: " + name);
    }//END printInfo

    public String getName() {
        return name;
    }//END getName
}//END Person

class Student extends Person {
    private String SID;
    private String major;
    private String[] enrolledCourses;
    private String[] attendedCourses;
    private int enrolledCount;
    private int attendedCount;

    public Student(String name, String SID, String major, int maxCourses) {
        super(name);
        this.SID = SID;
        this.major = major;
        this.enrolledCourses = new String[maxCourses];
        this.attendedCourses = new String[maxCourses];
        this.enrolledCount = 0;
        this.attendedCount = 0;
    }//END Student

    public void printInfo() {
        super.printInfo();
        System.out.println("SID: " + SID);
        System.out.println("Major: " + major);
    }//END printInfo

    public void enrollInCourse(String courseName) {
        if (enrolledCount < enrolledCourses.length) {
            enrolledCourses[enrolledCount++] = courseName;
            System.out.println(getName() + " has enrolled in " + courseName);
        } else {
            System.out.println("Cannot enroll in more courses.");
        }
    }//END enrollInCourse

    public void attendClass(String courseName) {
        if (attendedCount < attendedCourses.length) {
            attendedCourses[attendedCount++] = courseName;
            System.out.println(getName() + " attended " + courseName);
        }
    }//END attendClass

    public double calculateAverageAttendance() {
        if (enrolledCount == 0) {
            return 0.0;
        }
        return ((double) attendedCount / enrolledCount) * 100;
    }//END calculateAverageAttendance
}//END Student

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfCourses = Integer.parseInt(inputs("Enter the number of courses available: "));
        Student student = createStudent(numberOfCourses);
        student.printInfo();
        System.out.println();
        String[] availableCourses = new String[numberOfCourses];
        for (int i = 0; i < numberOfCourses; i++) {
            availableCourses[i] = inputs("Enter the name of course " + (i + 1) + ": ");
        }
        System.out.println("Available Courses:");
        for (int i = 0; i < availableCourses.length; i++) {
            System.out.println((i + 1) + ". " + availableCourses[i]);
        }
        System.out.println();
        enrollInCourses(student, availableCourses);
        attendClasses(student, availableCourses);
        double avgAttendance = student.calculateAverageAttendance();
        System.out.println("Average Attendance: " + avgAttendance + "%");
    }//END main

    public static Student createStudent(int numberOfCourses) {
        String name = inputs("What is the name of the student? ");
        String SID = inputs("What is the SID of " + name + "? ");
        String major = inputs("What is the major of " + name + "? ");
        return new Student(name, SID, major, numberOfCourses);
    }//END createStudent

    public static String inputs(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }//END inputs

    public static void enrollInCourses(Student student, String[] availableCourses) {
        boolean[] enrolledFlags = new boolean[availableCourses.length];
        boolean exit = false;
        while (!exit) {
            int courseNumber = getValidCourseNumber("Enter the number of a course to enroll in (or 0 to finish): ", availableCourses.length);
            if (courseNumber == 0) {
                exit = true;
            } else if (!enrolledFlags[courseNumber - 1]) {
                student.enrollInCourse(availableCourses[courseNumber - 1]);
                enrolledFlags[courseNumber - 1] = true;
            } else {
                System.out.println("You are already enrolled in this course.");
            }
        }
    }//END enrollInCourses

    public static void attendClasses(Student student, String[] availableCourses) {
        boolean[] attendedFlags = new boolean[availableCourses.length];
        boolean exit = false;
        while (!exit) {
            int courseNumber = getValidCourseNumber("Enter the number of a course you attended (or 0 to finish): ", availableCourses.length);
            if (courseNumber == 0) {
                exit = true;
            } else if (!attendedFlags[courseNumber - 1]) {
                student.attendClass(availableCourses[courseNumber - 1]);
                attendedFlags[courseNumber - 1] = true;
            } else {
                System.out.println("You have already marked attendance for this course.");
            }
        }
    }//END attendClasses

    private static int getValidCourseNumber(String message, int maxCourses) {
        int courseNumber = -1;
        boolean exit = false;
        while (!exit) {
            String input = inputs(message);
            if (isNumeric(input)) {
                courseNumber = Integer.parseInt(input);
                if (courseNumber >= 0 && courseNumber <= maxCourses) {
                    exit = true;
                } else {
                    System.out.println("Invalid number. Please enter a number between 1 and " + maxCourses + ", or 0 to finish.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return courseNumber;
    }//END getValidCourseNumber

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }//END isNumeric
}//END Main
