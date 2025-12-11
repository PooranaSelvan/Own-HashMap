// import java.util.HashMap;
// import java.util.Iterator;
import javax.print.Doc;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Scanner;


class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Hospital hos = new Hospital();

        int userChoice = 0;
        int doctorCount = 0;
        int patientCount = 0;

        while(true){
            try {
                System.out.print("How Many Doctors You want to Add ? : ");
                doctorCount = input.nextInt();

                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                System.out.println();
            }
        }
        input.nextLine();

        for(int i = 0; i < doctorCount; i++){
            System.out.print("Enter the Doctor Name : ");
            String doctorName = input.nextLine();

            System.out.print("Enter the Specialist of the Doctor : ");
            String specialist = input.nextLine();

            Doctor d = new Doctor(doctorName, specialist);
            hos.addDoctor(d);

            System.out.println(doctorName+" has been Successfully Added as Doctor!");
            System.out.println();
        }

        while (true){
            try{
                System.out.print("How Many Patients You want to Add ? : ");
                patientCount = input.nextInt();

                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }
        input.nextLine();


        for(int i = 0; i < patientCount; i++){
            System.out.print("Enter Patient Name : ");
            String name = input.nextLine();

            System.out.print("Enter Patient Age : ");
            int age = input.nextInt();
            input.nextLine();

            System.out.print("Enter Patient Disease : ");
            String disease = input.nextLine();

            System.out.print("Enter Priority (Priority Level : 1 = highest, 2 = medium, 3 = lowest) : ");
            int priority = input.nextInt();
            input.nextLine();

            System.out.println("Choose Your Doctor : ");
            hos.showDoctors();

            System.out.print("Enter the Doctor Id to Choose : ");
            int docId = input.nextInt();
            input.nextLine();

            Doctor doc = hos.getDoctor(docId);

            if(doc != null){
                hos.addPatient(doc, new Patient(name, age, disease, priority));
                System.out.println("Patient Added Successfully!");
            } else {
                System.out.println("Doctor Not Found!");
            }
        }

        System.out.println("All Doctor & Patients : ");

        for(Doctor d : hos.list.keySet()){
            System.out.println("Doctor : "+d);

            PriorityQueue<Patient> patientQueue = hos.getPatients(d);

            while (!patientQueue.isEmpty()){
                System.out.println("Patient : "+patientQueue.poll());
            }
            System.out.println();
        }
    }
}
