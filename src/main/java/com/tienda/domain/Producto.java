//Clase Producto
package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import com.tienda.domain.Categoria;

@Data //Para decirle a la clase que es una clase de datos
@Entity //
@Table(name="producto") //de mysql
public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long idProducto;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    private String detalle;
    private int existencias;
    private double precio;
    
    @ManyToOne//La asociacion entre las clases de producto y categoria
    @JoinColumn(name="id_categoria")
    Categoria categoria; //FK
    
    public Producto() {
    }

    public Producto(String producto, boolean activo) {
        this.descripcion = producto;
        this.activo = activo;
    }
    
}