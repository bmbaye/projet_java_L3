package com.file_rouge.views.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.service.DetteService;
import com.file_rouge.service.PaiementService;
import com.file_rouge.views.PaiementView;

public class PaiementViewImpl extends ViewImpl<Paiement> implements PaiementView {
    PaiementService paiementService;
    DetteService detteService;

    public PaiementViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        this.paiementService =paiementFactory.getServiceInstence();
        this.detteService = detteFactory.getServiceInstence();
    }


    @Override
    public Paiement saisie(Utilisateur userConnected) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saisie'");
    }

    @Override
    public void display(List<Paiement> paiements) {
        for (Paiement paiement : paiements) {
            System.out.println("ID :" + paiement.getId() + " |Montant: " + paiement.getMontant() + " |Dette_id: " + paiement.getDette().getId() + " |Datte: "+ paiement.getCreateAt());
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'display'");
    }

    @Override
    public void save(Utilisateur userConnected, Dette dette) {
       Double montant;
       Double montantRestant = dette.getMontant() - dette.getMontantVerse();
       System.out.println("Le montant restant est de: " +montantRestant);
       do {
        System.out.println("Entrer le montant");
        montant = scan.nextDouble();
       } while (montant <=0 || montant > montantRestant);

       Double montantVerse = dette.getMontantVerse() + montant;
       if(detteService.checkSolde(montantVerse, dette.getMontant())){
           dette.setStatut(StatutDette.SOLDE);
        }
        dette.setMontantVerse(montantVerse);

        Paiement paiement = new Paiement();
        paiement.setMontant(montant);
        paiement.setDette(dette);
        paiement.setCreatorUser(userConnected);
        paiement.setUpdateUser(userConnected);
        paiementService.create(paiement);
        
        detteService.update(dette);
    }
}
