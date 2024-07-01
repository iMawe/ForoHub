package forohub.controller;

import forohub.domain.topico.*;
import forohub.domain.usuario.UsuarioRepository;
import forohub.infra.errors.ValidacionDeIntegridad;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/topico")
@SecurityRequirement(name="bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoService topicoService;

    @PostMapping("/topico")
    @Transactional
    public ResponseEntity topicoRegistrado(@RequestBody @Valid TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        var topicoRegistrado = topicoService.topicoCreado(topicoDTO);
        return ResponseEntity.ok(topicoRegistrado);
    }

    @GetMapping("/topicos")
    public ResponseEntity<Page<ListarTopicosDTO>>  listarTopicos(@PageableDefault(size = 10) Pageable paged){
        return ResponseEntity.ok(topicoRepository.findByActiveTrue(paged).map(ListarTopicosDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity topicoActualizado (@RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO){
        Topico topico= topicoRepository.getReferenceById(topicoActualizadoDTO.id());
        topico.topicoActualizado(topicoActualizadoDTO);
        return ResponseEntity.ok(new RespuestaTopicoDTO(topico.getId(),
                topico.getTitle(),topico.getMessage(),
                topico.getStatus(),topico.getAuthor().getId(),
                topico.getCourse(),topico.getDate()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico= topicoRepository.getReferenceById(id);
        topico.diactivateTopic();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity <RespuestaTopicoDTO> respuestaTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var topicoId = new RespuestaTopicoDTO(topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getStatus(),
                topico.getAuthor().getId(),
                topico.getCourse(),
                topico.getDate());
        return ResponseEntity.ok(topicoId);
    }
}