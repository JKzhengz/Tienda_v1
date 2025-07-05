/*Clase categoría controller
cuando es vista, se crea un folder y si es código, es un package
*/

package com.tienda.controller;

import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;

@Controller
@Slf4j //permite manejar solicitudes de HTTP
@RequestMapping("/producto") //define la ruta en la aplicación. localhost:8080/producto

public class ProductoController {
    
    @Autowired  //Inyecta (obtiene info y lo utiliza) una instancia del servicio
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")  //localhost:8080/producto
    public String inicio(Model model) {
        var productos = productoService.getProductos(false); //se guarda todo en una variable
        var categorias = categoriaService.getCategorias(false);         
        model.addAttribute("categorias", categorias); 
        model.addAttribute("productos", productos);  //la unión del código con la vista
        model.addAttribute("totalProductos", productos.size()); //.size: total de la lista
        return "/producto/listado";  //vista que se le muestra al usuario
    }
    
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "producto", 
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado"; //redirecciona el resultado y se muestra la lista sin el que se eliminó
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto); //ver si existe y en caso de que si exista, se modifica
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }   
}
