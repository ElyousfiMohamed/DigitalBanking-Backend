# DigitalBanking
 On souhaite créer une application Web basée sur Spring et Angular qui permet de gérer des comptes bancaires. 
 Chaque compte appartient à un client il existe deux types de comptes : Courant et Epargnes. 
 Chaque Compte peut subir des opérations de types Débit ou crédit. 
 L'application se compose des couches suivantes :  
 - Couche DAO (Entités JPA et Repositories)  
 - Couche Service définissant les opérations suivantes :     
 - Ajouter des comptes     
 - Ajouter des client     
 - Effectuer un débit (Retrait)     
 - Effectuer un crédit (Versement)     
 - Effectuer un virement     
 - Consulter un compte 
 - La couche DTO - Mappers (DTO <=>Entities) 
 - La couche Web (Rest Controllers) 
 - Couche sécurité (Spring Security avec JWT)
