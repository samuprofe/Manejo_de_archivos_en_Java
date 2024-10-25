package org.example;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "curso")
public class Curso implements Serializable {
    private String codigo;
    private String titulo;

    @JacksonXmlElementWrapper(localName = "alumnos", useWrapping = false)
    List<Alumno> alumnos = new ArrayList<>();
}
