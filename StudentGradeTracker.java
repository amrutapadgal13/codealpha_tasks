

import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> marks = new ArrayList<Integer>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        if (n <= 0) {
            System.out.println("Please enter a valid number.");
            sc.close();
            return;
        }

        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter marks (0-100): ");
            int mark = sc.nextInt();
            sc.nextLine();

            if (mark < 0 || mark > 100) {
                System.out.println("Invalid marks! Enter marks between 0 and 100.");
                i--;
                continue;
            }

            names.add(name);
            marks.add(mark);
        }

        int total = 0;
        int highest = marks.get(0);
        int lowest = marks.get(0);

        String highestStudent = names.get(0);
        String lowestStudent = names.get(0);

        System.out.println("\n===== STUDENT REPORT =====");

        for (int i = 0; i < marks.size(); i++) {

            int mark = marks.get(i);

            System.out.println(names.get(i) + " : " + mark);

            total = total + mark;

            if (mark > highest) {
                highest = mark;
                highestStudent = names.get(i);
            }

            if (mark < lowest) {
                lowest = mark;
                lowestStudent = names.get(i);
            }
        }

        
        double average = (double) total / marks.size();

        System.out.printf("Average Marks: %.2f%n", average);
       


        System.out.println("\n===== SUMMARY REPORT =====");
        System.out.println("Total Students: " + names.size());
        System.out.printf("Average Marks: %.2f%n", average);
        System.out.println("Highest Marks: " + highest
                + " (" + highestStudent + ")");
        System.out.println("Lowest Marks: " + lowest
                + " (" + lowestStudent + ")");

        sc.close();
    }
}