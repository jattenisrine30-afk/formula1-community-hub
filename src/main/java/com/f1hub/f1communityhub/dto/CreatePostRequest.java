package com.f1hub.f1communityhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostRequest {

    @NotNull(message = "Le numéro de course est obligatoire")
    private Integer raceRound;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    @Size(max = 500, message = "Le contenu ne peut pas dépasser 500 caractères")
    private String content;
}