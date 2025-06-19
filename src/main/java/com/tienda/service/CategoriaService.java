//interfaz categoria service
package com.tienda.service;
import com.tienda.domain.Categoria; 
import java.util.List;

public interface CategoriaService {
    //Se asigna el metodo para después implementarlo en el serviceImpl (Read)
    public List<Categoria> getCategorias(boolean activos);
}
