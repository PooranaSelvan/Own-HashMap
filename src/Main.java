// import java.util.HashMap;
// import java.util.Iterator;
import java.util.*;


class Main {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Hospital hos = new Hospital();

        int userChoice = 0;

        while (userChoice != 5){
            System.out.println("---------- Hospital Management Menu -------");
            System.out.print("1. Add Doctor\n2. Add Patient\n3. Show all Doctors and their Patients\n4. Severe Patients\n5. Exit\nEnter your Choice : ");
            userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice){
                case  1:
                    addNewDoctor(hos);
                    break;
                case 2:
                    addNewPatient(hos);
                    break;
                case 3:
                    showAllDoctorsNPatients(hos);
                    break;
                case 4:
                    showSeverePatients(hos);
                    break;
                case 5:
                    System.out.println("\n----------\nThank you for Visiting!\n----------\n");
                    break;
                default:
                    System.out.println("\nEnter the Valid Choice!\n");
            }
        }
    }

    public static void addNewDoctor(Hospital hos){
        System.out.println();
        System.out.print("Enter the Doctor Name : ");
        String doctorName = input.nextLine();

        System.out.print("Enter the Specialist of the Doctor : ");
        String specialist = input.nextLine();

        hos.addDoctor(new Doctor(doctorName, specialist));

        System.out.println(doctorName+" has been Successfully Added as Doctor!");
        System.out.println();
    }

    public static void addNewPatient(Hospital hos){
        System.out.println();
        if (hos.list.keySet().isEmpty()) {
            System.out.println("No doctors! Add doctor to Add Patient.");
            System.out.println();
            return;
        }

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
        System.out.println();
    }

    public static void showSeverePatients(Hospital hos){
        System.out.println("\nSevere Patients : ");

        PriorityQueue<Patient> patients = new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator()));

        for (Doctor d : hos.list.keySet()){
            PriorityQueue<Patient> patientQueue = hos.getPatients(d);

            while (!patientQueue.isEmpty()){
                patients.add(patientQueue.poll());
            }
        }

        for (Patient p : patients){
            System.out.println(p);
        }

        System.out.println();
    }

    public static void showAllDoctorsNPatients(Hospital hos){
        System.out.println("All Doctor & Patients : ");

        for(Doctor d : hos.list.keySet()){
            System.out.println("Doctor : "+d);

            PriorityQueue<Patient> patientQueue = hos.getPatients(d);

            while (!patientQueue.isEmpty()){
                System.out.println("Patient : "+patientQueue.poll());
            }
            System.out.println();
        }
        System.out.println();
    }
}
