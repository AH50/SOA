package protobuf;

import pl.edu.agh.soa.pro1.models.StudentProtobuf;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/protobuf")
@Consumes("application/protobuf")
public class MessageWriter implements MessageBodyWriter, MessageBodyReader {
    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isStudentAssignableFrom(type);
    }
    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        if ( isStudentAssignableFrom(type) ) {
            return StudentProtobuf.Student.parseFrom(entityStream);
        }
        throw new BadRequestException("Error: Class " + type);
    }
    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isStudentAssignableFrom(type);
    }
    @Override
    public long getSize(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        if ( o instanceof StudentProtobuf.Student ) {
            entityStream.write(((StudentProtobuf.Student) o).toByteArray());
        }
    }
    private boolean isStudentAssignableFrom(Class type) {
        return StudentProtobuf.Student.class.isAssignableFrom(type);
    }
}