# Automatisation_CI_CD

#Groupe :
Youness FAHMI - Saad MOUJID - Khadija TOUTI - Younes LARHLID - Chaimaa Haji

Ona amélioré le projet MSPR qu'on a réalisé l'année derniere en implémentant des tests et en modifiant la pipeline pour répondre aux exigences. Nous avons également mis en place un serveur Jenkins connecté à un serveur Nexus pour le déploiement de snapshots et de versions finales. En outre, nous avons connecté un serveur SonarQube pour assurer une qualité de code optimale.

La pipeline effectue des vérifications sur les versions de Java et Maven, exécute des tests unitaires, vérifie le taux de couverture de code via SonarQube, crée un fichier .jar, récupère des informations à partir du fichier pom.xml, construit le projet et publie sur Nexus. Les images de Jenkins, Nexus et SonarQube sont intégrées dans un docker-compose pour faciliter le lancement du projet et permettre à ces composants de communiquer efficacement.

Les grandes difficultés qu'on a rencontré c'était la mise en place de Nexus et Sonarqube, il y'avait peu de ressources et de documentation sur internet sur les problèmes rencontrés.
Mais grâce à Google et les differents forums sur internet on a pu résoudre la plupart de ces problèmes.
