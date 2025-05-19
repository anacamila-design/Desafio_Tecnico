/* Criado por Ana Camila - 11/05/2025 */

/* Repositório para a entidade Pedidos.
   Herda as operações CRUD e permite criar consultas personalizadas.*/

package com.vert.logistica.pedidoapi.repository;
import com.vert.logistica.pedidoapi.model.pedido.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/* A interface herda metodos prontos */
/*Métodos usados: findByDataCompraBetween, indByIdpedido */

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
	
	/*Busca pedidos cuja data de compra esteja entre as datas especificadas.
	 * @return lista de pedidos dentro do intervalo de datas*/
	List<Pedidos> findByDataCompraBetween(LocalDate dataInicio, LocalDate dataFim);
	
	/*Busca pedidos pelo seu ID de forma personalizada.
	@return lista de pedidos com o id especificado (normalmente será um único pedido)*/
	List<Pedidos> findByIdPedido(Long idPedido);

}
