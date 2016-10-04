/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shakki.shakki.kayttoliittyma;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import shakki.shakki.logiikka.*;
import shakki.shakki.logiikka.nappulat.*;

/**
 *
 * @author Sami
 */
public class NappulanKuuntelija implements ActionListener {

    Ruutu[][] ruudut;
    Logiikka logiikka;

    ImageIcon sotilas_v = new ImageIcon("src/main/resources/images/sotilas_valkoinen.png");
    ImageIcon sotilas_m = new ImageIcon("src/main/resources/images/sotilas_musta.png");

    ImageIcon hevonen_v = new ImageIcon("src/main/resources/images/hevonen_valkoinen.png");
    ImageIcon hevonen_m = new ImageIcon("src/main/resources/images/hevonen_musta.png");

    ImageIcon torni_v = new ImageIcon("src/main/resources/images/torni_valkoinen.png");
    ImageIcon torni_m = new ImageIcon("src/main/resources/images/torni_musta.png");

    ImageIcon kuningas_v = new ImageIcon("src/main/resources/images/kuningas_valkoinen.png");
    ImageIcon kuningas_m = new ImageIcon("src/main/resources/images/kuningas_musta.png");

    ImageIcon kuningatar_v = new ImageIcon("src/main/resources/images/kuningatar_valkoinen.png");
    ImageIcon kuningatar_m = new ImageIcon("src/main/resources/images/kuningatar_musta.png");

    ImageIcon lahetti_v = new ImageIcon("src/main/resources/images/lahetti_valkoinen.png");
    ImageIcon lahetti_m = new ImageIcon("src/main/resources/images/lahetti_musta.png");

    Ruutu edellinenPainettuNappula;

    public NappulanKuuntelija(Ruutu[][] ruudut, Logiikka logiikka) {

        this.ruudut = ruudut;
        this.logiikka = logiikka;
        Ruutu edellinenPainettuNappula = null;

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        Ruutu nappi = (Ruutu) ae.getSource();

        if (edellinenPainettuNappula != null) {
            
            edellinenPainettuNappula.setEnabled(true);
            edellinenPainettuNappula.setValittu(true);
            
        }
        
        //Jos nappia ei olla painettu, näytetään mahdolliset siirtymäpaikat
        if (nappi.onkoValittu() == false) {

            if (onkoJokuRuutuValittu()) {

                palautaRuutujenValintaFalseksi();
                paivitaPelilauta();
            }

            edellinenPainettuNappula = nappi;
            nappi.setValittu(true);
            nappi.setEnabled(false);

            logiikka.paivitaValitunNappulanMahdollisetSiirrot(nappi.getRivi(), nappi.getSarake());
            boolean[][] mahdollisetSiirtymat = logiikka.naytaValitunNappulanMahdollisetSiirrot();

            for (int i = 0; i <= 7; i++) {

                for (int j = 0; j <= 7; j++) {

                    if (mahdollisetSiirtymat[i][j]) {

                        ruudut[i][j].setBackground(Color.yellow);

                    }

                }
            }

            //Jos nappia ollaan painettu, ja uusi painallus kohdistuu mahdolliseen siirtymäruutuun, niin siirretään nappula siihen    
        } else {

            Ruutu ruutu = (Ruutu) ae.getSource();
            palautaRuutujenValintaFalseksi();
            int ruudunRivi = ruutu.getRivi();
            int ruudunSarake = ruutu.getSarake();

            boolean[][] mahdollisetSiirtymat = logiikka.naytaValitunNappulanMahdollisetSiirrot();

            if (mahdollisetSiirtymat[ruudunRivi][ruudunSarake]) {

                logiikka.liikutaNappulaa(edellinenPainettuNappula.getRivi(), edellinenPainettuNappula.getSarake(), ruudunRivi, ruudunSarake);
                paivitaPelilauta();
            }

            nappi.setValittu(false);
        }

    }

    public boolean onkoJokuRuutuValittu() {

        for (int i = 0; i <= 7; i++) {

            for (int j = 0; j <= 7; j++) {

                if (ruudut[i][j].onkoValittu() == true) {

                    return true;

                }

            }

        }

        return false;

    }

    public void paivitaPelilauta() {

        varitaRuudut();

        Nappula[][] nappulat = logiikka.annaPelilauta();

        for (int i = 0; i <= 7; i++) {

            for (int j = 0; j <= 7; j++) {

                if (nappulat[i][j] != null) {

                    Nappula nappula = nappulat[i][j];

                    if (nappula.getClass().equals(Sotilas.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(sotilas_v);

                        } else {

                            ruudut[i][j].setIcon(sotilas_m);

                        }

                    } else if (nappula.getClass().equals(Torni.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(torni_v);

                        } else {

                            ruudut[i][j].setIcon(torni_m);

                        }

                    } else if (nappula.getClass().equals(Lahetti.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(lahetti_v);

                        } else {

                            ruudut[i][j].setIcon(lahetti_m);

                        }

                    } else if (nappula.getClass().equals(Hevonen.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(hevonen_v);

                        } else {

                            ruudut[i][j].setIcon(hevonen_m);

                        }

                    } else if (nappula.getClass().equals(Kuningas.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(kuningas_v);

                        } else {

                            ruudut[i][j].setIcon(kuningas_m);

                        }

                    } else if (nappula.getClass().equals(Kuningatar.class)) {

                        if (nappula.getPelaaja().getId() == 1) {

                            ruudut[i][j].setIcon(kuningatar_v);

                        } else {

                            ruudut[i][j].setIcon(kuningatar_m);

                        }

                    }
                }
            }

        }

    }

    public void varitaRuudut() {

        int ruudunVaritys = 1;

        for (int i = 0; i <= 7; i++) {

            for (int j = 0; j <= 7; j++) {

                if (ruudunVaritys == 1) {

                    ruudut[i][j].setBackground(Color.white);

                } else {

                    ruudut[i][j].setBackground(Color.darkGray);

                }

                ruudunVaritys = 3 - ruudunVaritys;

            }

            ruudunVaritys = 3 - ruudunVaritys;
        }

    }

    public void palautaRuutujenValintaFalseksi() {

        for (int i = 0; i <= 7; i++) {

            for (int j = 0; j <= 7; j++) {

                ruudut[i][j].setValittu(false);

            }

        }

    }

}
