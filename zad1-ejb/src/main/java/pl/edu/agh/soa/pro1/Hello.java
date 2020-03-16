package pl.edu.agh.soa.pro1;

import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@Stateless
@WebService
@WebContext(contextRoot = "/soa", urlPattern = "/hello")


public class Hello {
    @WebMethod(action = "hellollll")
    @WebResult(name = "result")
    public String Hi(@WebParam(name = "answer") String name) {
        return "Hello " + name + "!";
    }
}






