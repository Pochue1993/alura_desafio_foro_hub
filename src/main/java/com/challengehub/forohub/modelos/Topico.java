package com.challengehub.forohub.modelos;

import java.time.LocalDateTime;

import com.challengehub.forohub.DTOs.DTOGuardarTopico;

import jakarta.persistence.Column;
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

@Table(name = "topic")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topico
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Code;
    private String title;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JoinColumn(name="status", referencedColumnName="code")
    @OneToOne
    private Estado status;

    @JoinColumn(name="author", referencedColumnName="code")
    @OneToOne
    private Usuario author;

    @JoinColumn(name="course", referencedColumnName="code")
    @OneToOne
    private Curso course;

    public Topico(DTOGuardarTopico dtoCreateTopicToDatabase)
    {
        this.title = dtoCreateTopicToDatabase.title();
        this.message = dtoCreateTopicToDatabase.message();
        this.creationDate = LocalDateTime.now();
        this.author = dtoCreateTopicToDatabase.user();
        this.course = dtoCreateTopicToDatabase.course();
        this.status = dtoCreateTopicToDatabase.status();
    }

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Estado getStatus() {
		return status;
	}

	public void setStatus(Estado status) {
		this.status = status;
	}

	public Usuario getAuthor() {
		return author;
	}

	public void setAuthor(Usuario author) {
		this.author = author;
	}

	public Curso getCourse() {
		return course;
	}

	public void setCourse(Curso course) {
		this.course = course;
	}
}
