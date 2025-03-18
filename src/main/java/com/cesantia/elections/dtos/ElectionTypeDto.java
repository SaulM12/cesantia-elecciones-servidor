package com.cesantia.elections.dtos;

import java.util.List;

public class ElectionTypeDto {
    private String name;
    private String articles;
    private List<CandidateDto> candidates;
    private List<AuthorityDto> authorities;

    public ElectionTypeDto(String name, String articles, List<CandidateDto> candidates, List<AuthorityDto> authorities) {
        this.name = name;
        this.articles = articles;
        this.candidates = candidates;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public List<CandidateDto> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDto> candidates) {
        this.candidates = candidates;
    }

    public List<AuthorityDto> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityDto> authorities) {
        this.authorities = authorities;
    }
}
