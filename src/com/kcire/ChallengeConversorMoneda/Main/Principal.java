package com.kcire.ChallengeConversorMoneda.Main;


import com.kcire.ChallengeConversorMoneda.Servicios.ConversorService;

public class Principal {
    public static void main(String[] args) {

        ConversorService cs = new ConversorService();
        cs.menu();
    }
}
