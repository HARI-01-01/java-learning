import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main{
//    for the custom error messages
    static class InvalidInputException extends  Exception{
        public InvalidInputException(String message){
            super(message);
        }
    }
//    student manager class
    static class StudentManager{
    ArrayList<Student> stu = new ArrayList<>();

    void addStudent(Student s){
        stu.add(s);
    }
    void listStudent(){
        if(stu.isEmpty()){
            System.out.println("No Student data is present in the database!");
            return;
        }
        for(Student s:stu){
            System.out.println(s);
        }
    }
    Student searchByRollNo(int rollno){
        for(Student s: stu){
            if(s.rollNo == rollno){
                return s;
            }
        }
        return null;
    }
    boolean deleteByRollno(int roll){
        int n =  stu.size();
        for(int i=0;i<n;i++){
            if(stu.get(i).rollNo == roll){
                stu.remove(i);
                return true;
            }
        }
        return false;
    }
    boolean UpdateInfo(int roll,double marks){

        for(Student s: stu){
            if(s.rollNo == roll){
                s.marks = marks;
                return true;
            }
        }
        return false;
    }
    void saveToFile(String filename){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(Student s: stu){
                writer.write(s.name+","+s.rollNo+","+s.marks);
                writer.newLine();
            }
            System.out.println("Student saved to" + filename);
        }catch (IOException e){
            System.out.println("Error saving: "+e.getMessage());
        }
    }
    void loadFormFile(String filename){
        stu.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine())!= null){
                String[] parts = line.split(",");
                String name= parts[0];
                int rollno = Integer.parseInt(parts[1]);
                double marks = Double.parseDouble(parts[2]);
                stu.add(new Student(name,rollno,marks));
            }
            System.out.println("Student loaded from "+filename);
        }catch (IOException e){
            System.out.println("Error loading: "+ e.getMessage());
        }catch (InvalidInputException e){
            System.out.println("Invalid student data in file: "+e.getMessage());
        }
    }
    ArrayList<Student> Toppers(){
        if(stu.isEmpty()) return new ArrayList<>();
        stu.sort((s1,s2)-> Double.compare(s2.marks,s1.marks));
        ArrayList<Student> top  = new ArrayList<Student>();
        for(int i=0;i<stu.size() && i<3 ;i++){
            top.add(stu.get(i));
        }
        return top;
    }
    double Average(){
        if(stu.isEmpty()) {
            return -1;
        }
        double avg = stu.stream()
                .mapToDouble(s->s.marks)
                .average()
                .orElse(0.0);
        return avg;
    }
    void ListSortByName(){
        if(stu.isEmpty()){
            System.out.println("No Student yet.");
          return;
        }
        stu.stream()
                .sorted((a,b)-> a.name.compareToIgnoreCase(b.name))
                .forEach(System.out::println);
    }

}
//    Student class
    static class Student{
        String name;
        int rollNo;
        double marks;

        Student(String name,int rollNo,double marks) throws InvalidInputException{
            if(rollNo <=0) throw  new InvalidInputException("Roll number must be positive.");
            if(marks<0 || marks > 100) throw new InvalidInputException("Marks must be between 0 and 100");
            if(name == null || name.trim().isEmpty()) throw new InvalidInputException("Name can't be empty.");
            this.name = name;
            this.rollNo = rollNo;
            this.marks = marks;
        }
        @Override
        public String toString(){
            return "Roll: " + rollNo + " | Name: " + name + " | Marks: " + marks;
        }
}
        static int readInt(Scanner scan, String prompt){
            while (true){
                try {
                    System.out.println(prompt);
                    int value = scan.nextInt();
                    scan.nextLine();
                    return value;
                }catch (InputMismatchException e){
                    System.out.println("Invalid input! Please enter a number.");
                    scan.nextLine();
                }
            }
        }

        static double readDouble(Scanner scan, String prompt){
            while(true) {
               try {
                   System.out.println(prompt);
                   double value = scan.nextDouble();
                   scan.nextLine();
                   return value;
            }catch (InputMismatchException e) {
                   System.out.println("Invalid input ! please enter a number.");
                   scan.nextLine();
                }
               }
            }

    public static void main(String[] args){
        StudentManager stuMan = new StudentManager();
        Scanner scan = new Scanner(System.in);
        stuMan.loadFormFile("student.txt");
        while(true){
            System.out.println("===== Student management System =====");

            System.out.println(
                    "1. Add student\n" +
                    "2. List all Students \n" +
                    "3. Search by rollNo. \n" +
                            "4. Delete the student info \n" +
                            "5. Update the student marks! \n" +
                            "6. Save\n" +
                            "7. Load\n" +
                            "8. Show top 3 Student\n" +
                            "9. Show Average Marks\n" +
                            "10. Show Student Sorted By Name\n" +
                    "12. Exit\n");
            int choice = readInt(scan,"Enter your choice: ");
            scan.nextLine();
            switch (choice){
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();


                    int rollno = readInt(scan,"Enter your rollNo: ");


                    double mark = readDouble(scan,"Enter your marks: ");

                    try {
                        Student s1 = new Student(name,rollno,mark);
                        stuMan.addStudent(s1);
                    }catch (InvalidInputException e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case 2:
                    stuMan.listStudent();
                    break;
                case 3:
                    int roll = readInt(scan,"Enter roll number to search: ");
                    Student s = stuMan.searchByRollNo(roll);
                    if(s!=null){
                        System.out.println("Student found!");
                        System.out.println(s);
                    }else{
                        System.out.println("No Student found with roll number: " + roll);
                    }
                    break;
                case 4:
                    System.out.print("Enter roll number to delete: ");
                    int deleteRoll = scan.nextInt();
                    if (stuMan.deleteByRollno(deleteRoll)) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("No student found with roll number " + deleteRoll);
                    }
                    break;
                case 5:
                    System.out.print("Enter roll number to update: ");
                    int updateRoll = scan.nextInt();
                    System.out.print("Enter new marks: ");
                    double newMarks = scan.nextDouble();
                    if (stuMan.UpdateInfo(updateRoll, newMarks)) {
                        System.out.println("Marks updated successfully!");
                    } else {
                        System.out.println("No student found with roll number " + updateRoll);
                    }
                    break;
                case 6:
                    stuMan.saveToFile("student.txt");
                    break;
                case 7:
                    stuMan.loadFormFile("student.txt");
                    break;
                case 8:
                    ArrayList<Student> top = stuMan.Toppers();
                    System.out.println("Top 3 Student ");
                    for(int i=0;i<top.size();i++){
                        System.out.println(top.get(i));
                    }
                    break;
                case 9:
                    double avg = stuMan.Average();
                    if(avg == -1){
                        System.out.println("Database is empty !");
                    }else{
                        System.out.println("Average: "+ avg);
                    }
                    break;
                case 10:
                    stuMan.ListSortByName();
                    break;
                case 11:
                    System.out.println("GoodBye!");
                    stuMan.saveToFile("student.txt");
                    return;
                default:
                    System.out.print("Invalid choice");
            }
            System.out.println();
            System.out.println();

        }
    }
}