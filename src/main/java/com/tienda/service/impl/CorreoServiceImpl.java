package com.tienda.service.impl;

import com.tienda.service.CorreoService; 
import jakarta.mail.MessagingException; 
import jakarta.mail.internet.MimeMessage; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.mail.javamail.MimeMessageHelper; 
import org.springframework.stereotype.Service;

@Service
public class CorreoServiceImpl implements CorreoService{
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void enviarCorreoHtml(
            String para, //a quien se le va a enviar el correo.
            String asunto, //El título principal
            String contenidoHtml) //cuerpo del correo
            throws MessagingException{
    
        MimeMessage message = mailSender.createMimeMessage(); //
        MimeMessageHelper helper  //facilita el manejo del correo o mensaje. Es un correo multiparte
                = new MimeMessageHelper(message, 
                        true);
        helper.setTo(para); //estoy adjuntando o settea a la persona a quien le voy a mandar
        helper.setSubject(asunto); //
        helper.setText(contenidoHtml,true); //setteamos el valor o el texto que le vamos a enviar
        mailSender.send(message); //Enviar el correo
        }
}
