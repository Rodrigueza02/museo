package com.museum.backend.dto;

public class EmotionResponse {

    private Long obraId;
    private String comentario;

    public EmotionResponse() {}

    public EmotionResponse(Long obraId, String comentario) {
        this.obraId = obraId;
        this.comentario = comentario;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "EmotionResponse{obraId=" + obraId + ", comentario='" + comentario + "'}";
    }
}