package br.com.alelo.restAlelo.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alelo.restAlelo.model.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	@MockBean
	private ClienteServices clienteService;
	
	private static final String CPF = "03597208896";
	private static final String NOME = "Algusto";
	private static final String SOBRE_NOME = "Silva";
	private static final String ENDERESSO = "Av.Algusta nº 65";
	private static final String SEXO = "Maculino";
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testBuscarClientePorCnpj() {
		Optional<Cliente> cliente = this.clienteService.buscarPorCpf(CPF);
		assertTrue(cliente.isPresent());
	}
	
	@Test
	public void testBuscarClientePorId() {
		Optional<Cliente> cliente = this.clienteService.buscarPorId(1L);
		assertTrue(cliente.isPresent());
	}

	@Test
	public void testPersistirLancamento() {
		Cliente cliente = this.clienteService.persistir(obterDadosCliente());
		assertNotNull(cliente);
	}
	
	
	private Cliente obterDadosCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome(NOME);
		cliente.setSobreNome(SOBRE_NOME);
		cliente.setEnderesso(ENDERESSO);
		cliente.setSexo(SEXO);
		cliente.setCpf(CPF);
		
		return cliente;
	}	
	
}
