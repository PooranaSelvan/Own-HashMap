// import java.util.HashMap;
// import java.util.Iterator;
import java.util.*;


class Main {
    static Scanner input = new Scanner(System.in);
    static String cyanColor = "\u001B[96m";
    static String resetColor = "\u001B[0m";

    public static void main(String[] args) {
        Hospital hos = new Hospital();


////        Default Data :
//        Doctor d1 = new Doctor("Hari Sudhan", "Neurologist");
//        Doctor d2 = new Doctor("Poorana Selvan", "Cardiologist");
//
//        hos.addDoctor(d1);
//        hos.addDoctor(d2);
//
//        Patient p1 = new Patient("Hari Vignesh", 10, "Fever", 1);
//        Patient p2 = new Patient("Siva Surya", 20, "Cold", 3);
//        Patient p3 = new Patient("Baskar", 100, "Stomach Pain", 2);
//        Patient p4 = new Patient("Kowsik", 20, "Eye Pain", 2);
//        Patient p5 = new Patient("RagulRagul", 19, "Body Pain", 3);
//
//        hos.addPatient(d1, p1);
//        hos.addPatient(d1, p2);
//        hos.addPatient(d2, p3);
//        hos.addPatient(d2, p4);
//        hos.addPatient(d1, p5);


        int userChoice = 0;

        while (userChoice != 5){
            System.out.println("---------- Hospital Management Menu -------");
            System.out.print("1. Add Doctor --- (MyHashMap)\n2. Add Patient --- (MyHashMap)\n3. Show all Doctors and their Patients --- (MyHashSet, MyHashMap & PriorityQueue)\n4. Severe Patients --- (PriorityQueue)\n5. Exit\nEnter your Choice : ");
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

        System.out.println();
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
            System.out.println();
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

            patients.addAll(patientQueue);
        }

        for (Patient p : patients){
            System.out.println(p);
        }

        System.out.println();
    }

    public static void showAllDoctorsNPatients(Hospital hos){
        System.out.println();
        System.out.println("All Doctor & Patients : ");

        for(Doctor d : hos.list.keySet()){
            System.out.println(cyanColor+"Doctor : "+d+resetColor);

            PriorityQueue<Patient> patientQueue = hos.getPatients(d);

            for(Patient p : patientQueue){
                System.out.println("Patient : "+p);
            }
            System.out.println();
        }
        System.out.println();
    }
}
