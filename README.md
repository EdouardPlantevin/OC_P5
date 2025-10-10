# Application Full Stack - Yoga Studio

Cette application full stack comprend un backend Spring Boot et un frontend Angular pour la gestion d'un studio de yoga.

## 📁 Structure du projet

```
├── back/          # Backend Spring Boot
├── front/         # Frontend Angular
└── ressources/    # Documentation et collections Postman
```

## 🚀 Installation

### Prérequis

- **Java 11+** pour le backend
- **Node.js 16+** et **npm** pour le frontend
- **MySQL 8.0+** pour la base de données
- **Maven 3.6+** pour le backend

### Backend (Spring Boot)

1. **Naviguer vers le dossier backend :**
   ```bash
   cd back
   ```

2. **Installer les dépendances :**
   ```bash
   mvn clean install
   ```

3. **Configuration de la base de données :**
   
   Créer une base de données MySQL :
   ```sql
   CREATE DATABASE yoga_app;
   ```
   
   Modifier le fichier `src/main/resources/application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/yoga_app?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Lancer l'application :**
   ```bash
   mvn spring-boot:run
   ```
   
   L'API sera disponible sur : `http://localhost:8080`

### Frontend (Angular)

1. **Naviguer vers le dossier frontend :**
   ```bash
   cd front
   ```

2. **Installer les dépendances :**
   ```bash
   npm install
   ```

3. **Lancer l'application :**
   ```bash
   npm start
   ```
   
   L'application sera disponible sur : `http://localhost:4200`

## 🗄️ Base de données

### Scripts SQL

Les scripts SQL se trouvent dans :
- `ressources/sql/script.sql` - Script principal de création des tables
- `back/src/test/resources/script.sql` - Données de test

### Tables principales

- **USERS** - Utilisateurs (admin/élèves)
- **TEACHERS** - Professeurs de yoga
- **SESSIONS** - Sessions de yoga
- **PARTICIPATE** - Participation aux sessions

### Données de test

Le script de test contient :
- 3 utilisateurs (1 admin, 2 élèves)
- 3 professeurs
- 3 sessions de yoga
- Participations aux sessions

## 🧪 Tests

### Backend (Spring Boot)

#### Tests unitaires et d'intégration

```bash
# Lancer tous les tests
mvn test

# Si modifications, nettoyer avant
mvn clean test

# Lancer un test spécifique
mvn test -Dtest=UserTest

# Lancer les tests d'un package
mvn test -Dtest=com.openclassrooms.starterjwt.controllers.*
```

#### Couverture de code

La couverture est générée automatiquement

**Consulter le rapport :**
- Ouvrir : `back/target/site/jacoco/index.html`
- Couverture globale visible dans le navigateur

### Frontend (Angular)

#### Tests unitaires (Jest)

```bash
cd front

# Lancer tous les tests unitaires
npm run test
```

**Consulter la couverture Jest :**
- Ouvrir : `front/coverage/jest/index.html`

#### Tests end-to-end (Cypress)

```bash
cd front

# Lancer Cypress en mode interactif
npm run e2e

# Lancer Cypress en mode CI (headless)
npm run e2e:ci
```

**Consulter la couverture Cypress :**
- Ouvrir : `front/coverage/lcov-report/index.html`

## 📝 Notes de développement

- Les tests backend utilisent une base de données H2 en mémoire pour les tests unitaires
