//interfaz categoria service
package com.tienda.service;
import com.tienda.domain.Categoria; 
import java.util.List;

public interface CategoriaService {
    //Se asigna el metodo para después implementarlo en el serviceImpl (Read)
    public List<Categoria> getCategorias(boolean activos);
    //lo agarra de jpa repository
    public Categoria getCategoria(Categoria categoria);  //si esta vacio, lo guarda, y si no esta vacío, lo va a guardar pero siendo editado
    
    public void save(Categoria categoria); //lo guarda
    
    public void delete(Categoria categoria); //elimina
}
