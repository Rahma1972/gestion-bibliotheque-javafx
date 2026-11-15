package models.users;

import models.media.Media;
import java.time.LocalDate;

public record DemandeEmprunt(Lecteur lecteur, Media media, LocalDate date) {}
