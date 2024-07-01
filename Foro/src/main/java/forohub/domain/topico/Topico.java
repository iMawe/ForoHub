package forohub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import forohub.domain.usuario.Usuario;

@Entity(name="Topico")
@Table(name="topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "author_id")
    private Usuario author;
    private String course;
    private boolean active;

    public Topico(Long id, String title, String message, LocalDateTime date, Status status, Usuario usuario, String curso) {
        this.id=id;
        this.title=title;
        this.message=message;
        this.date = date;
        this.date=LocalDateTime.now();
        this.status=status;
        this.author=usuario;
        this.course=curso;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Usuario getAuthor() {
		return author;
	}

	public void setAuthor(Usuario author) {
		this.author = author;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void topicoActualizado(TopicoActualizadoDTO topicoActualizadoDTO) {
        if (topicoActualizadoDTO.title() !=null){
            this.title= topicoActualizadoDTO.title();
        }
        if (topicoActualizadoDTO.message() != null){
            this.message=topicoActualizadoDTO.message();
        }
        if (topicoActualizadoDTO.status() != null){
            this.status=topicoActualizadoDTO.status();
        }
        if (topicoActualizadoDTO.curso() != null){
            this.course=topicoActualizadoDTO.curso();
        }
    }
    public void diactivateTopic(){
        this.active=false;
    }
}