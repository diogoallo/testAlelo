package br.com.alelo.restAlelo.dtos;

import io.swagger.annotations.ApiModelProperty;

public class Message {
	
	@ApiModelProperty(notes = "Codigo do status da transação realizada")
	private int code;
	@ApiModelProperty(notes = "Mensagem do status da transação realizada")
	private String message;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
		
}
