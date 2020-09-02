package br.com.alelo.restAlelo.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alelo.restAlelo.dtos.ClienteDto;
import br.com.alelo.restAlelo.model.Cliente;
import br.com.alelo.restAlelo.responses.Response;
import br.com.alelo.restAlelo.services.ClienteServices;

@RestController
@RequestMapping("v1/api/cliente")
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	ClienteServices service;
	
	/**
	 * Cadastra um cliente  no sistema.
	 * 
	 * @param clienteDto
	 * @param result
	 * @return ResponseEntity<Response<ClienteDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<ClienteDto>> cadastrar(@Valid @RequestBody ClienteDto clienteDto,
			BindingResult result) throws NoSuchAlgorithmException { 
		log.info("Cadastrando Cliente: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		validarDadosExistentes(clienteDto, result);
		Cliente cliente = this.converterDtoParaCliente(clienteDto);
		
		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro do cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.service.persistir(cliente);
		response.setData(this.converteClienteDto(cliente));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um cliente.
	 * 
	 * @param id
	 * @param clienteDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ClienteDto clienteDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando cliente: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();
        
		Optional<Cliente> cliente = this.service.buscarPorId(id);
		this.atualizaDadosCliente(cliente.get(), clienteDto, result);
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("cliente", "Cliente não encontrado."));
		}
		
		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.service.persistir(cliente.get());
		response.setData(this.converteClienteDto(cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * Retorna um cliente por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<ClienteDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando cliente por ID: {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();
		Optional<Cliente> cliente = this.service.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converteClienteDto(cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um cliente por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Lancamento>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo cliente: {}", id);
		Response<String> response = new Response<String>();
		Optional<Cliente> cliente = this.service.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Erro ao remover cliente.", id);
			response.getErrors().add("Erro ao remover cliente. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.service.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	/**
	 * Verifica se o cpf cadastrado já existe.
	 * 
	 * @param clienteDto
	 * @param result
	 */
	private void validarDadosExistentes(ClienteDto clienteDto, BindingResult result) {
		Optional<Cliente> cliente = this.service.buscarPorCpf(clienteDto.getCpf());
		if (cliente.isPresent()) {
			result.addError(new ObjectError("cliente", "CPF já existente.."));
		}
	}
	
	/**
	 * Atualiza os dados do cliente com base nos dados encontrados no DTO.
	 * 
	 * @param cliente
	 * @param clienteDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizaDadosCliente(Cliente cliente,ClienteDto clienteDto, BindingResult result)
			throws NoSuchAlgorithmException {
		
		cliente.setNome(clienteDto.getNome());
		cliente.setSobreNome(clienteDto.getSobreNome());
		cliente.setEnderesso(clienteDto.getEnderesso());
		cliente.setSexo(clienteDto.getSexo());
		cliente.setCpf(clienteDto.getCpf());

	}

	/**
	 * Retorna um DTO com os dados de um cliente.
	 * 
	 * @param cliente
	 * @return ClienteDto
	 */
	private ClienteDto converteClienteDto(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		
		clienteDto.setNome(cliente.getNome());
		clienteDto.setSobreNome(cliente.getSobreNome());
		clienteDto.setEnderesso(cliente.getEnderesso());
		clienteDto.setSexo(cliente.getSexo());
		clienteDto.setCpf(cliente.getCpf());
		
		return clienteDto;
	}
    
	
	/**
	 * Converte os dados do DTO para cliente.
	 * 
	 * @param clienteDto
	 * @return cliente
	 */
	private Cliente converterDtoParaCliente(ClienteDto clienteDto) {
		Cliente cliente = new Cliente();
		cliente.setNome(clienteDto.getNome());
        cliente.setSobreNome(clienteDto.getSobreNome());
        cliente.setEnderesso(clienteDto.getEnderesso());
        cliente.setSexo(cliente.getSexo());
        cliente.setCpf(clienteDto.getCpf());
		
		return cliente;
	}
}
