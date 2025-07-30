//interfaz producto service
package com.tienda.service;

import java.util.List;
import com.tienda.domain.Producto; 
import org.springframework.data.repository.query.Param;

public interface ProductoService {
    //Se asigna el metodo para después implementarlo en el serviceImpl (Read)
    public List<Producto> getProductos(boolean activos);
    //lo agarra de jpa repository
    public Producto getProducto(Producto producto);  //si esta vacio, lo guarda, y si no esta vacío, lo va a guardar pero siendo editado
    
    public void save(Producto producto); //lo guarda
    
    public void delete(Producto producto); //elimina
    
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);
    
    public List<Producto> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
    
    public List<Producto> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
}
