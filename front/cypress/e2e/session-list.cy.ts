describe('Liste des sessions', () => {
  it('devrait afficher la liste des sessions', () => {
    cy.loadSessions();
    cy.loginAsAdmin();

    cy.contains('Yoga Session').should('be.visible');
  });

  it('devrait permettre à un admin de créer une nouvelle session', () => {

    cy.contains('Create').should('be.visible');
    cy.contains('Create').click();

    cy.url().should('include', '/sessions/create');
  });
});
