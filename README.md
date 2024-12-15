Etape 1 : 

Création de sous-packages

Quels packages on a besoin ?

entities
repositories
dto
service
web
mappers

Ce sont les modèles globalement utilisés par les entreprises ici

On commence par créer les entités
- compte bancaire
- client
- operations sur les comptes
- deux types de compte : débit/crédit

On commence par créer les attributs...

Partie héritage : 

==> Etant donné qu'on part du mapping héritage d'un compte bancaire
(bankaccount) on doit spécifier une annotation @Inheritance

et on oublie pas d'y lier la valeure discriminatoire 
CurrentAccount
SavingAccount

on spécifie la BDD (application.properties)

on va venir créer 3 interfaces dans repositorites pour JPA

test fictif d'insértion de données dans la base pour avoir un visuel H2

effectuer la bascule de H2 à MYSQL maintenant

création de la classe dans services pour gérer le flux des opérations (cf. eager/lazy)

on commence la couche service..

ensuite, on implémente la/les interface/s

pourquoi on a besoin des DTO's ?...

On commence par créer les différents rest controller pour les =>
- Customers
- Bank accounts

On a mis en place les DTO avec customerDTO et deux méthodes mapper
fromcustomer
fromcustomerdto

pour le transfer des données pour l'UI de customer à customerdto directement
en évitant de mapper les entités (domaine complexe pour l'UI)

On met en place le backend swagger :

- http://localhost:8085/v3/api-docs
- avec pom.xml springdocapi
  
L'url nous génére un document JSON qui nous montre les fonctionnalités de notre
API RESTful (interface du webservice)

On commence la partie RESTful pour les comptes, les retraits etc...

==>