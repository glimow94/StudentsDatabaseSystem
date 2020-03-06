package loginapp;

public enum options {
    Admin, Student;

    private options() {
    }

    public String value() {
        return name();
    }

    public static options fromvalue(String v) {
        return valueOf(v);
    }
}