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