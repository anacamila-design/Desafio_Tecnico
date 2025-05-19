package com.vert.logistica.pedidoapi.model.pedido;
import java.math.BigDecimal;

public class ProdutoDTO {
	private Long idProduto;
	private BigDecimal valorProduto;
	
   public ProdutoDTO() {
        // Construtor padrão
    }
    public ProdutoDTO(Long idProduto, BigDecimal valorProduto) {
        this.idProduto = idProduto;
        this.valorProduto = valorProduto;
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
	
}
