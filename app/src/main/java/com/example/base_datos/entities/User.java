package com.example.base_datos.entities;

public class User {
    //1. Declaración de atributos o propiedades
    private int document;
    private String names;
    private String lastNames;
    private  String user;
    private String password;
    //2. Constructores-> valores iniciales a los atributos una vez que se instancia
    public User(){}

    public User(int document, String names, String lastNames, String user, String password) {
        this.document = document;
        this.names = names;
        this.lastNames = lastNames;
        this.user = user;
        this.password = password;
    }
    //3. Métodos de acceso-> getter and setter

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //4. Imprimir directo el objeto

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("document=").append(document);
        sb.append(", names='").append(names).append('\'');
        sb.append(", lastNames='").append(lastNames).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getNombres() {
        return names;
    }

    public String getApellidos() {
        return lastNames;
    }

    public String getUsuario() {
        return user;
    }

}
