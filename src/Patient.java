public class Patient {
    static int globalId = 1;
    int patientId;
    String name;
    int age;
    String disease;
    int priority;

    Patient(String name, int age, String disease, int priority){
        this.patientId = globalId++;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.priority = priority;
    }

    @Override
    public String toString(){
        return "| PatientId : "+patientId+" | Name : "+name+" | Age : "+age+" | Disease : "+disease+" |";
    }
}
