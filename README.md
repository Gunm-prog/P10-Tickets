# P10-Tickets
Projet réalisé dans le cadre de la formation développeur d'applications JAVA OpenClassrooms. Amélioration d'un site de gestion de bibliothèque municipale. Contexte : Vous travaillez au sein de la Direction du Système d'Information (DSI) de la mairie d'une grande ville, sous la direction de Patricia, la responsable du service. Dans une mission précédente, vous avez travaillé sur un système d'information pour une grande bibliothèque municipale. Fort du succès de cette première version, le directeur du service culturel reprend contact avec vous.

Présentation
Ce projet est un fork du projet OC-bibliothèque réalisé dans le cadre du projet 7. L'objectif est de reprendre le travail fait sur la version 1 du projet livrée pour y apporter des améliorations et amener des correctifs.

Les compétences évaluées sont les suivantes :

Apporter des améliorations de fonctionnalités demandées par le client
Compléter une suite de tests unitaires et d’intégration afin de prendre en compte les modifications apportées
Corriger des dysfonctionnements signalés par le client sur l’application.

Modifications apportées:
Ticket#1 - Ajout d'une fonctionnalité de réservation d'ouvrages
Ticket#2 - Correction d'un bug dans la gestion des prolongations de prêt
Ticket#3 - Mise en place d'une stratégie de tests


Le projet :

Le site permettra aux utilisateurs de suivre leurs prêts de livres via l'interface web avec une nouvelle fonctionnalité :

Recherchez des livres et voyez le nombre d'exemplaires disponibles. Voir leurs prêts en cours. Le prêt d'un livre ne peut être prolongé qu'une seule fois. La prolongation ajoute une nouvelle période de prêt (4 semaines) à la période initiale. Les utilisateurs pourront réserver un livre. Un batch : Ce logiciel de traitement automatisé enverra des emails de rappel aux utilisateurs n'ayant pas rendu les livres à la fin de la période de prêt. L'envoi sera automatique à la fréquence d'un par jour.


Contraintes fonctionnelles:

- Application web avec un framework MVC.
- API web en REST (Les clients (site web, batch) communiqueront avec cette API web) 
- Packaging avec Maven.


Développement réalisé avec:

Intellij IDEA Community Edition (2022.1)
Java 11
Tomcat 9
MySQL Workbench(version 8.0 C.E.)
Spring (version 5.2.1)
Spring Boot
Feign Proxy
LOMBOK
Spring DATA JPA Hibernate
Docker
L'application a été développée selon une architecture multimodule.


Installation:

Installez Tomcat(9).

Effectuez un git clone du présent repository P10-Tickets (https://github.com/Gunm-Prog/P10-Tickets):

Ouvrez le projet dans Intellij Idea.

Allez dans le fichier application.properties file du module Lib10; à la ligne "spring.jpa.hibernate.ddl-auto =", mettez le mode de configuration sur "create".

Créez une base de données via un éditeur SQL (j'ai utilisé MySQLWorkbench), utilisez les données contenuez dans le dossier Dump, puis allez dans le fichier application.properties: "spring.datasource.url=" et entrez le lien de votre base de données "spring.datasource.username=" your username et "spring.datasource.password=" your password.






Afin de voir ce que peut faire l'utilisateur connecté (voir le détail de ses emprunts de livres et la liste des emprunts de livres dans son tableau de bord, idem pour les réservations), voici les identifiants nécessaires (identifiant et mot de passe) lors de la connexion :

username = lily@hotmail.com password = password

username = neo@hotmail.com password = password

username = tom@hotmail.com password = password

username = chris@hotmail.com password = password

username = marthe@hotmail.com password = password

(tous les utilisateurs peuvent être connectés avec le password = password)

Vous avez également la possibilité d'enregistrer un nouvel utilisateur.


Démarrez:

En premier le module Lib10, puis le module library10WebClient et finalement le module SpringBatch.

Vous pouvez accéder à l'application Web au port localhost:/8083 à partir de votre navigateur si vous n'avez pas modifié le "server.port.properties" dans le fichier application.properties.

Vous trouverez la configuration des propriétés de chaque microservice dans :

src/main/resources/application.properties.


Organisation du répertoire:

docker : répertoire relatifs aux conteneurs docker utiles pour le projet.
src : code source de l'application.


Environnement de développement:

Les composants nécessaires lors du développement sont disponibles via des conteneurs docker. L'environnement de développement est assemblé grâce à docker-compose (cf docker/ci/init/db/docker-compose.yml).

Il comporte :

une base de données MySQL contenant un jeu de données de démo (mysql://127.0.0.1:9032/db_lib10)

Lancement:

cd docker/dev
docker-compose up


Arrêt:

cd docker/dev
docker-compose stop


Remise à zero:

cd docker/dev
docker-compose stop
docker-compose rm -v
docker-compose up


Auteur:

Emilie Balsen - dans le cadre de ma formation de développeur d'applications java chez OpenClassrooms.
