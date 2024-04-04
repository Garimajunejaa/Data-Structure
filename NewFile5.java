import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class NewFile5 {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Set<LocalDate>> programAssignedDates = new HashMap<>();
    private static Map<String, Map<String, Map<String, List<String>>>> universityDetails = new HashMap<>();

    public static void main(String[] args) {
        // Input number of schools
        int numberOfSchools = getNumberOfSchools(scanner);

        // Input details for each school
        for (int schoolIndex = 1; schoolIndex <= numberOfSchools; schoolIndex++) {
            String schoolName = getSchoolName(scanner, "Enter the name of School " + schoolIndex + ": ");
            universityDetails.put(schoolName, new HashMap<>());

            // Input number of programs for the school
            int numberOfPrograms = getNumberOfPrograms(scanner, "Enter the number of programs for " + schoolName + ": ");

            // Input details for each program
            for (int programIndex = 1; programIndex <= numberOfPrograms; programIndex++) {
                String programName = getProgramName(scanner, "Enter the name of Program " + programIndex + ": ");
                universityDetails.get(schoolName).put(programName, new HashMap<>());

                // Input number of semesters for the program
                int numberOfSemesters = getNumberOfSemesters(scanner, "Enter the number of semesters for " + programName + ": ");

                // Input number of courses for each semester
                for (int semesterIndex = 1; semesterIndex <= numberOfSemesters; semesterIndex++) {
                    String semester = getSemesterName(scanner, "Enter the name of Semester " + semesterIndex + ": ");
                    int numberOfCoursesInSemester = getNumberOfCourses(scanner, "Enter the number of courses for " + semester + ": ");
                    List<String> courseNames = getCourseNames(scanner, numberOfCoursesInSemester);
                    universityDetails.get(schoolName).get(programName).put(semester, courseNames);
                }
            }
        }

        // Input date range
        LocalDate startDate = getDateFromUser("Enter the start date (yyyy-MM-dd): ");
        LocalDate endDate = getDateFromUser("Enter the end date (yyyy-MM-dd): ");

        // Generate and assign random dates for each course
        generateAndAssignRandomDatesAndShifts(startDate, endDate);

        // Display the assigned dates
        

        scanner.close();
    }

    private static String getSchoolName(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static String getProgramName(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static String getSemesterName(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static int getNumberOfSchools(Scanner scanner) {
        int numberOfSchools = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter the number of schools: ");

            if (scanner.hasNextInt()) {
                numberOfSchools = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }

        return numberOfSchools;
    }

    private static int getNumberOfPrograms(Scanner scanner, String prompt) {
        int numberOfPrograms = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                numberOfPrograms = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }

        return numberOfPrograms;
    }

    private static int getNumberOfSemesters(Scanner scanner, String prompt) {
        int numberOfSemesters = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                numberOfSemesters = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }

        return numberOfSemesters;
    }

    private static int getNumberOfCourses(Scanner scanner, String prompt) {
        int numberOfCourses = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                numberOfCourses = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }

        return numberOfCourses;
    }

    private static List<String> getCourseNames(Scanner scanner, int numberOfCourses) {
        List<String> courseNames = new ArrayList<>();

        for (int i = 1; i <= numberOfCourses; i++) {
            System.out.print("Enter the name of Course " + i + ": ");
            courseNames.add(scanner.next());
        }

        return courseNames;
    }

    private static LocalDate getDateFromUser(String prompt) {
        LocalDate date = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String dateString = scanner.next();
                date = LocalDate.parse(dateString);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid date in the format yyyy-MM-dd.");
            }
        }

        return date;
    }

    private static String getCourseNamesAsString(String schoolName, String programName, String semester) {
        List<String> courseNames = universityDetails.get(schoolName).get(programName).get(semester);
        return String.join(", ", courseNames);
    }

    private static void generateAndAssignRandomDatesAndShifts(LocalDate startDate, LocalDate endDate) {
        
        String semesterType = getSemesterType(scanner);

        for (String schoolName : universityDetails.keySet()) {

            for (String programName : universityDetails.get(schoolName).keySet()) {
                for (String semester : universityDetails.get(schoolName).get(programName).keySet()) {
                    int semesterIndex = Integer.parseInt(semester);

                    // Check if the semester type matches user choice
                    if ((semesterIndex % 2 == 1 && semesterType.equals("odd")) || (semesterIndex % 2 == 0 && semesterType.equals("even"))) {
                        List<String> courseNames = universityDetails.get(schoolName).get(programName).get(semester);
                        assignCoursesToSemester(schoolName, programName, semester, courseNames, startDate, endDate);

                    }
                }
            }
        }
    }

    


    private static String getSemesterType(Scanner scanner) {
        String semesterType = "";

        while (!semesterType.equals("odd") && !semesterType.equals("even")) {
            System.out.print("Enter 'odd' or 'even' to assign dates to semesters: ");
            semesterType = scanner.next().toLowerCase();
        }

        return semesterType;
    }

    private static void assignCoursesToSemester(String schoolName, String programName, String semester, List<String> courseNames, LocalDate startDate, LocalDate endDate) {
    System.out.println("Assigning courses for " + schoolName + " - " + programName + " - " + semester);
    System.out.println("|--------------------------|---------------|----------------|--------------|--------------------|");
    System.out.println("|        Program           |     Semester  |     Course     |     Date     |       Timing       |");
    System.out.println("|--------------------------|---------------|----------------|--------------|--------------------|s");

    // Generate random dates and shifts for the current semester
    List<LocalDate> semesterRandomDates = generateRandomDates(courseNames.size(), programName, startDate, endDate);
    List<ShiftCourse> semesterRandomShifts = generateRandomShifts(courseNames.size());

    Iterator<LocalDate> dateIterator = semesterRandomDates.iterator();
    Iterator<ShiftCourse> shiftIterator = semesterRandomShifts.iterator();

    

    for (String courseName : courseNames) {
        if (dateIterator.hasNext() && shiftIterator.hasNext()) {
            LocalDate randomDate = dateIterator.next();
            ShiftCourse randomShift = shiftIterator.next();

            // Check if the date is not a Sunday
            if (randomDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                // Add the date and shift to the program's semester
                String key = programName + "-" + semester;
                programAssignedDates.computeIfAbsent(key, k -> new HashSet<>()).add(randomDate);

                System.out.println("\t"+programName + "\t\t" + semester + "\t\t" + courseName + "\t\t" + randomDate + "\t" + randomShift);
                System.out.println("|--------------------------|---------------|----------------|--------------|--------------------|");
        
            } else {
                // If it's a Sunday, skip this date and shift and try the next one
                dateIterator.remove();
                shiftIterator.remove();
            }
        }
    }
    
}



    




    private static List<LocalDate> generateRandomDates(int numberOfDates, String programKey, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> randomDates = new ArrayList<>();
        Set<LocalDate> uniqueDates = new HashSet<>();
        Random random = new Random();

        while (uniqueDates.size() < numberOfDates) {
            long startEpochDay = startDate.toEpochDay();
            long endEpochDay = endDate.toEpochDay();
            long randomEpochDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay + 1));

            LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);

            // Check if the date is unique for the program and not a Sunday
            if (randomDate.getDayOfWeek() != DayOfWeek.SUNDAY && uniqueDates.add(randomDate) && isDateUniqueForProgram(programKey, randomDate)) {
                randomDates.add(randomDate);
            }
        }

        return randomDates;
    }
    private static List<ShiftCourse> generateRandomShifts(int numberOfShifts) {
        List<ShiftCourse> randomShifts = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfShifts; i++) {
            int shiftIndex = random.nextInt(2); // 0 for Morning, 1 for Afternoon
            randomShifts.add(ShiftCourse.values()[shiftIndex]);
        }

        return randomShifts;
    }
    private static enum ShiftCourse {
        MORNING, AFTERNOON
    }


    private static boolean isDateUniqueForProgram(String programKey, LocalDate date) {
        Set<LocalDate> assignedDates = programAssignedDates.computeIfAbsent(programKey, k -> new HashSet<>());
        return assignedDates.add(date);
    }
}
