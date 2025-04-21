// Package principal de l'application WACDO
package com.gdu.wacdo;

// On importe les classes de Spring nécessaires au démarrage de l'application
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Cette annotation est super importante :
// Elle dit à Spring Boot que c’est le point d’entrée de l’appli,
// et elle active plein de choses comme la détection automatique des beans, la config, etc.
@SpringBootApplication
public class WacdoApplication {

	// La fameuse méthode main : c’est par ici que tout commence quand on lance l’appli
	public static void main(String[] args) {

		// Cette ligne lance vraiment l’application Spring Boot
		// Elle va charger toute la config, démarrer le serveur intégré ,
		// scanner les composants (Controller, Service, Repository, etc.), bref tout le squelette de l’appli.
		SpringApplication.run(WacdoApplication.class, args);
	}
}
