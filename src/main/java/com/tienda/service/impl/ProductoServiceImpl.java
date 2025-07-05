//Clase producto service implement
package com.tienda.service.impl;

import com.tienda.dao.ProductoDao;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired  //poder sobre escribir
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly=true)  //va  a tener que hacer algo en la base de datos, se hace una transacción
    public List<Producto> getProductos(boolean activos) {
        var lista=productoDao.findAll();
        if (activos) {
           lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }
    
    //devuelve la categoría encontrada, si no, lo devuelve nulo.
    @Override
    @Transactional(readOnly=true)
    public Producto getProducto(Producto producto){
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }
    
    @Override
    @Transactional //donde si se modifican los datos. Con readOnly es para solo ver
    public void save(Producto producto){ //sin return es por que no es necesario ver lo que se está guardando
        productoDao.save(producto);
    }
    
    @Override
    @Transactional
    public void delete(Producto producto){
        productoDao.delete(producto);
    }
}
