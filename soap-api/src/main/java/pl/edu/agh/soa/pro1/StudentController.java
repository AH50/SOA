package pl.edu.agh.soa.pro1;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.pro1.models.Student;
import pl.edu.agh.soa.pro1.models.Subject;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;


@Stateless
@WebService
@SecurityDomain("Test-security-domain")
@DeclareRoles({"GrupaTestowa"})
@WebContext(contextRoot = "/soa", urlPattern = "/StudentController",
        authMethod = "BASIC")
public class StudentController {
    List<Student> students = new ArrayList<>();

    @WebMethod(action = "add")
    @WebResult(name = "addStudent")
    @RolesAllowed("GrupaTestowa")
    public String addStudent(@WebParam(name = "name") String name, @WebParam(name = "surname") String surname, @WebParam(name = "studentID") int studentID) {
        Subject subject = Subject.builder()
                .name("Podstawy algorytmow")
                .teacher("Jan Kowalski")
                .ECTS(5)
                .build();
        Subject subject2 = Subject.builder()
                .name("Prawo")
                .teacher("Andrzej Nowak")
                .ECTS(3)
                .build();

        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject2);

        if(findStudent(studentID)!=null){
            return "Student with this ID already exist!";
        }

        Student student = Student.builder()
                .name(name)
                .surname(surname)
                .studentId(studentID)
                .subjectList(subjects)
                .photoInBase64("Photo not seted")
                .build();

        students.add(student);
        return "Student: " + student.getName() + " " + student.getSurname() + " added. Student ID: " + studentID;
    }

    @WebMethod(action = "find")
    @WebResult(name = "findStudent")
    @RolesAllowed("GrupaTestowa")
    public Student findStudent(@WebParam(name = "IDstudent") int studentId) {

        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    @WebMethod(action = "set")
    @WebResult(name = "setPhoto")
    @RolesAllowed("GrupaTestowa")
    public String setPhoto(@WebParam(name = "studentID") int studentID, @WebParam(name = "photoBase64") String photoBase64) {
        Student student = new Student();
        if (findStudent(studentID) != null) {
            student = findStudent(studentID);
        } else {
            return "Nie ma takiego studenta";
        }

//        File file = new File(pathToPhoto);
//        String encodedfile;
//        try {
//            FileInputStream fileInputStreamReader = new FileInputStream(file);
//            byte[] bytes = new byte[(int) file.length()];
//            fileInputStreamReader.read(bytes);
//            encodedfile = Base64.getEncoder().encodeToString(bytes);
//        } catch (FileNotFoundException e) {
//            encodedfile = "Nie ma takiego pliku";
//        } catch (IOException e) {
//            encodedfile = "Błąd przesyłu";
//        }

        student.setPhotoInBase64(photoBase64);
        return photoBase64;
    }

    @WebMethod(action = "get")
    @WebResult(name = "getAllStudents")
    @RolesAllowed("GrupaTestowa")
    public List<Student> allStudents() {
        return students;
    }

    @WebMethod(action = "update")
    @WebResult(name = "updateStudenSurname")
    @RolesAllowed("GrupaTestowa")
    public boolean updateStudentSurname(@WebParam(name = "studentID") int studentID, @WebParam(name = "newSurname") String newSurname) {

        Student student;
        if (findStudent(studentID) != null) {
            student = findStudent(studentID);
        } else {
            return false;
        }
        student.setSurname(newSurname);
        return true;
    }

    @WebMethod(action = "get")
    @WebResult(name = "getStudentPhoto")
    @RolesAllowed("GrupaTestowa")
    public String getStudentPhoto(@WebParam(name = "studentID") int studentID) {

        Student student;
        if (findStudent(studentID) != null) {
            student = findStudent(studentID);
        } else {
            return "Student have no photo";
        }
        return student.getPhotoInBase64();
    }

    @WebMethod(action = "add")
    @WebResult(name = "mockData")
    @RolesAllowed("GrupaTestowa")
    public String mockData() {

        addStudent("Jan","Nowak",12345);
        addStudent("Tomasz","Kot",54321);
        addStudent("Jan","Nowak",123);

        return "Data added";
    }
}

