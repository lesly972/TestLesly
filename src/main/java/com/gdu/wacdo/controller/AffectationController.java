// Déclaration du package où cette classe se trouve
package com.gdu.wacdo.controller;

// Importation des classes nécessaires (DTOs, entités, services)
import com.gdu.wacdo.dtos.AffectationDto;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateurs;
import com.gdu.wacdo.entities.Restaurants;
import com.gdu.wacdo.service.AffectationService;
import com.gdu.wacdo.service.CollaborateursService;
import com.gdu.wacdo.service.FonctionsService;
import com.gdu.wacdo.service.RestaurantService;

// Importation des annotations Spring
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Importation de la classe List pour la gestion des listes d'objets
import java.util.List;

// Annotation pour indiquer que cette classe est un contrôleur Spring MVC
@Controller
public class AffectationController {

    // Déclaration des services utilisés par le contrôleur
    private final AffectationService affectationService;
    private final CollaborateursService collaborateursService;
    private final FonctionsService fonctionsService;
    private final RestaurantService restaurantService;

    // Constructeur pour injecter les services dans ce contrôleur
    public AffectationController(AffectationService affectationService,
                                 CollaborateursService collaborateursService,
                                 FonctionsService fonctionsService,
                                 RestaurantService restaurantService) {
        this.affectationService = affectationService; // AffectationService
        this.collaborateursService = collaborateursService; // CollaborateursService
        this.fonctionsService = fonctionsService; // FonctionsService
        this.restaurantService = restaurantService; // RestaurantService
    }

    // Méthode pour afficher le formulaire d'affectation
    @GetMapping("/affectation")
    public String afficherFormulaireAffectation(Model model) {
        AffectationDto dto = new AffectationDto();
        model.addAttribute("affectationDto", dto);
        model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());
        model.addAttribute("affectationList", affectationService.getAllAffectations());

        // Nouvelle liste des fonctions déjà affectées pour chaque couple collaborateur + restaurant
        List<Affectation> affectationsActives = affectationService.getAffectationsActives();

        //Test temporaire
        System.out.println(">> Affectations actives :");
        for (Affectation a : affectationsActives) {
            System.out.println(a.getCollaborateurs().getId() + "-" +
                    a.getRestaurants().getId() + "-" +
                    a.getFonctions().getId());
        }

        List<String> fonctionsDejaAffectees = affectationsActives.stream()
                .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId() + "-" + a.getFonctions().getId())
                .toList();
        model.addAttribute("fonctionsDejaAffectees", fonctionsDejaAffectees);

        List<String> combinaisonsActives = affectationsActives.stream()
                .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId())
                .toList();
        model.addAttribute("combinaisonsActives", combinaisonsActives);

        // Liste des collaborateurs sans affectation active
        List<Collaborateurs> sansAffectation = affectationService.getCollaborateursSansAffectation();
        model.addAttribute("collaborateursSansAffectation", sansAffectation);

        return "affectation";
    }

    @PostMapping("/affectation")
    public String creerAffectation(@ModelAttribute AffectationDto dto, Model model) {
        try {
            affectationService.creerAffectation(dto);
            return "redirect:/collaborateurs";
        } catch (IllegalStateException e) {
            model.addAttribute("messageErreur", e.getMessage());
            model.addAttribute("affectationDto", dto);
            model.addAttribute("collaborateurs", collaborateursService.getAllCollaborateurs());
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("fonctions", fonctionsService.getAllFonctions());
            model.addAttribute("affectationList", affectationService.getAllAffectations());

            List<Affectation> affectationsActives = affectationService.getAffectationsActives();
            List<String> combinaisonsActives = affectationsActives.stream()
                    .map(a -> a.getCollaborateurs().getId() + "-" + a.getRestaurants().getId())
                    .toList();
            model.addAttribute("combinaisonsActives", combinaisonsActives);

            return "affectation";
        }
    }

    // Méthode pour afficher les détails d'une affectation spécifique
    @GetMapping("/affectation/{id}")
    public String afficherDetailAffectation(@PathVariable Long id, Model model) {
        // Récupère l'affectation par son ID
        Affectation affectation = affectationService.getAffectationById(id);

        // Si l'affectation n'est pas trouvée, redirige vers la liste des affectations
        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/affectation";
        }

        // Ajoute l'affectation au modèle pour l'afficher dans la vue
        model.addAttribute("affectation", affectation);

        // Retourne la vue qui affiche les détails de l'affectation
        return "affectationDetails";
    }

    // Méthode pour afficher le formulaire de modification d'une affectation
    @GetMapping("/affectation/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        // Récupère l'affectation existante à modifier
        Affectation affectation = affectationService.getAffectationById(id);

        // Si l'affectation n'existe pas, redirige vers la liste des collaborateurs
        if (affectation == null) {
            model.addAttribute("notif", "Affectation non trouvée !");
            return "redirect:/collaborateurs";
        }

        // Crée un objet AffectationDto pour le formulaire de modification
        AffectationDto dto = new AffectationDto();
        dto.setCollaborateurId(affectation.getCollaborateurs().getId());
        dto.setRestaurantId(affectation.getRestaurants().getId());
        dto.setFonctionId(affectation.getFonctions().getId());
        dto.setDateDebut(affectation.getDateDebut());
        dto.setDateFin(affectation.getDateFin());

        // Ajoute l'ID de l'affectation et l'objet DTO au modèle
        model.addAttribute("affectationId", affectation.getId());
        model.addAttribute("affectationDto", dto);

        // Ajoute la liste des restaurants et fonctions au modèle pour les options dans le formulaire
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("fonctions", fonctionsService.getAllFonctions());

        List<Restaurants> restaurantsAffectes = affectationService
                .getRestaurantsAffectesParCollaborateur(dto.getCollaborateurId());
        model.addAttribute("restaurantsAffectes", restaurantsAffectes);



        // Retourne la vue pour le formulaire de modification
        return "modifierAffectation";
    }

    // Méthode pour enregistrer la modification d'une affectation
    //@PostMapping("/affectation/modifier/{id}")
   // public String modifierAffectation(@PathVariable Long id, @ModelAttribute AffectationDto dto) {
        // Appelle le service pour modifier l'affectation
        //affectationService.modifierAffectation(id, dto);

        // Redirige vers la page des détails du collaborateur après modification
       // return "redirect:/collaborateurs/" + dto.getCollaborateurId();
   // }

    @PostMapping("/affectation/modifier/{id}")
    public String modifierAffectation(@PathVariable Long id,
                                      @ModelAttribute AffectationDto dto,
                                      Model model) {
        try {
            affectationService.modifierAffectation(id, dto);
            return "redirect:/collaborateurs/" + dto.getCollaborateurId();
        } catch (IllegalStateException e) {
            model.addAttribute("messageErreur", e.getMessage());
            model.addAttribute("affectationId", id);
            model.addAttribute("affectationDto", dto);
            model.addAttribute("fonctions", fonctionsService.getAllFonctions());

            Restaurants restaurant = restaurantService.getRestaurantById(dto.getRestaurantId());
            if (restaurant != null) {
                model.addAttribute("restaurantNom", restaurant.getNom());
            }

            return "modifierAffectation";
        }
    }


}
