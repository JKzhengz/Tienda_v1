package com.tienda.service;

import com.tienda.domain.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface RegistroService {

    public Model activar(Model model, String usuario, String clave);  //activa el usuario internamente(lo almacena directamente a la base de datos)

    public Model crearUsuario(Model model, Usuario usuario) throws MessagingException; //crea un nuevo usuario (pasa al correo de activación)
    
    public void activar(Usuario usuario, MultipartFile imagenFile); //guarda una imágen
    
    public Model recordarUsuario(Model model, Usuario usuario) throws MessagingException;
}