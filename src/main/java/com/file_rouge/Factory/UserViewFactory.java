package com.file_rouge.Factory;

import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.views.users_views.AdminUserView;
import com.file_rouge.views.users_views.BoutiquierUserView;
import com.file_rouge.views.users_views.ClientUserView;

public class UserViewFactory {

   protected Utilisateur userConnected;
    public UserViewFactory(Utilisateur userConnected){
        this.userConnected = userConnected;
    }
    
    public BoutiquierUserView getBoutiquierView(){
        return new BoutiquierUserView(userConnected);
    }

    public AdminUserView getAdminUserView(){
        return new AdminUserView(userConnected);
    }


    public ClientUserView getClientView(){
        return new ClientUserView(userConnected);
    }
    public Utilisateur getUserConnected(){
        return this.userConnected;
    }
}
