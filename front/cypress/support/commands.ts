// ***********************************************
// Custom commands for Yoga App
// ***********************************************

declare namespace Cypress {
  interface Chainable<Subject = any> {
    loginAsAdmin(): typeof loginAsAdmin;
    loadSessions(): typeof loadSessions;
    loadTeachers(): typeof loadTeachers;
  }
}

function loginAsAdmin(): void {
  cy.intercept('POST', '**/api/auth/login', {
    statusCode: 200,
    body: {
      id: 1,
      username: 'admin',
      firstName: 'Admin',
      lastName: 'User',
      admin: true
    },
  }).as('loginRequest');

  cy.visit('/login');

  cy.get('input[formControlName=email]').type("test@test.com");
  cy.get('input[formControlName=password]').type("password");
  cy.get('button[type="submit"]').click();

  cy.wait('@loginRequest');
  cy.url().should('include', '/sessions');
}

function loadSessions(): void {
  cy.intercept('GET', '/api/session', {
    statusCode: 200,
    body: [
      {
        id: 1,
        name: 'Yoga Session',
        description: 'A relaxing yoga session',
        date: '2024-01-15',
        teacher_id: 1,
        users: []
      }
    ]
  }).as('sessionsRequest');
}

function loadTeachers(): void {
  cy.intercept('GET', '**/api/teacher', {
    statusCode: 200,
    body: [
      {
        id: 1,
        firstName: 'Edouard',
        lastName: 'Plantevin'
      }
    ]
  }).as('teachersRequest');
}

Cypress.Commands.add('loginAsAdmin', loginAsAdmin);
Cypress.Commands.add('loadSessions', loadSessions);
Cypress.Commands.add('loadTeachers', loadTeachers);
