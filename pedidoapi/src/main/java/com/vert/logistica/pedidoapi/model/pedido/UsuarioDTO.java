package com.vert.logistica.pedidoapi.model.pedido;
import java.util.List;

public class UsuarioDTO {
	
	private Long idUsuario;
	private String nome;
	private List<PedidoDTO> pedidos;
	
	// Construtor
    public UsuarioDTO(Long idUsuario, String nome, List<PedidoDTO> pedidos) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.pedidos = pedidos;
    }
	
	public Long getidUsuario() {
		return idUsuario;
	}
	
	public void setidUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getnome() {
		return nome;
	}
	
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public List<PedidoDTO> getPedidos() {
        return pedidos;
    }
    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

}
