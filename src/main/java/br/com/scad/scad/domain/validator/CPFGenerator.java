package br.com.scad.scad.domain.validator;

import org.springframework.stereotype.Component;

@Component
public class CPFGenerator {
    public static String gerarCPF() {
        java.util.Random random = new java.util.Random();
        int[] n = new int[9];

        // Gera os 9 primeiros dígitos
        for (int i = 0; i < 9; i++) {
            n[i] = random.nextInt(10);
        }

        // Calcula o 1º dígito verificador
        int soma = 0, peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += n[i] * peso--;
        }
        int d1 = 11 - (soma % 11);
        d1 = (d1 > 9) ? 0 : d1;

        // Calcula o 2º dígito verificador
        soma = 0;
        peso = 11;
        for (int i = 0; i < 9; i++) {
            soma += n[i] * peso--;
        }
        soma += d1 * peso--;
        int d2 = 11 - (soma % 11);
        d2 = (d2 > 9) ? 0 : d2;

        // Retorna formatado
        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d",
                n[0], n[1], n[2],
                n[3], n[4], n[5],
                n[6], n[7], n[8],
                d1, d2);
    }
}
