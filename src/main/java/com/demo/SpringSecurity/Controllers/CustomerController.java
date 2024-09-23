package com.demo.SpringSecurity.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/index")
    public String index() {
        return "Hello word!";
    }

    @GetMapping("/index2")
    public String index2() {
        return "Hello word not SECURED!!";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailSessions() {
        String sessionId = "";
        User userObject = null; // objeto definido dentro de spring security, representa un usuario

        List<Object> sessions = sessionRegistry.getAllPrincipals(); // nos devuelve una lista de objetos

        for(Object session: sessions){
            if(session instanceof User){
                userObject = (User) session;
            }
            List<SessionInformation> sessionInformations =  sessionRegistry.getAllSessions(session, false); 
            // usuario que estamos recupernado session, y boolean en falso (incluir sessions invalidas)
            for(SessionInformation sessionInformation: sessionInformations){
                sessionId = sessionInformation.getSessionId(); // recuperamos el id de la session
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("response", "Hello word!");
        response.put("sessionId", sessionId);
        response.put("sessionUser", userObject);
        return ResponseEntity.ok(response);
    }
}
