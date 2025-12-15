import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;

public class Hospital {
    MyHashMap<Doctor, PriorityQueue<Patient>> list = new MyHashMap<>();
    Connection con = DBConnection.makeConnection();
    private final Logger logger =  LogManager.getLogger("Hospital");

    Hospital() throws SQLException {
        loadDoctorsFromDb();
        loadPatientsFromDb();
    }

    public void addDoctor(Doctor d) throws SQLException {
        saveDoctorToDb(d);

        list.put(d, new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator())));

        logger.info("Added New Doctor! Name : ", d.name);
    }

    public void addPatient(Doctor d, Patient p) throws SQLException {
        PriorityQueue<Patient> patientList = list.get(d);

        if(patientList != null){
            patientList.add(p);
            savePatientDb(d.doctorId, p);
        }

        logger.info("Added New Patient! Name : ", p.name);
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


    public void loadDoctorsFromDb() throws SQLException {
        String query = "select * from doctors";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            con.setAutoCommit(false);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Doctor d = new Doctor(rs.getString("name"), rs.getString("specialist"));
                d.doctorId = rs.getInt("doctorId");

                list.put(d, new PriorityQueue<>(new PatientComparator().thenComparing(new NameComparator())));
            }

            con.commit();
        } catch (SQLException e){
            if (con != null && !con.isClosed()) con.rollback();
            System.out.println(e.getMessage());
            logger.error("Failed to Load Doctors From Db!");
        }
    }

    public void saveDoctorToDb(Doctor d) throws SQLException {
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

            con.setAutoCommit(false);
            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                d.doctorId = rs.getInt("doctorId");
            }

            con.commit();
        } catch (Exception e) {
            if(con != null && !con.isClosed()) con.rollback();
            System.out.println(e.getMessage());
            logger.error("Failed to Save Doctors to Db!");
        }
    }


    public void loadPatientsFromDb() throws SQLException {
        String query = "select * from patients";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            con.setAutoCommit(false);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int doctorId = rs.getInt("doctorId");

                Patient p = new Patient(rs.getString("name"), rs.getInt("age"), rs.getString("disease"), rs.getInt("priority"));

                Doctor d = getDoctor(doctorId);

                if (d != null){
                    list.get(d).add(p);
                }
            }

            con.commit();
        } catch (SQLException e){
            if(con != null && !con.isClosed()) con.rollback();
            System.out.println(e.getMessage());
            logger.error("Failed to Get Patients from Db!");
        }
    }

    public void savePatientDb(int doctorId, Patient p) throws SQLException {
        String query = "insert into patients(doctorId, name, age, disease, priority) values(?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, p.name);
            preparedStatement.setInt(3, p.age);
            preparedStatement.setString(4, p.disease);
            preparedStatement.setInt(5, p.priority);

            con.setAutoCommit(false);
            int res = preparedStatement.executeUpdate();

            if(res == 0){
                System.out.println("Error Inserting Patient");
            }
            con.commit();
        } catch (Exception e){
            if(con != null && !con.isClosed()) con.rollback();
            System.out.println(e.getMessage());
            logger.error("Failed to Save Patients to Db!");
        }
    }
}
