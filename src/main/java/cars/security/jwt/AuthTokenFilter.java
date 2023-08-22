package cars.security.jwt;

import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger AuthTokenFilterLogger= LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //we have out token now
        String jwtToken=parseJWT(request);
        try {
            //validate the token
            if(jwtToken!=null && jwtUtils.validateJwtToken(jwtToken)){
                String email=jwtUtils.getEmailFromToken(jwtToken);
                UserDetails userDetails= userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                //authenticated token (user details) will be sent to security context.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            AuthTokenFilterLogger.error("User not found {}: ", e.getMessage());
        }

        filterChain.doFilter(request,response);

    }
    private String parseJWT(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(StringUtils.hasText(header)&& header.startsWith("Beare ")){
            return header.substring(7);
        }
        return null;
    }

    /**
     * the endpoints that we do not filter through the security should be entered here.
     *
     */

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        return antPathMatcher.match("/register",request.getServletPath())
        || antPathMatcher.match("/login",request.getServletPath());


    }
}
