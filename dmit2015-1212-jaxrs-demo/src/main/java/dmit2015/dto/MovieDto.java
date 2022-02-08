package dmit2015.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MovieDto {

    private Long movieId;

    private String title;

    private LocalDate releaseDate;

    private BigDecimal price;

    private String genre;

    private String rating;

}
