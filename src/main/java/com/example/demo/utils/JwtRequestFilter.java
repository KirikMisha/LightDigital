//package com.example.demo.config;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Autowired
//    private JwtUserDetailsService jwtUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            try {
//                username = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
//            } catch (ExpiredJwtException e) {
//                // handle token expiration
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
//            if (jwtUserDetailsService.validateToken(jwt, userDetails)) {
//                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                usernamePasswordAuthenticationToken
//                        .setDetails(
//                                new WebAuthenticationDetailsSource()
//                                        .buildDetails(request)
//                        );
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
//
//