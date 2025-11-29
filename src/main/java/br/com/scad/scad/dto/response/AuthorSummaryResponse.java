package br.com.scad.scad.dto.response;

public class AuthorSummaryResponse {

    private Long id;
    private String name;
    private String nacionalityCountry;

    public AuthorSummaryResponse() {
    }

    public AuthorSummaryResponse(Long id, String name, String nacionalityCountry) {
        this.id = id;
        this.name = name;
        this.nacionalityCountry = nacionalityCountry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNacionalityCountry() {
        return nacionalityCountry;
    }

    public void setNacionalityCountry(String nacionalityCountry) {
        this.nacionalityCountry = nacionalityCountry;
    }
}