package br.com.alelo.restAlelo.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.com.alelo.restAlelo.model.Cliente;

@Service
public interface ClienteServices {
    
	/**
	 * Retorna uma cliente dado um CPF.
	 * 
	 * @param cpf
	 * @return Optional<Clienet>
	 */
	Optional<Cliente> buscarPorCpf(String cpf);
	
	/**
	 * Persiste um cliente na base de dados.
	 * 
	 * @param cliente
	 * @return Cliente
	 */
	Cliente persistir(Cliente cliente);
	

	/**
	 * Retorna um Cliente por ID.
	 * 
	 * @param id
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorId(Long id);
	
	
	/**
	 * Remove um cliente da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
}
