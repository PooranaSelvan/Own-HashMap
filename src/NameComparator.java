import java.util.Comparator;

class NameComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient p1, Patient p2) {
        return p1.name.compareTo(p2.name);
    }
}