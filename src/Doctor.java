public class Doctor {
    int doctorId;
    static int globalId = 1;
    String name;
    String specialist;

    Doctor(String name, String specialist){
        this.name = name;
        this.specialist = specialist;
        this.doctorId = globalId++;
    }

    @Override
    public String toString(){
        return "| DoctorId : "+doctorId+" | Name : "+name+" | Specialist : "+specialist+" |";
    }
}
