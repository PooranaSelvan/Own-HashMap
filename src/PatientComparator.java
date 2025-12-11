import java.util.Comparator;

class PatientComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient p1, Patient p2) {
        return Integer.compare(p1.priority, p2.priority);
    }
}