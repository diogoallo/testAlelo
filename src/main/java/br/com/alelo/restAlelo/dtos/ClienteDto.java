package br.com.alelo.restAlelo.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ClienteDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Nome Cliente não pode ser vazio.")
	private String nome;
	
	@NotEmpty(message = "Sobre Nome do Cliente não pode ser vazio.")
	private String sobreNome;
	
	@NotEmpty(message = "Endereço do Cliente não pode ser vazio.")
	private String enderesso;
	
	@NotEmpty(message = "Sexo do Cliente não pode ser vazio.")
	private String sexo;
	
	@NotEmpty(message = "Cpf do Cliente não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;
	
}
