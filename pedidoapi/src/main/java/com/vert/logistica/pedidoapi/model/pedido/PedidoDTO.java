package com.vert.logistica.pedidoapi.model.pedido;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PedidoDTO {
	private Long idPedido;
	//variavel somaPedidos guarda valor da soma dos produtos de um pedido.
	private BigDecimal somaPedidos;
	//private BigDecimal valorProduto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataCompra;
    private List<ProdutoDTO> produtos;
    
    // Construtor
    public PedidoDTO(Long idPedido, BigDecimal somaPedidos, LocalDate dataCompra, List<ProdutoDTO> produtos) {
        this.idPedido = idPedido;
        this.somaPedidos = somaPedidos;
        this.dataCompra = dataCompra;
        //this.valorProduto = valorProduto;
        this.produtos = produtos;
    }
    
	public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
    
    public BigDecimal getSomaPedidos() {
        return somaPedidos;
    }

    public void setSomaPedidos(BigDecimal somaPedidos) {
        this.somaPedidos = somaPedidos;
    }
    
    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }
    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}
