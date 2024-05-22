package practica.spring.crudbasico.servicios;

import practica.spring.crudbasico.dtos.ProductoDTO;
import java.util.List;
public interface ProductoServicio {
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO obtenerProductoPorId(Long id);
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);
    List<ProductoDTO> obtenerTodosLosProductos();
    void eliminarProducto(Long id);
}
