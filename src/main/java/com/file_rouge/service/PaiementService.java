package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;

public interface PaiementService extends Service<Paiement>{
    List<Paiement> selectByDette(Dette dette);
}
