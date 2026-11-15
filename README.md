# Gestion de Bibliothèque

> Application de bureau **JavaFX** de gestion de bibliothèque (livres, magazines, dictionnaires), avec gestion des utilisateurs par rôle, emprunts, recherche avancée et statistiques. **Sans base de données** : les données sont gérées en mémoire.

![Java](https://img.shields.io/badge/Java-24-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-25.0.1-blue)
![Type](https://img.shields.io/badge/Application-Desktop-lightgrey)

Projet réalisé dans le cadre de la matière Programmation orientée objet – ENICarthage, 1er semestre .

---

##  Aperçu

| Connexion | Tableau de bord |
|---|---|
| ![Connexion](docs/screenshots/connexion.png) | ![Tableau de bord](docs/screenshots/admin/tableau-de-bord.png) |

| Gestion des médias | Recherche avancée |
|---|---|
| ![Médias](docs/screenshots/admin/gestion-media.png) | ![Recherche](docs/screenshots/admin/recherche-avancee.png) |

| Emprunts | Statistiques |
|---|---|
| ![Emprunts](docs/screenshots/admin/gestion-emprunts.png) | ![Statistiques](docs/screenshots/admin/statistiques.png) |


## Fonctionnalités

- **Authentification** par email et mot de passe, avec message d'erreur en cas d'identifiants incorrects.
- **Redirection selon le profil** : un lecteur accède à son espace (`HistoriqueLecteurView`), le personnel (bibliothécaire, administrateur) accède au tableau de bord.
- **Gestion des médias** : ajout, consultation et suppression de **livres**, **magazines** et **dictionnaires** (stock géré par `StockService`).
- **Gestion des utilisateurs** : lecteurs, bibliothécaires et administrateur.
- **Emprunts** : un média disponible peut être emprunté par un lecteur ; chaque emprunt est enregistré dans un **journal** (`JournalEmpruntsService`) avec sa date. Un média déjà emprunté est refusé (`MediaNonDisponibleException`).
- **Recherche avancée** : filtrage des médias et des personnes grâce aux interfaces `FiltreMedia` et `FiltrePersonne`.
- **Statistiques** sur les médias (`StatistiquesMedia`).
- **Droits différenciés** : seul l'administrateur peut supprimer.

### Rôles

| Rôle | Classe | Accès |
|---|---|---|
| Lecteur | `Lecteur` | Espace lecteur : catalogue et historique de ses emprunts |
| Bibliothécaire | `Bibliothecaire` | Tableau de bord complet : médias, utilisateurs, emprunts, recherche avancée, statistiques. **Sans droit de suppression** |
| Administrateur | `AdminBibliotheque` | Mêmes écrans que le bibliothécaire, avec en plus le **droit de suppression** |

Le bibliothécaire et l'administrateur partagent la même interface (`TabDeBordView`) ; la seule différence de droits est la **suppression**, réservée à l'administrateur.

---

##  Architecture

Organisation en packages, proche du modèle **MVC** :

```
src/
 ├─ app/          MainFX                        point d'entrée JavaFX
 ├─ controle/     *Controller                   logique des écrans
 ├─ models/
 │   ├─ media/    Media, Livre, Magazine, Dictionnaire,
 │   │            StockService / StockServiceImpl, FiltreMedia,
 │   │            StatistiquesMedia, MediaNonDisponibleException
 │   └─ users/    Personne, Lecteur, Employe, Bibliothecaire, AdminBibliotheque,
 │                GestionUtilisateursService / Impl, JournalEmpruntsService,
 │                DemandeEmprunt, FiltrePersonne, UtilisateurNonTrouveException
 └─ views/        *View + style.css             interfaces JavaFX (construites en Java)
```

### Concepts de programmation orientée objet mis en œuvre
- **Héritage** : `Media` → `Livre`, `Magazine`, `Dictionnaire` ; `Personne` → `Lecteur`, `Employe` → `Bibliothecaire`, `AdminBibliotheque`.
- **Classes scellées** (`sealed`) : `Personne` n'autorise que `Lecteur` et `Employe` (`permits`).
- **Programmation fonctionnelle** : `Stream`, lambdas et références de méthode pour filtrer le stock et les utilisateurs.
- **Interfaces** : `StockService`, `GestionUtilisateursService`, `FiltreMedia`, `FiltrePersonne`.
- **Séparation interface / implémentation** (`*Impl`).
- **Exceptions personnalisées** : `MediaNonDisponibleException`, `UtilisateurNonTrouveException`.
- **Records** : `DemandeEmprunt`, `StatistiquesMedia` *(à confirmer)*.
- **Collections** (`Map`, `List`) et interface graphique JavaFX avec feuille de style CSS.

---

## Données

Il n'y a **pas de base de données** : les données sont stockées **en mémoire** (`HashMap`, `ArrayList`) et initialisées au démarrage par `MainFX` : 4 utilisateurs et 13 médias de démonstration (5 livres, 4 magazines, 4 dictionnaires).
Les modifications effectuées pendant l'utilisation sont **perdues à la fermeture** de l'application.

---

## Technologies

Java 24 · JavaFX 25.0.1 (module `javafx.controls`) · CSS · IntelliJ IDEA

---

## Installation et lancement

### Prérequis
- **JDK 23 ou plus récent** (développé avec JDK 24)
- **SDK JavaFX 25.0.1** : [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/) (choisir votre système, type *SDK*)

### Lancer avec IntelliJ IDEA

```bash
git clone https://github.com/<votre-compte>/<votre-depot>.git
```

1. Ouvrez le dossier dans IntelliJ (**File → Open**).
2. Définissez le JDK du projet (**File → Project Structure → SDK**).
3. Créez une configuration d'exécution **Run → Edit Configurations → + → Application** :
   - **Main class** : `app.MainFX`
   - **VM options** (adaptez le chemin à votre SDK JavaFX) :
     ```
     --module-path "C:\chemin\vers\javafx-sdk-25.0.1\lib" --add-modules javafx.controls
     ```
4. Cliquez sur **Run**.

### Lancer en ligne de commande
```bash
javac --module-path "chemin/javafx-sdk-25.0.1/lib" --add-modules javafx.controls -d out $(find src -name "*.java")
java  --module-path "chemin/javafx-sdk-25.0.1/lib" --add-modules javafx.controls -cp "out:src" app.MainFX
```
*(Sous Windows, remplacez `:` par `;` dans `-cp` et listez les fichiers avec `dir /s /b src\*.java`.)*

>  Erreur « JavaFX runtime components are missing » : le `--module-path` ne pointe pas vers le dossier `lib` du SDK JavaFX.

---

##  Comptes de démonstration

Créés au démarrage par `MainFX` :

| Rôle | Email | Mot de passe |
|---|---|---|
| Administrateur | admin@example.com | admin123 |
| Bibliothécaire | biblio@example.com | biblio123 |
| Lecteur | lecteur1@example.com | lecteur123 |
| Lecteur | lecteur2@example.com | lecteur123 |

>  Comptes **fictifs** de démonstration. [À ADAPTER] : ces adresses doivent être celles que vous mettez dans `MainFX.java` (voir conseils de publication).

---

##  Limites connues
- Données non persistantes (en mémoire uniquement).
- Les mots de passe sont **stockés et comparés en clair** (pas de hachage) et les comptes de démonstration sont définis dans le code.
- Pas de protection contre les tentatives de connexion répétées.
- Les rôles « bibliothécaire » et « administrateur » ne se distinguent que par le droit de suppression.
- Le journal des emprunts est recréé à chaque connexion du personnel : il n'est pas conservé d'une session à l'autre.
- Application destinée à l'apprentissage, non prévue pour la production.

##  Perspectives
- Sauvegarde des données dans un fichier (JSON/CSV) ou une base de données
- Hachage des mots de passe (par exemple BCrypt)
- Journal des emprunts partagé et persistant
- Gestion du retour des médias
- Séparation plus nette des droits entre bibliothécaire et administrateur (par exemple, gestion des comptes réservée à l'administrateur)
- Gestion des retards et pénalités
- Packaging en exécutable (`jpackage`) ou migration vers Maven

---

##  Auteure
[LinkedIn](https://linkedin.com/in/tobji-rahma-4b1029411) · [GitHub](https://github.com/Rahma1972)

