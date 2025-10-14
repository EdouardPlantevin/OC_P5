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
    cy.login(true);

    cy.url().should('include', '/sessions');

    cy.contains('button', 'Detail').click();
    cy.url().should('include', '/sessions/detail/1');

    cy.contains('Yoga Session').should('be.visible');
    cy.contains('A relaxing yoga session').should('be.visible');
  });

  it('devrait permettre de revenir en arrière avec le bouton back', () => {
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
    cy.intercept('GET', '/api/teacher/1', {
      body: {
        id: 1,
        firstName: 'Edouard',
        lastName: 'Plantevin'
      }
    });
    cy.login(true);

    cy.url().should('include', '/sessions');
    cy.contains('button', 'Detail').click();
    cy.url().should('include', '/sessions/detail/1');

    cy.get('button[mat-icon-button]').contains('arrow_back').click();

    cy.url().should('include', '/sessions');
    cy.url().should('not.include', '/detail');
  });
});
