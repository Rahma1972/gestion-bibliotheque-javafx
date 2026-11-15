package controle;

import models.media.*;
import models.users.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import views.HistoriqueLecteurView;
import views.TabDeBordView;

public class ConnexionController {

    private final GestionUtilisateursServiceImpl utilisateurService;
    private final StockServiceImpl stockService;

    public ConnexionController(GestionUtilisateursServiceImpl utilisateurService, StockServiceImpl stockService) {
        this.utilisateurService = utilisateurService;
        this.stockService = stockService;
    }

    public void login(String email, String password, Stage loginStage) {
        Personne user = null;
        for (Personne p : utilisateurService.lister()) {
            if (p.getEmail().equals(email) && p.getMotDePasse().equals(password)) {
                user = p;
                break;
            }
        }

        if (user == null) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "Email ou mot de passe incorrect !").showAndWait();
            });
            return;
        }

        Personne finalUser = user;

        Platform.runLater(() -> {

            if (finalUser instanceof Lecteur) {
                Stage lecteurStage = new Stage();
                HistoriqueLecteurView view = new HistoriqueLecteurView(
                        (Lecteur) finalUser,
                        lecteurStage,
                        utilisateurService,
                        stockService
                );
                view.show();
                loginStage.close();
            } else if (finalUser instanceof Bibliothecaire || finalUser instanceof AdminBibliotheque) {
                boolean peutSupprimer = (finalUser instanceof AdminBibliotheque);
                JournalEmpruntsService journal = new JournalEmpruntsService();
                StatistiquesController statsCtrl = new StatistiquesController(stockService);
                RechercheAvanceeController rechercheCtrl = new RechercheAvanceeController(utilisateurService, stockService);


                GestionUtilisateursController utilisateursCtrl = new GestionUtilisateursController(utilisateurService);
                GestionMediasController mediasCtrl = new GestionMediasController(stockService);
                EmpruntsController empruntsCtrl = new EmpruntsController(utilisateurService, stockService, journal);


                TabDeBordController tabdebordCtrl = new TabDeBordController(
                        utilisateursCtrl,
                        mediasCtrl,
                        empruntsCtrl,
                        utilisateurService,
                        stockService,
                        statsCtrl,
                        rechercheCtrl,
                        peutSupprimer
                );

                Stage tabdebordStage = new Stage();
                TabDeBordView tabdebordView = new TabDeBordView(tabdebordCtrl, tabdebordStage);
                tabdebordView.show();
                loginStage.close();
            }
        });
    }
}