/* Criado por Ana Camila - 10/05/2025  */
package com.vert.logistica.pedidoapi.model.pedido;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.vert.logistica.pedidoapi.model.pedido.ProdutoDTO;

@Entity
//@Table(name = "pedidos")

public class Pedidos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@Column(name = "idUsuario", length = 10, nullable = false)
	private Long idUsuario;
	
	//@Column(name = "nome", length = 45, nullable = false)
	private String nome;
	
	//@Column(name = "idPedido", length = 10, nullable = false)
	private Long idPedido;
	
	//@Column(name = "idProduto", length = 10, nullable = false)
	private Long idProduto;
	
	//@Column(name = "valorProduto", length = 12, nullable = false)
	private BigDecimal valorProduto;
	
	//@Column(name = "dataCompra", length = 8, nullable = false)
	private LocalDate dataCompra;
	
	//private BigDecimal somaPedidos;
	
	
	
	// Getters e Setters
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
    
    /*public BigDecimal getSomaPedidos() {
        return somaPedidos;
    }

    public void setSomaPedidos(BigDecimal somaPedidos) {
        this.somaPedidos = somaPedidos;
    }
    */

    @Override
    public String toString() {
        return "Pedido{" + 
                "id=" + id +
        		"idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", idPedido=" + idPedido +
                ", idProduto=" + idProduto +
                ", valorProduto=" + valorProduto +
                ", dataCompra=" + dataCompra +
                '}';
    }


}
