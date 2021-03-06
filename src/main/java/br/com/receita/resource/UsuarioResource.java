package br.com.receita.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.receita.domain.Usuario;
import br.com.receita.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Void> criar(@RequestBody Usuario usuario) {
		usuario = usuarioService.salvar(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuarios = usuarioService.listar();
		
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> pesquisar(@PathVariable String id) {
		Usuario usuario = usuarioService.pesquisar(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario, @PathVariable String id) {
		usuario.setId(id); 
		usuarioService.salvar(usuario);
		return ResponseEntity.noContent().build();
	}
}
