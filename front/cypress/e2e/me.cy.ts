describe('Me Component Tests', () => {
  it('devrait afficher les informations utilisateur après connexion', () => {

    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'admin_user',
        firstName: 'Edouard',
        lastName: 'Plantevin',
        email: 'test@test.com',
        admin: true,
        password: 'password',
        createdAt: '2025-09-27T00:00:00.000Z',
        updatedAt: '2025-09-27T00:00:00.000Z'
      }
    });

    cy.login(true);

    // On pars vers /me
    cy.contains('Account').click();

    cy.get('body').should('contain', 'User information');

    cy.get('body').should('contain', 'Edouard');
    cy.get('body').should('contain', 'PLANTEVIN'); // lastName en majuscules
    cy.get('body').should('contain', 'test@test.com');
    cy.get('body').should('contain', 'You are admin');
  });

  it('devrait supprimer le compte utilisateur et rediriger vers la page d\'accueil', () => {

    // Ne dois pas être admin
    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'user_test',
        firstName: 'Edouard',
        lastName: 'Plantevin',
        email: 'user@test.com',
        admin: false,
        password: 'password',
        createdAt: '2025-09-27T00:00:00.000Z',
        updatedAt: '2025-09-27T00:00:00.000Z'
      }
    });

    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200,
      body: {}
    });

    cy.login(false);

    // On pars vers /me
    cy.contains('Account').click();

    cy.get('body').should('contain', 'Delete my account');
    cy.get('button').should('contain', 'Detail');
    cy.get('button').contains('Detail').click();
    cy.get('body').should('contain', 'Your account has been deleted !');

    cy.url().should('include', '/');
  });

});
