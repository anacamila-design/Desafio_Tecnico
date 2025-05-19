/* Controlador simples para expor uma API REST que utiliza o serviço */
package com.vert.logistica.pedidoapi.controller;
import com.vert.logistica.pedidoapi.model.pedido.Pedidos;
//import com.vert.logistica.pedidoapi.service.GetMapping;
import com.vert.logistica.pedidoapi.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.vert.logistica.pedidoapi.model.pedido.UsuarioDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

	@Autowired
	private PedidosService pedidosService;

	@PostMapping("/importarArquivo")

	public ResponseEntity<List<Pedidos>> importarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        // Verifica se o arquivo está vazio
        if (arquivo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		System.out.println("Recebendo arquivo: " + arquivo.getOriginalFilename());
		try {
			List<Pedidos> pedidosSalvos = pedidosService.processarArquivo(arquivo);
			System.out.println("Pedidos salvos: " + pedidosSalvos.size());
			return new ResponseEntity<>(pedidosSalvos, HttpStatus.OK); 
			//return ResponseEntity.ok(pedidos);
		} catch (Exception e) {
			System.err.println("Erro ao processar arquivo: " + e.getMessage()); 
            e.printStackTrace(); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	// GET para listar todos
	@GetMapping(produces = "application/json")
	public List<Pedidos> listarTodos() {
		return pedidosService.listarPedidos();
	}

	// GET para filtrar por ID ou data
	@GetMapping("/filtro")
	public List<Pedidos> filtrar(@RequestParam(required = false) Long idPedido,
			@RequestParam(required = false) String dataInicio, @RequestParam(required = false) String dataFim) {

		LocalDate inicio = (dataInicio != null) ? LocalDate.parse(dataInicio) : null;
		LocalDate fim = (dataFim != null) ? LocalDate.parse(dataFim) : null;

		return pedidosService.filtrarPedidos(idPedido, inicio, fim);
	}
	
    // Novo endpoint para listar usuários com pedidos
    @GetMapping("/listarUsuariosComPedidos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosComPedidos() {
    List<UsuarioDTO> usuarioDTOS = pedidosService.listarUsuariosComPedidos();
    return new ResponseEntity<>(usuarioDTOS, HttpStatus.OK);
}}
