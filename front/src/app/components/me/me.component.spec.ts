import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../services/session.service';
import { MeComponent } from './me.component';
import { of } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userServiceSpy: any;
  let routerMock: any;
  let matSnackBarMock: any;

  const mockSessionService = {
    sessionInformation: {
      token: 'token123',
      type: 'Bearer',
      id: 1,
      username: 'user',
      firstName: 'Edouard',
      lastName: 'Plantevin',
      admin: false
    },
    logOut: jest.fn()
  };

  beforeEach(async () => {
    userServiceSpy = {
      getById: jest.fn().mockReturnValue(of({ id: 1, email: 'user@test.com' })),
      delete: jest.fn().mockReturnValue(of({}))
    };
    routerMock = { navigate: jest.fn() };
    matSnackBarMock = { open: jest.fn() };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        NoopAnimationsModule
      ],
      declarations: [MeComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: userServiceSpy },
        { provide: Router, useValue: routerMock },
        { provide: MatSnackBar, useValue: matSnackBarMock }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait afficher les informations de l'utilisateur", () => {
    const mockUser = {
      ...mockSessionService.sessionInformation,
      email: 'edouard@test.com'
    };

    userServiceSpy.getById.mockReturnValue(of(mockUser));
    component.ngOnInit();
    fixture.detectChanges();

    expect(userServiceSpy.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);

    // Vérifie l'affichage
    const nameElement = fixture.debugElement.nativeElement.querySelector('p');
    expect(nameElement.textContent.trim()).toContain('Name: Edouard PLANTEVIN');

    const emailElement = fixture.debugElement.nativeElement.querySelectorAll('p')[1];
    expect(emailElement.textContent.trim()).toContain('Email: edouard@test.com');
  });

  it("devrait déconnecter l'utilisateur et naviguer vers /", () => {
    component.delete();

    expect(userServiceSpy.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarMock.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });
});
