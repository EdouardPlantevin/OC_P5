describe('Session Detail Component', () => {

  it('devrait afficher les détails d\'une session', () => {

    cy.loadSessions();
    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Yoga Session',
        description: 'A relaxing yoga session',
        date: '2025-09-27',
        teacher_id: 1,
        users: [],
        teacher: {
          id: 1,
          firstName: 'Edouard',
          lastName: 'Plantevin'
        }
      }
    });
    cy.loadTeachers();
    cy.loginAsAdmin();

    cy.url().should('include', '/sessions');

    cy.contains('button', 'Detail').click();
    cy.url().should('include', '/sessions/detail/1');

    cy.contains('Yoga Session').should('be.visible');
    cy.contains('A relaxing yoga session').should('be.visible');
  });
});
