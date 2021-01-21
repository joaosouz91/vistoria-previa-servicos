package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ControleArquivoService;

@RestController
@RequestMapping("/api/controlearquivo")
public class ControleArquivoController {
	
	@Autowired
	private ControleArquivoService controleArquivoService;
	
	private static String message = "Arquivo gerado";
	private static String content = "Content-Disposition";
	private static String attachment = "attachment; filename=";

 
	@GetMapping(value = "/marcamodelo", produces = "application/octet-stream")
	public ResponseEntity<String> arquivoMarcaModelo(HttpServletResponse response) throws  IOException{
		try {
			 byte[] arquivo = null;
	            String nomeArquivo = "MarcaModelo_" + DateUtil.formataDataYYYYMMDD(new Date());
	            arquivo = controleArquivoService.downloadArquivoMarcaModelo();
	            response.setHeader(content, attachment + nomeArquivo);
	            response.getOutputStream().write(arquivo);
	            response.getOutputStream().flush();
	            //retorna NULL pois não deve ir para nenhuma página depois.
	            return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/arquivoperiodo", produces = "application/octet-stream")
	public ResponseEntity<String> arquivoPeriodo(HttpServletResponse response) throws  IOException{
		try {
			 byte[] arquivo = null;
	            String nomeArquivo = "Periodo_" + DateUtil.formataDataYYYYMMDD(new Date());
	            arquivo = controleArquivoService.downloadArquivoPeriodo();
	            response.setHeader(content, attachment + nomeArquivo);
	            response.getOutputStream().write(arquivo);
	            response.getOutputStream().flush();
	            //retorna NULL pois não deve ir para nenhuma página depois.
	            return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/arquivotabelas", produces = "application/octet-stream")
	public ResponseEntity<String> arquivoTabelasTokioMarine(HttpServletResponse response, @RequestParam(required = true) Long tparquivo) throws  IOException{
		try {
			 byte[] arquivo = null;
     			String nomeArquivo = controleArquivoService.obterNomeArquivo(tparquivo) + "_" + DateUtil.formataDataYYYYMMDD(new Date());
	            arquivo = controleArquivoService.downloadArquivoTabelasTokioMarine(tparquivo);
	            response.setHeader(content, attachment + nomeArquivo+".txt");
	            response.getOutputStream().write(arquivo);
	            response.getOutputStream().flush();
	            //retorna NULL pois não deve ir para nenhuma página depois.
	            return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/arquivocorretor", produces = "application/octet-stream")
	public ResponseEntity<String> arquivoCorretor(HttpServletResponse response) throws  IOException{
		try {
			 byte[] arquivo = null;
	            String nomeArquivo = "Corretor_" + DateUtil.formataDataYYYYMMDD(new Date());
	            arquivo = controleArquivoService.downloadArquivoCorretor();
	            response.setHeader(content, attachment + nomeArquivo);
	            response.getOutputStream().write(arquivo);
	            response.getOutputStream().flush();
	            //retorna NULL pois não deve ir para nenhuma página depois.
	            return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
