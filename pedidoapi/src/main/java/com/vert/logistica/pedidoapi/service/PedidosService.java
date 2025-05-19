package com.vert.logistica.pedidoapi.service;

import com.vert.logistica.pedidoapi.model.pedido.Pedidos;
import com.vert.logistica.pedidoapi.model.pedido.PedidoDTO;
import com.vert.logistica.pedidoapi.model.pedido.ProdutoDTO;
import com.vert.logistica.pedidoapi.model.pedido.UsuarioDTO;

import com.vert.logistica.pedidoapi.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/* Classe de serviço que utiliza o repositório para implementar a lógica de negócio. */
@Service
public class PedidosService {

	@Autowired
	private PedidosRepository pedidosRepository;

	public List<Pedidos> processarArquivo(MultipartFile arquivo) throws IOException {
	    List<Pedidos> pedidos = new ArrayList<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	    try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream()))) {
	        StringBuilder buffer = new StringBuilder();
	        String linha;

	        while ((linha = br.readLine()) != null) {
	            buffer.append(linha.trim());

	            while (buffer.length() >= 95) {
	                String registro = buffer.substring(0, 95);
	                buffer.delete(0, 95);

	                // valida se todos os campos essenciais parecem ok
	                if (registro.trim().isEmpty() || registro.length() < 95) {
	                    System.err.println("Registro ignorado por tamanho incorreto ou vazio: [" + registro + "]");
	                    continue;
	                }

	                try {
	                    Long idUsuario = Long.parseLong(registro.substring(0, 10).trim()); //certo
	                    String nome = registro.substring(10, 55).trim(); //certo
	                    Long idProduto = Long.parseLong(registro.substring(55, 65).trim()); //certo
	                    Long idPedido = Long.parseLong(registro.substring(65, 75).trim());
	                    BigDecimal valorProduto = new BigDecimal(registro.substring(75, 87).trim());
	                    LocalDate dataCompra = LocalDate.parse(registro.substring(87, 95), formatter);

	                    Pedidos pedido = new Pedidos();
	                   
	                    pedido.setIdUsuario(idUsuario);
	                    pedido.setNome(nome);
	                    pedido.setIdProduto(idProduto);
	                    pedido.setIdPedido(idPedido);
	                    pedido.setValorProduto(valorProduto);
	                    pedido.setDataCompra(dataCompra);

	                    pedidos.add(pedido);
	                    System.out.println("Pedido montado: " + pedido);

	                } catch (Exception e) {
	                    System.err.println("Erro ao processar registro: [" + registro + "]");
	                    System.err.println("Mensagem: " + e.getMessage());
	                }
	            }
	        }
	    }

	    if (pedidos.isEmpty()) {
	        System.out.println("Nenhum pedido foi lido do arquivo.");
	    }

	    try {
	        return pedidosRepository.saveAll(pedidos);
	    } catch (Exception e) {
	        System.err.println("Erro ao salvar pedidos: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}
	
	
	public List<Pedidos> listarPedidos() {
		return pedidosRepository.findAll();
	}

	public List<Pedidos> filtrarPedidos(Long idPedido, LocalDate dataInicio, LocalDate dataFim) {
		if (idPedido != null) {
			return pedidosRepository.findByIdPedido(idPedido); // nome do método deve estar certo no repositório
		} else if (dataInicio != null && dataFim != null) {
			return pedidosRepository.findByDataCompraBetween(dataInicio, dataFim);
		} else {
			return listarPedidos();
		}
	}

	/* salva um novo pedido e retorna o pedido salvo */
	public Pedidos salvarPedidos(Pedidos pedidos) {
		return pedidosRepository.save(pedidos);
	}
	
	
	public List<UsuarioDTO> listarUsuariosComPedidos() {
	    List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
	    List<Pedidos> pedidos = pedidosRepository.findAll();
	    System.out.println("Total de pedidos encontrados: " + pedidos.size());

	    Map<Long, UsuarioDTO> userMap = new HashMap<>();

	    for (Pedidos pedido : pedidos) {
	        Long idUsuario = pedido.getIdUsuario();
	        String nome = pedido.getNome();

	        // Se o usuário ainda não estiver no mapa, adicione-o
	        if (!userMap.containsKey(idUsuario)) {
	            userMap.put(idUsuario, new UsuarioDTO(idUsuario, nome, new ArrayList<>()));
	        }

	        // Adicione o pedido ao usuário
	        UsuarioDTO usuarioDTO = userMap.get(idUsuario);
	        List<PedidoDTO> pedidosUsuario = usuarioDTO.getPedidos();

	        // Verifique se o pedido já existe
	        PedidoDTO pedidoExistente = pedidosUsuario.stream()
	            .filter(p -> p.getIdPedido().equals(pedido.getIdPedido()))
	            .findFirst()
	            .orElse(null);

	        if (pedidoExistente == null) {
	            // Se o pedido não existe, crie um novo
	            List<ProdutoDTO> produtoDTOS = new ArrayList<>();
	            ProdutoDTO produtoDTO = new ProdutoDTO();
	            produtoDTO.setIdProduto(pedido.getIdProduto());
	            produtoDTO.setValorProduto(pedido.getValorProduto());
	            produtoDTOS.add(produtoDTO);

	            // Calcular a soma dos produtos
	            BigDecimal somaPedidos = pedido.getValorProduto(); // Aqui você deve somar os valores dos produtos se houver mais de um

	            PedidoDTO novoPedido = new PedidoDTO(
	                pedido.getIdPedido(),
	                somaPedidos, // Passar diretamente o BigDecimal
	                pedido.getDataCompra(),
	                produtoDTOS
	            );

	            pedidosUsuario.add(novoPedido);
	        } else {
	            // Se o pedido já existe, adicione o produto ao pedido existente
	            ProdutoDTO produtoDTO = new ProdutoDTO();
	            produtoDTO.setIdProduto(pedido.getIdProduto());
	            produtoDTO.setValorProduto(pedido.getValorProduto());
	            pedidoExistente.getProdutos().add(produtoDTO);

	            // Atualize a soma do pedido existente
	            BigDecimal novaSoma = pedidoExistente.getSomaPedidos().add(pedido.getValorProduto());
	            pedidoExistente.setSomaPedidos(novaSoma);
	        }
	    }

	    usuarioDTOS.addAll(userMap.values());
	    return usuarioDTOS;
	}
}