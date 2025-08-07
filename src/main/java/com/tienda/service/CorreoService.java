package com.tienda.service;

import jakarta.mail.MessagingException;

public interface CorreoService {
    public void enviarCorreoHtml(
            String para, //a quien se le va a enviar el correo.
            String asunto, //El título principal
            String contenidoHtml) //cuerpo del correo
            throws MessagingException; //No puede enviar el correo 
}
