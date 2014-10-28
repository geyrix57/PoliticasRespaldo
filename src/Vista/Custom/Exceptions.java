/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Custom;

import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author geykel
 */
public class Exceptions {
    public static void InformationDialog(String ms, Stage stage){
        Dialogs.create()
        .owner(stage)
        .title("Dialogo de Informacion")
        .masthead(null)
        .message(ms)
        .showInformation();
    }
    
    public static void ExceptionDialog(Exception e, Stage stage){
        Dialogs.create()
        .owner(stage)
        .title("Dialogo de Excepcion")
        .masthead("Excepcion")
        .message(e.getMessage())
        .showException(e);
    }
    
    public static void ErrorDialog(String msg1,String msg2, Stage stage) {
        Dialogs.create()
        .owner(stage)
        .title("Dialogo de Error")
        .masthead(msg1)
        .message(msg2)
        .showError();
    }

    public static boolean ComfirmDialog(String head,String ms, Stage stage){
        Action response = Dialogs.create()
        .owner(stage)
        .title("Dialogo de Confirmacion")
        .masthead(head)
        .message(ms)
        .showConfirm();
        return response == Dialog.Actions.YES;
    }
}
