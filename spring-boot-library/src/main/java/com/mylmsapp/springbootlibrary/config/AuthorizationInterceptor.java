package com.mylmsapp.springbootlibrary.config;

import com.mylmsapp.springbootlibrary.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final Map<String, Role[]> allowedRolesMap = new HashMap<>();

    private JwtService jwtService;

    public AuthorizationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
        // Define allowed roles for each endpoint pattern
        allowedRolesMap.put("/users", new Role[]{Role.ADMIN});
        allowedRolesMap.put("/saveUser", new Role[]{Role.ADMIN});
        allowedRolesMap.put("/user/delete/{userId}", new Role[]{Role.ADMIN});
        allowedRolesMap.put("/course", new Role[]{Role.ADMIN});
        allowedRolesMap.put("/courses", new Role[]{Role.ADMIN, Role.USER, Role.TEACHER});
        allowedRolesMap.put("/courses/{courseId}/users/{userId}", new Role[]{Role.ADMIN});
        allowedRolesMap.put("course/enrolledUsers", new Role[]{Role.ADMIN});
        // Add more mappings as needed
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println(request.getRequestURI());
        if (request.getRequestURI().startsWith("/api/v1/auth/")){
            return true;
        }
        else{

            String requestURI = request.getRequestURI();
            final String authHeader = request.getHeader("Authorization");
            final String jwt = authHeader.substring(7); //except after Bearer
            String userRole =  jwtService.extractRoleFromToken(jwt);// Implement this method to get current user's role
            System.out.println("Return userRole from preHandler: "+ userRole);


            // Check if the requested URI matches any entry in the allowedRolesMap
            for (Map.Entry<String, Role[]> entry : allowedRolesMap.entrySet()) {
                String endpointPattern = entry.getKey();
                Role[] allowedRoles = entry.getValue();

                if (requestURI.startsWith(endpointPattern) && !isUserAllowed(userRole, allowedRoles)) {

                    response.getWriter().print("Access Denied. Br nyr.");
                    response.setHeader("Content-Type", "text/plain; charset=utf-8");
                    response.setStatus(401);
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "{\"value\":\"Access denied\"}");
                    return false;
                }
            }

        }

        return true;
    }


    // Check if the current user's role is allowed for the given endpoint
    private boolean isUserAllowed(String userRole, Role[] allowedRoles) {
        for (Role role : allowedRoles) {
            if (role.name().equals(userRole)) {
                return true;
            }
        }
        return false;
    }


}
