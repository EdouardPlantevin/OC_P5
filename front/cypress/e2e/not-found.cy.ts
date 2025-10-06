describe('Not Found Component (404)', () => {
  it('devrait afficher la page 404 pour une route inexistante', () => {
    cy.visit('/route-inexistante', { failOnStatusCode: false });
    cy.contains('Page not found !').should('be.visible');
  });
});
