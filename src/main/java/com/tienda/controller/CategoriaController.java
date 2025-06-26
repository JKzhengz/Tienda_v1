/*Clase categoría controller
cuando es vista, se crea un folder y si es código, es un package
*/

package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
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

@Controller
@Slf4j //permite manejar solicitudes de HTTP
@RequestMapping("/categoria") //define la ruta en la aplicación. localhost:8080/categoria

public class CategoriaController {
    
    @Autowired  //Inyecta (obtiene info y lo utiliza) una instancia del servicio
    private CategoriaService categoriaService;

    @GetMapping("/listado")  //localhost:8080/categoria
    public String inicio(Model model) {
        var categorias = categoriaService.getCategorias(false); //se guarda todo en una variable
        model.addAttribute("categorias", categorias);  //la unión del código con la vista
        model.addAttribute("totalCategorias", categorias.size()); //.size: total de la lista
        return "/categoria/listado";  //vista que se le muestra al usuario
    }
    
    @GetMapping("/nuevo")
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar")
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "categoria", 
                            categoria.getIdCategoria()));
        }
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado"; //redirecciona el resultado y se muestra la lista sin el que se eliminó
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria); //ver si existe y en caso de que si exista, se modifica
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }   
}
