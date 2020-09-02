package br.com.alelo.restAlelo.controller;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/versao")
public class VersaoController {
	
	@Autowired
	BuildProperties buildProperties;
	
	@GetMapping("/**")
	public ResponseEntity<String> getVersao() {
		
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Locale.US )
			                     .withZone( ZoneId.systemDefault() );
		
		return new ResponseEntity<String>(buildProperties.getName() + " - " + buildProperties.getVersion() +  " - " +	formatter.format( buildProperties.getTime() ) + " - " + buildProperties.getArtifact() + " - " +	buildProperties.getGroup(),HttpStatus.OK);
	}
}