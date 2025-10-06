describe('Register Component', () => {
  beforeEach(() => {
    cy.visit('/register');
  });

  it("devrait permettre de s'inscrire avec succès", () => {

    cy.intercept('POST', '/api/auth/register', {
      statusCode: 200,
      body: {}
    });

    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('john.doe@yoga.com');
    cy.get('input[formControlName="password"]').type('password123');

    cy.get('button[type="submit"]').click();

    cy.url().should('include', '/login');
  });

  it('devrait afficher une erreur en cas d\'échec d\'inscription', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: { error: 'Registration failed' }
    });

    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('john.doe@yoga.com');
    cy.get('input[formControlName="password"]').type('password123');

    cy.get('button[type="submit"]').click();

    cy.contains('An error occurred').should('be.visible');
  });

  it('devrait désactiver le bouton submit si le formulaire est invalide', () => {
    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="firstName"]').type('John');

    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('john.doe@yoga.com');
    cy.get('input[formControlName="password"]').type('password123');

    cy.get('button[type="submit"]').should('not.be.disabled');
  });

  it('devrait valider les champs requis', () => {
    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="email"]').type('invalid-email'); // < Email invalide ici
    cy.get('input[formControlName="password"]').type('password123');

    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="email"]').clear().type('john.doe@yoga.com');

    cy.get('button[type="submit"]').should('not.be.disabled');
  });
});
