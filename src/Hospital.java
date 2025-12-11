import java.util.PriorityQueue;

public class Hospital {
    MyHashMap<Doctor, PriorityQueue<Patient>> list = new MyHashMap<>();

    public void addDoctor(Doctor d){
        list.put(d, new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator())));
    }

    public void addPatient(Doctor d, Patient p){
        PriorityQueue<Patient> doctorList = list.get(d);

        if(doctorList != null){
            doctorList.add(p);
        }
    }

    public Doctor getDoctor(int id){
        for (Doctor d : list.keySet()){
            if(d.doctorId == id){
                return d;
            }
        }

        return null;
    }

    public void showDoctors(){
        for (Doctor d : list.keySet()){
            System.out.println(d);
        }
    }

    public PriorityQueue<Patient> getPatients(Doctor d){
        return list.get(d);
    }

}
