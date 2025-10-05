import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';
import { ListComponent } from './list.component';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;
  let sessionApiSpy: any;

  const mockSessionServiceAdmin = {
    sessionInformation: {
      token: 'token123',
      type: 'Bearer',
      id: 1,
      username: 'admin',
      firstName: 'Admin',
      lastName: 'User',
      admin: true
    }
  };

  const mockSessions = [
    {
      id: 1,
      name: 'Session de Yoga',
      description: 'Session relaxante',
      date: new Date('2025-09-27'),
      teacher_id: 1,
      users: [1, 2]
    },
    {
      id: 2,
      name: 'Session de Méditation',
      description: 'Session de méditation guidée',
      date: new Date('2025-09-28'),
      teacher_id: 2,
      users: [1, 3]
    }
  ];

  beforeEach(async () => {
    sessionApiSpy = { all: jest.fn().mockReturnValue(of(mockSessions)) };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        NoopAnimationsModule
      ],
      declarations: [ListComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionServiceAdmin },
        { provide: SessionApiService, useValue: sessionApiSpy }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait afficher la liste des sessions", () => {
    expect(sessionApiSpy.all).toHaveBeenCalled();
    expect(component.sessions$).toBeDefined();
  });

  it("devrait vérifier que le bouton Create est visible pour un admin", () => {
    const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
    expect(createButton).toBeTruthy();
    expect(createButton.textContent.trim()).toContain('Create');
  });

  it("devrait vérifier que le bouton Edit est visible pour un admin", () => {
    const updateButton = fixture.debugElement.nativeElement.querySelector('button[ng-reflect-router-link="update,1"]');
    expect(updateButton).toBeTruthy();
    expect(updateButton.textContent.trim()).toContain('Edit');
  });

  it("devrait vérifier que l'utilisateur non-admin ne peut pas voir le bouton Create", () => {
    if (component.user) {
      component.user.admin = false;
      fixture.detectChanges();

      const createButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="create"]');
      expect(createButton).toBeNull();
    }
  });
});
