package com.challengehub.forohub.modelos;

import java.time.LocalDateTime;

import com.challengehub.forohub.DTOs.DTOGuardarRespuesta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "responses")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Respuesta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;

    private String message;

    private LocalDateTime creationdate;

    private String solution;

    @JoinColumn(name="author", referencedColumnName="code")
    @OneToOne
    private Usuario author;

    @JoinColumn(name="topic", referencedColumnName="code")
    @OneToOne
    private Topico topic;

    public Respuesta(DTOGuardarRespuesta dtoCreateResponseToDatabase)
    {
        this.message = dtoCreateResponseToDatabase.message();
        this.creationdate = LocalDateTime.now();
        this.author = dtoCreateResponseToDatabase.author();
        this.topic = dtoCreateResponseToDatabase.topic();
        this.solution = dtoCreateResponseToDatabase.solution();
    }

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

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Usuario getAuthor() {
		return author;
	}

	public void setAuthor(Usuario author) {
		this.author = author;
	}

	public Topico getTopic() {
		return topic;
	}

	public void setTopic(Topico topic) {
		this.topic = topic;
	}



}
