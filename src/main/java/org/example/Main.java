package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Alumno alumno = new Alumno("Pepe", "Rodriquez", 30, LocalDateTime.now());
        Alumno alumno2 = new Alumno("Carmen", "Perez", 25, LocalDateTime.now());
        Curso curso = new Curso("DAM2", "Desarrollo de Aplicaciones Multiplataforma", new ArrayList<>());
        curso.getAlumnos().add(alumno);
        curso.getAlumnos().add(alumno2);

        //Creamos un listado de cursos añadiendo uno sin alumnos
        List<Curso> cursos = new ArrayList<>();
        cursos.add(curso);
        cursos.add(new Curso("DAM", "DAM", new ArrayList<Alumno>()));

        imprimirCurso(curso);
        imprimirCursos(cursos);


        /*****************   ARCHIVOS BINARIOS DE DATOS    ****************/

        //Guardamos los datos en un archivo de datos
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("curso.dat"));
            objectOutputStream.writeObject(curso);
            objectOutputStream.close();
            System.out.println("Se han exportado los datos al archivo curso.dat");

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("cursos.dat"));
            objectOutputStream.writeObject(cursos);
            objectOutputStream.close();
            System.out.println("Se han exportado los datos al archivo cursos.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Leemos los datos de un archivo de datos
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("curso.dat"));
            Curso cursoLeidoDatos = (Curso)objectInputStream.readObject();
            System.out.println("Datos leídos de curso.dat");
            imprimirCurso(cursoLeidoDatos);

            objectInputStream = new ObjectInputStream(new FileInputStream("cursos.dat"));
            ArrayList<Curso> cursosLeidosDatos = (ArrayList<Curso>)objectInputStream.readObject();
            System.out.println("Datos leídos de cursos.dat");
            imprimirCursos(cursosLeidosDatos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        /*****************   ARCHIVOS JSON / XML   ****************/
        //Inicializamos los objetos que se van a encargar de serializar y deserializar a JSON y XML
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

        try {
            //Añadimos tabulaciones
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            //Registramos el módulo para fechas Java8
            objectMapper.registerModule(new JavaTimeModule());
            //Serializamos a JSON
            objectMapper.writeValue(new File("curso.json"), curso);
            System.out.println("Se han exportado los datos a curso.json");

            //Añadimos tabulaciones
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            //Registramos el módulo para fechas Java8
            xmlMapper.registerModule(new JavaTimeModule());
            //Serializamos a XML
            xmlMapper.writeValue(new File("curso.xml"), curso);
            System.out.println("Se han exportado los datos a curso.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Leemos los datos desde los archivos curso.json y curso.xml y los imprimimos
        try {
            System.out.println("Datos leidos de curso.json");
            Curso cursoLeido = objectMapper.readValue(new File("curso.json"), Curso.class);
            imprimirCurso(cursoLeido);
            System.out.println("Datos leidos de curso.xml");
            Curso cursoLeidoXml = xmlMapper.readValue(new File("curso.xml"), Curso.class);
            imprimirCurso(cursoLeidoXml);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Guaramos el listado de cursos en cursos.json y cursos.xml
        try {
            objectMapper.writeValue(new File("cursos.json"), cursos);
            System.out.println("Se han exportado los datos a cursos.json");

            xmlMapper.writeValue(new File("cursos.xml"), cursos);
            System.out.println("Se han exportado los datos a cursos.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Leemos los listados de cursos desde cursos.json y cursos.xml y los imprimimos por pantalla
        try {
            Curso[] cursosLeidos = objectMapper.readValue(new File("cursos.json"), Curso[].class);
            List<Curso> cursosLeidosList = List.of(cursosLeidos);
            System.out.println("Datos leidos de cursos.json");
            imprimirCursos(cursosLeidosList);

            Curso[] cursosLeidosXml = xmlMapper.readValue(new File("cursos.xml"), Curso[].class);
            List<Curso> cursosLeidosXmlList = List.of(cursosLeidosXml);
            System.out.println("Datos leidos de cursos.xml");
            imprimirCursos(cursosLeidosXmlList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void imprimirCurso(Curso curso) {
        System.out.println(curso.getCodigo() + " " + curso.getTitulo());
        curso.getAlumnos().forEach(a -> {
            System.out.println("- " + a.getNombre() + " " + a.getApellidos() + " " + a.getEdad());
        });
    }

    static void imprimirCursos(List<Curso> cursos) {
        cursos.forEach(curso -> {
            System.out.println("- " + curso.getCodigo() + " " + curso.getTitulo());
            curso.getAlumnos().forEach(a -> {
                System.out.println("  - " + a.getNombre() + " " + a.getApellidos() + " " + a.getEdad());
            });
        });

    }
}