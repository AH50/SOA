package pl.edu.agh.soa.pro1.api;

import io.swagger.annotations.*;
import pl.edu.agh.soa.pro1.*;
import pl.edu.agh.soa.pro1.JWT.JWTTokenNeeded;
import pl.edu.agh.soa.pro1.models.Student;

import pl.edu.agh.soa.pro1.models.StudentProtobuf;
import pl.edu.agh.soa.pro1.models.StudentRepository;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Base64;
import java.util.List;

@Api(value = "StudentApi")
@Path("/students")
public class StudentController {

    static StudentRepository studentRepository = new StudentRepository();
    @EJB
    private MarkDao markDao = new MarkDao();
    @EJB
    private StudentDao studentDao = new StudentDao();

    @GET
    @Path("/")
    @ApiOperation(value = "Get students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents(@QueryParam("ID") int Id,
                                   @QueryParam("firstName") String name,
                                   @QueryParam("surname") String surname) {
//        List<Student> studentsList = studentRepository.getStudentList();
        List<Student> studentsList = studentDao.findAllstudents();

        if (studentsList == null || studentsList.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student database is empty").build();
        }
        if (name != null) {
//            Student student = studentRepository.getStudentByName(name);
            Student student = null;
            try {
                student = studentDao.findbyname(name);
            } catch (Exception e) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student with this name not found").build();
            }
            return Response.status(Response.Status.OK).entity(student).build();
        }
        if (surname != null) {
//            Student student = studentRepository.getStudentBySurname(surname);
            Student student = null;
            try {
                student = studentDao.findbysurname(surname);
            } catch (Exception e) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student with this surname not found").build();
            }
            return Response.status(Response.Status.OK).entity(student).build();
        }
        if (Id != 0) {
//            Student student = studentRepository.getStudentByID(Id);
            Student student = null;
            try {
                student = studentDao.findbystudentId(Id);
            } catch (Exception e) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student with this id not found").build();
            }
            return Response.status(Response.Status.OK).entity(student).build();
        }

        return Response.status(Response.Status.OK).entity(studentsList).build();
    }

    @GET
    @Path("/{studentID}")
    @ApiOperation(value = "Get student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("studentID") int studentId) {
        Student student;
        try {
            student = studentDao.findbystudentId(studentId);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Methods", "GET").build();
        }

        return Response.ok().entity(student).status(Response.Status.OK).entity(student).build();
    }

    @PUT
    @Path("/{studentID}/photo")
    @ApiOperation(value = "Change student photo")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response setPhoto(@PathParam("studentID") int studentID, @ApiParam(required = true) @QueryParam("photo") String photoBase64) {

        Student student = new Student();

        try {
            student = studentDao.findbystudentId(studentID);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        student.setPhotoInBase64(photoBase64);
        try {
            studentDao.update(student);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        return Response.status(Response.Status.OK).entity(student).build();
    }

    @PUT
    @Path("/{studentID}/surname")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Change student surname")
//    @JWTTokenNeeded
    public Response updateStudentSurname(@PathParam("studentID") int studentID, @ApiParam(required = true, name = "surname") @QueryParam("surname") String newSurname) {

        Student student = new Student();

        try {
            student = studentDao.findbystudentId(studentID);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        student.setSurname(newSurname);
        try {
            studentDao.update(student);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        return Response.status(Response.Status.OK).entity(student).build();
    }

    @GET
    @Path("/{studentID}/photo")
    @Produces("image/jpeg")
    @ApiOperation(value = "Get student photo", response = byte.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "This is student photo."),
            @ApiResponse(code = 403, message = "Student don't have photo."),
            @ApiResponse(code = 404, message = "Student not found.")})

    public Response getStudentPhoto(@PathParam("studentID") int studentID)  {

        Student student = null;
        try {
            student = studentDao.findbystudentId(studentID);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

//        Student student = studentRepository.getStudentByID(studentID);

        byte[] imageBytes = Base64.getDecoder().decode(student.getPhotoInBase64());
        if (student != null) {
            if (!student.getPhotoInBase64().equals("")) {
                return Response.status(Response.Status.OK).entity(imageBytes).build();
            }
            return Response.status(Response.Status.FORBIDDEN).entity("Student don't have photo.").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found.").build();

    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add student", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student added.", response = Student.class),
            @ApiResponse(code = 409, message = "Student already exists.")})
//    @JWTTokenNeeded
    public Response addStudent(Student student) {

        Response r = getStudentById(student.getStudentId());
        if (r.getStatus() == 404) {
//            studentRepository.addStudent(student);
            studentDao.save(student);
            return Response.status(Response.Status.CREATED).entity(student).build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Student alredy exists").build();
    }

    @GET
    @Produces("application/protobuf")
    @Path("/{id}/protobuf")
    @ApiOperation("Get Student by ID - Protobuf")
    public Response getStudentByIdProtobuf(@ApiParam(required = true) @PathParam("id") int ID) {

        var studentBuilder = StudentProtobuf.Student.newBuilder();
        if (studentRepository.getStudentByID(ID) != null) {
            Student student = studentRepository.getStudentByID(ID);

            studentBuilder.setId(student.getStudentId()).setName(student.getName()).setSurname(student.getSurname());
            var newStudent = studentBuilder.build();
            return Response.status(Response.Status.OK).entity(newStudent).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/adddata/mockdata")
    public Response addstu() {

        studentDao.save(studentRepository.getStudentByID(123));
        studentDao.save(studentRepository.getStudentByID(1234));
        Student s = studentRepository.getStudentByID(123);
        s.setPhotoInBase64("");
        try {
            studentDao.update(s);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found.").build();        }


        return Response.status(Response.Status.OK).entity("Data to database").build();
    }
}


