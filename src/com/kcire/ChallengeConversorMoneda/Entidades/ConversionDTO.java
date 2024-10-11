package com.kcire.ChallengeConversorMoneda.Entidades;

import java.util.Map;

public record ConversionDTO(String result,
                            String base_code,
                            String target_code,
                            double conversion_rate,
                            double conversion_result) {
}
