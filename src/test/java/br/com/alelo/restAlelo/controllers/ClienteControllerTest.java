package br.com.alelo.restAlelo.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alelo.restAlelo.dtos.ClienteDto;
import br.com.alelo.restAlelo.model.Cliente;
import br.com.alelo.restAlelo.services.ClienteServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteServices clienteService;
 
	private static final String URL_BASE = "/v1/api/cliente";
	private static final Long ID = 1L;
	private static final String CPF = "03597208710";
	private static final String NOME = "Algusto";
	private static final String SOBRE_NOME = "Silva";
	private static final String ENDERESSO = "Av.Algusta nº 65";
	private static final String SEXO = "Maculino";


	@Test
	@WithMockUser
	public void testCadastrarCliente() throws Exception {
		Cliente cliente = obterDadosCliente();
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.clienteService.persistir(Mockito.any(Cliente.class))).willReturn(cliente);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.sobrenome").value(SOBRE_NOME))
				.andExpect(jsonPath("$.data.endereco").value(ENDERESSO))
				.andExpect(jsonPath("$.data.sexo").value(SEXO))
				.andExpect(jsonPath("$.data.cpf").value(CPF))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testAlterarCliente() throws Exception {
		Cliente cliente = obterDadosCliente();
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.clienteService.persistir(Mockito.any(Cliente.class))).willReturn(cliente);

		mvc.perform(MockMvcRequestBuilders.put(URL_BASE + ID)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.sobrenome").value(SOBRE_NOME))
				.andExpect(jsonPath("$.data.endereco").value(ENDERESSO))
				.andExpect(jsonPath("$.data.sexo").value(SEXO))
				.andExpect(jsonPath("$.data.cpf").value(CPF))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testRemoverCliente() throws Exception {
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testBuscarClientePorId() throws Exception {
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong()))
				.willReturn(Optional.of(this.obterDadosCliente()));

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.sobrenome").value(SOBRE_NOME))
				.andExpect(jsonPath("$.data.endereco").value(ENDERESSO))
				.andExpect(jsonPath("$.data.sexo").value(SEXO))
				.andExpect(jsonPath("$.data.cpf").value(CPF))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarClienteIdInvalido() throws Exception {
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Cliente não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
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
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setNome(NOME);
		clienteDto.setSobreNome(SOBRE_NOME);
		clienteDto.setEnderesso(ENDERESSO);
		clienteDto.setSexo(SEXO);
		clienteDto.setCpf(CPF);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(clienteDto);
	}
}
