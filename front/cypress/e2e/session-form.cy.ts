describe('Session Form Component', () => {
  it('devrait créer une nouvelle session', () => {
    cy.intercept('GET', '/api/session', {
      body: []
    }).as('sessionsRequest');

    cy.loadTeachers();

    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: {
        id: 1,
        name: 'Nouvelle Session Yoga',
        description: 'Une session de yoga relaxante',
        date: '2025-09-27',
        teacher_id: 1,
        users: []
      }
    }).as('createSessionRequest');

    cy.loginAsAdmin();

    cy.contains('Create').click();

    cy.url().should('include', '/sessions/create');

    cy.get('input[formControlName="name"]').type('Nouvelle Session Yoga trop bien !');
    cy.get('input[formControlName="date"]').type('2025-09-27');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').contains('Edouard Plantevin').click();
    cy.get('textarea[formControlName="description"]').type('Une session de yoga relaxante');

    cy.get('button[type="submit"]').click();

    cy.wait('@createSessionRequest');

    // Vérifier qu'on est redirigé vers la liste des sessions
    cy.url().should('include', '/sessions');
  });
});
