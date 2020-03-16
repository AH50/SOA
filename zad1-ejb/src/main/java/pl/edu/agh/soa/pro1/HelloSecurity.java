package pl.edu.agh.soa.pro1;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Stateless
@WebService
@SecurityDomain("Test-security-domain")
@DeclareRoles({"GrupaTestowa"})
@WebContext(contextRoot = "/soa", urlPattern = "/helloSecurity",
        authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)

public class HelloSecurity {
    @RolesAllowed("GrupaTestowa")
    @WebMethod(action = "helloSecurity")
    @WebResult(name = "HiSecurityResult")
    public String SecurityHello(@WebParam(name = "answer") String name) {
        return "Hello " + name + "!";
    }
}
