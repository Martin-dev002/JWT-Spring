package practica.spring.crudbasico.controladores;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import practica.spring.crudbasico.dtos.ProductoDTO;
import practica.spring.crudbasico.servicios.ProductoServicio;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    ProductoServicio productoServicio;

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoDTO> crearProducto( @RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProductoDTO = productoServicio.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProductoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/obtenerProducto/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {
        ProductoDTO productoDTO = productoServicio.obtenerProductoPorId(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/actualizarProducto/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        ProductoDTO productoActualizadoDTO = productoServicio.actualizarProducto(id, productoDTO);
        return new ResponseEntity<>(productoActualizadoDTO, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/borrarProducto/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id){
        productoServicio.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        List<ProductoDTO> productos = productoServicio.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
