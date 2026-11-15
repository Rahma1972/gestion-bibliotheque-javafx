package controle;

import javafx.application.Platform;
import javafx.stage.Stage;

import models.users.GestionUtilisateursServiceImpl;
import models.media.StockServiceImpl;
import views.*;

public class TabDeBordController {

    private final GestionUtilisateursController utilisateursController;
    private final GestionMediasController mediasController;
    private final EmpruntsController empruntsController;
    private final GestionUtilisateursServiceImpl utilisateurService;
    private final StockServiceImpl stockService;
    private final StatistiquesController statsController;
    private final RechercheAvanceeController rechercheController;
    private final boolean peutSupprimer;

    public TabDeBordController(
            GestionUtilisateursController utilisateursController,
            GestionMediasController mediasController,
            EmpruntsController empruntsController,
            GestionUtilisateursServiceImpl utilisateurService,
            StockServiceImpl stockService,
            StatistiquesController statsController,
            RechercheAvanceeController rechercheController,
            boolean peutSupprimer) {
        this.utilisateursController = utilisateursController;
        this.mediasController = mediasController;
        this.empruntsController = empruntsController;
        this.utilisateurService = utilisateurService;
        this.stockService = stockService;
        this.statsController = statsController;
        this.rechercheController = rechercheController;
        this.peutSupprimer = peutSupprimer;
    }

    public void openGestionUtilisateurs() {
        GestionUtilisateursView view = new GestionUtilisateursView(utilisateursController, this.peutSupprimer);
        view.show(new Stage());
    }

    public void openGestionMedias() {
        GestionMediasView view = new GestionMediasView(mediasController);
        view.show(new Stage());
    }

    public void openEmprunts() {
        EmpruntsView view = new EmpruntsView(empruntsController);
        view.show(new Stage());
    }

    public void logout(Stage tabdebordStage) {
        tabdebordStage.close();
        Platform.runLater(() -> {
            ConnexionController connexionCtrl = new ConnexionController(utilisateurService, stockService);
            ConnexionView connexionView = new ConnexionView(connexionCtrl);
            connexionView.show(new Stage());
        });
    }

    public void openStatistiques() {
        StatistiquesView view = new StatistiquesView(statsController);
        view.show(new Stage());
    }

    public void openRechercheAvancee() {
        RechercheAvanceeView view = new RechercheAvanceeView(rechercheController);
        view.show(new Stage());
    }
}