package pl.edu.agh.soa.pro1.JWT;


import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {


    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("token ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("token".length()).trim();

        try {
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception ex) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}