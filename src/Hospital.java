import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;

public class Hospital {
    MyHashMap<Doctor, PriorityQueue<Patient>> list = new MyHashMap<>();
    Connection con = DBConnection.makeConnection();

    Hospital(){
        loadDoctorsFromDb();
        loadPatientsFromDb();
    }

    public void addDoctor(Doctor d){
        saveDoctorToDb(d);

        list.put(d, new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator())));
    }

    public void addPatient(Doctor d, Patient p){
        PriorityQueue<Patient> patientList = list.get(d);

        if(patientList != null){
            patientList.add(p);
            savePatientDb(d.doctorId, p);
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


    public void loadDoctorsFromDb() {
        String query = "select * from doctors";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Doctor d = new Doctor(rs.getString("name"), rs.getString("specialist"));
                d.doctorId = rs.getInt("doctorId");

                list.put(d, new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator())));
            }
        } catch (SQLException e){

        }
    }

    public void saveDoctorToDb(Doctor d) {
        String insert = "insert into doctors(name, specialist) values(?, ?)";
        String select = "select doctorId from doctors where name=? and specialist=? order by doctorId desc limit 1";

        try {
            PreparedStatement ps1 = con.prepareStatement(insert);
            ps1.setString(1, d.name);
            ps1.setString(2, d.specialist);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(select);
            ps2.setString(1, d.name);
            ps2.setString(2, d.specialist);

            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                d.doctorId = rs.getInt("doctorId");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void loadPatientsFromDb(){
        String query = "select * from patients";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int doctorId = rs.getInt("doctorId");

                Patient p = new Patient(rs.getString("name"), rs.getInt("age"), rs.getString("disease"), rs.getInt("priority"));

                Doctor d = getDoctor(doctorId);

                if (d != null){
                    list.get(d).add(p);
                }
            }
        } catch (SQLException e){

        }
    }

    public void savePatientDb(int doctorId, Patient p){
        String query = "insert into patients(doctorId, name, age, disease, priority) values(?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, p.name);
            preparedStatement.setInt(3, p.age);
            preparedStatement.setString(4, p.disease);
            preparedStatement.setInt(5, p.priority);

            int res = preparedStatement.executeUpdate();

            if(res == 0){
                System.out.println("Error Inserting Patient");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
